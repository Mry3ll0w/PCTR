
public class ej3 {
    /**
     * Se desea realizar el escalado de un vector de n ́umeros enteros de 106
     * componentes. Escriba un programa secuencial escalaVector.java que haga
     * el trabajo. Ahora, escriba una nueva versi ́on paralela multihebrada y ll
     * ́amela
     * escalaVPar.java que utilice 4 hebras paralelas con divisi ́on manual del
     * domi-
     * nio de datos (vector) entre ellas. Escriba un documento que incluya una tabla
     * de
     * an ́alisis tablaCPU.pdf que deber ́a recoger de forma aproximada los picos de
     * uso
     * m ́aximo de la CPU como una funci ́on del tama ̃no del vector
     * (106,2×106,3×106...)
     * y del tipo de procesamiento empleado. No tiene por qu ́e haber una mejora ra-
     * dical del aprovechamiento del procesador.
     * 
     * @param args
     */
    public static void main(String args[]) throws Exception{
        Hebra h1 = new Hebra(0, 2, 2);
        Hebra h2 = new Hebra(2, 4, 2);
        Hebra h3 = new Hebra(4, 6, 2);
        Hebra h4 = new Hebra(6, 8, 2);

        //Vector
        for(Integer i: Hebra.iVector){
            System.out.println(i);
        }
        h1.start();h2.start();h3.start();h4.start();
        h1.join();h2.join();h3.join();h4.join();
        for(Integer i: Hebra.iVector){
            System.out.println(i);
        }
    }
}

class Hebra extends Thread {
    public static Integer[] iVector= {1,2,3,4,5,6,7,8};
    private int iLimInf, iLimSup, iEscalado;

    Hebra(int iCotaInferior, int iCotaSuperior, int iFactorEscalado){
        this.iLimInf = iCotaInferior;
        this.iLimSup = iCotaSuperior;
        this.iEscalado = iFactorEscalado;
        //Inicializamos el Vector 
        
    }

    @Override
    public void run(){
        escaladoVector();
    }
    public void escaladoVector(){
        for(int i = iLimInf; i< iLimSup; i++){
            iVector[i]*=iEscalado;
        }
    }

    public void estadoVector(){
        for(int i = 0; i < iVector.length; i++)
            System.out.print(" "+ iVector[i]);
        System.out.println();
    }
}
