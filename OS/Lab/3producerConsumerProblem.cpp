// Lab 3: WAP to implement producer-consumer problem using semaphores
#include <iostream>
#include <thread>
#include <vector>
#include <queue>
#include <semaphore.h>
#include <mutex>
#include <chrono>

using namespace std;

// Global variables
queue<int> buffer;
sem_t emptySem, fullSem;
mutex bufferMutex, printMutex;
int totalProduced = 0, totalConsumed = 0;
int bufferSize, totalItems, numProducers, numConsumers;

void producer(int id, int items)
{
    for (int i = 1; i <= items; ++i)
    {
        sem_wait(&emptySem);

        int item = id * 1000 + i;
        {
            lock_guard<mutex> lock(bufferMutex);
            buffer.push(item);
            totalProduced++;

            lock_guard<mutex> plock(printMutex);
            cout << "[P" << id << "] produced " << item
                 << " | Buffer: " << buffer.size() << "/" << bufferSize << endl;
        }

        sem_post(&fullSem);
        this_thread::sleep_for(chrono::milliseconds(50));
    }
}

void consumer(int id, int items)
{
    for (int i = 1; i <= items; ++i)
    {
        sem_wait(&fullSem);

        int item;
        {
            lock_guard<mutex> lock(bufferMutex);
            item = buffer.front();
            buffer.pop();
            totalConsumed++;

            lock_guard<mutex> plock(printMutex);
            cout << "  [C" << id << "] consumed " << item
                 << " | Buffer: " << buffer.size() << "/" << bufferSize << endl;
        }

        sem_post(&emptySem);
        this_thread::sleep_for(chrono::milliseconds(70));
    }
}

int main()
{
    cout << "Enter buffer size, total items, producers, consumers: ";
    cin >> bufferSize >> totalItems >> numProducers >> numConsumers;

    if (bufferSize <= 0 || totalItems <= 0 || numProducers <= 0 || numConsumers <= 0)
    {
        cout << "Error: All values must be positive!" << endl;
        return 1;
    }

    sem_init(&emptySem, 0, bufferSize);
    sem_init(&fullSem, 0, 0);

    cout << "\nBuffer: " << bufferSize << " | Items: " << totalItems
         << " | Producers: " << numProducers << " | Consumers: " << numConsumers << endl;
    cout << string(50, '-') << endl;

    vector<thread> producers, consumers;
    vector<int> prodItems(numProducers, totalItems / numProducers);
    vector<int> consItems(numConsumers, totalItems / numConsumers);

    // Distribute remainder
    for (int i = 0; i < totalItems % numProducers; i++)
        prodItems[i]++;
    for (int i = 0; i < totalItems % numConsumers; i++)
        consItems[i]++;

    // Start threads
    for (int i = 0; i < numProducers; i++)
        producers.emplace_back(producer, i, prodItems[i]);
    for (int i = 0; i < numConsumers; i++)
        consumers.emplace_back(consumer, i, consItems[i]);

    // Wait for completion
    for (auto &p : producers)
        p.join();
    for (auto &c : consumers)
        c.join();

    sem_destroy(&emptySem);
    sem_destroy(&fullSem);

    cout << string(50, '-') << endl;
    cout << "Result: Produced=" << totalProduced << " Consumed=" << totalConsumed;
    if (totalProduced == totalConsumed && totalProduced == totalItems)
        cout << " [SUCCESS]" << endl;
    else
        cout << " [ERROR]" << endl;

    return 0;
}