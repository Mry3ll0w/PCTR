
/**
 * Utilizando implementaci ́on de la interfaz Runnable, cree una condici ́on
 * de concurso sobre un objeto com ́un que albergar ́a una variable entera n con
 * valor inicial 0. La clase que modela al objeto tendr ́a dos modificadores que
 * respectivamente incrementen y decrementen a la variable n, y un observador para conocer
 * su estado. Ahora, cree dos hebras que compartan el acceso a un objeto de la
 * clase ya construida (una que incremente y otra que decremente) concurrente-
 * mente y compruebe que, aunque el valor te ́orico final de n debe ser cero, en
 * la
 * pr ́actica no tiene por qu ́e ser as ́ı. Guarde su c ́odigo en
 * tareaRunnable.java y
 * Usa tareaRunnable.java. Escriba (utilizando LATEX v ́ıa OverLeaf ) una corta
 * tabla de prueba en tabla2.pdf donde recoger ́a el n ́umero de iteraciones que
 * realizaron los hilos y el valor final obtenido para n junto con su an ́alisis
 * acerca
 * de todo ello
 */

public class ej2 {
    
    public static void main(String args[]) throws Exception{
        
        Concurso c = new Concurso(0);
        TareaRunnable t1 = new TareaRunnable(c, 0);
        TareaRunnable t2 = new TareaRunnable(c, 1);

        Thread h1 = new Thread(t1);
        Thread h2 = new Thread(t2);

        h1.start();
        h2.start();
        h1.join();h2.join();
        //Comprobamos ahora el valor de n 
        System.out.println("Valor de n "+ c.n());
        
    }
}

class Concurso{
    private int n;
    Concurso(int n_){
        n = n_;
    }
    
    public void inc(){
        n++;
    }

    public void dec(){
        n--;
    }

    public int n(){return n;}
    
} 

class TareaRunnable implements Runnable{
   
    private Concurso c;
    int iTipo;
    TareaRunnable(Concurso c, int Tipo){
        this.c = c;
        iTipo = Tipo;
    }
    public void run(){
        for(int i = 0; i < 1000; i++){
            if(iTipo == 0){
                c.inc();
            }
            else{
                c.dec();
            }
        }
    }

} 