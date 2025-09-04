#include <iostream>
#include <iomanip>
using namespace std;

class Process {
public:
    int pid;             // Process ID
    int arrivalTime;     // Arrival Time
    int burstTime;       // Burst Time
    int completionTime;  // Completion Time
    int waitingTime;     // Waiting Time
    int turnaroundTime;  // Turnaround Time

    // Constructor to initialize Process object with default values
    Process() : pid(0), arrivalTime(0), burstTime(0), completionTime(0), waitingTime(0), turnaroundTime(0) {}

    Process(int p, int at, int bt) : pid(p), arrivalTime(at), burstTime(bt),
                                      completionTime(0), waitingTime(0), turnaroundTime(0) {}

    // Function to calculate completion, waiting, and turnaround time
    void calculateTimes(int currentTime) {
        // Process starts when the CPU is free, or when it arrives
        completionTime = max(currentTime, arrivalTime) + burstTime;
        turnaroundTime = completionTime - arrivalTime;
        waitingTime = turnaroundTime - burstTime;
    }
};

class FCFS {
private:
    Process processes[20]; // Array to hold up to 20 processes
    int n;  // Number of processes
    float avgWaitingTime;
    float avgTurnaroundTime;

public:
    FCFS() : n(0), avgWaitingTime(0), avgTurnaroundTime(0) {}

    void getInput() {
        cout << "Enter number of processes: ";
        cin >> n;

        for (int i = 0; i < n; i++) {
            int pid, at, bt;
            cout << "Enter Process ID, Arrival Time, and Burst Time for process " << i + 1 << ": ";
            cin >> pid >> at >> bt;
            processes[i] = Process(pid, at, bt);
        }
    }

    void sortProcessesByArrival() {
        // Sorting processes by arrival time using bubble sort
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (processes[i].arrivalTime > processes[j].arrivalTime) {
                    // Swap the processes
                    Process temp = processes[i];
                    processes[i] = processes[j];
                    processes[j] = temp;
                }
            }
        }
    }

    void calculateTimes() {
        int currentTime = 0;
        for (int i = 0; i < n; i++) {
            processes[i].calculateTimes(currentTime);
            currentTime = processes[i].completionTime;  // Update current time after process finishes
        }
    }

    void printGanttChart() {
        cout << "\nGantt Chart: \n|";
        int currentTime = 0;
        for (int i = 0; i < n; i++) {
            if (processes[i].arrivalTime > currentTime) {
                // If the process arrives after the previous one finishes, show idle time
                cout << "  -  |";  // CPU is idle
                currentTime = processes[i].arrivalTime;
            }
            cout << "  P" << processes[i].pid << "  |";
            currentTime = processes[i].completionTime;
        }
        cout << endl;

        // Print the timeline below the Gantt chart
        cout << "0";
        currentTime = 0;
        for (int i = 0; i < n; i++) {
            if (processes[i].arrivalTime > currentTime) {
                cout << "     " << processes[i].arrivalTime;
                currentTime = processes[i].arrivalTime;
            }
            currentTime += processes[i].burstTime;
            cout << "     " << currentTime;
        }
        cout << endl;
    }

    void printProcessDetails() {
        cout << "\nPID\tAT\tBT\tCT\tTAT\tWT\n";
        for (int i = 0; i < n; i++) {
            cout << processes[i].pid << "\t" << processes[i].arrivalTime << "\t"
                 << processes[i].burstTime << "\t" << processes[i].completionTime << "\t"
                 << processes[i].turnaroundTime << "\t" << processes[i].waitingTime << endl;
        }
    }

    void calculateAverages() {
        for (int i = 0; i < n; i++) {
            avgTurnaroundTime += processes[i].turnaroundTime;
            avgWaitingTime += processes[i].waitingTime;
        }
        avgTurnaroundTime /= n;
        avgWaitingTime /= n;
    }

    void printAverages() {
        cout << "\nAverage Turnaround Time = " << fixed << setprecision(2) << avgTurnaroundTime;
        cout << "\nAverage Waiting Time = " << fixed << setprecision(2) << avgWaitingTime << endl;
    }
};

int main() {
    FCFS fcfs;

    fcfs.getInput();
    fcfs.sortProcessesByArrival();
    fcfs.calculateTimes();
    fcfs.printGanttChart();
    fcfs.printProcessDetails();
    fcfs.calculateAverages();
    fcfs.printAverages();

    return 0;
}