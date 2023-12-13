//calculadora distribuida con broadcast...
import mpi.*;
public class calculadoraDistribuida3 {

public static void main(String args[]) throws Exception {
 MPI.Init(args);
 int rank = MPI.COMM_WORLD.Rank();
 int size = MPI.COMM_WORLD.Size();
 int emisor = 0;
 int tag = 100; 
 int unitSize = 2;
 
  int bufer[] = new int[2];
  if(rank==0){
    bufer[0] = 4;
    bufer[1] = 3;
  } 

MPI.COMM_WORLD.Bcast(bufer, 0, unitSize, MPI.INT, 0);//Al dejarlo fuera todos llaman al broadcast.
//Si no lo invocatan todos los procesos se bloquea
//En el proceso que hace la difusion el proceso envia y recibe
//En el resto solo tienen caracter receptor

  if(rank==1){ 
    int res;
    res = bufer[0]+bufer[1];
    System.out.println("Suma: "+res);
   }else if(rank==2){ 
 	  int res;
          res = bufer[0]-bufer[1];
 	  System.out.println("Resta: "+res);
        }else if(rank==3){
 	        int res;
                res = bufer[0]*bufer[1];
 	        System.out.println("Producto: "+res);
              }else if(rank==4){ 
 	              float res;
                      if(bufer[1]!=0){
                        res = bufer[0]/(float)bufer[1]; 
                        System.out.println("Cociente: "+res);}
                      else System.out.println("No se puede dividir por cero...");
       }
 MPI.Finalize();
 }
}