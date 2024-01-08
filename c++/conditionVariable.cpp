#include <iostream>
#include <thread>
#include <mutex>
#include <condition_variable>
using namespace std; // Avoid using std:: ....

// Usada sobre tdoo dentro de monitores
std::mutex mtx;
std::condition_variable cv;
bool ready = false;

void print_id(int id)
{
    std::unique_lock<std::mutex> lck(mtx); // gestiona automaticamente semaforo
    while (!ready)
        cv.wait(lck);
    // SC
    cout << "ID: " << id << endl;
}

void go()
{
    std::unique_lock<std::mutex> lck(mtx);
    ready = true;
    cv.notify_all();
}

int main()
{
    std::thread threads[10];
    for (int i = 0; i < 6; i++)
    {
        threads[i] = std::thread(print_id, i);
        go();
    }
    for (auto &t : threads)
        t.join();

    return 0;
}