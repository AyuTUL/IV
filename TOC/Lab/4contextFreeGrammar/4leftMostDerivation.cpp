/*Lab 4.4: WAP to find left most derivation of given grammar
	S->S*S|S+S|-S|(S)|id
	string: -(id*id)+id
*/	
#include <bits/stdc++.h>
using namespace std;

struct Match {
    int pos = -1;
    int len = 0;
    string pat;
    bool found() const { return pos >= 0; }
};

Match find_leftmost_handle(const string& s, const vector<string>& patterns) {
    Match best;
    for (const string& pat : patterns) {
        size_t p = s.find(pat);
        if (p != string::npos) {
            if (best.pos == -1 || (int)p < best.pos || ((int)p == best.pos && (int)pat.size() > best.len)) {
                best.pos = (int)p;
                best.len = (int)pat.size();
                best.pat = pat;
            }
        }
    }
    return best;
}

int main() {
    string target = "-(id*id)+id";
    vector<string> reduce_patterns = { "id", "(S)", "-S", "S*S", "S+S" };
    vector<string> reductions;
    reductions.push_back(target);
    string cur = target;
    const int MAX_STEPS = 1000;

    for (int step = 0; step < MAX_STEPS && cur != "S"; ++step) {
        Match m = find_leftmost_handle(cur, reduce_patterns);
        if (!m.found()) {
            cerr << "Reduction failed: no handle found in \"" << cur << "\"\n";
            return 1;
        }
        cur = cur.substr(0, m.pos) + "S" + cur.substr(m.pos + m.len);
        reductions.push_back(cur);
    }

    if (cur != "S") {
        cerr << "Reduction failed: step limit reached.\n";
        return 1;
    }

    reverse(reductions.begin(), reductions.end());
    cout << "Leftmost Derivation:\n";
    for (size_t i = 0; i < reductions.size(); ++i) {
        cout << (i + 1) << ": " << reductions[i] << "\n";
    }
    return 0;
}

