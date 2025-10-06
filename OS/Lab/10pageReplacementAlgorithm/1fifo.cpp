// Lab 10.1: WAP to simulate FIFO page replacement algorithm
#include <iostream>
#include <vector>
#include <queue>
#include <iomanip>
using namespace std;

void printTableHeader(int nFrames)
{
    cout << endl
         << "FIFO Page Replacement Algorithm :" << endl;

    // Top border
    cout << "+-------+";
    for (int i = 0; i < nFrames; i++)
        cout << "-------+";
    cout << "--------+" << endl;

    // Header row
    cout << "| Pages |";
    for (int i = 0; i < nFrames; i++)
        cout << "  F " << i + 1 << "  |";
    cout << " Status |" << endl;

    // Header separator
    cout << "+-------+";
    for (int i = 0; i < nFrames; i++)
        cout << "-------+";
    cout << "--------+" << endl;
}

void printFrameState(const vector<int> &frames, int nFrames, int page, bool isHit)
{
    cout << "|   " << page << "   |";
    for (int i = 0; i < nFrames; i++)
        if (i < frames.size())
            cout << "   " << frames[i] << "   |";
        else
            cout << "   -   |";
    cout << " " << (isHit ? "  HIT" : " MISS") << "  |" << endl;
}

void printTableFooter(int nFrames)
{
    cout << "+-------+";
    for (int i = 0; i < nFrames; i++)
        cout << "-------+";
    cout << "--------+" << endl;
}

int main()
{
    int nFrames, nPages;
    cout << "Enter number of frames : ";
    cin >> nFrames;
    cout << "Enter number of pages : ";
    cin >> nPages;

    vector<int> pages(nPages);
    cout << "Enter page reference string : ";
    for (int i = 0; i < nPages; i++)
        cin >> pages[i];

    queue<int> q;
    vector<int> frames;
    int pageFaults = 0;
    int pageHits = 0;

    printTableHeader(nFrames);

    for (int i = 0; i < nPages; i++)
    {
        bool found = false;

        // Check if page is already in frames (HIT)
        for (int f : frames)
            if (f == pages[i])
            {
                found = true;
                pageHits++;
                break;
            }

        if (!found) // MISS
        {
            if ((int)frames.size() < nFrames)
            {
                // Add page to empty frame
                frames.push_back(pages[i]);
                q.push(pages[i]);
            }
            else
            {
                // Replace oldest page (FIFO)
                int remove = q.front();
                q.pop();
                for (int k = 0; k < (int)frames.size(); k++)
                {
                    if (frames[k] == remove)
                    {
                        frames[k] = pages[i];
                        break;
                    }
                }
                q.push(pages[i]);
            }
            pageFaults++;
        }

        printFrameState(frames, nFrames, pages[i], found);
    }

    printTableFooter(nFrames);

    cout << "\nRESULTS:" << endl
         << string(30, '-') << endl
         << "Total Page References : " << nPages << endl
         << "Total Page Faults     : " << pageFaults << endl
         << "Total Page Hits       : " << pageHits << endl;

    double missRatio = (double)pageFaults / nPages * 100;
    double hitRatio = (double)pageHits / nPages * 100;

    cout << fixed << setprecision(2) << "Miss Ratio            : " << missRatio << "%" << endl
         << "Hit Ratio             : " << hitRatio << "%" << endl;

    return 0;
}