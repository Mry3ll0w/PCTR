import java.util.concurrent.*;

public class impares extends Thread {
  static long s = 0;
  static Semaphore sem1 = new Semaphore(1);
  static Semaphore sem2 = new Semaphore(1);
  int tipoProceso;

  public impares(int tipoProceso) {
    this.tipoProceso = tipoProceso;
  }

  public void run() {
    switch (tipoProceso) {
      case 0: { // proceso sucesion
        long S0 = 0;
        long S1 = 1;
        while (true)
        // for(int i=0; i<20; i++)
        {
          try {
            sem1.acquire();
          } catch (InterruptedException e) {
          }
          s = S0 + S1;
          sem2.release();
          S0 = S1;
          S1 = s;
          // System.out.println(s);
        }
        // break;
      }
      case 1: {// proceso suma
        long suma = 0;
        while (true)
        /// for(int i=0; i<20; i++)
        {
          try {
            sem2.acquire();
          } catch (InterruptedException e) {
          }
          try {
            sem2.acquire();
          } catch (InterruptedException e) {
          }
          suma = suma + s;
          // por que dos release? release desbloquea procesos mientras los haya en la
          // cola,
          // e incrementa en uno cuando esta se queda vacia
          // la tarea sucesion calcula un termino tras el primer release y se bloquea,
          // necesitando otro release para calcular el siguiente
          // pues queremos la suma de los términos impares.
          sem1.release();
          sem1.release();
          // System.out.println(suma);
        }
        // break;
      }
    }// switch
  }// run

  public static void main(String[] args) throws Exception {
    impares sucesion = new impares(0);
    impares suma = new impares(1);
    sucesion.start();
    suma.start();
    sucesion.join();
    suma.join();
  }
}// class