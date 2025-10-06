// Lab 13.1: WAP to simulate FCFS disk scheduling algorithm
#include <iostream>
#include <vector>
using namespace std;

int main()
{
    int n, head;
    cout << "Enter number of disk requests : ";
    cin >> n;
    vector<int> requests(n);
    cout << "Enter the track sequence : ";
    for (int i = 0; i < n; ++i)
    {
        cin >> requests[i];
    }
    cout << "Enter initial head position : ";
    cin >> head;

    int total_movement = 0;
    cout << endl
         << "---FCFS Disk Scheduling Algorithm---" << endl
         << "Order of servicing requests : " << endl
         << "\t" << head;
    for (int i = 0; i < n; ++i)
    {
        total_movement += abs(requests[i] - head);
        head = requests[i];
        cout << " -> " << head;
    }
    cout << endl
         << endl
         << "Total head movement = " << total_movement << endl;
    cout << "Average head movement = " << (double)total_movement / n << endl;
    return 0;
}