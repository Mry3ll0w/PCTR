import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class P8 {
    public static void main(String[] args) {

        // Seccion pruebas Cuenta corriente
        pruebaCuentaCorriente();

        // Seccion pruebas impresoras
        pruebaImpresoras();
    }

    static void pruebaCuentaCorriente() {

        // Creamos cuenta corriente
        CuentaCorriente cuenta = new CuentaCorriente(100.0);

        // Creamos pool de hilos que saque dinero
        ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1));

        // Agregamos tareas al pool
        for (int i = 0; i < 10; i++) {
            // Primeros 5 van a intenar retirar
            if (i % 2 == 0) {
                pool.submit(() -> {
                    cuenta.retirarDinero(100.);
                });
            } else {
                pool.submit(() -> {
                    cuenta.ingresarDinero(100.);
                });
            }
        }
        pool.shutdown();
        cuenta.checkSaldo();
    }

    static void pruebaImpresoras() {
        Impresoras impresoras = new Impresoras();

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(6, 6, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());

        // Metemos tareas en el pool de hilos
        for (int i = 0; i < 6; i++) {
            // Metemos en cada hilo un numero aleatorio entre 0 y 2
            poolExecutor.submit(() -> {
                Integer nImpresora = impresoras.usarImpresora();
                impresoras.liberarImpresora(nImpresora);
            });
        }
        poolExecutor.shutdown();
    }
}

class CuentaCorriente {

    private Double _dSaldo;

    CuentaCorriente(Double dSaldo) {
        _dSaldo = dSaldo;
    }

    // Monitor con Synchronized (API Standard)
    public synchronized void ingresarDinero(final Double dinero) {
        _dSaldo += dinero;
    }

    public synchronized void retirarDinero(final Double dinero) {
        // Usamos condiciones de guarda
        while (_dSaldo < dinero) {
            try {
                wait();
            } catch (Exception e) {

            }
        }
        // Cuando sale de las condiciones de guarda retiramos el dinero y notificamos a
        // las dormidas
        _dSaldo -= dinero;

        notifyAll();// Para evitar inanicion notificamos a todas para evitar despertar hebras que no
                    // cumplen
    }

    // Mostramos saldo
    public synchronized void checkSaldo() {
        System.out.println("El saldo es: " + _dSaldo);
    }

}

class Impresoras {

    // Si estan en uso se ponen a true
    Boolean _abImpresoras[] = { true, false, false };

    synchronized int usarImpresora() {
        // Filtramos por las impresoras hasta llegar a true
        int iPosicionLibre = IntStream.range(0, _abImpresoras.length)
                .filter(i -> !_abImpresoras[i])
                .findFirst()
                .orElse(-1);
        while (iPosicionLibre == -1) {
            try {
                wait();
            } catch (Exception e) {
            }
        }
        // Si index no lo es entonces se marca en uso
        _abImpresoras[iPosicionLibre] = true;
        return iPosicionLibre;
    }

    synchronized void liberarImpresora(Integer iPos) {
        _abImpresoras[iPos] = true;
        notifyAll();
    }

}