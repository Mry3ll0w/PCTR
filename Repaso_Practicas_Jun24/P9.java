import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class P9 {
    public static void main(String[] args) {
        pruebaBancoSem();
    }

    static void pruebaBanco() {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(4, 4, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        CuentaBanco banco = new CuentaBanco();
        for (int i = 4; i < 4; i++) {
            if (i % 2 == 0) {
                pool.submit(() -> {
                    banco.ingresarDinero(10.5);
                });
            } else {
                pool.submit(() -> {
                    banco.retirarDinero(10.5);
                });
            }

        }
        pool.shutdown();
        // Mostramos el saldo
        System.out.println("Banco: " + banco._dSaldo);
        // ! Si usamos el submit se admite callable y void, en execute solo void
    }

    static void pruebaBancoSem() {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(4, 4, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        CuentaBanco banco = new CuentaBanco();
        for (int i = 4; i < 4; i++) {
            if (i % 2 == 0) {
                pool.submit(() -> {
                    banco.ingresarDinero(10.5);
                });
            } else {
                pool.submit(() -> {
                    banco.retirarDinero(10.5);
                });
            }

        }
        pool.shutdown();
        // Mostramos el saldo
        System.out.println("Banco: " + banco._dSaldo);
        // ! Si usamos el submit se admite callable y void, en execute solo void
    }

}

class CuentaBanco {

    Double _dSaldo = 0.;
    ReentrantLock lock = new ReentrantLock();

    Condition conNoSaldo = lock.newCondition();

    void ingresarDinero(Double d) {
        lock.lock();// wait al mutex
        _dSaldo += d;
        lock.unlock();
    }

    void retirarDinero(Double d) {

        lock.lock();
        try {
            // Condicion de guarda ==> mientras el saldo no sea superior no se puede retirar
            // esa cantidad
            while (_dSaldo < d)
                conNoSaldo.await();
            _dSaldo -= d;
            conNoSaldo.signal();// Notificamos y liberamos

        } catch (Exception e) {
        } finally {
            lock.unlock();// Pase lo que pase se libera el lock en caso de no poder
        }

    }

}

class CuentaBancoSemaphore {

    Double _dSaldo = 0.;
    Semaphore sem = new Semaphore(1), saldo = new Semaphore(0);// Ya que al inicio no hay saldo

    void ingresarDinero(Double d) throws Exception {
        sem.acquire();// wait al mutex
        _dSaldo += d;
        sem.release();
    }

    void retirarDinero(Double d) throws Exception {

        sem.acquire();
        try {
            // Condicion de guarda ==> mientras el saldo no sea superior no se puede retirar
            // esa cantidad
            while (_dSaldo < d)
                saldo.notify();
            _dSaldo -= d;
            saldo.wait();// Notificamos y liberamos

        } catch (Exception e) {
        } finally {
            sem.release();// Pase lo que pase se libera el lock en caso de no poder
        }

    }

}

// Ejercicio 4

class ProductorConsumidor {

    // Simulamos el recurso a producir
    private Integer _aiRecursos[] = new Integer[5];

    // Semaforos y variables de condicion del monitor
    private ReentrantLock lock = new ReentrantLock();
    private Condition cBufferVacio = lock.newCondition();
    private Condition cBufferLleno = lock.newCondition();

    // Control de elementos del buffer
    private Integer _iElementosBuffer = 0;

    private final Integer _iBufferSize = 5;

    ProductorConsumidor() {
        for (int i = 0; i < _aiRecursos.length; i++) {
            _aiRecursos[i] = 0;// Inicialmente no tenemos nada producido
        }
    }

    // ! Al pedirnos usar la API de alto nivel para monitors se usan lock y
    // ! condition en vez de synchronized
    public void consumir() throws InterruptedException {
        lock.lock();
        // Condiciones de guardia
        while (_iElementosBuffer == 0) {
            cBufferVacio.await();
        }
        // Implica que existen elementos en el buffer
        Integer iPos = 0;
        while (_aiRecursos[iPos] == 0) {
            iPos++;
        }

        // Consumimos el que este en posicion
        _aiRecursos[iPos] = 0;
        _iElementosBuffer--;
        cBufferLleno.signal();// Como hemos consumido un elemento del buffer es imposible que no haya hueco
        lock.unlock();
    }

    public void producir() throws InterruptedException {
        lock.lock();
        // Condiciones de guarda
        while (_iElementosBuffer == _iBufferSize) {
            cBufferLleno.await();
        }

        // Hay hueco en el buffer por lo que producimos
        Integer iPos = 0;
        while (_aiRecursos[iPos] != 0) {
            iPos++;
        }

        // Producimos en hueco
        _aiRecursos[iPos] = ThreadLocalRandom.current().nextInt();

        _iElementosBuffer++;
        cBufferVacio.signal();
        lock.unlock();
    }

}
