import java.util.concurrent.ThreadLocalRandom;

public class P1 {
    static int n = 0;// Para que sea compartida por todos

    public static void main(String args[]) throws Exception {
        ej3EscaladoParalelo();
    }

    public static void ej1() throws Exception {

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                n++;
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                n--;
            }
        });

        // Lanzamos ambos hilos a la vez
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(n);
    }

    public static void ej2() throws InterruptedException {
        Ejercicio2 r1 = new Ejercicio2(true);
        Ejercicio2 r2 = new Ejercicio2(false);
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(n);

    }

    public static void ej3EscaladoParalelo() throws InterruptedException {

        // Primero construimos el vector
        Double dVector[] = new Double[1000000];
        for (int i = 0; i < dVector.length; i++) {
            Double dRand = ThreadLocalRandom.current().nextDouble();
            dVector[i] = dRand;
        }

        // Hacemos la division manual del dominio del vector
        Integer iDominio = 0;
        Integer iIncrement = 250000;

        Thread aHilos[] = new Thread[4];
        // Mostramos los 4 primeros del vector para comprobarlo
        System.out.print("Elementos del vector antes de incremento: ");
        for (int i = 0; i < 4; i++) {
            System.out.print(dVector[i] + ", ");
        }

        // Inicializamos los Hilos
        for (int i = 0; i < aHilos.length; i++) {
            aHilos[i] = new Thread(new EscaladoParalelo(iDominio, iIncrement + iDominio, 4.43, dVector));
            iDominio += iIncrement;
        }
        for (int i = 0; i < aHilos.length; i++) {
            aHilos[i].start();
        }
        for (int i = 0; i < aHilos.length; i++) {
            aHilos[i].join();
        }

        // Mostramos el vector despues del incremento
        System.out.print("Elementos del vector despues de incremento: ");
        for (int i = 0; i < 4; i++) {
            System.out.print(dVector[i] + ", ");
        }

    }

}

class Ejercicio2 implements Runnable {

    private Boolean bInc_;

    Ejercicio2(Boolean bIncrement) {
        bInc_ = bIncrement;
    }

    @Override
    public void run() {
        if (bInc_) {
            for (int i = 0; i < 10000; i++) {
                P1.n++;
            }
        } else {
            for (int i = 0; i < 10000; i++) {
                P1.n--;
            }
        }
    }

}

class EscaladoParalelo implements Runnable {
    private Integer iStart, iEnd;
    private Double dEscalado;
    private Double dArray[];

    EscaladoParalelo(Integer start, Integer end, Double escala, Double array[]) {
        iStart = start;
        iEnd = end;
        dEscalado = escala;
        dArray = array;
    }

    @Override
    public void run() {
        for (int i = iStart; i < iEnd; i++) {
            dArray[i] *= dEscalado;
        }
    }

}