//Lab 1.2: WAP to contruct DFA that accepts string that starts with 'a' and ends with 'b'
#include<iostream>
using namespace std;
int main()
{
	string input;
	char state='A';
	cout<<"Enter input : ";
	cin>>input;
	for(int i=0;i<input.length();i++)
		if(input[i]!='a' && input[i]!='b')
		{
			cout<<"Invalid input. The alphabet is {a,b}.";
			return 0;
		}
	for(int i=0;i<input.length();i++)
	{
		switch(state)
		{
			case 'A':
				state=(input[i]=='a')?'B':'X';
				break;
			case 'B':
				state=(input[i]=='b')?'C':'B';
				break;
			case 'C':
				state=(input[i]=='b')?'C':'B';
				break;
			case 'X':
				state='X';
				break;
		}
	}
	if(state=='C')
		cout<<input<<" is accepted by the DFA";
	else
		cout<<input<<" is rejected by the DFA";
	return 0;
}
