#include <iostream>
#include <thread>
#include <atomic>

using namespace std; // Avoid using std:: ....
std::atomic_int i(0);
std::atomic_char c('c');
std::atomic_bool b(false);

void fun(char r) // Al usar atomic todas las operaciones contempladas (++,--,=,...)  son atomicas
{
    i++;
    c = r;
    b = !b;
}

int main()
{
    char c = 'a';
    thread t1(fun, std::ref(c));
    c = 'b';
    thread t2(fun, std::ref(c));
    c = 'c';
    thread t3(fun, std::ref(c));
    t1.join();
    t2.join();
    t3.join();

    cout << "Valor de las atomicas: " << endl;
    cout << "i " << i << endl;
    cout << "c " << c << endl;
    cout << "b " << b << endl;

    return 0;
}