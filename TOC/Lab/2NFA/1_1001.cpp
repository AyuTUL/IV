//Lab 2.1: WAP to contruct NFA that accepts 1001
#include<iostream>
#include<cstring>
using namespace std;
int main()
{
	system("color f0");
	string input;
	char state='A';
	cout<<"Enter input : ";
	cin>>input;
	for(int i=0;i<input.length();i++)
		if(input[i]!='0' && input[i]!='1')
		{
			cout<<"Invalid input. The alphabet is {0,1}.";
			return 0;
		}
	for(int i=0;i<input.length();i++)
	{
		switch(state)
		{
			case 'A':
				if(input[i]=='1') state='B';
				break;
			case 'B':
				state=(input[i]=='1')?'C':'B';
				break;
			case 'C':
				break;
			}
	}
	if(state=='C')
		cout<<"NFA accepts the string : "<<input;
	else
		cout<<"NFA rejects the string : "<<input;
	return 0;
}