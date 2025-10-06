// Lab 10.4: WAP to simulate optimal page replacement algorithm
#include <iostream>
#include <vector>
#include <iomanip>
using namespace std;

void printTableHeader(int nFrames)
{
    cout << endl
         << "OPTIMAL Page Replacement Algorithm :" << endl;

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

    vector<int> frames;
    vector<int> arrivalTime(nFrames, -1); // Track when each frame arrived (for FIFO tie-breaking)
    int pageFaults = 0;
    int pageHits = 0;

    printTableHeader(nFrames);

    for (int i = 0; i < nPages; i++)
    {
        bool found = false;

        // Check if page is already in frames (HIT)
        for (int j = 0; j < frames.size(); j++)
        {
            if (frames[j] == pages[i])
            {
                found = true;
                pageHits++;
                break;
            }
        }

        if (!found) // MISS
        {
            if ((int)frames.size() < nFrames)
            {
                // Add page to empty frame
                frames.push_back(pages[i]);
                arrivalTime[frames.size() - 1] = i;
            }
            else
            {
                // Find optimal frame to replace with proper FIFO tie-breaking
                int replaceIdx = 0;
                int farthest = i + 1;
                
                for (int j = 0; j < nFrames; j++)
                {
                    int nextUse = nPages + 1; // Sentinel: assume never used again
                    
                    // Find next occurrence of this frame's page
                    for (int k = i + 1; k < nPages; k++)
                    {
                        if (pages[k] == frames[j])
                        {
                            nextUse = k;
                            break;
                        }
                    }
                    
                    // Choose page based on optimal strategy with FIFO tie-breaking
                    if (nextUse > farthest || 
                        (nextUse == farthest && nextUse == nPages + 1 && arrivalTime[j] < arrivalTime[replaceIdx]))
                    {
                        farthest = nextUse;
                        replaceIdx = j;
                    }
                }
                
                // Replace optimal frame and update its arrival time
                frames[replaceIdx] = pages[i];
                arrivalTime[replaceIdx] = i;
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