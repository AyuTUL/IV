// Lab 10.3: WAP to simulate LFU page replacement algorithm
#include <iostream>
#include <vector>
#include <iomanip>
using namespace std;

void printTableHeader(int nFrames)
{
    cout << endl
         << "LFU Page Replacement Algorithm :" << endl;

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
    vector<int> frequency(nFrames, 0);    // Track frequency of each frame
    vector<int> arrivalTime(nFrames, -1); // Track when each frame arrived (for FIFO tie-breaking)
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
            // Increment frequency (arrival time stays the same for FIFO tie-breaking)
            frequency[frameIndex]++;
        else // MISS
        {
            if ((int)frames.size() < nFrames)
            {
                // Add page to empty frame
                frames.push_back(pages[i]);
                frequency[frames.size() - 1] = 1;
                arrivalTime[frames.size() - 1] = i;
            }
            else
            {
                // Find LFU frame (frame with minimum frequency)
                // In case of tie, use FIFO (smallest arrival time)
                int lfuIndex = 0;
                for (int j = 1; j < nFrames; j++)
                {
                    if (frequency[j] < frequency[lfuIndex] ||
                        (frequency[j] == frequency[lfuIndex] && arrivalTime[j] < arrivalTime[lfuIndex]))
                        lfuIndex = j;
                }
                // Replace LFU frame
                frames[lfuIndex] = pages[i];
                frequency[lfuIndex] = 1;
                arrivalTime[lfuIndex] = i;
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