#include <iostream>
#include <thread>
#include <vector>

void helloThread();

int main()
{
    std::vector<std::thread> threads;

    for (int i = 0; i < 4; i++)
    {
        threads.push_back(std::thread(helloThread)); // En el momento que se instancia comienza, es el equivalente a thread.start de java.
    }

    // Ahora espera a que se acaben los hilos para evitar el error de punteros
    for (auto &i : threads)
    {
        i.join();
    }

    return 0;
}

void helloThread() { std::cout << "Hola desde " << std::this_thread::get_id() << std::endl; }