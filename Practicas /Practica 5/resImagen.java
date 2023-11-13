
import java.util.concurrent.ThreadLocalRandom;

public class resImagen {
    static int [][] iMatrix = new int[2560][1440];//Tomara valores aleatorios de 0 a 255
    private static int m=2560,n=1440;
    resImagen(){
        
        initializeMatrix();
    }
    public static void initializeMatrix(){
        //Inicializamos los valores de la matriz
        for(int i = 0; i < m; i++){
            for(int j =0 ; j < n; j++){
                iMatrix[i][j] = ThreadLocalRandom.current().nextInt(0, 255);
            }
        }
    }

    public static void main(String args[]){
        
        //Inicializamos los valores de la matriz
        

        //Usamos la funcion dada para escalar 
        
        double inicTiempo = System.nanoTime();
        
        //Algoritmo
        for(int i = 0; i < m; i++){
            for(int j =0 ; j < n; j++){
                try{
                    iMatrix[i][j]= (int)(4*iMatrix[i][j]-iMatrix[i+1][j]-iMatrix[i][j+1]-iMatrix[i-1][j]-iMatrix[i][j-1])/8;
                }catch(Exception e){
                    //Si i o j estan out bound no me interesa calcularlo, por lo que capturo las excepciones.
                }
            }
        }

        double tiempoTotal = (System.nanoTime() - inicTiempo) / (double) 1.0e9;

        System.out.println("El algoritmo ha tardado : "+ tiempoTotal + " segundos");

    }

}
