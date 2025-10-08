// Lab 11.3: WAP to simulate hierarchical directory file organization technique
#include <iostream>
#include <vector>
#include <string>
#include <map>
#include <algorithm>
using namespace std;

struct Directory
{
    string name;
    map<string, Directory *> subdirs;
    vector<string> files;
    Directory *parent;

    Directory(string n, Directory *p = nullptr) : name(n), parent(p) {}

    ~Directory()
    {
        for (auto &dir : subdirs)
            delete dir.second;
    }
};

Directory *root;
Directory *current;

void showCurrentLocation()
{
    cout << endl
         << "Current Directory : " << current->name;
    if (current->parent)
        cout << " (Parent : " << current->parent->name << ")";
    cout << endl;
}
void listDirectory()
{
    showCurrentLocation();
    cout << string(30, '-') << endl;

    if (!current->subdirs.empty())
    {
        cout << "Subdirectories :" << endl;
        for (auto &dir : current->subdirs)
            cout << "  " << dir.first << "/" << endl;
    }

    if (!current->files.empty())
    {
        cout << "Files :" << endl;
        for (auto &file : current->files)
            cout << "  " << file << endl;
    }

    if (current->subdirs.empty() && current->files.empty())
        cout << "  (empty directory)" << endl;
}

void changeDirectory()
{
    string dirName;
    cout << "Enter directory name (or '..' for parent) : ";
    cin >> dirName;

    if (dirName == "..")
        if (current->parent)
        {
            current = current->parent;
            cout << "Moved to parent directory." << endl;
        }
        else
            cout << "Already at root directory." << endl;
    else
    {
        auto it = current->subdirs.find(dirName);
        if (it != current->subdirs.end())
        {
            current = it->second;
            cout << "Changed to directory : " << dirName << endl;
        }
        else
            cout << "Directory '" << dirName << "' not found." << endl;
    }
}

void createDirectory()
{
    string dirName;
    cout << "Enter directory name : ";
    cin >> dirName;

    if (current->subdirs.find(dirName) != current->subdirs.end())
        cout << "Directory '" << dirName << "' already exists." << endl;
    else
    {
        current->subdirs[dirName] = new Directory(dirName, current);
        cout << "Directory '" << dirName << "' created." << endl;
    }
}

void deleteDirectory()
{
    string dirName;
    cout << "Enter directory name to delete : ";
    cin >> dirName;

    auto it = current->subdirs.find(dirName);
    if (it == current->subdirs.end())
        cout << "Directory '" << dirName << "' not found." << endl;
    else
    {
        delete it->second;
        current->subdirs.erase(it);
        cout << "Directory '" << dirName << "' deleted." << endl;
    }
}

void createFile()
{
    string fileName;
    cout << "Enter file name : ";
    cin >> fileName;

    auto it = find(current->files.begin(), current->files.end(), fileName);
    if (it != current->files.end())
        cout << "File '" << fileName << "' already exists." << endl;
    else
    {
        current->files.push_back(fileName);
        cout << "File '" << fileName << "' created." << endl;
    }
}

void deleteFile()
{
    string fileName;
    cout << "Enter file name to delete : ";
    cin >> fileName;

    auto it = find(current->files.begin(), current->files.end(), fileName);
    if (it == current->files.end())
        cout << "File '" << fileName << "' not found." << endl;
    else
    {
        current->files.erase(it);
        cout << "File '" << fileName << "' deleted." << endl;
    }
}

void searchFile()
{
    string fileName;
    cout << "Enter file name to search : ";
    cin >> fileName;

    auto it = find(current->files.begin(), current->files.end(), fileName);
    if (it != current->files.end())
        cout << "File '" << fileName << "' found." << endl;
    else
        cout << "File '" << fileName << "' not found." << endl;
}

int main()
{
    root = new Directory("root");
    current = root;

    cout << "--Hierarchical Directory--" << endl;

    int choice;
    do
    {
        cout << endl
             << "---Hierarchical Directory---" << endl
             << "   1. List current directory" << endl
             << "   2. Change directory" << endl
             << "   3. Create directory" << endl
             << "   4. Delete directory" << endl
             << "   5. Create file" << endl
             << "   6. Delete file" << endl
             << "   7. Search file" << endl
             << "   8. Exit" << endl
             << "Enter choice : ";
        cin >> choice;

        switch (choice)
        {
        case 1:
            listDirectory();
            break;
        case 2:
            changeDirectory();
            break;
        case 3:
            createDirectory();
            break;
        case 4:
            deleteDirectory();
            break;
        case 5:
            createFile();
            break;
        case 6:
            deleteFile();
            break;
        case 7:
            searchFile();
            break;
        case 8:
            cout << "Exiting program." << endl;
            break;
        default:
            cout << "Invalid choice. Please choose from 1-8." << endl;
        }
    } while (choice != 8);

    delete root;
    return 0;
}