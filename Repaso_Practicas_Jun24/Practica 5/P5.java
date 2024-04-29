import java.util.concurrent.ThreadLocalRandom;

public class P5 {

    public static void main(String args[]) throws InterruptedException {

        // Inicializamos la matriz original
        Integer dMatrizO[][] = new Integer[5][5];
        // Creamos tambien una copia de la original donde guardamos el resultado
        Integer dMatrizR[][] = new Integer[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                dMatrizO[i][j] = ThreadLocalRandom.current().nextInt(0, 255);
                dMatrizR[i][j] = 0;// Inicializamos a 0
            }
        }

        // Creamos 1 hilo por cada Fila para procesar la matriz en paralelo
        Thread[] aHilos = new Thread[dMatrizO[0].length];// la matriz es cuadrada
        for (int i = 0; i < dMatrizO[0].length; i++) {
            aHilos[i] = new Thread(new EscalaHilos(dMatrizO, dMatrizR, i));
        }

        // Comenzamos la ejecucion
        for (Thread t : aHilos) {
            t.start();
        }
        // Esperamos
        for (Thread t : aHilos) {
            t.join();
        }
    }

}

class EscalaHilos implements Runnable {

    // Args
    Integer dMatrizOriginal[][];
    Integer dMatrizResultado[][];
    int iFila;

    EscalaHilos(Integer dMatrizOriginal[][], Integer dMatrizResultado[][], int iFila) {
        this.dMatrizOriginal = dMatrizOriginal;
        this.dMatrizResultado = dMatrizResultado;
        this.iFila = iFila;
    }

    @Override
    public void run() {

        // Esta tarea la realizara un hilo, uno por fila de la matriz
        for (int i = 0; i < dMatrizOriginal[iFila].length; i++) {// La matriz es cuadrada
            try {
                dMatrizResultado[iFila][i] = (4 * dMatrizOriginal[iFila][i] - dMatrizOriginal[iFila + 1][i]
                        - dMatrizOriginal[iFila][i + 1] - dMatrizOriginal[iFila - 1][i] - dMatrizOriginal[iFila][i - 1]) // Peta
                                                                                                                         // por
                                                                                                                         // index
                                                                                                                         // pero
                                                                                                                         // pereza
                                                                                                                         // fix
                        / 8;
            } catch (Error ejecucion) {
                System.out.println(ejecucion);
            }
        }
    }

}
