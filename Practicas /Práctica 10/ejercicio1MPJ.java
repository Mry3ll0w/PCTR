import mpi.*

public class ejercicio1MPJ {

    public static void main(String args[]){

        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        int emisor = 0;
        int tag = 100; int unitSize = 1;

        if(rank==emisor){ 
            
            int bufer[] = new int[2];
            bufer[0] = 4;  	 bufer[1] = 3;
            for(int i=1; i<size; i++){
                MPI.COMM_WORLD.Send(bufer, 0, unitSize, MPI.INT, i, tag+i);
                MPI.COMM_WORLD.Send(bufer, 1, unitSize, MPI.INT, i, tag+i);
            }

        } else if(rank==1){ 
            int res;
            int revbufer[] = new int[2];
            MPI.COMM_WORLD.Recv(revbufer, 0, unitSize, MPI.INT, emisor, tag+rank); 	 
            MPI.COMM_WORLD.Recv(revbufer, 1, unitSize, MPI.INT, emisor, tag+rank);
            res = revbufer[0]+revbufer[1];
            System.out.println("Suma: "+res);
        }  
        MPI.Finalize();

    }
    
};