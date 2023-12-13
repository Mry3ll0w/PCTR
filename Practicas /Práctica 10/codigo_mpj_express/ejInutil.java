import mpi.*;
public class ejInutil{

  public static void main(String args[]) throws Exception {
    MPI.Init(args);//Inicializacion del entorno de ejecucion
    int me = MPI.COMM_WORLD.Rank();//El PID del proceso asignador dentro del comunicador
    int size = MPI.COMM_WORLD.Size();//Que tamaño tiene el comunicador (cuantos procesos hay)
    System.out.println("Soy el proceso <"+me+">");
    MPI.Finalize();//Finalizacion
  }
} 