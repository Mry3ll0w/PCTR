import java.util.concurrent.Callable;

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

};