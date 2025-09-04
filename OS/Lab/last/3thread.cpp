#include <windows.h>
#include <iostream>
#include <string>

using namespace std;

// Thread control flag
volatile bool threadRunning = false;

// Thread function prototype
DWORD WINAPI WorkerThread(LPVOID lpParam);

// Function to create a thread
HANDLE CreateNewThread(int threadId) {
    DWORD threadID;
    int* param = new int(threadId);
    
    HANDLE hThread = CreateThread(
        NULL,                   // Default security attributes
        0,                      // Default stack size
        WorkerThread,           // Thread function
        param,                  // Parameter to thread function
        0,                      // Default creation flags
        &threadID              // Returns thread identifier
    );
    
    if (hThread == NULL) {
        cerr << "Error creating thread: " << GetLastError() << endl;
        delete param;
        return NULL;
    }
    
    cout << "Thread " << threadId << " created successfully (ID: " << threadID << ")" << endl;
    return hThread;
}

// Worker thread function
DWORD WINAPI WorkerThread(LPVOID lpParam) {
    threadRunning = true;
    int threadId = *(int*)lpParam;
    delete (int*)lpParam;  // Clean up the parameter memory
    
    cout << "Thread " << threadId << " started working..." << endl;
    
    for (int i = 1; i <= 10 && threadRunning; i++) {
        cout << "Thread " << threadId << " progress: " << i << "/10" << endl;
        Sleep(1000);  // Simulate work
    }
    
    if (threadRunning) {
        cout << "Thread " << threadId << " completed successfully" << endl;
    } else {
        cout << "Thread " << threadId << " was stopped gracefully" << endl;
    }
    
    return 0;
}

// Function to stop thread gracefully
bool StopThreadGracefully(HANDLE hThread) {
    threadRunning = false;  // Signal thread to stop
    
    // Wait for thread to finish (with timeout)
    DWORD result = WaitForSingleObject(hThread, 3000);
    
    if (result == WAIT_OBJECT_0) {
        cout << "Thread stopped gracefully" << endl;
        CloseHandle(hThread);
        return true;
    }
    
    // If thread didn't stop, force termination
    if (TerminateThread(hThread, 0)) {
        cerr << "Thread terminated forcefully" << endl;
        CloseHandle(hThread);
        return true;
    }
    
    cerr << "Failed to stop thread: " << GetLastError() << endl;
    return false;
}

int main() {
    cout << "=== Thread Management Demonstration ===" << endl;
    
    // Create a thread
    cout << "\nCreating worker thread..." << endl;
    HANDLE hThread = CreateNewThread(1);
    if (hThread == NULL) {
        return 1;
    }
    
    // Let the thread run for 3 seconds
    cout << "\nMain thread waiting for 3 seconds..." << endl;
    Sleep(3000);
    
    // Stop the thread
    cout << "\nStopping worker thread..." << endl;
    if (!StopThreadGracefully(hThread)) {
        CloseHandle(hThread);
        return 1;
    }
    
    cout << "\nProgram completed successfully" << endl;
    return 0;
}