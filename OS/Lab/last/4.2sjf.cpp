#include<iostream>
#include<iomanip>
#include<climits>
using namespace std;
class Process 
{
	public:
	    char process;
		int arrival,burst,completion,turnaround,waiting;
		bool done;
	    Process(char p=' ',int at=0,int bt=0) 
	    {
	    	process=p;
	    	arrival=at;
	    	burst=bt;
	    	completion=turnaround=waiting=0;
	    	done=false;
		}
};
class SJF
{
	private:
    	Process* p;
    	int n;
	public:
	    SJF(int num)
		{
	    	n=num;
		    p=new Process[n];
	    }
	    ~SJF() 
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
	    int selectProcess(int currentTime) 
	    {
	        int index=-1,minBurst=INT_MAX;
	        for(int i=0;i<n;i++) 
	            if(!p[i].done && p[i].arrival<=currentTime && p[i].burst<minBurst) 
	            {
	                minBurst=p[i].burst;
	                index=i;
	            }
	        return index;
	    }
		void schedule()
	    {
	        int completedCount,currentTime;
			completedCount=currentTime=0;
	        while(completedCount<n) 
	        {
	            int index=selectProcess(currentTime);
	            if(index==-1) 
	                currentTime++;
	            else 
	            {
	                currentTime+=p[index].burst;
	                p[index].completion=currentTime;
	                p[index].done=true;
	                completedCount++;
	            }
	        }
	    }
	    void printGanttChart() 
		{
	        Process* pCopy=new Process[n];
	        for(int i=0;i<n;i++)
	            pCopy[i]=p[i];
	        for(int i=0;i<n-1;i++)
	            for(int j=i+1;j<n;j++)
	                if(pCopy[i].completion>pCopy[j].completion)
	                    swap(pCopy[i],pCopy[j]);
	        cout<<"\nGantt Chart :\n|";
	        int currentTime=0;
	        for(int i=0;i<n;i++)
	        {
	            if(currentTime<pCopy[i].arrival)
	            {
	                cout<<"  -  |";
	                currentTime=pCopy[i].arrival;
	            }
	            cout<<"  "<<pCopy[i].process<<"  |";
	            currentTime+=pCopy[i].burst;
	        }
	        cout<<"\n0";
	        currentTime=0;
	        for(int i=0;i<n;i++)
	        {
	            if(currentTime<pCopy[i].arrival)
	                currentTime=pCopy[i].arrival;
	            currentTime+=pCopy[i].burst;
	            cout<<setw(6)<<currentTime;
	        }
	        cout<<endl;
	        delete[] pCopy;
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
    SJF sjf(n);
 	sjf.inputProcesses();
 	sjf.schedule();
    sjf.printGanttChart();
    sjf.calculateTimes(avgTurnaround,avgWaiting);
    sjf.printProcessTable();
    cout<<fixed<<setprecision(2);
    cout<<endl<<"Average Turnaround Time = "<<(avgTurnaround/n);
    cout<<endl<<"Average Waiting Time = "<<(avgWaiting/n);
    return 0;
}