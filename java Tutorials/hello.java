
public class hello {
    public static int threadCount = 0;
    public String pruebaFuncion() {
        return "Hola desde pruebaFuncion";
    }
    public static void main(String[] args) throws InterruptedException { //Es el equivalente del main en C, para compilar es javac hello.java y para ejecutar java hello
        for(;;){
            Thread newThread = new Thread(() -> {
                // Your thread's code here
                System.out.println(Thread.currentThread().getName() + " is running.");
            });

            // Set a name for the thread (optional)
            newThread.setName("Thread-" + threadCount);

            // Start the thread
            newThread.start();

            threadCount++;
        }
    }
}
