//Lab 1.2: WAP to contruct DFA that accepts string that starts with 'a' and ends with 'b'
#include<iostream>
using namespace std;
int main()
{
	string input;
	bool accept=false;
	cout<<"Enter input : ";
	cin>>input;
	if(input[0]=='a' && input[input.length()-1]=='b')
		accept=true;
	if(accept==true)
		cout<<"String is accepted";
	else
		cout<<"String is rejected";
	return 0;
}