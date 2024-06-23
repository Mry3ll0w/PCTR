import mpi.*

public class ejercicio1MPJ {

    public static void main(String args[]){

        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();// numero que corresponde a ese hilo en el commWorld
        int size = MPI.COMM_WORLD.Size();// Cuantos hilos hay en ese commworld
        int emisor = 0;
        int tag = 100; int unitSize = 1;

        if(rank== 0){ //Emisor
            
            int buffer[] = new int[2];
            buffer[0] = 4;  	 buffer[1] = 3;
            for(int i=1; i<size; i++){
                MPI.COMM_WORLD.Send(buffer, 0, unitSize, MPI.INT, i, tag+i);// Envio el elemento i como un entero a los receptores
                MPI.COMM_WORLD.Send(buffer, 1, unitSize, MPI.INT, i, tag+i);
            }

        } else if(rank==1){ //Receptor
            int res;
            int revbuffer[] = new int[2];
            MPI.COMM_WORLD.Recv(revbuffer, 0, unitSize, MPI.INT, emisor, tag+rank); 	 
            MPI.COMM_WORLD.Recv(revbuffer, 1, unitSize, MPI.INT, emisor, tag+rank);
            res = revbuffer[0]+revbuffer[1];
            System.out.println("Suma: "+res);
        }  
        MPI.Finalize();

    }
    
};


/*
 * 3 Hilos
 * 
 */
