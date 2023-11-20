public class Sem{

  //representa un semaforo
  private int s;
  //No hay Condition ya que no hay variables de condicion al usar la cola
  //general wait set
  public Sem(int s){
    this.s = s;
  }

  public synchronized void waitS(){
    while(s==0)try{wait();}catch(InterruptedException e){}
    s=s-1;
  }

  public synchronized void signalS(){
    s=s+1;
    notifyAll();
  }
}
