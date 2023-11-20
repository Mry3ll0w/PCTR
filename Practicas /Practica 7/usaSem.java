public class usaSem 
  extends Thread{

  public static int n = 0;
  public Sem semaforo;

  public usaSem(Sem semaforo){this.semaforo=semaforo;}

  public void run(){
    for(int i=0; i<1000000; i++){
      semaforo.waitS();
      n++;
      semaforo.signalS();
    }
  }

  public static void main(String[] args) throws Exception{
    Sem mon_semaforo = new Sem(1);//Siempre es necesario usar el 1 para inicializar para que no espere
    usaSem A         = new usaSem(mon_semaforo);  
    usaSem B         = new usaSem(mon_semaforo);
    A.start(); B.start();
    A.join(); B.join();
    System.out.println(n);
  }
}