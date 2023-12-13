import java.util.concurrent.CyclicBarrier;
import java.util.ArrayList;
import java.util.List;
public class Barrera {
    public static CyclicBarrier barrera = new CyclicBarrier(3);
    private Integer n = 0;
    public static void main(String args[]) throws InterruptedException{

        Runnable task = () -> {
            try{
                System.out.println("Esperando a barrera");
                barrera.await();
            }catch(Exception e){}
            System.out.println("Saliendo de barrera");
        };

        List<Thread> lThreads = new ArrayList<Thread>();
        for(int i = 0; i < 3; i++){
            lThreads.add( new Thread(task));
        }
        for(Thread i : lThreads){
            i.start();
            //! NO PODEMOS PONER EL JOIN EN EL MISMO SITIO QUE EL START O PETA 
        }
        
        for(Thread i : lThreads){
            i.join();
        }
        
        
    }



}


