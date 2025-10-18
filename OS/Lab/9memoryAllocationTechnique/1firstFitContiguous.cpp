// Lab 9.1: WAP to simulate first fit contiguous memory allocation technique
#include <iostream>
#include <vector>
#include <iomanip>
#include <string>
using namespace std;

void printFinalMemoryState(const vector<int> &blocks, const vector<string> &allocated, const vector<int> &originalBlocks)
{
    cout << endl
         << "Final Memory Allocation State :" << endl;

    for (int i = 0; i < blocks.size(); i++)
    {
        cout << "+------+" << endl;
        string displayName = (allocated[i] == "Free") ? "--" : allocated[i];
        if (allocated[i] == "Free")
            cout << "|  " << setw(2) << displayName << "  | " << blocks[i] << " KB" << endl;
        else
        {
            int occupied = originalBlocks[i] - blocks[i];
            cout << "|  " << setw(2) << displayName << "  | " << occupied << "/" << originalBlocks[i] << " KB" << endl;
        }
    }
    cout << "+------+" << endl;
}

int main()
{
	system("color f0");
    int n, m;
    cout << "Enter number of memory blocks : ";
    cin >> n;

    vector<int> block(n);
    vector<int> originalBlocks(n);
    vector<string> allocated(n, "Free");

    cout << "Enter sizes of blocks : ";
    for (int i = 0; i < n; i++)
    {
        cin >> block[i];
        originalBlocks[i] = block[i]; // Store original sizes
    }

    cout << "Enter number of processes : ";
    cin >> m;

    vector<int> process(m);
    cout << "Enter sizes of processes : ";
    for (int i = 0; i < m; i++)
        cin >> process[i];

    cout << endl
         << "---First Fit Memory Allocation---" << endl;

    for (int i = 0; i < m; i++)
    {
        bool isAllocated = false;

        for (int j = 0; j < n; j++)
            // Only check blocks that are still free
            if (allocated[j] == "Free" && block[j] >= process[i])
            {
                cout << "Process P" << (i + 1) << " (" << process[i] << " KB) allocated to Block " << (j + 1) << endl;
                block[j] -= process[i];
                allocated[j] = "P" + to_string(i + 1);
                isAllocated = true;
                break;
            }

        if (!isAllocated)
            cout << "Process P" << (i + 1) << " (" << process[i] << " KB) could not be allocated" << endl;
    }

    printFinalMemoryState(block, allocated, originalBlocks);

    return 0;
}
