import java.util.concurrent.ThreadLocalRandom;

import javax.management.RuntimeMBeanException;

class ProdConMonitor {

    final int N = 10;//Limitamos a 10 el buffer
    int oldest = 0, newest = 0;
    volatile int count = 0;//Con volatile los cambios en la variables se ven reflejados en cualquier hilo
    Integer buffer[] = new Integer[N];

    //Productor
    synchronized void producirElementos(){

        while (count == N)
        try{
            wait();//Mientras que el contador de creados sea N espera a que 
            //se consuman para seguir produciendo
        }catch(Exception e){}

        //Si no se produce
        buffer[newest]= ThreadLocalRandom.current().nextInt(0, 255);
        newest = (newest + 1) % N;
        count++;
        notifyAll();

    }

    //Consumidor
    synchronized void consumirElementos(){
        
        while (count == 0)
        try{
            wait();//Si no puedo consumir espero.
        }catch(Exception e){}
        System.out.println(buffer[oldest]);
        oldest = (oldest + 1)%N;
        count-=1;
        notifyAll();
        
    }


};

//Creamos una clase Productora que use el monitor
class Productor implements Runnable{
    private ProdConMonitor monitor;//Pasamos el monitor 

    Productor(ProdConMonitor m){
        this.monitor = m;
    }

    @Override
    public void run(){
        int i = 10;
        while( i > 0)
        try{
            monitor.producirElementos();
            i--;
        }catch(Exception e){}
    }
}

class Consumidor implements Runnable{
    private ProdConMonitor monitor;//Pasamos el monitor 

    Consumidor(ProdConMonitor m){
        this.monitor = m;
    }

    @Override
    public void run(){
        int i = 10;
        while( i > 0)
        try{
            monitor.consumirElementos();
            i--;
        }catch(Exception e){}
    }
}

public class usaProdCon {
    public static void main(String args[]){
        ProdConMonitor monitor = new ProdConMonitor();
        Thread hiloConsumidor = new Thread(new Consumidor(monitor));
        Thread hiloProductor = new Thread(new Productor(monitor));
        //Empezamos la secuencia
        hiloConsumidor.start();
        hiloProductor.start();
    }
}
