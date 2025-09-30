// Lab 6.2: WAP to simulate pre-emptive priority scheduling algorithm to find turn around & waiting time
#include <iostream>
#include <iomanip>
#include <vector>
#include <queue>
#include <algorithm>
#include <climits>
#include <cfloat>
using namespace std;

// Helper to print float without .00 if whole number
void printSmartFloat(float value)
{
    if (value == (int)value)
        cout << (int)value;
    else
        cout << fixed << setprecision(2) << value;
}

class Process
{
public:
    char process;
    float arrival, burst, remaining;
    int priority;
    float completion, turnaround, waiting, startTime;

    Process(char p = ' ', float at = 0, float bt = 0, int pr = 0)
    {
        process = p;
        arrival = at;
        burst = bt;
        priority = pr;
        remaining = bt; // Initially, remaining burst time is the full burst time.
        completion = turnaround = waiting = startTime = 0;
    }
};

class PriorityScheduling
{
    Process *p;
    int n;
    vector<pair<float, char>> gantt; // Store the Gantt chart as pairs of (time, process)

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
        cout << "Enter name, arrival time, burst time & priority for each process:\n";
        for (int i = 0; i < n; i++)
        {
            cout << "Process " << i + 1 << " : ";
            cin >> process >> arrival >> burst >> priority;
            p[i] = Process(process, arrival, burst, priority);
        }
    }

    void scheduleProcesses()
    {
        // Clear the Gantt chart
        gantt.clear();

        float currentTime = 0;
        int completedCount = 0;
        vector<bool> completed(n, false);

        // Reset completion data
        for (int i = 0; i < n; i++)
        {
            p[i].completion = 0;
            p[i].turnaround = 0;
            p[i].waiting = 0;
            p[i].startTime = 0;
            p[i].remaining = p[i].burst;
        }

        char lastProcessId = ' ';
        float lastChangeTime = 0;

        while (completedCount < n)
        {
            int selectedIdx = -1;
            int highestPriority = INT_MAX;

            // Find the process with highest priority among the arrived ones
            for (int i = 0; i < n; i++)
            {
                if (!completed[i] && p[i].arrival <= currentTime && p[i].remaining > 0)
                {
                    if (p[i].priority < highestPriority)
                    {
                        highestPriority = p[i].priority;
                        selectedIdx = i;
                    }
                }
            }

            // If no process is available, find the next arriving process
            if (selectedIdx == -1)
            {
                float nextArrival = FLT_MAX;
                int nextProcess = -1;
                for (int i = 0; i < n; i++)
                {
                    if (!completed[i] && p[i].arrival < nextArrival && p[i].arrival > currentTime)
                    {
                        nextArrival = p[i].arrival;
                        nextProcess = i;
                    }
                }

                if (nextProcess != -1)
                {
                    // Add idle time in Gantt chart
                    if (lastProcessId != '-')
                    {
                        if (lastProcessId != ' ')
                            gantt.push_back({currentTime, lastProcessId});
                        lastProcessId = '-';
                        lastChangeTime = currentTime;
                    }
                    currentTime = nextArrival;
                    continue;
                }
                else
                {
                    break; // No more processes to schedule
                }
            }

            // If process has changed, update Gantt chart
            if (lastProcessId != p[selectedIdx].process)
            {
                if (lastProcessId != ' ')
                    gantt.push_back({currentTime, lastProcessId});
                lastProcessId = p[selectedIdx].process;
                lastChangeTime = currentTime;

                // Record start time if first time running
                if (p[selectedIdx].startTime == 0)
                    p[selectedIdx].startTime = currentTime;
            }

            // Determine how long this process will run
            float runUntil = currentTime + 1; // Default: run for 1 time unit

            // Check if process will complete
            if (p[selectedIdx].remaining <= 1)
            {
                runUntil = currentTime + p[selectedIdx].remaining;
                p[selectedIdx].remaining = 0;
                p[selectedIdx].completion = runUntil;
                p[selectedIdx].turnaround = p[selectedIdx].completion - p[selectedIdx].arrival;
                p[selectedIdx].waiting = p[selectedIdx].turnaround - p[selectedIdx].burst;
                completed[selectedIdx] = true;
                completedCount++;
            }
            else
            {
                // Check if any higher priority process will arrive before next time unit
                float nextPreemption = currentTime + 1;
                bool willBePreempted = false;

                for (int i = 0; i < n; i++)
                {
                    if (!completed[i] && p[i].arrival > currentTime && p[i].arrival < nextPreemption && p[i].priority < p[selectedIdx].priority)
                    {
                        nextPreemption = p[i].arrival;
                        willBePreempted = true;
                    }
                }

                if (willBePreempted)
                {
                    runUntil = nextPreemption;
                }

                // Update remaining time
                p[selectedIdx].remaining -= (runUntil - currentTime);
            }

            // Move time forward
            currentTime = runUntil;
        }

        // Add the last process to Gantt chart
        if (lastProcessId != ' ')
            gantt.push_back({currentTime, lastProcessId});
    }

    void printGanttChart()
    {
        cout << "\nGantt Chart:\n|";

        // Print the process IDs
        for (auto &entry : gantt)
        {
            cout << setw(4) << entry.second << setw(4) << "|";
        }

        // Print the timeline
        cout << "\n0";
        float currentTime = 0;

        for (auto &entry : gantt)
        {
            currentTime = entry.first;
            cout << setw(8);
            printSmartFloat(currentTime);
        }
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
        avgTurnaround /= n;
        avgWaiting /= n;
    }

    void printProcessTable()
    {
        cout << "\n+-----+--------+--------+-----+--------+--------+--------+\n";
        cout << "| PID |   AT   |   BT   | PR  |   CT   |  TAT   |   WT   |\n";
        cout << "+-----+--------+--------+-----+--------+--------+--------+\n";
        for (int i = 0; i < n; i++)
        {
            cout << "|  " << setw(2) << p[i].process << " | ";
            cout << setw(6);
            printSmartFloat(p[i].arrival);
            cout << " | ";
            cout << setw(6);
            printSmartFloat(p[i].burst);
            cout << " | ";
            cout << setw(3) << p[i].priority << " | ";
            cout << setw(6);
            printSmartFloat(p[i].completion);
            cout << " | ";
            cout << setw(6);
            printSmartFloat(p[i].turnaround);
            cout << " | ";
            cout << setw(6);
            printSmartFloat(p[i].waiting);
            cout << " |\n";
        }
        cout << "+-----+--------+--------+-----+--------+--------+--------+\n";
    }
};

int main()
{
    int n;
    float avgTurnaround, avgWaiting;

    cout << "Enter number of processes: ";
    cin >> n;

    PriorityScheduling ps(n);
    ps.inputProcesses();
    ps.scheduleProcesses();
    ps.printGanttChart();
    ps.calculateAverages(avgTurnaround, avgWaiting);
    ps.printProcessTable();

    cout << fixed << setprecision(2);
    cout << "\nAverage Turnaround Time = " << avgTurnaround;
    cout << "\nAverage Waiting Time = " << avgWaiting << endl;

    return 0;
}
