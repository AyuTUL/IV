// Lab 10.2: WAP to simulate LRU page replacement algorithm
#include <iostream>
#include <vector>
#include <iomanip>
using namespace std;

void printTableHeader(int nFrames)
{
    cout << endl
         << "LRU Page Replacement Algorithm :" << endl;

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
    vector<int> lastUsed(nFrames, -1); // Track when each frame was last used
    int pageFaults = 0;
    int pageHits = 0;

    printTableHeader(nFrames);

    for (int i = 0; i < nPages; i++)
    {
        bool found = false;
        int frameIndex = -1;

        // Check if page is already in frames (HIT)
        for (int j = 0; j < frames.size(); j++)
            if (frames[j] == pages[i])
            {
                found = true;
                frameIndex = j;
                pageHits++;
                break;
            }

        if (found)
            // Update last used time for this frame
            lastUsed[frameIndex] = i;
        else // MISS
        {
            if ((int)frames.size() < nFrames)
            {
                // Add page to empty frame
                frames.push_back(pages[i]);
                lastUsed[frames.size() - 1] = i;
            }
            else
            {
                // Find LRU frame (frame with minimum lastUsed value)
                int lruIndex = 0;
                for (int j = 1; j < nFrames; j++)
                    if (lastUsed[j] < lastUsed[lruIndex])
                    {
                        lruIndex = j;
                    }
                // Replace LRU frame
                frames[lruIndex] = pages[i];
                lastUsed[lruIndex] = i;
            }
            pageFaults++;
        }

        printFrameState(frames, nFrames, pages[i], found);
    }

    printTableFooter(nFrames);

    cout << endl
         << "Results :" << endl
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