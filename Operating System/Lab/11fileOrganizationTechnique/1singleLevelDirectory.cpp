// Lab 11.1: WAP to simulate single level directory file organization technique
#include <iostream>
#include <vector>
#include <string>
#include <algorithm>
using namespace std;

void listFiles(const vector<string> &files)
{
    if (files.empty())
    {
        cout << "No files in directory." << endl;
        return;
    }

    cout << endl
         << "Single Level Directory Contents :" << endl
         << string(30, '-') << endl;
    for (const auto &f : files)
        cout << f << endl;
    cout << endl
         << "Total files : " << files.size() << endl;
}

void searchFile(const vector<string> &files)
{
    string searchName;
    cout << "Enter file name to search : ";
    cin >> searchName;

    auto it = find(files.begin(), files.end(), searchName);
    if (it != files.end())
        cout << "File '" << searchName << "' found in directory." << endl;
    else
        cout
            << "File '" << searchName << "' not found." << endl;
}

void deleteFile(vector<string> &files)
{
    string deleteName;
    cout << "Enter file name to delete : ";
    cin >> deleteName;

    auto it = find(files.begin(), files.end(), deleteName);
    if (it != files.end())
    {
        files.erase(it);
        cout << "File '" << deleteName << "' deleted successfully." << endl;
    }
    else
        cout << "File '" << deleteName << "' not found." << endl;
}

void addFile(vector<string> &files)
{
    string fname;
    cout << "Enter file name to add : ";
    cin >> fname;

    auto it = find(files.begin(), files.end(), fname);
    if (it != files.end())
        cout << "File '" << fname << "' already exists." << endl;
    else
    {
        files.push_back(fname);
        cout << "File '" << fname << "' added successfully." << endl;
    }
}

int main()
{
    int n;
    cout << "Enter no. of files to be created : ";
    cin >> n;

    vector<string> files;
    for (int i = 0; i < n; i++)
    {
        string fname;
        cout << "Enter file name : ";
        cin >> fname;
        files.push_back(fname);
    }

    int choice;
    do
    {
        cout << endl
             << "---Single Level Directory---" << endl
             << "   1. List all files" << endl
             << "   2. Search file" << endl
             << "   3. Delete file " << endl
             << "   4. Add file " << endl
             << "   5. Exit " << endl
             << "Enter choice : ";
        cin >> choice;

        switch (choice)
        {
        case 1:
            listFiles(files);
            break;
        case 2:
            searchFile(files);
            break;
        case 3:
            deleteFile(files);
            break;
        case 4:
            addFile(files);
            break;
        case 5:
            cout << "Exiting program." << endl;
            break;
        default:
            cout << "Invalid choice. Please choose from 1-5." << endl;
        }
    } while (choice != 5);

    return 0;
}
