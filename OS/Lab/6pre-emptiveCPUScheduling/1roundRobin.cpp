// Lab 6.1: WAP to simulate pre-emptive round robin scheduling algorithm to find turn around & waiting time
#include <iostream>
#include <iomanip>
#include <queue>
#include <vector>
#include <algorithm>
using namespace std;

// Helper to print float without .00 if whole number
void printSmartFloat(float value) {
    if (value == (int)value)
        cout << (int)value;
    else
        cout << fixed << setprecision(2) << value;
}

class Process {
public:
    char process;
    float arrival, burst, remaining;
    float completion, turnaround, waiting;

    Process(char p = ' ', float at = 0, float bt = 0) {
        process = p;
        arrival = at;
        burst = bt;
        remaining = bt;
        completion = turnaround = waiting = 0;
    }
};

class RoundRobin {
    Process* p;
    int n;
    float timeQuantum;
    queue<int> q;
    vector<pair<float, char>> gantt;  // For Gantt Chart

public:
    RoundRobin(int num, float tq) {
        n = num;
        timeQuantum = tq;
        p = new Process[n];
    }

    ~RoundRobin() {
        delete[] p;
    }

    void inputProcesses() {
        char process;
        float arrival, burst;
        cout << "Enter name, arrival time & burst time for each process:\n";
        for (int i = 0; i < n; i++) {
            cout << "Process " << i + 1 << " : ";
            cin >> process >> arrival >> burst;
            p[i] = Process(process, arrival, burst);
        }
    }

    void sortByArrival() {
        for (int i = 0; i < n - 1; i++)
            for (int j = i + 1; j < n; j++)
                if (p[i].arrival > p[j].arrival)
                    swap(p[i], p[j]);
    }

    void scheduleProcesses() {
        sortByArrival();

        float currentTime = 0;
        int completed = 0;
        bool* visited = new bool[n]{false};

        q.push(0);
        visited[0] = true;

        while (!q.empty()) {
            int idx = q.front();
            q.pop();

            float execTime = min(timeQuantum, p[idx].remaining);
            gantt.push_back({currentTime, p[idx].process});
            currentTime += execTime;
            p[idx].remaining -= execTime;

            // Check for new arrivals during this execution
            for (int i = 0; i < n; i++) {
                if (!visited[i] && p[i].arrival <= currentTime) {
                    q.push(i);
                    visited[i] = true;
                }
            }

            if (p[idx].remaining > 0) {
                q.push(idx);
            } else {
                p[idx].completion = currentTime;
                completed++;
            }

            if (q.empty() && completed < n) {
                for (int i = 0; i < n; i++) {
                    if (!visited[i]) {
                        q.push(i);
                        visited[i] = true;
                        currentTime = max(currentTime, p[i].arrival);
                        break;
                    }
                }
            }
        }

        delete[] visited;

        for (int i = 0; i < n; i++) {
            p[i].turnaround = p[i].completion - p[i].arrival;
            p[i].waiting = p[i].turnaround - p[i].burst;
        }
    }

    void printGanttChart() {
        cout << "\nGantt Chart:\n|";
        for (auto& entry : gantt) {
            cout << setw(4) << entry.second << setw(4) << "|";
        }

        cout << "\n";
        if (gantt.empty()) return;

        printSmartFloat(gantt[0].first);
        for (auto& entry : gantt) {
            float endTime = entry.first + timeQuantum;
            float compTime = getCompletionTime(entry.second);
            if (endTime > compTime) endTime = compTime;
            cout << setw(8);
            printSmartFloat(endTime);
        }
        cout << endl;
    }

    float getCompletionTime(char proc) {
        for (int i = 0; i < n; i++) {
            if (p[i].process == proc)
                return p[i].completion;
        }
        return 0;
    }

    void calculateAverages(float& avgTAT, float& avgWT) {
        avgTAT = avgWT = 0;
        for (int i = 0; i < n; i++) {
            avgTAT += p[i].turnaround;
            avgWT += p[i].waiting;
        }
    }

    void printProcessTable() {
        cout << "\n+-----+--------+--------+--------+--------+--------+\n";
        cout << "| PID |   AT   |   BT   |   CT   |  TAT   |   WT   |\n";
        cout << "+-----+--------+--------+--------+--------+--------+\n";
        for (int i = 0; i < n; i++) {
            cout << "|  " << setw(2) << p[i].process << " | ";
            cout << setw(6); printSmartFloat(p[i].arrival); cout << " | ";
            cout << setw(6); printSmartFloat(p[i].burst); cout << " | ";
            cout << setw(6); printSmartFloat(p[i].completion); cout << " | ";
            cout << setw(6); printSmartFloat(p[i].turnaround); cout << " | ";
            cout << setw(6); printSmartFloat(p[i].waiting); cout << " |\n";
        }
        cout << "+-----+--------+--------+--------+--------+--------+\n";
    }
};

int main() {
    int n;
    float tq;
    float avgTAT, avgWT;

    cout << "Enter number of processes: ";
    cin >> n;

    cout << "Enter Time Quantum: ";
    cin >> tq;

    RoundRobin rr(n, tq);
    rr.inputProcesses();
    rr.scheduleProcesses();
    rr.printGanttChart();
    rr.calculateAverages(avgTAT, avgWT);
    rr.printProcessTable();

    cout << fixed << setprecision(2);
    cout << "\nAverage Turnaround Time = " << (avgTAT / n);
    cout << "\nAverage Waiting Time = " << (avgWT / n) << endl;

    return 0;
}