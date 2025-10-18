// Lab 12.1: WAP to simulate sequential file allocation strategy

#include <iostream>
#include <vector>
#include <string>
#include <algorithm>
using namespace std;

struct File
{
    string name;
    int startBlock, length;

    File(string n, int start, int len) : name(n), startBlock(start), length(len) {}
};

class SequentialFileAllocation
{
private:
    vector<int> disk; // 0 = free, 1 = allocated
    vector<File> files;
    int diskSize;

public:
    SequentialFileAllocation(int size) : diskSize(size)
    {
        disk.resize(size, 0); // Initialize all blocks as free (0)
    }

    // Display current disk status
    void displayDisk()
    {
        cout << endl
             << "Disk Status : ";
        for (int i = 0; i < diskSize; i++)
            cout << disk[i] << " "; // Direct output since disk[i] is already 0 or 1
        cout << endl;
    }

    // Find first fit for sequential allocation
    int findFirstFit(int fileSize)
    {
        for (int i = 0; i <= diskSize - fileSize; i++)
        {
            bool canAllocate = true;
            for (int j = i; j < i + fileSize; j++)
                if (disk[j])
                {
                    canAllocate = false;
                    break;
                }
            if (canAllocate)
                return i;
        }
        return -1; // No space found
    }

    // Allocate file
    bool allocateFile(string fileName, int fileSize)
    {
        // Check if file already exists
        for (const auto &file : files)
            if (file.name == fileName)
            {
                cout << "Error: File '" << fileName << "' already exists" << endl;
                return false;
            }

        int startBlock = findFirstFit(fileSize);
        if (startBlock == -1)
        {
            cout << "Allocation not possible - insufficient contiguous space." << endl;
            return false;
        }

        // Allocate blocks (similar to user's approach)
        for (int i = startBlock; i < startBlock + fileSize; i++)
            disk[i] = 1;

        // Add file to list
        files.push_back(File(fileName, startBlock, fileSize));
        cout << "File '" << fileName << "' allocated successfully at blocks "
             << startBlock << " to " << (startBlock + fileSize - 1) << endl;
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

        // Free blocks
        for (int i = it->startBlock; i < it->startBlock + it->length; i++)
            disk[i] = 0;

        cout << "File '" << fileName << "' deallocated successfully." << endl;
        files.erase(it);
        return true;
    }

    // Display all files
    void displayFiles()
    {
        cout << endl
             << "Allocated Files :" << endl
             << "+---------------+-------------+--------+-----------+" << endl
             << "| File Name     | Start Block | Length | End Block |" << endl
             << "+---------------+-------------+--------+-----------+" << endl;

        for (const auto &file : files)
        {
            cout << "| " << file.name;
            // Pad file name to 13 characters
            for (int i = file.name.length(); i < 13; i++)
                cout << " ";

            cout << " | " << file.startBlock;
            // Pad start block to 11 characters
            int startLen = to_string(file.startBlock).length();
            for (int i = startLen; i < 11; i++)
                cout << " ";

            cout << " | " << file.length;
            // Pad length to 6 characters
            int lenLen = to_string(file.length).length();
            for (int i = lenLen; i < 6; i++)
                cout << " ";

            cout << " | " << (file.startBlock + file.length - 1);
            // Pad end block to 9 characters
            int endLen = to_string(file.startBlock + file.length - 1).length();
            for (int i = endLen; i < 9; i++)
                cout << " ";

            cout << " |" << endl;
        }

        cout << "+---------------+-------------+--------+-----------+" << endl;

        if (files.empty())
            cout << "| No files allocated.                              |" << endl
                 << "+---------------+-------------+--------+-----------+" << endl;
    }
};

int main()
{
    int diskSize;
    cout << "---Sequential File Allocation---" << endl
         << "Enter disk size : ";
    cin >> diskSize;

    SequentialFileAllocation sfa(diskSize);

    int choice, fileSize;
    string fileName;

    do
    {
        cout << endl
             << "---Sequential File Allocation---" << endl
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
            sfa.allocateFile(fileName, fileSize);
            break;

        case 2:
            cout << "Enter file name to deallocate : ";
            cin >> fileName;
            sfa.deallocateFile(fileName);
            break;

        case 3:
            sfa.displayFiles();
            break;

        case 4:
            sfa.displayDisk();
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