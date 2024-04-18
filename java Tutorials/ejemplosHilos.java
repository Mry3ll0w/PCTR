import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.*;

public class ejemplosHilos {

}

class hiloBasico {

    static int n = 0;

    public static void main(String args[]) throws Exception {

        List<Thread> t = new ArrayList<Thread>();

        for (int i = 0; i < 1000; i++) {

            Thread task = new Thread(
                    () -> {
                        n = n + 1;
                    });
            t.add(task);

        }

        for (Thread i : t) {
            i.start();
        }

        for (Thread i : t)
            i.join();

        System.out.println();
    }
}

class primosParalelos {

    public static void main(String[] args) throws Exception {
        long nPuntos = Long.parseLong(args[0]);
        int nTareas = Integer.parseInt(args[1]);
        // int nTareas = Runtime.getRuntime().availableProcessors();
        long tVentana = nPuntos / nTareas;// Determino el numero de segmentos que tiene cada hebra
        long primosTotal = 0;
        long linf = 0;
        long lsup = tVentana;

        List<Future<Long>> contParciales = Collections.synchronizedList(
                new ArrayList<Future<Long>>());
        // ContParciales ==> Lo que voy a tener es una lista de n primos que cada tarea
        // encuentra
        // Future lo necesitamos ya que callable devuelve a futuro, el collections
        // sychronized sirve
        // para evitar problemas de consistencia de resultado con el uso de Future y
        // Callable

        long inicTiempo = System.nanoTime();

        ThreadPoolExecutor ept = new ThreadPoolExecutor(
                2, // numero minimo de hilos
                18, // numero maximo de hilos
                0L, // KeepAlive
                TimeUnit.MILLISECONDS, // Unidad de tiempo
                new LinkedBlockingQueue<Runnable>(1));// Cola de trabajo

        ept.allowCoreThreadTimeOut(true);

        for (int i = 0; i < nTareas; i++) {
            contParciales.add(
                    ept.submit(
                            new tareaPrimos(linf, lsup)));
            linf = lsup + 1;
            lsup += tVentana;
        }
        for (Future<Long> iterador : contParciales)
            try {
                primosTotal += iterador.get();// Get pilla el objeto Future para cogerlo cuando este.
            } catch (Exception e) {
            }

        long tiempoTotal = (System.nanoTime() - inicTiempo) / (long) 1.0e9;
        ept.shutdown();
        System.out.println("Primos hallados: " + primosTotal);
        System.out.println("Calculo finalizado en " + tiempoTotal + " segundos");
    }
}
