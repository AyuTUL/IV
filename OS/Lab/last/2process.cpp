#include <windows.h>
#include <tchar.h>
#include <iostream>

using namespace std;

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
    // Try to close the main window first (graceful shutdown)
    HWND hwnd = FindWindow(NULL, _T("Untitled - Notepad"));
    if (hwnd) {
        PostMessage(hwnd, WM_CLOSE, 0, 0);
        
        // Wait for the process to exit gracefully
        if (WaitForSingleObject(processInfo.hProcess, 3000) == WAIT_OBJECT_0) {
            cout << "Process closed gracefully." << endl;
            CloseHandles(processInfo);
            return true;
        }
    }

    // Force termination if graceful close failed
    if (::TerminateProcess(processInfo.hProcess, 1)) {
        WaitForSingleObject(processInfo.hProcess, INFINITE); // Wait until actually terminated
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

int main() {
    TCHAR processPath[] = _T("notepad.exe");
    PROCESS_INFORMATION processInfo = {0};

    cout << "Starting Notepad..." << endl;
    if (!StartProcess(processPath, processInfo)) {
        return 1;
    }

    cout << "Waiting 5 seconds..." << endl;
    Sleep(5000);

    cout << "Attempting to close Notepad..." << endl;
    if (!TerminateProcessGracefully(processInfo)) {
        CloseHandles(processInfo);
        return 1;
    }

    cout << "Operation completed." << endl;
    return 0;
}