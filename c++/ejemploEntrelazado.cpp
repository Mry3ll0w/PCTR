#include <iostream>
#include <thread>
#include <vector>

void entrelazados(int &n);

int main()
{
    std::vector<std::thread> threads;
    int n = 6;
    for (int i = 0; i < 6; i++)
    {
        threads.push_back(std::thread(entrelazados, std::ref(n))); // En el momento que se instancia comienza, es el equivalente a thread.start de java.
    }

    // Ahora espera a que se acaben los hilos para evitar el error de punteros
    for (auto &i : threads)
    {
        i.join();
    }

    std::cout << "Valor de n: " << n << std::endl;

    return 0;
}

void entrelazados(int &n)
{
    for (size_t i = 0; i < 6; i++)
    {
        n--;
    }
}