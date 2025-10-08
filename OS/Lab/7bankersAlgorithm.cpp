// Lab 7: WAP to simulate bankers algorithm for deadlock prevention
#include <iostream>
#include <vector>
using namespace std;

void printMatrix(const string &name, vector<vector<int>> &matrix, int n, int m)
{
    cout << name << " Matrix :" << endl
         << "     ";
    for (int j = 0; j < m; j++)
        cout << char('A' + j) << " ";
    cout << endl;
    for (int i = 0; i < n; i++)
    {
        cout << "P" << (i + 1) << " [ ";
        for (int j = 0; j < m; j++)
            cout << matrix[i][j] << " ";
        cout << "]" << endl;
    }
    cout << endl;
}

bool isSafe(vector<vector<int>> &allocation, vector<vector<int>> &max, vector<int> &available)
{
    int n = allocation.size(), m = available.size();
    vector<vector<int>> need(n, vector<int>(m));
    vector<bool> finished(n, false);
    vector<int> safeSeq;

    // Calculate Need matrix
    for (int i = 0; i < n; i++)
        for (int j = 0; j < m; j++)
            need[i][j] = max[i][j] - allocation[i][j];

    cout << "Available : [ ";
    for (int i = 0; i < m; i++)
        cout << available[i] << " ";
    cout << "]" << endl
         << endl;

    printMatrix("Need", need, n, m);

    // Find safe sequence
    for (int count = 0; count < n; count++)
    {
        int found = -1;
        for (int i = 0; i < n; i++)
            if (!finished[i])
            {
                bool canAllocate = true;
                for (int j = 0; j < m; j++)
                    if (need[i][j] > available[j])
                    {
                        canAllocate = false;
                        break;
                    }
                if (canAllocate)
                {
                    found = i;
                    break;
                }
            }

        if (found == -1)
            return false; // Unsafe state

        finished[found] = true;
        safeSeq.push_back(found);
        for (int j = 0; j < m; j++)
            available[j] += allocation[found][j];
    }

    cout << "Safe sequence : ";
    for (int i = 0; i < n; i++)
    {
        cout << "P" << (safeSeq[i] + 1);
        if (i < n - 1)
            cout << " -> ";
    }
    cout << endl;
    return true;
}

int main()
{
    int n, m;
    cout << "Enter number of processes and resources : ";
    cin >> n >> m;

    vector<vector<int>> allocation(n, vector<int>(m));
    vector<vector<int>> max(n, vector<int>(m));
    vector<int> available(m);

    cout << endl
         << "Enter Allocation Matrix (row by row) :" << endl;
    for (int i = 0; i < n; i++)
    {
        cout << "P" << (i + 1) << ": ";
        for (int j = 0; j < m; j++)
            cin >> allocation[i][j];
    }

    cout << endl
         << "Enter Max Matrix (row by row) :" << endl;
    for (int i = 0; i < n; i++)
    {
        cout << "P" << (i + 1) << ": ";
        for (int j = 0; j < m; j++)
            cin >> max[i][j];
    }

    cout << endl
         << "Enter Available resources : ";
    for (int i = 0; i < m; i++)
        cin >> available[i];
    cout << endl
         << "---Banker's Algorithm---" << endl;
    printMatrix("Allocation", allocation, n, m);
    printMatrix("Max", max, n, m);

    if (isSafe(allocation, max, available))
        cout << endl
             << "Result : System is in SAFE state.";
    else
        cout << endl
             << "Result : System is in UNSAFE state.";

    return 0;
}