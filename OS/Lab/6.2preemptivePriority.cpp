#include <iostream>
#include <iomanip>
#include <vector>
#include <queue>
#include <algorithm>
#include <climits>
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
    int priority;
    float completion, turnaround, waiting, startTime;

    Process(char p = ' ', float at = 0, float bt = 0, int pr = 0) {
        process = p;
        arrival = at;
        burst = bt;
        priority = pr;
        remaining = bt; // Initially, remaining burst time is the full burst time.
        completion = turnaround = waiting = startTime = 0;
    }
};

class PriorityScheduling {
    Process* p;
    int n;
    vector<pair<float, char>> gantt; // Store the Gantt chart as pairs of (time, process)

public:
    PriorityScheduling(int num) {
        n = num;
        p = new Process[n];
    }

    ~PriorityScheduling() {
        delete[] p;
    }

    void inputProcesses() {
        char process;
        float arrival, burst;
        int priority;
        cout << "Enter name, arrival time, burst time & priority for each process:\n";
        for (int i = 0; i < n; i++) {
            cout << "Process " << i + 1 << " : ";
            cin >> process >> arrival >> burst >> priority;
            p[i] = Process(process, arrival, burst, priority);
        }
    }

    void scheduleProcesses() {
        float currentTime = 0;
        int completedCount = 0;
        vector<bool> completed(n, false);
        priority_queue<pair<int, int>, vector<pair<int, int>>, greater<pair<int, int>>> readyQueue;

        while (completedCount < n) {
            // Add processes that have arrived up to the current time
            for (int i = 0; i < n; i++) {
                if (!completed[i] && p[i].arrival <= currentTime) {
                    readyQueue.push({p[i].priority, i});
                }
            }

            // If no process is available to run, we simulate idle time
            if (readyQueue.empty()) {
                currentTime++;
                continue;
            }

            // Select the highest priority process (lowest priority number)
            int idx = readyQueue.top().second;
            readyQueue.pop();

            // Start the process if not started already
            if (p[idx].startTime == 0) {
                p[idx].startTime = currentTime;
            }

            // Execute for 1 unit of time (preemptively, so smaller time slice)
            float execTime = 1;
            if (p[idx].remaining <= execTime) {
                execTime = p[idx].remaining;
            }

            currentTime += execTime;
            p[idx].remaining -= execTime;

            // Log process execution in the Gantt chart
            gantt.push_back({currentTime, p[idx].process});

            // If the process finishes, calculate turnaround and waiting times
            if (p[idx].remaining == 0) {
                p[idx].completion = currentTime;
                p[idx].turnaround = p[idx].completion - p[idx].arrival;
                p[idx].waiting = p[idx].turnaround - p[idx].burst;
                completed[idx] = true;
                completedCount++;
            } else {
                readyQueue.push({p[idx].priority, idx}); // Push process back into queue if it's not finished
            }
        }
    }

    void printGanttChart() {
        float currentTime = 0;
        int completedCount = 0;
        vector<bool> completed(n, false);
        priority_queue<pair<int, int>, vector<pair<int, int>>, greater<pair<int, int>>> readyQueue;

        cout << "\nGantt Chart:\n|";
        vector<pair<float, char>> ganttChart; // Store (time, process) pairs for Gantt chart visualization

        while (completedCount < n) {
            // Add processes that have arrived up to the current time
            for (int i = 0; i < n; i++) {
                if (!completed[i] && p[i].arrival <= currentTime) {
                    readyQueue.push({p[i].priority, i});
                }
            }

            // If no process is available to run, we simulate idle time
            if (readyQueue.empty()) {
                cout << setw(4) << "-" << setw(4) << "|";
                currentTime++;
                continue;
            }

            // Select the highest priority process (lowest priority number)
            int idx = readyQueue.top().second;
            readyQueue.pop();

            // Execute for 1 unit of time (preemptively)
            float execTime = 1;
            if (p[idx].remaining <= execTime) {
                execTime = p[idx].remaining;
            }

            currentTime += execTime;
            p[idx].remaining -= execTime;

            // Log process execution in the Gantt chart
            ganttChart.push_back({currentTime, p[idx].process});

            if (p[idx].remaining == 0) {
                completed[idx] = true;
                completedCount++;
            } else {
                readyQueue.push({p[idx].priority, idx}); // Process is not finished, re-add to ready queue
            }
        }

        // Print the Gantt Chart
        for (auto& entry : ganttChart) {
            cout << setw(4) << entry.second << setw(4) << "|";
        }

        cout << "\n0";
        currentTime = 0;

        for (auto& entry : ganttChart) {
            currentTime = entry.first;
            cout << setw(8);
            printSmartFloat(currentTime);
        }
        cout << endl;
    }

    void calculateAverages(float& avgTurnaround, float& avgWaiting) {
        avgTurnaround = avgWaiting = 0;
        for (int i = 0; i < n; i++) {
            avgTurnaround += p[i].turnaround;
            avgWaiting += p[i].waiting;
        }
    }

    void printProcessTable() {
        cout << "\n+-----+--------+--------+-----+--------+--------+--------+\n";
        cout << "| PID |   AT   |   BT   | PR  |   CT   |  TAT   |   WT   |\n";
        cout << "+-----+--------+--------+-----+--------+--------+--------+\n";
        for (int i = 0; i < n; i++) {
            cout << "|  " << setw(2) << p[i].process << " | ";
            cout << setw(6); printSmartFloat(p[i].arrival); cout << " | ";
            cout << setw(6); printSmartFloat(p[i].burst); cout << " | ";
            cout << setw(3) << p[i].priority << " | ";
            cout << setw(6); printSmartFloat(p[i].completion); cout << " | ";
            cout << setw(6); printSmartFloat(p[i].turnaround); cout << " | ";
            cout << setw(6); printSmartFloat(p[i].waiting); cout << " |\n";
        }
        cout << "+-----+--------+--------+-----+--------+--------+--------+\n";
    }
};

int main() {
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
    cout << "\nAverage Turnaround Time = " << (avgTurnaround / n);
    cout << "\nAverage Waiting Time = " << (avgWaiting / n) << endl;

    return 0;
}
