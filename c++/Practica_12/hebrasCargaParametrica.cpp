// hebras en C++ que procesan funciones con carga parametrica...
// 17/01/2017
// A.T.

#include <iostream>
#include <thread>
using namespace std;

// Cerrojo siempre declaramos como global.
std::mutex cerrojo;

void hola(int cotaSuperior, int &totalHits) // codigo a ejecutar por la hebras con parametros
{
    int hits = 0;
    // cout << "Hola Mundo..." << this_thread::get_id() << " ";
    for (int i = 0; i < cotaSuperior; i++)
        hits++;
    // Proteccion de cerrrojo
    cerrojo.lock();
    totalHits += hits; // SC
    cerrojo.unlock();
}

int main()
{
    int nHilos = 10;
    int totalHits = 0;
    thread hilos[nHilos];
    for (int i = 0; i < nHilos; i++)
        hilos[i] = thread(hola, 100, std::ref(totalHits));
    // las hebras se crean -y ejecutan de esta forma

    for (int i = 0; i < nHilos; i++)
        hilos[i].join();
    cout << "Valor de totalHits: " << totalHits << endl;
    return (0);
}