import java.util.*;
import java.io.*;
import java.util.concurrent.*;

public class volcadoRed
{
  public static void main(String[] args) throws Exception
  {
    long iniTiempo=0;
    LinkedList<tareaRed> tareas = new LinkedList<tareaRed>();//Collecion de tareas, lista (no aporta nada)
    int nNuc = Runtime.getRuntime().availableProcessors();//Obtenemos el numero de cores
    float Cb = Float.parseFloat(args[0]);//Obtenemos el coeficiente de bloqueo
    int tamPool = (int)(nNuc/(1-Cb));//Ecuacion de Subramanian
    ThreadPoolExecutor ept = new ThreadPoolExecutor(
    	    tamPool, 
    	    tamPool,
    	    0L,
            TimeUnit.MILLISECONDS, 
            new LinkedBlockingQueue<Runnable>());//Ten las hebras ya creadas, no las creamos poco a poco
    ept.prestartAllCoreThreads();
    try {
    	 int cont = 0;
    	 String linea=" ";      
    	 RandomAccessFile direcciones = new RandomAccessFile("direccionesRed.txt","r");
         iniTiempo = System.nanoTime();
         while(linea!=null){
           linea =(String)direcciones.readLine();
           if(linea!=null)tareas.add(new tareaRed(linea, cont));
           cont++;
         }
         direcciones.close();
        } catch (EOFException e) {}
    for (Iterator iter = tareas.iterator(); iter.hasNext();) 
      ept.execute((Runnable)iter.next());
    ept.shutdown();//Termina el pool cuando termina de trabajar
    //while(!ept.isTerminated()){}//NO ES MUY CORRECTO YA QUE LA ESPERA ES OCUPADA
    ept.awaitTermination(3600, TimeUnit.MILLISECONDS);
    long finTiempo = System.nanoTime();
    System.out.println("Numero de Nucleos: "+nNuc);
    System.out.println("Coficiente de Bloqueo: "+Cb);
    System.out.println("Tamano del Pool: "+tamPool);
    System.out.println("Tiempo Total (segundos): "+(finTiempo-iniTiempo)/1.0e9);                              
  }
}  
