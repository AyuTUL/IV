//Lab 1.1: WAP to contruct DFA that accepts 1001
#include<iostream>
#include<string.h>
using namespace std;
int main()
{
	string input;
	char state='A';
	cout<<"Enter input : ";
	cin>>input;
	for(int i=0;i<input.length();i++)
	{
		switch(state)
		{
			case 'A':
				if(input[i]=='1')
					state='B';
				else
					state='X';
				break;
			case 'B':
				if(input[i]=='0')
					state='C';
				else
					state='X';
				break;
			case 'C':
				if(input[i]=='0')
					state='D';
				else
					state='X';
				break;
			case 'D':
				state='X';
				break;
			case 'X':
				state='X';
				break;
		}
	}
	if(state=='D')
		cout<<"String is accepted";
	else
		cout<<"String is rejected";
	return 0;
}