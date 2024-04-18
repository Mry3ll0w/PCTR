import java.util.ArrayList;
import java.util.List;

public class testHebras {

    static int c = 0;

    synchronized static void inc() {
        c++;
    }

    public static void main(String args[]) throws Exception {
        List<Thread> l = new ArrayList<Thread>();

        for (int i = 0; i < 100000; i++) {
            Thread t = new Thread(
                    () -> {
                        inc();
                    });
            l.add(t);
        }

        // Start all thread
        for (Thread t : l) {
            t.start();
        }

        for (Thread t : l)
            t.join();

        System.out.println(c);
    }

    void fun() {

    }

}
