public class ej2 {
    public static void main(String args[]) throws Exception{
        //Creamos las hebras 
        int [][]m= {{-3,5,-6}, {7,10,-1}};
        int []v= {-6,-2,5};
        
        TareaMatrizEscalar t1 = new TareaMatrizEscalar(0, 3, m, v);
        TareaMatrizEscalar t2 = new TareaMatrizEscalar(1, 3, m, v);
        Thread h1 = new Thread(t1);
        Thread h2 = new Thread(t2);
        h1.start();
        h2.start();
        h1.join();
        h2.join();
        System.out.println(t1.resultadoMultiplicacion()+", "+t2.resultadoMultiplicacion());
    }
}

class TareaMatrizEscalar implements Runnable{
    private int[][]iMatriz;
    private int[] iVector;
    private int iFila;
    private int iSizeColumna;
    private int iResColumna=0;

    TareaMatrizEscalar(int f, int s, int [][]m, int []v){
        this.iFila = f;
        this.iSizeColumna = s;
        this.iMatriz=m;
        this.iVector = v;
    }

    public void run(){
        //Calculamos el producto vectorial
        for(int i = 0; i < iSizeColumna; ++i){
            iResColumna += iMatriz[iFila][i] * iVector[i];
        }
    }

    public int resultadoMultiplicacion(){return iResColumna;}
}
