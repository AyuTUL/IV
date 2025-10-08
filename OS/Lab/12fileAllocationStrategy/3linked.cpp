// Lab 12.3: WAP to simulate linked file allocation strategy

#include <iostream>
#include <vector>
#include <string>
#include <algorithm>
using namespace std;

struct File
{
    string name;
    int startBlock;
    int length;

    File(string n, int start, int len) : name(n), startBlock(start), length(len) {}
};

class LinkedFileAllocation
{
private:
    vector<int> disk;    // 0 = free, 1 = allocated
    vector<int> nextPtr; // Stores pointer to next block (-1 means end of file)
    vector<File> files;
    int diskSize;

public:
    LinkedFileAllocation(int size) : diskSize(size)
    {
        disk.resize(size, 0);     // Initialize all blocks as free (0)
        nextPtr.resize(size, -1); // Initialize all pointers as -1 (end)
    }

    // Display current disk status
    void displayDisk()
    {
        cout << endl
             << "Disk Status : ";
        for (int i = 0; i < diskSize; i++)
            cout << disk[i] << " ";
        cout << endl;
        cout << "Block Pointers : ";
        for (int i = 0; i < diskSize; i++)
            if (disk[i] == 1)
                cout << nextPtr[i] << " ";
            else
                cout << "- ";
        cout << endl;
    }

    // Find free blocks for linked allocation
    vector<int> findFreeBlocks(int numBlocks)
    {
        vector<int> freeBlocks;
        for (int i = 0; i < diskSize && freeBlocks.size() < numBlocks; i++)
            if (disk[i] == 0)
                freeBlocks.push_back(i);
        return freeBlocks;
    }

    // Allocate file using linked allocation
    bool allocateFile(string fileName, int fileSize)
    {
        // Check if file already exists
        for (const auto &file : files)
            if (file.name == fileName)
            {
                cout << "Error : File '" << fileName << "' already exists" << endl;
                return false;
            }

        // Find free blocks
        vector<int> freeBlocks = findFreeBlocks(fileSize);
        if (freeBlocks.size() < fileSize)
        {
            cout << "Allocation not possible - insufficient free blocks" << endl;
            return false;
        }

        // Allocate blocks and create links
        for (int i = 0; i < fileSize; i++)
        {
            disk[freeBlocks[i]] = 1;
            if (i < fileSize - 1)
                nextPtr[freeBlocks[i]] = freeBlocks[i + 1]; // Point to next block
            else
                nextPtr[freeBlocks[i]] = -1; // End of file
        }

        // Add file to list
        files.push_back(File(fileName, freeBlocks[0], fileSize));

        cout << "File '" << fileName << "' allocated successfully starting at block " << freeBlocks[0] << endl
             << "Block chain : ";
        for (int i = 0; i < fileSize; i++)
        {
            cout << freeBlocks[i];
            if (i < fileSize - 1)
                cout << " -> ";
        }
        cout << " -> END" << endl;

        return true;
    }

    // Deallocate file
    bool deallocateFile(string fileName)
    {
        auto it = find_if(files.begin(), files.end(),
                          [&fileName](const File &f)
                          { return f.name == fileName; });

        if (it == files.end())
        {
            cout << "Error : File '" << fileName << "' not found!" << endl;
            return false;
        }

        // Follow the chain and free all blocks
        int currentBlock = it->startBlock;
        while (currentBlock != -1)
        {
            int nextBlock = nextPtr[currentBlock];
            disk[currentBlock] = 0;
            nextPtr[currentBlock] = -1;
            currentBlock = nextBlock;
        }

        cout << "File '" << fileName << "' deallocated successfully." << endl;
        files.erase(it);
        return true;
    }

    // Display all files
    void displayFiles()
    {
        cout << endl
             << "Allocated Files :" << endl
             << "File Name\tStart Block\tLength\tBlock Chain" << endl
             << string(50, '-') << endl;

        for (const auto &file : files)
        {
            cout << file.name << "\t\t" << file.startBlock << "\t\t" << file.length << "\t";

            // Display the chain
            int currentBlock = file.startBlock;
            int count = 0;
            while (currentBlock != -1 && count < file.length)
            {
                cout << currentBlock;
                currentBlock = nextPtr[currentBlock];
                if (currentBlock != -1)
                    cout << "->";
                count++;
            }
            cout << "->END" << endl;
        }

        if (files.empty())
            cout << "No files allocated." << endl;
    }
};

int main()
{
    int diskSize;
    cout << "---Linked File Allocation---" << endl
         << "Enter disk size : ";
    cin >> diskSize;

    LinkedFileAllocation lfa(diskSize);

    int choice;
    string fileName;
    int fileSize;

    do
    {
        cout << "---Linked File Allocation---" << endl
             << "   1. Allocate File" << endl
             << "   2. Deallocate File" << endl
             << "   3. Display Files" << endl
             << "   4. Display Disk Status" << endl
             << "   5. Exit" << endl
             << "Enter your choice : ";
        cin >> choice;

        switch (choice)
        {
        case 1:
            cout << "Enter file name : ";
            cin >> fileName;
            cout << "Enter file size (in blocks) : ";
            cin >> fileSize;
            lfa.allocateFile(fileName, fileSize);
            break;

        case 2:
            cout << "Enter file name to deallocate : ";
            cin >> fileName;
            lfa.deallocateFile(fileName);
            break;

        case 3:
            lfa.displayFiles();
            break;

        case 4:
            lfa.displayDisk();
            break;

        case 5:
            cout << "Exiting program." << endl;
            break;

        default:
            cout << "Invalid choice. Please choose from 1-5." << endl;
        }
    } while (choice != 5);

    return 0;
}