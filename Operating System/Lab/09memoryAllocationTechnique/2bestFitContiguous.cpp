// Lab 9.2: WAP to simulate best fit contiguous memory allocation technique
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
         << "---Best Fit Memory Allocation---" << endl;

    for (int i = 0; i < m; i++)
    {
        bool isAllocated = false;
        int bestIndex = -1;
        int minWaste = INT_MAX;

        // Find the best fit block (smallest block that can accommodate the process)
        for (int j = 0; j < n; j++)
            if (allocated[j] == "Free" && block[j] >= process[i])
            {
                int waste = block[j] - process[i];
                if (waste < minWaste)
                {
                    minWaste = waste;
                    bestIndex = j;
                }
            }

        if (bestIndex != -1)
        {
            cout << "Process P" << (i + 1) << " (" << process[i] << " KB) allocated to Block " << (bestIndex + 1) << endl;
            block[bestIndex] -= process[i];
            allocated[bestIndex] = "P" + to_string(i + 1);
            isAllocated = true;
        }

        if (!isAllocated)
            cout << "Process P" << (i + 1) << " (" << process[i] << " KB) could not be allocated" << endl;
    }

    printFinalMemoryState(block, allocated, originalBlocks);

    return 0;
}