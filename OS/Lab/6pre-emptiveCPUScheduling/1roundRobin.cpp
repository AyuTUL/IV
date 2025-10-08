// Lab 6.1: WAP to simulate pre-emptive round robin scheduling algorithm to find turn around & waiting time
#include <iostream>
#include <iomanip>
#include <queue>
#include <vector>
#include <algorithm>
using namespace std;

class Process
{
public:
    char process;
    float arrival, burst, remaining;
    float completion, turnaround, waiting;

    Process(char p = ' ', float at = 0, float bt = 0)
    {
        process = p;
        arrival = at;
        burst = bt;
        remaining = bt;
        completion = turnaround = waiting = 0;
    }
};

class RoundRobin
{
    Process *p;
    int n;
    float timeQuantum;
    queue<int> q;
    vector<pair<float, char>> gantt; // For Gantt Chart

public:
    RoundRobin(int num, float tq)
    {
        n = num;
        timeQuantum = tq;
        p = new Process[n];
    }

    ~RoundRobin()
    {
        delete[] p;
    }

    void inputProcesses()
    {
        char process;
        float arrival, burst;
        cout << "Enter name, arrival time & burst time for each process :" << endl;
        for (int i = 0; i < n; i++)
        {
            cout << "Process " << i + 1 << " : ";
            cin >> process >> arrival >> burst;
            p[i] = Process(process, arrival, burst);
        }
    }

    void sortByArrival()
    {
        for (int i = 0; i < n - 1; i++)
            for (int j = i + 1; j < n; j++)
                if (p[i].arrival > p[j].arrival)
                    swap(p[i], p[j]);
    }

    void scheduleProcesses()
    {
        sortByArrival();

        float currentTime = 0;
        int completed = 0;
        bool *visited = new bool[n]{false};

        q.push(0);
        visited[0] = true;

        while (!q.empty())
        {
            int idx = q.front();
            q.pop();

            float execTime = min(timeQuantum, p[idx].remaining);
            gantt.push_back({currentTime, p[idx].process});
            currentTime += execTime;
            p[idx].remaining -= execTime;

            // Check for new arrivals during this execution
            for (int i = 0; i < n; i++)
            {
                if (!visited[i] && p[i].arrival <= currentTime)
                {
                    q.push(i);
                    visited[i] = true;
                }
            }

            if (p[idx].remaining > 0)
                q.push(idx);
            else
            {
                p[idx].completion = currentTime;
                completed++;
            }

            if (q.empty() && completed < n)
                for (int i = 0; i < n; i++)
                    if (!visited[i])
                    {
                        q.push(i);
                        visited[i] = true;
                        currentTime = max(currentTime, p[i].arrival);
                        break;
                    }
        }

        delete[] visited;

        for (int i = 0; i < n; i++)
        {
            p[i].turnaround = p[i].completion - p[i].arrival;
            p[i].waiting = p[i].turnaround - p[i].burst;
        }
    }

    void printGanttChart()
    {
        cout << endl
             << "Gantt Chart :" << endl
             << "|";
        for (auto &entry : gantt)
            cout << setw(4) << entry.second << setw(4) << "|";

        cout << endl;
        if (gantt.empty())
            return;

        float startTime = gantt[0].first;
        if (startTime == (int)startTime)
            cout << (int)startTime;
        else
            cout << fixed << setprecision(2) << startTime;
        for (auto &entry : gantt)
        {
            float endTime = entry.first + timeQuantum;
            float compTime = getCompletionTime(entry.second);
            if (endTime > compTime)
                endTime = compTime;
            cout << setw(8);
            if (endTime == (int)endTime)
                cout << (int)endTime;
            else
                cout << fixed << setprecision(2) << endTime;
        }
        cout << endl;
    }

    float getCompletionTime(char proc)
    {
        for (int i = 0; i < n; i++)
            if (p[i].process == proc)
                return p[i].completion;
        return 0;
    }

    void calculateAverages(float &avgTAT, float &avgWT)
    {
        avgTAT = avgWT = 0;
        for (int i = 0; i < n; i++)
        {
            avgTAT += p[i].turnaround;
            avgWT += p[i].waiting;
        }
    }

    void printProcessTable()
    {
        cout << endl
             << "+-----+--------+--------+--------+---------+--------+" << endl
             << "| PID |   AT   |   BT   |   CT   |   TAT   |   WT   |" << endl
             << "+-----+--------+--------+--------+---------+--------+" << endl;
        for (int i = 0; i < n; i++)
        {
            cout << "|  " << p[i].process << "  | ";
            cout << setw(6) << fixed << setprecision(2) << p[i].arrival << " | ";
            cout << setw(6) << fixed << setprecision(2) << p[i].burst << " | ";
            cout << setw(6) << fixed << setprecision(2) << p[i].completion << " | ";
            cout << setw(7) << fixed << setprecision(2) << p[i].turnaround << " | ";
            cout << setw(6) << fixed << setprecision(2) << p[i].waiting << " |" << endl;
        }
        cout << "+-----+--------+--------+--------+---------+--------+" << endl;
    }
};

int main()
{
    int n;
    float tq;
    float avgTAT, avgWT;

    cout << "Enter number of processes : ";
    cin >> n;

    cout << "Enter Time Quantum : ";
    cin >> tq;

    RoundRobin rr(n, tq);
    rr.inputProcesses();
    rr.scheduleProcesses();
    cout << endl
         << "---Round Robin CPU Scheduling Algorithm---" << endl;
    rr.printGanttChart();
    rr.calculateAverages(avgTAT, avgWT);
    rr.printProcessTable();

    cout << fixed << setprecision(2);
    cout << endl
         << "Average Turnaround Time = " << (avgTAT / n) << endl
         << "Average Waiting Time = " << (avgWT / n) << endl;

    return 0;
}