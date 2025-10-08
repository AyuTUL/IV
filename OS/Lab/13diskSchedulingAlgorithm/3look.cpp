// Lab 13.3: WAP to simulate look disk scheduling algorithm
#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

int main()
{
    int n, head, dir;
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
    cout << "Enter direction (0 = towards smaller, 1 = towards larger) : ";
    cin >> dir;

    vector<int> left, right;
    for (int r : requests)
    {
        if (r <= head)
            left.push_back(r);
        else
            right.push_back(r);
    }

    sort(left.begin(), left.end());   // ascending
    sort(right.begin(), right.end()); // ascending

    int total_movement = 0;
    vector<int> service_order;
    service_order.push_back(head);

    if (dir == 1)
    {
        // move right first
        for (int r : right)
        {
            total_movement += abs(r - head);
            head = r;
            service_order.push_back(head);
        }
        // then service left in descending order (no need to go to end in LOOK)
        for (auto it = left.rbegin(); it != left.rend(); ++it)
        {
            total_movement += abs(*it - head);
            head = *it;
            service_order.push_back(head);
        }
    }
    else
    {
        // move left first
        for (auto it = left.rbegin(); it != left.rend(); ++it)
        {
            total_movement += abs(*it - head);
            head = *it;
            service_order.push_back(head);
        }
        // then service right in ascending order (no need to go to start in LOOK)
        for (int r : right)
        {
            total_movement += abs(r - head);
            head = r;
            service_order.push_back(head);
        }
    }

    cout << endl
         << "---Look Disk Scheduling Algorithm---" << endl
         << "Order of servicing requests : " << endl
         << "\t";
    for (size_t i = 0; i < service_order.size(); ++i)
    {
        if (i)
            cout << " -> ";
        cout << service_order[i];
    }
    cout << endl
         << endl
         << "Total head movement = " << total_movement << endl
         << "Average head movement = " << (double)total_movement / n << endl;
    return 0;
}