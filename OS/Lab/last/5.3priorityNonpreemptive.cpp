#include<iostream>
#include<iomanip>
#include<climits>
//WRONG
using namespace std;

class Process {
public:
    char process;
    int arrival, burst, priority, completion, turnaround, waiting;
    bool done;

    Process(char p = ' ', int at = 0, int bt = 0, int pr = 0) {
        process = p;
        arrival = at;
        burst = bt;
        priority = pr;
        completion = turnaround = waiting = 0;
        done = false;
    }
};

class PriorityScheduling {
private:
    Process* p;
    int n;

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
        int arrival, burst, priority;
        cout << "Enter name, arrival, burst time, and priority for the following processes:\n";
        for (int i = 0; i < n; i++) {
            cout << "Process " << i + 1 << " : ";
            cin >> process >> arrival >> burst >> priority;
            p[i] = Process(process, arrival, burst, priority);
        }
    }

    int selectProcess(int currentTime) {
        int index = -1, highestPriority = INT_MIN;
        for (int i = 0; i < n; i++) {
            if (!p[i].done && p[i].arrival <= currentTime && p[i].priority > highestPriority) {
                highestPriority = p[i].priority;
                index = i;
            }
        }
        return index;
    }

    void schedule() {
        int completedCount = 0, currentTime = 0;
        while (completedCount < n) {
            int index = selectProcess(currentTime);
            if (index == -1) {
                currentTime++; // No process is ready, increment time
            } else {
                currentTime += p[index].burst;
                p[index].completion = currentTime;
                p[index].done = true;
                completedCount++;
            }
        }
    }

    void printGanttChart() {
        Process* pCopy = new Process[n];
        for (int i = 0; i < n; i++) {
            pCopy[i] = p[i];
        }

        // Sorting the processes by completion time manually using bubble sort
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (pCopy[i].completion > pCopy[j].completion) {
                    Process temp = pCopy[i];
                    pCopy[i] = pCopy[j];
                    pCopy[j] = temp;
                }
            }
        }

        cout << "\nGantt Chart :\n|";
        int currentTime = 0;
        for (int i = 0; i < n; i++) {
            if (currentTime < pCopy[i].arrival) {
                cout << "  -  |";
                currentTime = pCopy[i].arrival;
            }
            cout << "  " << pCopy[i].process << "  |";
            currentTime += pCopy[i].burst;
        }
        cout << "\n0";
        currentTime = 0;
        for (int i = 0; i < n; i++) {
            if (currentTime < pCopy[i].arrival) {
                currentTime = pCopy[i].arrival;
            }
            currentTime += pCopy[i].burst;
            cout << setw(6) << currentTime;
        }
        cout << endl;
        delete[] pCopy;
    }

    void calculateTimes(float &avgTurnaround, float &avgWaiting) {
        avgTurnaround = avgWaiting = 0;
        for (int i = 0; i < n; i++) {
            p[i].turnaround = p[i].completion - p[i].arrival;
            p[i].waiting = p[i].turnaround - p[i].burst;
            avgTurnaround += p[i].turnaround;
            avgWaiting += p[i].waiting;
        }
    }

    void printProcessTable() {
        cout << "\n+-----+-----+-----+----------+-----+-----+-----+\n";
        cout << "| PID | AT  | BT  | Priority | CT  | TAT | WT  |\n";
        cout << "+-----+-----+-----+----------+-----+-----+-----+\n";
        for (int i = 0; i < n; i++) {
            cout << "|  " << setw(2) << p[i].process << " | "
                 << setw(3) << p[i].arrival << " | "
                 << setw(3) << p[i].burst << " | "
                 << setw(8) << p[i].priority << " | "
                 << setw(3) << p[i].completion << " | "
                 << setw(3) << p[i].turnaround << " | "
                 << setw(3) << p[i].waiting << " |\n";
        }
        cout << "+-----+-----+-----+----------+-----+-----+-----+\n";
    }
};

int main() {
    int n;
    float avgTurnaround, avgWaiting;

    cout << "Enter number of processes: ";
    cin >> n;

    PriorityScheduling ps(n);
    ps.inputProcesses();
    ps.schedule();
    ps.printGanttChart();
    ps.calculateTimes(avgTurnaround, avgWaiting);
    ps.printProcessTable();

    cout << fixed << setprecision(2);
    cout << endl << "Average Turnaround Time = " << (avgTurnaround / n);
    cout << endl << "Average Waiting Time = " << (avgWaiting / n);

    return 0;
}
