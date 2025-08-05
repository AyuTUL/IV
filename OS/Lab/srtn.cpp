#include<iostream>
#include<iomanip>
#include<climits>
#define MAX 1000
using namespace std;
class Process 
{
	public:
	    char process;
		int arrival,burst,completion,turnaround,waiting,remaining;
		bool done;
	    Process(char p=' ',int at=0,int bt=0) 
	    {
	    	process=p;
	    	arrival=at;
	    	burst=remaining=bt;
	    	completion=turnaround=waiting=0;
	    	done=false;
		}
};
class SRTN
{
	private:
    	Process* p;
    	int n,totalTime;
    	int* gantt;
	public:
	    SRTN(int num)
		{
	    	n=num;
		    p=new Process[n];
		    gantt=new int[MAX];
	    }
	    ~SRTN() 
		{
	        delete[] p;
	        delete[] gantt;
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
	    int selectProcess(int currentTime) 
	    {
	        int index=-1,minRemaining=INT_MAX;
	        for(int i=0;i<n;i++) 
	            if(!p[i].done && p[i].arrival<=currentTime && p[i].remaining<minRemaining && p[i].remaining>0) 
	            {
	                minRemaining=p[i].remaining;
	                index=i;
	            }
	        return index;
	    }
		void schedule()
	    {
	        int completedCount,currentTime,timeIndex;
			completedCount=currentTime=timeIndex=0;
	        while(completedCount<n) 
	        {
	            int index=selectProcess(currentTime);
	            if(index==-1) 
	            {
	            	gantt[timeIndex]=-1;
					currentTime++;
				}

	            else 
	            {
	            	p[index].remaining--;
	            	gantt[timeIndex++]=index;
	            	currentTime++;
	                if (p[index].remaining==0) 
	                {
	                    p[index].completion=currentTime;
	                    p[index].done=true;
	                    completedCount++;
	                }
	            }
	        }
	        totalTime=timeIndex;
	    }
	    void printGanttChart()
	    {
	        cout<<"\nGantt Chart :\n|";
	        for(int i=0;i<totalTime;i++)
	        {
	            if(gantt[i]==-1)
	                cout<<"  -  |";
	            else
	                cout<<"  "<<p[gantt[i]].process<<"  |";
	        }
	        cout<<"\n0";
	        for(int i=1;i<=totalTime;i++)
	            cout<<setw(6)<<i;
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
		    cout<<"\n+-----+-----+-----+-----+-----+-----+\n";
		    cout<<"| PID | AT  | BT  | CT  | TAT | WT  |\n";
		    cout<<"+-----+-----+-----+-----+-----+-----+\n";
		    for(int i=0;i<n;i++) 
		        cout<<"|  "<<setw(2)<<p[i].process<<" | "
		            <<setw(3)<<p[i].arrival<<" | "
		            <<setw(3)<<p[i].burst<<" | "
		            <<setw(3)<<p[i].completion<<" | "
		            <<setw(3)<<p[i].turnaround<<" | "
		            <<setw(3)<<p[i].waiting<<" |\n";
		    cout<<"+-----+-----+-----+-----+-----+-----+\n";
		}
};
int main()
 {
    int n;
    float avgTurnaround,avgWaiting;
    cout<<"Enter number of processes : ";
    cin>>n;
    SRTN srtn(n);
 	srtn.inputProcesses();
 	srtn.schedule();
    srtn.printGanttChart();
    srtn.calculateTimes(avgTurnaround,avgWaiting);
    srtn.printProcessTable();
    cout<<fixed<<setprecision(2);
    cout<<endl<<"Average Turnaround Time = "<<(avgTurnaround/n);
    cout<<endl<<"Average Waiting Time = "<<(avgWaiting/n);
    return 0;
}