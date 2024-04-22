import java.util.concurrent.ThreadLocalRandom;

public class P3 {

    static Integer[] aV1 = new Integer[1000000];
    static Integer[] aV2 = new Integer[1000000];
    static Integer prodEscalar = 0;

    // main para prod escalar paralelo
    public static void main(String args[]) throws InterruptedException {

        // Inicializamos los vectores
        for (int i = 0; i < 1000000; i++) {
            Integer n = ThreadLocalRandom.current().nextInt();
            Integer n2 = ThreadLocalRandom.current().nextInt();
            aV1[i] = n;
            aV2[i] = n2;
        }
        // Creamos el offset para realizar el desplazamiento del cursor
        int iOffset = 0;
        Thread aHilos[] = new Thread[4];
        for (int i = 0; i < aHilos.length; i++) {
            aHilos[i] = new Thread(new ProdEscalarParalelo(i, iOffset, iOffset + 250000));
            iOffset += 250000;
        }

        // Lanzamos los hilos
        for (int i = 0; i < aHilos.length; i++) {
            aHilos[i].start();
        }
        for (int i = 0; i < aHilos.length; i++) {
            aHilos[i].join();
        }

        // Comprobamos cual es el escalar resultado
        System.out.println("Escalar: " + P3.prodEscalar);

    }

}

class ProdEscalarParalelo implements Runnable {
    int i, inicio, fin;

    ProdEscalarParalelo(int i, int inicio, int fin) {
        this.i = i;
        this.inicio = inicio;
        this.fin = fin;
    }

    @Override
    public void run() {

        // Realizamos el producto escalar de los vectores
        for (int i = inicio; i < fin; i++) {
            P3.prodEscalar += P3.aV1[i] * P3.aV2[i];
        }
    }
}