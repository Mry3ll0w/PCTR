#include <iostream>
#include <thread>
#include <vector>
#include <mutex>

void entrelazadoProtegido(int &n);

std::mutex cerrojo;                 // Declaramos el cerrojo de forma global para poder referenciarlo desde cualquier funcion.
std::recursive_mutex reentrantLock; // Es el reentrant lock de Java

int main()
{
    std::vector<std::thread> threads;
    int n = 0;
    for (int i = 0; i < 6; i++)
    {
        threads.push_back(std::thread(entrelazadoProtegido, std::ref(n))); // En el momento que se instancia comienza, es el equivalente a thread.start de java.
    }

    // Ahora espera a que se acaben los hilos para evitar el error de punteros
    for (auto &i : threads)
    {
        i.join();
    }

    std::cout << "Valor de n: " << n << std::endl;

    return 0;
}

void entrelazadoProtegido(int &n)
{
    for (size_t i = 0; i < 6; i++) // Seccion Critica
    {
        std::lock_guard<std::mutex> guarda(cerrojo); // Maneja automaticamente el wait y el signal del cerrojo, se puede definir con reentrant o normal
        n++;
    }
}