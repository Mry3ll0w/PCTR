package Repaso_Practicas_Jun24;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.*;

public class EjemploThreadPool {

    public static void main(String args[]) throws Exception {
        // Ejemplos de Uso de Thread Executor, todo lo hacemos con el POOL altamente
        // customizable pq solo cae ese en realidad xdd

        ThreadPoolExecutor executorPool = new ThreadPoolExecutor(
                2, // Numero inicial de hilos con los que partimos salvo q el timeOut se lo funda
                4, // Numero maximo de Hilos que el pool va a tener
                1000L, // KeepAliveTime del Hilo en Idle
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());// Tipo de cola que se usa para procesar los hilos

        // Permitimos el funcionamiento del TimeOut
        executorPool.allowCoreThreadTimeOut(true);// Si esta a true el keepAlive tiene que ser > 0

        // Preparamos una lista de numeros aleatorios (ejemplo de uso de pool simple)
        List<Future<Integer>> lResultado = Collections.synchronizedList(
                new ArrayList<Future<Integer>>());

        // Mandamos las tareas al pool
        for (int i = 0; i < 5; i++) {
            // Metemos en lista
            lResultado.add(
                    executorPool.submit(() -> {
                        return ThreadLocalRandom.current().nextInt(1, 1000);// Lo hace como si fuese un callables
                    }));
        }

        // Mostramos los resultados de los hilos por pantalla pero tenemos q esperar a
        // que esten listos
        for (Future<Integer> it : lResultado) {
            try {
                Integer i = it.get();
                System.out.println(i);
            } catch (Error e) {
                // Si no esta listo lanza excepciones
            }
        }
        // Indicamos al pool que no acepte mas tareas y se apague
        executorPool.shutdown();
    }

}
