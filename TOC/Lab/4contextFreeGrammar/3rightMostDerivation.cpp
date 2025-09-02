/*Lab 4.3: WAP to find right most derivation of given grammar
	S->S*S|S+S|-S|(S)|id
	string: -(id*id)+id
*/	
#include <iostream>
#include <string>
#include <vector>

using namespace std;

void replaceRightmostS(string &expr, const string &replacement) {
    int pos = expr.rfind('S');
    if (pos != string::npos) {
        expr.replace(pos, 1, replacement);
    }
}

void rightmostDerivation() {
    vector<string> steps;
    string expr = "S";
    steps.push_back(expr);
    replaceRightmostS(expr, "S+S");
    steps.push_back(expr);
    replaceRightmostS(expr, "ID");
    steps.push_back(expr);

    replaceRightmostS(expr, "-S");
    steps.push_back(expr);
    replaceRightmostS(expr, "(S)");
    steps.push_back(expr);
    replaceRightmostS(expr, "S*S");
    steps.push_back(expr);
    replaceRightmostS(expr, "ID");
    steps.push_back(expr);
    replaceRightmostS(expr, "ID");
    steps.push_back(expr);
    cout << "Rightmost derivation steps for the string '-(ID*ID)+ID':\n";
    for (int i = 0; i < steps.size(); i++) {
        cout << i + 1 << ": " << steps[i] << "\n";
    }
}

int main() {
    rightmostDerivation();
    return 0;
}

