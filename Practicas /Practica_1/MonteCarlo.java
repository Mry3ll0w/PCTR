public class MonteCarlo {

    public static void main(String [] args){

        /**
         * contador_exitos <- 0
         * Para i <- 0 Hasta n Con Paso 1 Hacer
         *  coordenada_x <- aleatorio (0,1)
         *  coordenada_y <- aleatorio (0,1)
         *  Si coordenada_y <= f(coordenada_x)
         *      contador_exitos <- contador_exitos + 1
         *  Fin Si
         * Fin Para
         * Escribir �Integral aproximada: �, (contador_exitos/n)
         */
        int iNumeroExitos = 0;
        int n = 300000;
        for(int i = 0; i < n; i++){
            double dCoordenadaX = generarAleatorio();
            double dCoordenadaY = generarAleatorio();
            if(dCoordenadaY <= Math.sin(dCoordenadaX)){
                iNumeroExitos += 1;
            }
        }
        System.out.println("Integral Aprox: "+ (double)iNumeroExitos/n);
    }

    public static double generarAleatorio(){
        double dRandNumber = Math.random();
        return dRandNumber;
    }

}