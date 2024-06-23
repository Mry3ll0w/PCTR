import java.util.concurrent.*;

public class Repaso {

  // main
  public static void main(String[] args) {

  }

};

// Semaforos alto nivel
class SemaforoProdCon {

  Semaphore sem = new Semaphore(1);// Colocado a 1 para poder usarlo de primeras.
  Semaphore vacios = new Semaphore(10);// Gestiona el numero de huecos del buffer
  Semaphore llenos = new Semaphore(0);

  Integer[] iBuffer = new Integer[10];

  public static void main(String[] args) {

  }

  // Productor

  void producir() {

    try {
      // Esperamos a vacios, puesto que si no hay huecos no podemos producir.
      vacios.acquire();
      sem.acquire();// Una vez tenemos hueco esperamos EM.

      // Producimos buscando el hueco.
      int i = 0;
      for (; i < 10 && iBuffer[i] != null; i++)
        ;
      iBuffer[i] = 4;

      // Avisamos que se han generado elementos a llenos

    } catch (Exception e) {
      // Gestion exception
    } finally {
      sem.release();
      llenos.release();
    }

  }

  // Consumidor
  void consumir() {
    try {

      // Esperamos a que hayan elementos en llenos
      llenos.acquire();
      sem.acquire();

      // Consumimos el elemento que este disponible
      int i = 0;
      for (; i < 10 && iBuffer[i] == null; i++)
        ;
      iBuffer[i] = null;

    } catch (Exception e) {
      // handle exception
    } finally {
      sem.release();
      vacios.release();// Tras consumir obviamente el numero de huecos debe aumentar.
    }
  }

};

class Monitor {

};
