// Lab 5.1: WAP to simulate non pre-emptive FCFS scheduling algorithm to find turn around & waiting time
#include<iostream>
#include<iomanip>
using namespace std;
class Process 
{
public:
    char process;
	int arrival,burst,completion,turnaround,waiting;
    Process(char p=' ',int at=0,int bt=0) 
    {
    	process=p;
    	arrival=at;
    	burst=bt;
    	completion=turnaround=waiting=0;
	}
};
class FCFS 
{
    Process* p;
    int n;
public:
    FCFS(int num)
	{
    	n=num;
	    p=new Process[n];
    }
    ~FCFS() 
	{
        delete[] p;
    }
    void inputProcesses() 
	{
		char process;
		int arrival,burst;
		cout<<"Enter name,arrival & burst time for following processes : "<<endl;
		for(int i=0;i<n;i++) 
		{
         	cout<<"Process "<<i+1<<" : ";   
			cin>>process>>arrival>>burst;
            p[i]=Process(process,arrival,burst);
        }
    }
    void sortByArrival() 
	{
        for(int i=0;i<n-1;i++)
            for(int j=i+1;j<n;j++) 
                if(p[i].arrival>p[j].arrival)
                    swap(p[i],p[j]);
    }
    void printGanttChart() 
	{
        int currentTime=0;
        cout<<endl<<"Gantt Chart :\n|";
        for(int i=0;i<n;i++) 
		{
            if (currentTime<p[i].arrival) 
			{
                cout<<"  -  |";
                currentTime=p[i].arrival;
            }
            cout<< "   " <<p[i].process<< "  |";
            currentTime+=p[i].burst;
            p[i].completion=currentTime;
        }
        cout<<"\n0";
        currentTime=0;
        for(int i=0;i<n;i++)
		 {
            if(currentTime<p[i].arrival) 
			{
                cout<<"     "<<p[i].arrival;
                currentTime=p[i].arrival;
            }
            currentTime+=p[i].burst;
            cout<<"     "<<currentTime;
        }
        cout<<endl;
    }
    void calculateTimes(float &avgTurnaround,float &avgWaiting) 
	{
        avgTurnaround=avgWaiting=0;
        for(int i=0;i<n;i++)
		{
            p[i].turnaround=p[i].completion-p[i].arrival;
            p[i].waiting=p[i].turnaround-p[i].burst;
            avgTurnaround+=p[i].turnaround;
            avgWaiting+=p[i].waiting;
        }
    }
    void printProcessTable() 
	{
        cout<<endl<<"PID\tAT\tBT\tCT\tTAT\tWT"<<endl;
        for(int i=0;i<n;i++)
            cout<<p[i].process<<"\t"<< p[i].arrival<<"\t"<<p[i].burst<<"\t"<<p[i].completion<<"\t"<<p[i].turnaround<<"\t"<<p[i].waiting<<endl;
    }
};
int main() {
    int n;
    float avgTurnaround,avgWaiting;
    cout<<"Enter number of processes : ";
    cin>>n;
    FCFS fcfs(n);
 	fcfs.inputProcesses();
    fcfs.sortByArrival();
    fcfs.printGanttChart();
    fcfs.calculateTimes(avgTurnaround,avgWaiting);
    fcfs.printProcessTable();
    cout<<fixed<<setprecision(2);
    cout<<endl<<"Average Turnaround Time = "<<(avgTurnaround/n);
    cout<<endl<<"Average Waiting Time = "<<(avgWaiting/n)<<endl;
    return 0;
}
