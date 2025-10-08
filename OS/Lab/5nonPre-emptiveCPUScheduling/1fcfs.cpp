// Lab 5.1: WAP to simulate non pre-emptive FCFS scheduling algorithm to find turn around & waiting time

#include <iostream>
#include <iomanip>
using namespace std;

class Process
{
public:
    char process;
    float arrival, burst, completion, turnaround, waiting;

    Process(char p = ' ', float at = 0, float bt = 0)
    {
        process = p;
        arrival = at;
        burst = bt;
        completion = turnaround = waiting = 0;
    }
};

class FCFS
{
    Process *p;
    int n;

public:
    FCFS(int num)
    {
        n = num;
        p = new Process[n];
    }

    ~FCFS()
    {
        delete[] p;
    }

    void inputProcesses()
    {
        char process;
        float arrival, burst;
        cout << "Enter name, arrival & burst time for following processes :" << endl;
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

    void printGanttChart()
    {
        float currentTime = 0;
        cout << endl
             << "Gantt Chart :" << endl
             << "|";
        for (int i = 0; i < n; i++)
        {
            if (currentTime < p[i].arrival)
            {
                cout << setw(4) << "-" << setw(4) << "|";
                currentTime = p[i].arrival;
            }
            cout << setw(4) << p[i].process << setw(4) << "|";
            currentTime += p[i].burst;
            p[i].completion = currentTime;
        }

        cout << endl;
        currentTime = 0;
        if (0.0 == (int)0.0)
            cout << (int)0.0;
        else
            cout << fixed << setprecision(2) << 0.0;
        for (int i = 0; i < n; i++)
        {
            if (currentTime < p[i].arrival)
            {
                cout << setw(8);
                if (p[i].arrival == (int)p[i].arrival)
                    cout << (int)p[i].arrival;
                else
                    cout << fixed << setprecision(2) << p[i].arrival;
                currentTime = p[i].arrival;
            }
            currentTime += p[i].burst;
            cout << setw(8);
            if (currentTime == (int)currentTime)
                cout << (int)currentTime;
            else
                cout << fixed << setprecision(2) << currentTime;
        }
        cout << endl;
    }

    void calculateTimes(float &avgTurnaround, float &avgWaiting)
    {
        avgTurnaround = avgWaiting = 0;
        for (int i = 0; i < n; i++)
        {
            p[i].turnaround = p[i].completion - p[i].arrival;
            p[i].waiting = p[i].turnaround - p[i].burst;
            avgTurnaround += p[i].turnaround;
            avgWaiting += p[i].waiting;
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
    float avgTurnaround, avgWaiting;

    cout << "Enter number of processes : ";
    cin >> n;

    FCFS fcfs(n);
    fcfs.inputProcesses();
    fcfs.sortByArrival();
    cout << endl
         << "---FCFS CPU Scheduling Algorithm---" << endl;
    fcfs.printGanttChart();
    fcfs.calculateTimes(avgTurnaround, avgWaiting);
    fcfs.printProcessTable();

    cout << fixed << setprecision(2);
    cout << endl
         << "Average Turnaround Time = " << (avgTurnaround / n)<< endl
         << "Average Waiting Time = " << (avgWaiting / n) << endl;

    return 0;
}
