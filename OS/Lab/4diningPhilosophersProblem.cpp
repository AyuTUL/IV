// Lab 4: WAP to simulate concept of dining philosophers problem
#include <iostream>
#include <thread>
#include <mutex>
#include <chrono>
#include <vector>

using namespace std;

mutex printMutex; // Add mutex for synchronized printing

void philosopher(int id, vector<mutex> &forks, int meals)
{
    int n = forks.size();
    int left = id;
    int right = (id + 1) % n;

    // Always lock forks in order: lower index first, then higher
    int first = min(left, right);
    int second = max(left, right);

    for (int meal = 1; meal <= meals; ++meal)
    {
        // Thinking phase
        this_thread::sleep_for(chrono::milliseconds(100));

        // Acquire forks in order (deadlock prevention)
        {
            unique_lock<mutex> lock1(forks[first]);
            unique_lock<mutex> lock2(forks[second]);

            // Eating phase
            {
                lock_guard<mutex> print_lock(printMutex);
                cout << "Philosopher " << (id + 1) << " eating meal " << meal << endl;
            }
            this_thread::sleep_for(chrono::milliseconds(150));

            // Locks automatically released when going out of scope
        }

        // Brief pause between meals
        this_thread::sleep_for(chrono::milliseconds(50));
    }

    {
        lock_guard<mutex> print_lock(printMutex);
        cout << "Philosopher " << (id + 1) << " finished all meals" << endl;
    }
}

int main()
{
    int numPhilosophers, mealsPerPhilosopher;

    cout << "Enter philosophers and meals per philosopher: ";
    cin >> numPhilosophers >> mealsPerPhilosopher;

    cout << "\nPhilosophers: " << numPhilosophers << " | Meals: " << mealsPerPhilosopher << endl;
    cout << string(40, '-') << endl;

    // Create forks (mutexes) and philosopher threads
    vector<mutex> forks(numPhilosophers);
    vector<thread> philosophers;

    // Start all philosopher threads
    for (int i = 0; i < numPhilosophers; ++i)
    {
        philosophers.emplace_back(philosopher, i, ref(forks), mealsPerPhilosopher);
    }

    // Wait for all philosophers to finish
    for (auto &p : philosophers)
    {
        p.join();
    }

    cout << string(40, '-') << endl;
    cout << "All philosophers finished successfully!" << endl;

    return 0;
}