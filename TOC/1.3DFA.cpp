//Lab 1.3: WAP to contruct DFA that accepts {10,01,001,110}
#include<iostream>
using namespace std;
int main()
{
	string input;
	bool accept=false;
	cout<<"Enter input : ";
	cin>>input;
	if(input[0]=='1' && input[1]=='0' || input[0]=='0' && input[1]=='1' || input[0]=='0' && input[1]=='0' && input[2]=='1' || input[0]=='1' && input[1]=='1' && input[2]=='0')
		accept=true;
	if(accept==true)
		cout<<"String is accepted";
	else
		cout<<"String is rejected";
	return 0;
}