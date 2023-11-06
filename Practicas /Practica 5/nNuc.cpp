#include <iostream>
#include <thread>
// se usa pthread para coger
int main()
{
    unsigned int n = std::thread::hardware_concurrency();
    std::cout << "Nucleos disponibles: " << n;
}