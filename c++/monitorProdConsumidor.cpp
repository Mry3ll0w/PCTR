#include <iostream>
#include <mutex>
#include <thread>
#include <condition_variable>

using namespace std; // Avoid using std:: ....

// Modelamos el buffer
struct Buffer
{
    int *buffer;
    int tam;
    int In_Ptr, Out_Ptr, cont;

    mutex em;
    condition_variable not_full, not_empty;
    Buffer(int capacidad)
    {
        tam = capacidad;
        cont = In_Ptr = Out_Ptr = 0;
        buffer = new int[tam];
    }
    ~Buffer() { delete[] buffer; }

    void producir(int dato)
    {
        unique_lock<mutex> lck(em);
        while (cont == tam)
            not_full.wait(lck);
        // Ya hay hueco para producir
        buffer[In_Ptr] = dato;
        In_Ptr = (In_Ptr + 1) % tam;
        ++cont;

        not_empty.notify_one();

        lck.unlock();
    }

    int consumir()
    {
        unique_lock<mutex> lck(em); // Synchronized de java
        while (cont == 0)
            not_empty.wait(lck); // Si esta vacio no puedo consumir
        int result = buffer[Out_Ptr];
        cont--;
        Out_Ptr = (Out_Ptr + 1) % tam;
        not_full.notify_one(); // Al haberse consumido al menos tenemos un hueco, igual que el notify normal
        lck.unlock();
        return result;
    }
};

int main()
{

    return 0;
}
