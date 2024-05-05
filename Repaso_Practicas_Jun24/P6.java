import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class P6 {
    public static void main(String args[]) {
        try {
            Ejercicio2.main(args);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/**
 * Deseamos que varias hebras soportadas mediante herencia de la cla-
 * se Thread utilicen un TAD lista de enteros, implementado con un array de
 * forma segura. Provea una soluci ́on llamada tADListaSegura.java que cum-
 * pla con la especificaci ́on descrita, utilizando cerrojos synchronized.Ahora,
 * escriba un programa en usaTADListaSegura.java que instancie una lista
 * 1
 * segura y m ́ultiples hebras soportadas mediante la interfaz Runnable que la
 * utilicen.
 */
class Ejercicio2 {
    static ListaSegura lista = new ListaSegura();

    // La lista segura tendra las operaciones insertar, eliminar, ver
    public static void main(String args[]) throws InterruptedException {

        // Creamos 2 Hilos
        Thread t1 = new Thread(new TareaRunnable());
        Thread t2 = new Thread(new TareaRunnable());
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        // Comprobamos el resultado
        lista.printList();
    }

}

class TareaRunnable implements Runnable {

    @Override
    public void run() {

        // Metemos numeros pseudoaleatorios en la lista
        for (int i = 0; i < 10; i++) {
            Ejercicio2.lista.insertar(ThreadLocalRandom.current().nextInt());
        }

    }

}

// Clase protegida mediante el uso de metodos synchronized
class ListaSegura {

    private List<Integer> _iLista = new ArrayList<>();

    // Se inserta al final
    synchronized void insertar(Integer i) {
        _iLista.addLast(i);
    }

    synchronized void eliminarUltimo() {
        _iLista.removeLast();
    }

    synchronized void printList() {
        for (Integer i : _iLista) {
            System.out.print(i + ", ");
        }
    }

}

/**
 * Escriba, utilizando synchronized, un c ́odigo donde tres hebras dife-
 * rentes (soportadas por herencia de Thread) entren en deadlock. Guarde el
 * c ́odigo en deadlock.java
 */
class Shared {
    // La clave esta en que method1 llama a 2 y viceversa creando un deadlock
    // haciendo que el primer hilo que la tenga se quede con el infinitamente.
    synchronized void method1(Shared s) {
        s.method2(this);
    }

    synchronized void method2(Shared s) {
        s.method1(this);
    }
}

class ThreadA extends Thread {
    private Shared s1;
    private Shared s2;

    ThreadA(Shared s1, Shared s2) {
        this.s1 = s1;
        this.s2 = s2;
    }

    public void run() {
        s1.method1(s2);
    }
}

class ThreadB extends Thread {
    private Shared s1;
    private Shared s2;

    ThreadB(Shared s1, Shared s2) {
        this.s1 = s1;
        this.s2 = s2;
    }

    public void run() {
        s2.method2(s1);
    }
}

class Deadlock {
    public static void main(String[] args) {
        Shared s1 = new Shared();
        Shared s2 = new Shared();

        ThreadA t1 = new ThreadA(s1, s2);
        ThreadB t2 = new ThreadB(s1, s2);

        t1.start();
        t2.start();
    }
}