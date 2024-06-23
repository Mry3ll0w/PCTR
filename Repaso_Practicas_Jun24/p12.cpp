#include <condition_variable>
#include <iostream>
#include <mutex>
#include <ostream>
#include <thread>

class Barrera {

  // Private Vars
  const int _iLimite;
  int _iEsperando;
  std::mutex _mtx; // Gestion de la exclusión mutua
  std::condition_variable cvBarreraLlena;

public:
  Barrera(const int limite) : _iLimite(limite), _iEsperando(0) {}

  // Espera de barrera
  void threadToWait() {
    std::unique_lock<std::mutex> lock(_mtx);
    _iEsperando++;
    // Comprobamos que no haya mas procesos esperando
    if (_iEsperando < _iLimite) {
      std::cout << "Proceso en espera" << std::endl;
      cvBarreraLlena.wait(lock);
    } else
      liberaBarrera();
  }

  // Libera los elementos parados en la barrera
  void liberaBarrera() {
    // std::unique_lock<std::mutex> lock(_mtx);

    cvBarreraLlena.notify_all(); // Notifica a todos ya que libera a todos los
                                 // procesos que esperan.
    // Llamamos 3 veces ya que tenemos 3 procesos esperando, no un único.

    _iEsperando = 0;
    std::cout << "Proceso liberado" << std::endl;
  }
};

// Definimos la tarea del hilo
void tareaHilo(Barrera &b) { b.threadToWait(); }

int main() {

  // Definimos los Threads
  std::vector<std::thread> hilos(6);

  // Barrera
  Barrera barrier(3);

  for (int i = 0; i < 6; i++) {
    hilos[i] = (std::thread(tareaHilo, std::ref(barrier)));
  }

  for (auto &i : hilos)
    i.join();
}
