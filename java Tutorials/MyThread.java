import java.lang.Thread;
public class MyThread extends Thread {
    
    public static int numThreads = 0;
    public static void main(String[] args) {
        
    }
    @Override
    public void run() {
      System.out.println("Soy el hilo " + this.getName());
    }
}
