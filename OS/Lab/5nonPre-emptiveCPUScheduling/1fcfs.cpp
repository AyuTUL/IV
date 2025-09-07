// Lab 5.1: WAP to simulate non pre-emptive FCFS scheduling algorithm to find turn around & waiting time

#include<iostream>
#include<iomanip>
using namespace std;

// ? Helper function to smartly print float values
void printSmartFloat(float value) {
    if (value == (int)value)
        cout << (int)value;
    else
        cout << fixed << setprecision(2) << value;
}

// ? Process class with float attributes
class Process {
public:
    char process;
    float arrival, burst, completion, turnaround, waiting;

    Process(char p=' ', float at=0, float bt=0) {
        process = p;
        arrival = at;
        burst = bt;
        completion = turnaround = waiting = 0;
    }
};

// ? FCFS scheduling class
class FCFS {
    Process* p;
    int n;

public:
    FCFS(int num) {
        n = num;
        p = new Process[n];
    }

    ~FCFS() {
        delete[] p;
    }

    void inputProcesses() {
        char process;
        float arrival, burst;
        cout << "Enter name, arrival & burst time for following processes:\n";
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

    void printGanttChart() {
        float currentTime = 0;
        cout << "\nGantt Chart:\n|";
        for (int i = 0; i < n; i++) {
            if (currentTime < p[i].arrival) {
                cout << setw(4) << "-" << setw(4) << "|";
                currentTime = p[i].arrival;
            }
            cout << setw(4) << p[i].process << setw(4) << "|";
            currentTime += p[i].burst;
            p[i].completion = currentTime;
        }

        // Time line under Gantt chart
        cout << "\n";
        currentTime = 0;
        printSmartFloat(0);
        for (int i = 0; i < n; i++) {
            if (currentTime < p[i].arrival) {
                cout << setw(8);
                printSmartFloat(p[i].arrival);
                currentTime = p[i].arrival;
            }
            currentTime += p[i].burst;
            cout << setw(8);
            printSmartFloat(currentTime);
        }
        cout << endl;
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

// ? Main function
int main() {
    int n;
    float avgTurnaround, avgWaiting;

    cout << "Enter number of processes: ";
    cin >> n;

    FCFS fcfs(n);
    fcfs.inputProcesses();
    fcfs.sortByArrival();
    fcfs.printGanttChart();
    fcfs.calculateTimes(avgTurnaround, avgWaiting);
    fcfs.printProcessTable();

    cout << fixed << setprecision(2);
cout << "\nAverage Turnaround Time = " << (avgTurnaround / n);
cout << "\nAverage Waiting Time = " << (avgWaiting / n) << endl;


    return 0;
}
