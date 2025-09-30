// Lab 8.1: WAP to simulate MVT & MFT memory management techniques
#include <iostream>
#include <vector>
using namespace std;

int main()
{
    int memSize, blockSize, n;

    cout << "Enter total memory size: ";
    cin >> memSize;
    cout << "Enter block size (for MFT): ";
    cin >> blockSize;
    cout << "Enter number of processes: ";
    cin >> n;

    vector<int> processes(n);
    cout << "Enter memory required by each process: ";
    for (int i = 0; i < n; i++)
    {
        cin >> processes[i];
    }

    // MFT Technique
    cout << "\n=== MFT (Fixed Partitioning) ===" << endl;
    int totalBlocks = memSize / blockSize;
    int usedBlocks = 0;
    int internalFragmentation = 0;

    cout << "Total blocks available: " << totalBlocks << " (each " << blockSize << " KB)" << endl;
    cout << string(40, '-') << endl;

    for (int i = 0; i < n && usedBlocks < totalBlocks; i++)
    {
        if (processes[i] <= blockSize)
        {
            cout << "Process P" << (i + 1) << " (" << processes[i] << " KB) -> Block " << (usedBlocks + 1);
            int waste = blockSize - processes[i];
            internalFragmentation += waste;
            if (waste > 0)
                cout << " [Waste: " << waste << " KB]";
            cout << endl;
            usedBlocks++;
        }
        else
        {
            cout << "Process P" << (i + 1) << " (" << processes[i] << " KB) -> TOO LARGE for block" << endl;
        }
    }

    // Handle remaining processes that couldn't be allocated
    for (int i = usedBlocks; i < n; i++)
    {
        if (processes[i] <= blockSize)
        {
            cout << "Process P" << (i + 1) << " (" << processes[i] << " KB) -> NO BLOCKS AVAILABLE" << endl;
        }
    }

    cout << string(40, '-') << endl;
    cout << "Used blocks: " << usedBlocks << "/" << totalBlocks << endl;
    cout << "Total internal fragmentation: " << internalFragmentation << " KB" << endl;
    cout << "External fragmentation: " << (totalBlocks - usedBlocks) * blockSize << " KB" << endl;

    // MVT Technique
    cout << "\n=== MVT (Dynamic Partitioning) ===" << endl;
    int remainingMemory = memSize;
    int allocatedProcesses = 0;

    cout << "Total memory available: " << memSize << " KB" << endl;
    cout << string(40, '-') << endl;

    for (int i = 0; i < n; i++)
    {
        if (processes[i] <= remainingMemory)
        {
            cout << "Process P" << (i + 1) << " (" << processes[i] << " KB) -> ALLOCATED";
            remainingMemory -= processes[i];
            cout << " [Remaining: " << remainingMemory << " KB]" << endl;
            allocatedProcesses++;
        }
        else
        {
            cout << "Process P" << (i + 1) << " (" << processes[i] << " KB) -> CANNOT ALLOCATE";
            cout << " [Need: " << processes[i] << ", Available: " << remainingMemory << "]" << endl;
        }
    }

    cout << string(40, '-') << endl;
    cout << "Allocated processes: " << allocatedProcesses << "/" << n << endl;
    cout << "Used memory: " << (memSize - remainingMemory) << " KB" << endl;
    cout << "External fragmentation: " << remainingMemory << " KB" << endl;
    cout << "Internal fragmentation: 0 KB (exact fit)" << endl;

    return 0;
}
