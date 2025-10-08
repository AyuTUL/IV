// Lab 8.2: WAP to simulate paging technique of memory management
#include <iostream>
#include <vector>
#include <iomanip>
using namespace std;

struct Process
{
    int id;
    int size;
    vector<int> pages;

    Process(int pid, int psize) : id(pid), size(psize) {}
};

class PagingSystem
{
private:
    int pageSize;
    int totalMemory;
    int totalPages;
    vector<bool> pageTable;     // true = occupied, false = free
    vector<int> frameToProcess; // which process occupies each frame

public:
    PagingSystem(int memory, int pSize) : totalMemory(memory), pageSize(pSize)
    {
        totalPages = totalMemory / pageSize;
        pageTable.resize(totalPages, false);
        frameToProcess.resize(totalPages, -1);
    }

    void displayMemoryInfo()
    {
        cout << endl
             << "---PAGING MEMORY MANAGEMENT---" << endl
             << "Total Memory Size : " << totalMemory << " KB" << endl
             << "Page Size         : " << pageSize << " KB" << endl
             << "Total Pages       : " << totalPages << endl
             << string(30, '-') << endl;
    }

    bool allocateProcess(Process &process)
    {
        int pagesNeeded = (process.size + pageSize - 1) / pageSize; // Ceiling division

        cout << endl
             << "Process P" << process.id << " (Size : " << process.size
             << " KB) needs " << pagesNeeded << " pages" << endl;

        // Check if enough free pages available
        int freePages = 0;
        for (int i = 0; i < totalPages; i++)
        {
            if (!pageTable[i])
                freePages++;
        }

        if (freePages < pagesNeeded)
        {
            cout << "ERROR : Not enough free pages! Available : " << freePages
                 << ", Required : " << pagesNeeded << endl;
            return false;
        }

        // Allocate pages (non-contiguous allocation)
        vector<int> allocatedPages;
        for (int i = 0; i < totalPages && allocatedPages.size() < pagesNeeded; i++)
            if (!pageTable[i])
            {
                pageTable[i] = true;
                frameToProcess[i] = process.id;
                allocatedPages.push_back(i);
            }

        process.pages = allocatedPages;

        cout << "Process P" << process.id << " allocated to pages : ";
        for (int page : allocatedPages)
            cout << page << " ";
        cout << endl;

        return true;
    }

    void displayPageTable()
    {
        cout << endl
             << "---PAGE TABLE---" << endl
             << "+-------+--------+----------+" << endl
             << "| Page  | Status | Process  |" << endl
             << "+-------+--------+----------+" << endl;

        for (int i = 0; i < totalPages; i++)
        {
            cout << "|" << setw(6) << i << " |";
            if (pageTable[i])
                cout << setw(7) << "BUSY" << " |" << setw(9) << ("P" + to_string(frameToProcess[i])) << " |";
            else
                cout << setw(7) << "FREE" << " |" << setw(9) << "-" << " |";
            cout << endl;
        }
        cout << "+-------+--------+----------+" << endl;
    }

    void displayMemoryMap()
    {
        cout << endl
             << "---MEMORY MAP---" << endl;

        for (int i = 0; i < totalPages; i++)
        {
            int startAddr = i * pageSize;
            int endAddr = startAddr + pageSize - 1;

            cout << "+--------+" << endl;
            if (pageTable[i])
                cout << "|   P" << frameToProcess[i] << "   | " << startAddr << "-" << endAddr << " KB" << endl;
            else
                cout << "|  FREE  | " << startAddr << "-" << endAddr << " KB" << endl;
        }
        cout << "+--------+" << endl;
    }

    void displayStatistics()
    {
        int usedPages = 0;
        int freePages = 0;

        for (int i = 0; i < totalPages; i++)
            if (pageTable[i])
                usedPages++;
            else
                freePages++;

        cout << endl
             << "---MEMORY STATISTICS---" << endl
             << "Total Pages       : " << totalPages << endl
             << "Used Pages        : " << usedPages << endl
             << "Free Pages        : " << freePages << endl
             << "Memory Utilization: " << fixed << setprecision(2)
             << (double)usedPages / totalPages * 100 << "%" << endl
             << "Internal Fragmentation : Minimal (fixed page size)" << endl
             << "External Fragmentation : None (non-contiguous allocation)" << endl;
    }
};

int main()
{
    int totalMemory, pageSize, numProcesses;

    cout << "Enter total memory size (KB) : ";
    cin >> totalMemory;
    cout << "Enter page size (KB) : ";
    cin >> pageSize;
    cout << "Enter number of processes : ";
    cin >> numProcesses;

    PagingSystem pagingSystem(totalMemory, pageSize);
    pagingSystem.displayMemoryInfo();

    vector<Process> processes;

    // Input process information
    for (int i = 0; i < numProcesses; i++)
    {
        int processSize;
        cout << "Enter size of Process P" << (i + 1) << " (KB) : ";
        cin >> processSize;
        processes.push_back(Process(i + 1, processSize));
    }

    cout << endl<<"---PROCESS ALLOCATION---" << endl;

    // Allocate each process
    for (auto &process : processes)
        pagingSystem.allocateProcess(process);

    // Display results
    pagingSystem.displayPageTable();
    pagingSystem.displayMemoryMap();
    pagingSystem.displayStatistics();

    return 0;
}