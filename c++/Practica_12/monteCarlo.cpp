#include <iostream>
#include <random>
#include <math.h>

using namespace std; // Avoid using std:: ....
std::mutex m;
double generateRandomPoint()
{
    std::random_device rd;
    std::mt19937 gen(rd());
    std::uniform_real_distribution<> dis(0, 1); // La integral esta limitada entre 0 y 1
    return dis(gen);
}

double MonteCarloTask(int nTotalPoints, int &iSuccess)
{
    for (int i = 0; i < nTotalPoints; i++)
    {
        double dCoordenadaX = generateRandomPoint();
        double dCoordenadaY = generateRandomPoint();
        int n = 0;
        if (dCoordenadaY <= sin(dCoordenadaX))
        {
            n++;
        }
        m.lock();
        iSuccess += n;
        m.unlock();
    }
}

int main()
{

    return 0;
}
