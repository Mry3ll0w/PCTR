
import java.util.concurrent.*;
import java.util.*;

public class primosParalelos {

  public static void main(String[] args) throws Exception {
    long nPuntos     = Long.parseLong(args[0]);
    int  nTareas     = Integer.parseInt(args[1]);
    //int  nTareas     = Runtime.getRuntime().availableProcessors();
    long tVentana    = nPuntos/nTareas;//Determino el numero de segmentos que tiene cada hebra
    long primosTotal = 0;
    long linf        = 0;
    long lsup        = tVentana;
    
    List<Future<Long>> contParciales = Collections.synchronizedList(
      new ArrayList<Future<Long>>());
    //ContParciales ==> Lo que voy a tener es una lista de n primos que cada tarea encuentra
    //Future lo necesitamos ya que callable devuelve a futuro, el colletions sychronized sirve
    //para evitar problemas de consistencia de resultado con el uso de Future y Callable

    long inicTiempo = System.nanoTime();  
    ThreadPoolExecutor ept = new ThreadPoolExecutor(
      nTareas,//numero minimo de hilos
      nTareas,//numero maximo de hilos
      0L,//KeepAlive
      TimeUnit.MILLISECONDS,//Unidad de tiempo
      new LinkedBlockingQueue<Runnable>());//Cola de trabajo

    for(int i=0; i<nTareas; i++){
      contParciales.add(ept.submit(
      	 new tareaPrimos(linf, lsup)));
      linf=lsup+1;
      lsup+=tVentana;
    }  
    for(Future<Long> iterador:contParciales)
      try{
      	  primosTotal +=  iterador.get();//Get pilla el objeto Future para cogerlo cuando este.
      }catch (Exception e){}

    long tiempoTotal = (System.nanoTime()-inicTiempo)/(long)1.0e9;   
    ept.shutdown();
    System.out.println("Primos hallados: "+primosTotal);
    System.out.println("Calculo finalizado en "+tiempoTotal+" segundos");
  }   
}
