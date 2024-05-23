#include <iostream>
#include <thread>
#include <mutex>
#include <condition_variable>
#include <vector>

class BarreraCiclica {
    const int _limite;
    int _enCola;
    std::mutex _mutex;
    std::condition_variable _cv;

public:
    BarreraCiclica(int limite) : _limite(limite), _enCola(0) {}

    void esperar() {
        std::unique_lock<std::mutex> lock(_mutex);
        _enCola++;
       
        // ! Usamos if else en vez de while para evitar problemas de bloqueo. 
        if (_enCola < _limite) {
            std::cout << "Hilo esperando en la barrera\n";
            _cv.wait(lock);
        } else {
            std::cout << "Barrera liberada\n";
            _enCola = 0; // Reiniciar la barrera para su uso cíclico
            _cv.notify_all();
        }
    }
};

void hiloTrabajo(BarreraCiclica &barrera) {
    // Simulación de trabajo antes de la barrera
    std::this_thread::sleep_for(std::chrono::milliseconds(100));
    barrera.esperar();
    // Simulación de trabajo después de la barrera
    std::this_thread::sleep_for(std::chrono::milliseconds(100));
}

int main() {
    const int numHilos = 6;
    const int limite = 3; // Número de hilos a esperar en la barrera
    BarreraCiclica barrera(limite);
    std::vector<std::thread> hilos;

    for (int i = 0; i < numHilos; ++i) {
        hilos.emplace_back(hiloTrabajo, std::ref(barrera));
    }

    for (auto &hilo : hilos) {
        hilo.join();
    }

    return 0;
}

