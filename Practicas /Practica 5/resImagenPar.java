import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;


class TareaFila implements Callable<int []>{
    private int [][] iMatrix;
    private int iNumFila, iLargoPixeles, iAnchoPixeles;
    TareaFila(int fila, int largo, int ancho, int[][] m){
        this.iNumFila = fila;
        this.iMatrix = m;
        this.iLargoPixeles = largo;
        this.iAnchoPixeles = ancho;
    }
    //Es el void run de runnable, devuelve la fila tratada lista, segmentamos la matriz por filas.
    public int[] call(){
        int res[] = iMatrix[this.iNumFila];
        for(int j = 0; j < iAnchoPixeles; j++){
            int top = (iNumFila > 0) ? iMatrix[iNumFila-1][j] : -1;
            int bottom = (iNumFila < iLargoPixeles-1) ? iMatrix[iNumFila+1][j] : -1;
            int left = (j > 0) ? iMatrix[iNumFila][j-1] : -1;
            int right = (j < iAnchoPixeles-1) ? iMatrix[iNumFila][j+1] : -1;
            if(top != -1 && bottom != -1 && left != -1&& right!=-1){
                res[j] = (int)(4*iMatrix[iNumFila][j]-top-bottom-left-right)/8;
            }
        }
        return res;
    }
}

public class resImagenPar{
    
    private static int iMatrix[][];


    public static void main(String args[]){

        
        //Creamos la matriz a partir de los argumentos pasados
        int largo = Integer.parseInt(args[0]);
        int ancho = Integer.parseInt(args[1]);
        iMatrix = new int [largo][ancho];
        for(int i = 0; i < largo; i++){
            for(int j =0 ; j <ancho; j++){
                iMatrix[i][j]= ThreadLocalRandom.current().nextInt(0, 255);
            }
        }
        //Comprobamos el numero de hilos del sistema 
        int nHilos = Runtime.getRuntime().availableProcessors();//Al ser de tipo matematico se considera el cb como nulo

        //Creamos el pool ejecutor aunque no tenga mucho sentido al  no reutilizar las hebras 
        //pero por practicar.
        ThreadPoolExecutor ept = new ThreadPoolExecutor(
            nHilos,
            nHilos,
            0L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>()//Cola de procesos en el pool
        );

        //Creamos la lista de arrays que va a guardar el resultado de la matriz
        List<Future<int[]>> lParciales = Collections.synchronizedList(new ArrayList<Future<int[]>>());

        //Agregamos al pool las tareas
        for(int i = 0; i < largo; i++){
            lParciales.add(ept.submit(
                new TareaFila(i, largo, ancho, iMatrix)
            ));
        }

        // Pillamos resultados de las operaciones y los guardamos en la fila 
        // correspondiente de la matriz final.
        int i = 0;
        for(Future<int[]> it: lParciales){
            try{
                iMatrix[i] = it.get();//Puede no estar procesado aun
            }catch(Exception e){}
        }
        ept.shutdown();
        System.out.println("Matriz procesada");
    }

    

};