#include <iostream>
#include <string>
using namespace std;

string tm(string input) {
    char state = 'A';
    int head = 0;
    
    // Add blank on both sides
    input.insert(input.begin(), 'B');
    input.push_back('B');
    head = 0; // Start at left blank
    
    cout << "Initial: " << input << " Head at: " << head << " State: " << state << endl;
    
    while (true) {
        cout << "State " << state << ", Head at " << head << " (" << input[head] << ")" << endl;
        
        switch (state) {
            case 'A':
                // Move right through the input to reach right end
                if (input[head] == 'B' && head == 0) {
                    // At left blank, move right
                    head++;
                } else if (input[head] == '0' || input[head] == '1') {
                    head++; // Move right through digits
                } else if (input[head] == 'B' && head > 0) {
                    // Reached right blank, go to state B and move left
                    state = 'B';
                    head--; // Move left to last digit
                } else {
                    return "ERROR in state A";
                }
                break;
                
            case 'B':
                // Scan from right to left, copy 0s until we find first 1 from right
                if (input[head] == '0') {
                    head--; // Move left, keep the 0
                } else if (input[head] == '1') {
                    // Found rightmost 1, keep it and go to state C to flip remaining bits
                    state = 'C';
                    head--; // Move left to start flipping
                } else if (input[head] == 'B') {
                    // Reached left end with all zeros - 2's complement of all zeros is all zeros
                    state = 'D';
                } else {
                    return "ERROR in state B";
                }
                break;
                
            case 'C':
                // Flip all remaining bits (complement)
                if (input[head] == '0') {
                    input[head] = '1';
                    head--;
                } else if (input[head] == '1') {
                    input[head] = '0';
                    head--;
                } else if (input[head] == 'B') {
                    // Reached left blank, done
                    state = 'D';
                } else {
                    return "ERROR in state C";
                }
                break;
                
            case 'D':
                // Final state - clean up and return
                cout << "Final result: " << input << endl;
                
                // Remove blanks
                if (!input.empty() && input.front() == 'B') {
                    input.erase(input.begin());
                }
                if (!input.empty() && input.back() == 'B') {
                    input.pop_back();
                }
                return input;
                
            default:
                return "ERROR: Unknown state";
        }
        
        cout << "After transition: " << input << " Head at: " << head << " State: " << state << endl;
        cout << "---" << endl;
        
        // Safety check to avoid infinite loops
        static int steps = 0;
        if (++steps > 100) {
            return "ERROR: Too many steps, possible infinite loop";
        }
    }
}

int main() {
    string input;
    cout << "Enter binary input: ";
    cin >> input;
    
    // Validate input
    for (char c : input) {
        if (c != '0' && c != '1') {
            cout << "Invalid input. Only binary digits (0,1) are allowed." << endl;
            return 0;
        }
    }
    
    if (input.empty()) {
        cout << "Empty input not allowed." << endl;
        return 0;
    }
    
    cout << "\nProcessing: " << input << endl;
    cout << "=========================" << endl;
    
    string result = tm(input);
    
    if (result.find("ERROR") != string::npos) {
        cout << result << endl;
    } else {
        cout << "\n2's complement of " << input << " : " << result << endl;
    }
    
    return 0;
}