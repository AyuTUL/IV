//Lab 1.1: WAP to contruct DFA that accepts 1001
#include<iostream>
#include<string.h>
using namespace std;
int main()
{
	string input;
	bool accept=false;
	cout<<"Enter input : ";
	cin>>input;
	if(input[0]=='1')
		if(input[1]=='0')
			if(input[2]=='0')
				if(input[3]=='1')
					accept=true;
	if(accept==true)
		cout<<"String is accepted";
	else
		cout<<"String is rejected";
	return 0;
}