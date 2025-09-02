/*Lab 4.2: WAP to remove the left factoring of given grammar
	E->iRtsAs|iEts
	A->a
*/
#include <iostream>
#include <string>
using namespace std;

void leftFactoring(char nonTerminal, const string& prod1, const string& prod2) {
    string prefix, alpha, beta;
    int i = 0;

    // Find common prefix
    while (i < prod1.length() && i < prod2.length() && prod1[i] == prod2[i]) {
        prefix += prod1[i];
        i++;
    }

    // Extract remaining parts
    alpha = prod1.substr(i);
    beta = prod2.substr(i);

    // Output transformed grammar
    cout << nonTerminal << " -> " << prefix << nonTerminal << "'" << endl;
    cout << nonTerminal << "' -> ";

    if (!alpha.empty())
        cout << alpha;
    else
        cout << "e";

    cout << " | ";

    if (!beta.empty())
        cout << beta;
    else
        cout << "e";

    cout << endl;
}

int main() {
    cout << "Original Grammar:" << endl;
    cout << "E -> iEtsAs | iEts" << endl;
    cout << "A -> a" << endl << endl;

    cout << "Grammar after Left Factoring:" << endl;
    leftFactoring('E', "iEtsAs", "iEts");

    // A stays same
    cout << "A -> a" << endl;

    return 0;
}

