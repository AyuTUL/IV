#include<iostream>
#include<cstring>
using namespace std;
int main()
{
	string input;
	char state='A';
	cout<<"Enter input string : ";
	cin>>input;
	for(int i=0;i<input.length();i++)
	{
		switch(state)
		{
			case 'A':
				state=(input[i]=='1')?'B':'D';
				break;
			case 'B':
				state=(input[i]=='0')?'C':'F';
				break;
			case 'C':
				state='X';
				break;
			case 'D':
				state=(input[i]=='1')?'C':'E';
				break;
			case 'E':
				state=(input[i]=='1')?'C':'X';
				break;
			case 'F':
				state=(input[i]=='0')?'C':'X';
				break;
			case 'X':
				state='X';
				break;
		}
	}
	if(state=='C')
		cout<<"DFA accepts the string : "<<input;
	else
		cout<<"DFA rejects the string : "<<input;
	return 0;
}