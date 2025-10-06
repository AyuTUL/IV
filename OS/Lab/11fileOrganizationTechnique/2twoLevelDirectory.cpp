// Lab 11.2: WAP to simulate two level directory file organization technique
#include <iostream>
#include <vector>
#include <string>
#include <map>
#include <algorithm>
using namespace std;

void listAllUsers(const map<string, vector<string>>& directories) {
    if (directories.empty()) {
        cout << "No users in the system." << endl;
        return;
    }
    
    cout << endl << "All Users in System:" << endl
         << string(25, '-') << endl;
    for (const auto& user : directories) {
        cout << user.first << " (" << user.second.size() << " files)" << endl;
    }
}

void listUserFiles(const map<string, vector<string>>& directories) {
    string username;
    cout << "Enter username : ";
    cin >> username;
    
    auto it = directories.find(username);
    if (it == directories.end()) {
        cout << "User '" << username << "' not found." << endl;
        return;
    }
    
    if (it->second.empty()) {
        cout << "No files in " << username << "'s directory." << endl;
        return;
    }
    
    cout << endl << username << "'s Directory Contents:" << endl
         << string(30, '-') << endl;
    for (const auto& file : it->second) {
        cout << file << endl;
    }
    cout << endl << "Total files: " << it->second.size() << endl;
}

void searchFile(const map<string, vector<string>>& directories) {
    string username, filename;
    cout << "Enter username : ";
    cin >> username;
    cout << "Enter file name to search : ";
    cin >> filename;
    
    auto userIt = directories.find(username);
    if (userIt == directories.end()) {
        cout << "User '" << username << "' not found." << endl;
        return;
    }
    
    auto fileIt = find(userIt->second.begin(), userIt->second.end(), filename);
    if (fileIt != userIt->second.end()) {
        cout << "File '" << filename << "' found in " << username << "'s directory." << endl;
    } else {
        cout << "File '" << filename << "' not found in " << username << "'s directory." << endl;
    }
}

void deleteFile(map<string, vector<string>>& directories) {
    string username, filename;
    cout << "Enter username : ";
    cin >> username;
    cout << "Enter file name to delete : ";
    cin >> filename;
    
    auto userIt = directories.find(username);
    if (userIt == directories.end()) {
        cout << "User '" << username << "' not found." << endl;
        return;
    }
    
    auto fileIt = find(userIt->second.begin(), userIt->second.end(), filename);
    if (fileIt != userIt->second.end()) {
        userIt->second.erase(fileIt);
        cout << "File '" << filename << "' deleted from " << username << "'s directory." << endl;
    } else {
        cout << "File '" << filename << "' not found in " << username << "'s directory." << endl;
    }
}

void addFile(map<string, vector<string>>& directories) {
    string username, filename;
    cout << "Enter username : ";
    cin >> username;
    cout << "Enter file name to add : ";
    cin >> filename;
    
    auto userIt = directories.find(username);
    if (userIt == directories.end()) {
        cout << "User '" << username << "' not found." << endl;
        return;
    }
    
    auto fileIt = find(userIt->second.begin(), userIt->second.end(), filename);
    if (fileIt != userIt->second.end()) {
        cout << "File '" << filename << "' already exists in " << username << "'s directory." << endl;
    } else {
        userIt->second.push_back(filename);
        cout << "File '" << filename << "' added to " << username << "'s directory." << endl;
    }
}

void addUser(map<string, vector<string>>& directories) {
    string username;
    cout << "Enter new username : ";
    cin >> username;
    
    if (directories.find(username) != directories.end()) {
        cout << "User '" << username << "' already exists." << endl;
    } else {
        directories[username] = vector<string>();
        cout << "User '" << username << "' added successfully." << endl;
    }
}

int main() {
    int numUsers;
    cout << "Enter no. of users : ";
    cin >> numUsers;
    
    map<string, vector<string>> directories;
    
    // Create users and their files
    for (int i = 0; i < numUsers; i++) {
        string username;
        int numFiles;
        
        cout << "Enter username : ";
        cin >> username;
        cout << "Enter no. of files for " << username << " : ";
        cin >> numFiles;
        
        vector<string> userFiles;
        for (int j = 0; j < numFiles; j++) {
            string filename;
            cout << "Enter file name : ";
            cin >> filename;
            userFiles.push_back(filename);
        }
        
        directories[username] = userFiles;
    }
    
    int choice;
    do {
        cout << endl << "---Two Level Directory---" << endl
             << "   1. List all users" << endl
             << "   2. List user files" << endl
             << "   3. Search file" << endl
             << "   4. Delete file" << endl
             << "   5. Add file" << endl
             << "   6. Add user" << endl
             << "   7. Exit" << endl
             << "Enter choice : ";
        cin >> choice;
        
        switch (choice) {
            case 1:
                listAllUsers(directories);
                break;
            case 2:
                listUserFiles(directories);
                break;
            case 3:
                searchFile(directories);
                break;
            case 4:
                deleteFile(directories);
                break;
            case 5:
                addFile(directories);
                break;
            case 6:
                addUser(directories);
                break;
            case 7:
                cout << "Exiting program." << endl;
                break;
            default:
                cout << "Invalid choice. Please choose from 1-7." << endl;
        }
    } while (choice != 7);
    
    return 0;
}