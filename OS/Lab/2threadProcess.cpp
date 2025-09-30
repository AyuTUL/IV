#include <windows.h>
#include <tchar.h>
#include <iostream>
#include <string>

using namespace std;

/* ---------------- Process Management Section ---------------- */

// Forward declaration
void CloseHandles(PROCESS_INFORMATION& processInfo);

bool StartProcess(LPCTSTR processPath, PROCESS_INFORMATION& processInfo) {
    STARTUPINFO startupInfo = { sizeof(startupInfo) };

    if (!CreateProcess(
        NULL,
        (LPTSTR)processPath,
        NULL,
        NULL,
        FALSE,
        0,
        NULL,
        NULL,
        &startupInfo,
        &processInfo))
    {
        cerr << "CreateProcess failed (" << GetLastError() << ")." << endl;
        return false;
    }
    cout << "Process started successfully (PID: " << processInfo.dwProcessId << ")." << endl;
    return true;
}

bool TerminateProcessGracefully(PROCESS_INFORMATION& processInfo) {
    HWND hwnd = FindWindow(NULL, _T("Untitled - Notepad"));
    if (hwnd) {
        PostMessage(hwnd, WM_CLOSE, 0, 0);

        if (WaitForSingleObject(processInfo.hProcess, 3000) == WAIT_OBJECT_0) {
            cout << "Process closed gracefully." << endl;
            CloseHandles(processInfo);
            return true;
        }
    }

    if (::TerminateProcess(processInfo.hProcess, 1)) {
        WaitForSingleObject(processInfo.hProcess, INFINITE);
        cout << "Process force-terminated." << endl;
        CloseHandles(processInfo);
        return true;
    }

    cerr << "Failed to terminate process (" << GetLastError() << ")." << endl;
    return false;
}

void CloseHandles(PROCESS_INFORMATION& processInfo) {
    if (processInfo.hProcess) CloseHandle(processInfo.hProcess);
    if (processInfo.hThread) CloseHandle(processInfo.hThread);
    ZeroMemory(&processInfo, sizeof(processInfo));
}

/* ---------------- Thread Management Section ---------------- */

volatile bool threadRunning = false;

DWORD WINAPI WorkerThread(LPVOID lpParam) {
    threadRunning = true;
    int threadId = *(int*)lpParam;
    delete (int*)lpParam;

    cout << "Thread " << threadId << " started working..." << endl;

    for (int i = 1; i <= 10 && threadRunning; i++) {
        cout << "Thread " << threadId << " progress: " << i << "/10" << endl;
        Sleep(1000);
    }

    if (threadRunning) {
        cout << "Thread " << threadId << " completed successfully" << endl;
    } else {
        cout << "Thread " << threadId << " was stopped gracefully" << endl;
    }

    return 0;
}

HANDLE CreateNewThread(int threadId) {
    DWORD threadID;
    int* param = new int(threadId);

    HANDLE hThread = CreateThread(
        NULL,
        0,
        WorkerThread,
        param,
        0,
        &threadID
    );

    if (hThread == NULL) {
        cerr << "Error creating thread: " << GetLastError() << endl;
        delete param;
        return NULL;
    }

    cout << "Thread " << threadId << " created successfully (ID: " << threadID << ")" << endl;
    return hThread;
}

bool StopThreadGracefully(HANDLE hThread) {
    threadRunning = false;

    DWORD result = WaitForSingleObject(hThread, 3000);

    if (result == WAIT_OBJECT_0) {
        cout << "Thread stopped gracefully" << endl;
        CloseHandle(hThread);
        return true;
    }

    if (TerminateThread(hThread, 0)) {
        cerr << "Thread terminated forcefully" << endl;
        CloseHandle(hThread);
        return true;
    }

    cerr << "Failed to stop thread: " << GetLastError() << endl;
    return false;
}

/* ---------------- Unified Main ---------------- */

int main() {
    cout << "Process Management Demo :\n" << endl;

    // --- Process Demo ---
    cout << "[1] Starting Notepad process..." << endl;
    TCHAR processPath[] = _T("notepad.exe");
    PROCESS_INFORMATION processInfo = {0};

    if (!StartProcess(processPath, processInfo)) {
        return 1;
    }

    cout << "Waiting 5 seconds before attempting to close Notepad..." << endl;
    Sleep(5000);

    cout << "Attempting to close Notepad..." << endl;
    TerminateProcessGracefully(processInfo);

    cout << endl<<"Thread Management Demo :" << endl;
    cout << "\n[2] Starting worker thread..." << endl;
    HANDLE hThread = CreateNewThread(1);
    if (hThread == NULL) {
        return 1;
    }

    cout << "Main thread waiting 3 seconds before stopping worker thread..." << endl;
    Sleep(3000);

    cout << "Stopping worker thread..." << endl;
    StopThreadGracefully(hThread);

    return 0;
}
