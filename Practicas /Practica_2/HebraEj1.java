public class HebraEj1 extends Thread{
    
    /**
     * Utilizando herencia de la clase Thread, cree una condici ́on de concurso
     * sobre una variable com ́un n (valor inicial 0) entre dos hilos que
     * respectivamente
     * incrementen y decrementen el mismo n ́umero de veces a n. Lance ambos hilos
     * concurrentemente utilizando start()-join() y compruebe que, aunque el va-
     * lor te ́orico final debe ser cero, en la pr ́actica no tiene por qu ́e ser as
     * ́ı. Guarde su c ́odigo en hebra.java y Usa hebra.java. Escriba (utilizando LATEX v ́ıa
     * Over-
     * Leaf ) una corta tabla de prueba en tabla.pdf donde recoger ́a el n ́umero de
     * iteraciones que realizaron los hilos y el valor final obtenido para n junto
     * con su
     * an ́alisis acerca de todo ello.
     * 
     */
    
    public static int n = 0;
    private int iPrivado = 0;
    
    @Override
    public void run(){
        //Incrementamos la n para crear la condicion de concurso, ya que n es compartida
        for(int i = 0; i < 10000; i++)
            n++;iPrivado--;
    }
    public int n(){return n;}
    public int iPrivado(){return iPrivado;}

}
