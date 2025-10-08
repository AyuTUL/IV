// Lab 5.3: WAP to simulate non pre-emptive priority scheduling algorithm to find turn around & waiting time
#include <iostream>
#include <iomanip>
#include <algorithm> // for std::fill
#include <climits>
using namespace std;

class Process
{
public:
    char process;
    float arrival, burst;
    int priority;
    float completion, turnaround, waiting;

    Process(char p = ' ', float at = 0, float bt = 0, int pr = 0)
    {
        process = p;
        arrival = at;
        burst = bt;
        priority = pr;
        completion = turnaround = waiting = 0;
    }
};

class PriorityScheduling
{
    Process *p;
    int n;

public:
    PriorityScheduling(int num)
    {
        n = num;
        p = new Process[n];
    }

    ~PriorityScheduling()
    {
        delete[] p;
    }

    void inputProcesses()
    {
        char process;
        float arrival, burst;
        int priority;
        cout << "Enter name, arrival time, burst time & priority for each process :" << endl;
        for (int i = 0; i < n; i++)
        {
            cout << "Process " << i + 1 << " : ";
            cin >> process >> arrival >> burst >> priority;
            p[i] = Process(process, arrival, burst, priority);
        }
    }

    void scheduleProcesses()
    {
        float currentTime = 0;
        bool *completed = new bool[n]{false};
        int completedCount = 0;

        while (completedCount < n)
        {
            int idx = -1;
            int highestPriority = INT_MAX;

            for (int i = 0; i < n; i++)
            {
                if (!completed[i] && p[i].arrival <= currentTime)
                {
                    if (p[i].priority < highestPriority)
                    {
                        highestPriority = p[i].priority;
                        idx = i;
                    }
                    else if (p[i].priority == highestPriority)
                    {
                        if (p[i].arrival < p[idx].arrival)
                        {
                            idx = i;
                        }
                    }
                }
            }

            if (idx == -1)
            {
                currentTime += 1; // Idle time
                continue;
            }

            currentTime = max(currentTime, p[idx].arrival);
            p[idx].completion = currentTime + p[idx].burst;
            p[idx].turnaround = p[idx].completion - p[idx].arrival;
            p[idx].waiting = p[idx].turnaround - p[idx].burst;
            currentTime = p[idx].completion;
            completed[idx] = true;
            completedCount++;
        }

        delete[] completed;
    }

    void printGanttChart()
    {
        float currentTime = 0;
        bool *completed = new bool[n]{false};
        int completedCount = 0;

        cout << endl
             << "Gantt Chart :" << endl
             << "|";
        while (completedCount < n)
        {
            int idx = -1;
            int highestPriority = INT_MAX;

            for (int i = 0; i < n; i++)
            {
                if (!completed[i] && p[i].arrival <= currentTime)
                {
                    if (p[i].priority < highestPriority)
                    {
                        highestPriority = p[i].priority;
                        idx = i;
                    }
                    else if (p[i].priority == highestPriority)
                    {
                        if (p[i].arrival < p[idx].arrival)
                        {
                            idx = i;
                        }
                    }
                }
            }

            if (idx == -1)
            {
                cout << setw(4) << "-" << setw(4) << "|";
                currentTime += 1;
                continue;
            }

            currentTime = max(currentTime, p[idx].arrival);
            cout << setw(4) << p[idx].process << setw(4) << "|";
            currentTime += p[idx].burst;
            completed[idx] = true;
            completedCount++;
        }

        cout << endl
             << "0";
        currentTime = 0;
        completedCount = 0;
        fill(completed, completed + n, false);

        while (completedCount < n)
        {
            int idx = -1;
            int highestPriority = INT_MAX;

            for (int i = 0; i < n; i++)
            {
                if (!completed[i] && p[i].arrival <= currentTime)
                {
                    if (p[i].priority < highestPriority)
                    {
                        highestPriority = p[i].priority;
                        idx = i;
                    }
                    else if (p[i].priority == highestPriority)
                    {
                        if (p[i].arrival < p[idx].arrival)
                        {
                            idx = i;
                        }
                    }
                }
            }

            if (idx == -1)
            {
                float idleTime = currentTime + 1.0;
                cout << setw(8);
                if (idleTime == (int)idleTime)
                    cout << (int)idleTime;
                else
                    cout << fixed << setprecision(2) << idleTime;
                currentTime += 1;
                continue;
            }

            currentTime = max(currentTime, p[idx].arrival);
            currentTime += p[idx].burst;
            cout << setw(8);
            if (currentTime == (int)currentTime)
                cout << (int)currentTime;
            else
                cout << fixed << setprecision(2) << currentTime;
            completed[idx] = true;
            completedCount++;
        }

        delete[] completed;
        cout << endl;
    }

    void calculateAverages(float &avgTurnaround, float &avgWaiting)
    {
        avgTurnaround = avgWaiting = 0;
        for (int i = 0; i < n; i++)
        {
            avgTurnaround += p[i].turnaround;
            avgWaiting += p[i].waiting;
        }
    }

    void printProcessTable()
    {
        cout << endl
             << "+-----+--------+--------+-----+--------+---------+--------+" << endl
             << "| PID |   AT   |   BT   | PR  |   CT   |   TAT   |   WT   |" << endl
             << "+-----+--------+--------+-----+--------+---------+--------+" << endl;
        for (int i = 0; i < n; i++)
        {
            cout << "|  " << p[i].process << "  | ";
            cout << setw(6) << fixed << setprecision(2) << p[i].arrival << " | ";
            cout << setw(6) << fixed << setprecision(2) << p[i].burst << " | ";
            cout << setw(3) << p[i].priority << " | ";
            cout << setw(6) << fixed << setprecision(2) << p[i].completion << " | ";
            cout << setw(7) << fixed << setprecision(2) << p[i].turnaround << " | ";
            cout << setw(6) << fixed << setprecision(2) << p[i].waiting << " |" << endl;
        }
        cout << "+-----+--------+--------+-----+--------+---------+--------+" << endl;
    }
};

int main()
{
    int n;
    float avgTurnaround, avgWaiting;

    cout << "Enter number of processes : ";
    cin >> n;

    PriorityScheduling ps(n);
    ps.inputProcesses();
    ps.scheduleProcesses();
    cout << endl
         << "---Non Pre-emptive Priority CPU Scheduling Algorithm---" << endl;
    ps.printGanttChart();
    ps.calculateAverages(avgTurnaround, avgWaiting);
    ps.printProcessTable();

    cout << fixed << setprecision(2);
    cout << endl
         << "Average Turnaround Time = " << (avgTurnaround / n) << endl
         << "Average Waiting Time = " << (avgWaiting / n) << endl;

    return 0;
}