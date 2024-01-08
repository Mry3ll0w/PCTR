#include <iostream>
#include <thread>
#include <mutex>
using namespace std; // Avoid using std:: ....
once_flag bandera;
void fun()
{
    call_once(bandera, []
              { cout << "llamada lambda" << endl; });
}
int main()
{
    thread t1(fun); // Al usar call once solo se ejecuta una vez, el resto de elementos no pueden.
    thread t2(fun);
    thread t3(fun);

    t1.join();
    t2.join();
    t3.join();
    return 0;
}