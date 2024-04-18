
class MonitorLE {

    Integer nLectores = 0;
    Boolean bEscribiendo = false, bQuiereEscribir = false;
    Integer valor = 0;
    // Metodos Lectura

    synchronized void abrirLectura() {
        while (bEscribiendo || bQuiereEscribir)
            try {
                wait();
            } catch (Exception e) {
            }
        // Leo el valor
        nLectores++;// Aumenta el numero de lectores
        System.out.println("Proceso Lector lee: " + valor);
    }

    synchronized void cerrarLectura() {
        while (nLectores > 0)
            nLectores--;
        notifyAll();// Si no hay nadie leyendo despierto al resto de procesos escritores
    }

    // Metodos Escritura
    synchronized void abrirEscritura() {
        while (nLectores > 0 && !bEscribiendo)// Si nadie lee ni nadie escribe
            try {
                bQuiereEscribir = true;
                wait();
            } catch (Exception e) {

            }
        // Si no hay lectores entonces escribo
        bEscribiendo = true;
        valor = 1;
    }

    synchronized void cerrarEscritura() {
        while (bEscribiendo)
            try {
                wait();
            } catch (Exception e) {
            }
        // Libero y notifico al resto de procesos
        notifyAll();
    }

}

public class monitorLectoresEscritores {

}
