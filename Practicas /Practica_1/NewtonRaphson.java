
import java.util.Scanner;
public class NewtonRaphson {
    public static void main(String args[]){
        /**
         * Procedimiento Newton-Raphson (x0, iteraciones)
         * xN <- x0
         * Para i <- 0 Hasta iteraciones Con Paso 1 Hacer
         *  Si f�(xN) <> 0
         *      xN1 = xN � f(xN) / f�(xN)
         *      Escribir �Iteraci�n: �, i, � Aproximaci�n: �, xN1
         *      xN <- xN1
         *  Fin Si
         * Fin Para
         * Escribir �Resultado: �, xN
         * Fin Procedimiento
         */
    
        double dXn = 0.0;
        int iIteraciones = 20;
        //Leemos con Scanner por pantalla
        System.out.println("Introduce x0 ");
        try(Scanner sc = new Scanner(System.in)){
            dXn = sc.nextDouble();
            for(int i = 0; i < iIteraciones; ++i){
                if(f(dXn) != 0){
                    double dXn1 = dXn - f(dXn)/fPrima(dXn);
                    System.out.println("Iteracion "+ i +" Aproximacion, "+dXn1 );
                    dXn = dXn1;
                }
            }
            System.out.println("Resultado "+ dXn);
        }
    }

    public static double f(double x){
        return Math.cos(x) - Math.pow(x, 3);
    }

    public static double fPrima(double x){
        return -Math.sin(x) - 3*Math.pow(x, 2);
    }

}
