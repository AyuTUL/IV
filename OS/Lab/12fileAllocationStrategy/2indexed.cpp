// Lab 12.2: WAP to simulate indexed file allocation strategy

#include <iostream>
#include <vector>
#include <string>
#include <algorithm>
using namespace std;

struct File
{
    string name;
    int indexBlock, fileSize;
    vector<int> dataBlocks;

    File(string n, int idx, vector<int> blocks, int size)
        : name(n), indexBlock(idx), dataBlocks(blocks), fileSize(size) {}
};

class IndexedFileAllocation
{
private:
    vector<int> disk; // 0 = free, 1 = data block, 2 = index block
    vector<File> files;
    int diskSize;

public:
    IndexedFileAllocation(int size) : diskSize(size)
    {
        disk.resize(size, 0); // Initialize all blocks as free (0)
    }

    // Display current disk status
    void displayDisk()
    {
        cout << endl
             << "Disk Status : ";
        for (int i = 0; i < diskSize; i++)
            if (disk[i] == 0)
                cout << "0 ";
            else if (disk[i] == 1)
                cout << "D ";
            else if (disk[i] == 2)
                cout << "I ";
        cout << endl
             << "Legend : 0=Free, D=Data Block, I=Index Block" << endl;
    }

    // Find free blocks for data
    vector<int> findFreeBlocks(int numBlocks)
    {
        vector<int> freeBlocks;
        for (int i = 0; i < diskSize && freeBlocks.size() < numBlocks; i++)
            if (disk[i] == 0)
                freeBlocks.push_back(i);
        return freeBlocks;
    }

    // Find a free block for index
    int findFreeIndexBlock()
    {
        for (int i = 0; i < diskSize; i++)
            if (disk[i] == 0)
                return i;
        return -1;
    }

    // Allocate file using indexed allocation
    bool allocateFile(string fileName, int fileSize)
    {
        // Check if file already exists
        for (const auto &file : files)
            if (file.name == fileName)
            {
                cout << "Error : File '" << fileName << "' already exists" << endl;
                return false;
            }

        // Need fileSize + 1 blocks (fileSize for data + 1 for index)
        if (fileSize + 1 > diskSize)
        {
            cout << "Allocation not possible - file too large" << endl;
            return false;
        }

        // Find free block for index first
        int indexBlock = findFreeIndexBlock();
        if (indexBlock == -1)
        {
            cout << "Allocation not possible - no free block for index" << endl;
            return false;
        }

        // Find free blocks for data, excluding the index block
        vector<int> dataBlocks;
        for (int i = 0; i < diskSize && dataBlocks.size() < fileSize; i++)
        {
            if (disk[i] == 0 && i != indexBlock)
            {
                dataBlocks.push_back(i);
            }
        }
        if (dataBlocks.size() < fileSize)
        {
            cout << "Allocation not possible - insufficient free blocks" << endl;
            return false;
        }

        // Allocate index block
        disk[indexBlock] = 2;

        // Allocate data blocks
        for (int block : dataBlocks)
            disk[block] = 1;

        // Add file to list
        files.push_back(File(fileName, indexBlock, dataBlocks, fileSize));

        cout << "File '" << fileName << "' allocated successfully at index block " << indexBlock << endl;
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

        // Free index block
        disk[it->indexBlock] = 0;

        // Free data blocks
        for (int block : it->dataBlocks)
            disk[block] = 0;

        cout << "File '" << fileName << "' deallocated successfully." << endl;
        files.erase(it);
        return true;
    }

    // Display all files
    void displayFiles()
    {
        cout << endl
             << "Allocated Files :" << endl
             << "+---------------+-------------+---------------------+-----------+" << endl
             << "| File Name     | Index Block | Data Blocks         | File Size |" << endl
             << "+---------------+-------------+---------------------+-----------+" << endl;

        for (const auto &file : files)
        {
            cout << "| " << file.name;
            // Pad file name to 13 characters
            for (int i = file.name.length(); i < 13; i++)
                cout << " ";

            cout << " | " << file.indexBlock;
            // Pad index block to 11 characters
            int idxLen = to_string(file.indexBlock).length();
            for (int i = idxLen; i < 11; i++)
                cout << " ";

            cout << " | ";
            // Build data blocks string
            string dataBlocksStr = "";
            for (int i = 0; i < file.dataBlocks.size(); i++)
            {
                dataBlocksStr += to_string(file.dataBlocks[i]);
                if (i < file.dataBlocks.size() - 1)
                    dataBlocksStr += ",";
            }
            cout << dataBlocksStr;
            // Pad data blocks to 19 characters
            for (int i = dataBlocksStr.length(); i < 19; i++)
                cout << " ";

            cout << " | " << file.fileSize;
            // Pad file size to 9 characters
            int sizeLen = to_string(file.fileSize).length();
            for (int i = sizeLen; i < 9; i++)
                cout << " ";

            cout << " |" << endl;
        }

        cout << "+---------------+-------------+---------------------+-----------+" << endl;

        if (files.empty())
            cout << "| No files allocated.                                         |" << endl
                 << "+---------------+-------------+---------------------+-----------+" << endl;
    }
};

int main()
{
    int diskSize;
    cout << "---Indexed File Allocation---" << endl
         << "Enter disk size : ";
    cin >> diskSize;

    IndexedFileAllocation ifa(diskSize);

    int choice, fileSize;
    string fileName;

    do
    {
        cout << endl
             << "---Indexed File Allocation---" << endl
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
            ifa.allocateFile(fileName, fileSize);
            break;

        case 2:
            cout << "Enter file name to deallocate : ";
            cin >> fileName;
            ifa.deallocateFile(fileName);
            break;

        case 3:
            ifa.displayFiles();
            break;

        case 4:
            ifa.displayDisk();
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