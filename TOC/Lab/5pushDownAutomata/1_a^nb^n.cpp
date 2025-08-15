//Lab 5.1: WAP to design PDA that accepts L={a^n b^n | n>=1}. Also check for aaaabbbb
//incomplete
#include <iostream>
#include <stack>
#include <string>
using namespace std;
bool pda(string& input) {
    stack<char> s;
    s.push('z');
    for(int i=0;i<input.length();i++)
	{
		switch(state)
		{
			case 'A':
				if(input[i]=='a' && s.top()=='z')
				{
					s.push('a');
					state='A';	
				}
				else if(input[i]=='a' && s.top()=='a')
				{
					s.push('a');
					state='A';	
				}
				else if(input[i]=='b' && s.top()=='a')
				{
					s.pop();
					state='B';	
				}
				break;
			case 'B':
				if(input[i]=='b' && s.top()=='a')
				{
					s.pop();
					state='B';	
				}
				else if(input[i]=='\0' && s.top()=='z')
				{
					state='A';	
				}
				break;
			case 'C':
				if(input[i]=='0') state='D';
				break;
			case 'D':
				if(input[i]=='1') state='E';
				break;
			}
	}
    int i=0,n=input.size();
    while(i<n && inpout[i]=='a') {
        stk.push('a');
        i++;
    }
    if (i == 0) return false;

    // State q1: read 'b's and pop 'A's from stack
    while (i < n && input[i] == 'b') {
        if (stk.empty() || stk.top() != 'A') return false;
        stk.pop();
        i++;
    }

    // At the end, input consumed, stack should have only 'Z' left
    if (i == n && stk.size() == 1 && stk.top() == 'Z')
        return true;

    return false;
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
    string test="aaaabbbb";
    cout<<"\nTesting string:"<<test<<endl;
    if (pda_accepts(test_str))
        cout<<"PDA accepts the string : "<<input;
    else
        cout<<"PDA rejects the string : "<<input;
    return 0;
}
