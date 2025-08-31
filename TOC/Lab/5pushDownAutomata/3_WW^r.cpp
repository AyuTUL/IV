//Lab 5.3: WAP to design PDA that accepts L={WW^r | W belongs to (a+b)*}. Also check for abba
//Lab 5.2: WAP to design PDA that accepts L={WcW^r | W belongs to (0+1)*}. Also check for ||c||
//barsha lai maga ||c||
#include <iostream>
#include <stack>
#include <string>
using namespace std;
bool pda(string& input) 
{
	char state='A';
    stack<char> s;
    s.push('z');
    for(int i=0;i<input.length();i++)
	{
		switch(state)
		{
			case 'A':
				if(input[i]=='a' && (s.top()=='z' || s.top()=='a' || s.top()=='b'))
				{
					s.push('a');
					state='A';	
				}
				else if(input[i]=='b' && (s.top()=='z' || s.top()=='a' || s.top()=='b'))
				{
					s.push('b');
					state='A';	
				}
				else if(input[i]=='a' || s.top()=='a')
				{
					s.pop();
					state='B';
				}
				else if(input[i]=='a' || s.top()=='a')
				{
					s.pop();
					state='B';
				}
				else
					return false;
				break;
			case 'B':
				if(input[i]=='a' && s.top()=='a')
				{
					s.pop();
					state='B';	
				}
				else if(input[i]=='b' && s.top()=='b')
				{
					s.pop();
					state='B';	
				}
				else 
					return false;	
				break;
			}
	}
	if(state=='B' && s.top()=='z')
		state='C';
	return(state=='C' && s.top()=='z');
}
int main() {
    string input;
    cout<<"Enter input : ";
	cin>>input;
	for(int i=0;i<input.length();i++)
		if(input[i]!='a' && input[i]!='b')
		{
			cout<<"Invalid input. The alphabet is {a,b}.";
			return 0;
		}
    if(pda(input))
        cout<<"PDA accepts the string : "<<input;
    else
        cout<<"PDA rejects the string : "<<input;
    return 0;
}

