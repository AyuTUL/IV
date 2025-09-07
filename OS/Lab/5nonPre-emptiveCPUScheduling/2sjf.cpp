// Lab 5.2: WAP to simulate non pre-emptive SJF scheduling algorithm to find turn around & waiting time
#include <iostream>
#include <iomanip>
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
    float arrival, burst, completion, turnaround, waiting;
    bool done;

    Process(char p = ' ', float at = 0, float bt = 0) {
        process = p;
        arrival = at;
        burst = bt;
        completion = turnaround = waiting = 0;
        done = false;
    }
};

class SJF {
private:
    Process* p;
    int n;

public:
    SJF(int num) {
        n = num;
        p = new Process[n];
    }
    ~SJF() {
        delete[] p;
    }

    void inputProcesses() {
        char process;
        float arrival, burst;
        cout << "Enter name, arrival & burst time for following processes : " << endl;
        for (int i = 0; i < n; i++) {
            cout << "Process " << i + 1 << " : ";
            cin >> process >> arrival >> burst;
            p[i] = Process(process, arrival, burst);
        }
    }

    int selectProcess(float currentTime) {
        int index = -1;
        float minBurst = 1e9;  // large float value instead of INT_MAX
        for (int i = 0; i < n; i++)
            if (!p[i].done && p[i].arrival <= currentTime && p[i].burst < minBurst) {
                minBurst = p[i].burst;
                index = i;
            }
        return index;
    }

    void schedule() {
        int completedCount = 0;
        float currentTime = 0;
        while (completedCount < n) {
            int index = selectProcess(currentTime);
            if (index == -1)
                currentTime += 1; // increment time by 1 unit if no process ready
            else {
                currentTime += p[index].burst;
                p[index].completion = currentTime;
                p[index].done = true;
                completedCount++;
            }
        }
    }

    void printGanttChart() {
        Process* pCopy = new Process[n];
        for (int i = 0; i < n; i++)
            pCopy[i] = p[i];

        // Sort by completion time
        for (int i = 0; i < n - 1; i++)
            for (int j = i + 1; j < n; j++)
                if (pCopy[i].completion > pCopy[j].completion)
                    swap(pCopy[i], pCopy[j]);

        cout << "\nGantt Chart :\n|";
        float currentTime = 0;
        for (int i = 0; i < n; i++) {
            if (currentTime < pCopy[i].arrival) {
                cout << setw(4) << "-" << setw(4) << "|";
                currentTime = pCopy[i].arrival;
            }
            cout << setw(4) << pCopy[i].process << setw(4) << "|";
            currentTime += pCopy[i].burst;
        }
        cout << "\n0";
        currentTime = 0;
        for (int i = 0; i < n; i++) {
            if (currentTime < pCopy[i].arrival)
                currentTime = pCopy[i].arrival;
            currentTime += pCopy[i].burst;
            cout << setw(8);
            printSmartFloat(currentTime);
        }
        cout << endl;

        delete[] pCopy;
    }

    void calculateTimes(float& avgTurnaround, float& avgWaiting) {
        avgTurnaround = avgWaiting = 0;
        for (int i = 0; i < n; i++) {
            p[i].turnaround = p[i].completion - p[i].arrival;
            p[i].waiting = p[i].turnaround - p[i].burst;
            avgTurnaround += p[i].turnaround;
            avgWaiting += p[i].waiting;
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
    float avgTurnaround, avgWaiting;

    cout << "Enter number of processes : ";
    cin >> n;

    SJF sjf(n);
    sjf.inputProcesses();
    sjf.schedule();
    sjf.printGanttChart();
    sjf.calculateTimes(avgTurnaround, avgWaiting);
    sjf.printProcessTable();

    cout << fixed << setprecision(2);
    cout << "\nAverage Turnaround Time = " << (avgTurnaround / n);
    cout << "\nAverage Waiting Time = " << (avgWaiting / n) << endl;

    return 0;
}
