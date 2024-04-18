import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReProdConsumidores {
    private boolean[] buffer = new boolean[10];
    private int count = 0;
    private ReentrantLock lock = new ReentrantLock();// Cerrojo excl mutua
    private Condition notFull = lock.newCondition();// Condiciones a proteger
    private Condition notEmpty = lock.newCondition();

    public void produce() throws InterruptedException {
        lock.lock();// Pillo exclusion mutua
        try {
            while (count == buffer.length) {// Si no hay hueco para producir espero
                notFull.await();
            }
            buffer[count] = true;// Produce
            count++;// Aumenta los elementos consumidos
            System.out.println("Producido, count = " + count);
            notEmpty.signal();// Tras producir no puede estar vacia, libero a los que esperan
        } finally {
            lock.unlock();
        }
    }

    public void consume() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {// Si no hay no puedo consumir
                notEmpty.await();
            }
            buffer[count - 1] = false;
            count--;
            System.out.println("Consumido, count = " + count);
            notFull.signal();// Tras consumir no puede estar lleno, por lo que libero a los que esperan
        } finally {
            lock.unlock();
        }
    }
}

class MonitorProdConsumidor {

    private ReentrantLock lock;
    private Condition cBufferVacio = lock.newCondition();
    private Condition cBufferLleno = lock.newCondition();

}