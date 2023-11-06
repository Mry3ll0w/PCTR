
class P1 extends Thread{
    private int t=1;
    private int iIteraciones = 10;
    public void run(){
        while (iIteraciones > 0) {
            t=1;
            while(t != 1){}
            //sc
            System.out.println("Entro en SC de P1, con turno: "+ t);
            t = 2;
            iIteraciones--;
        }
    }
}

class P2 extends Thread{
    private int t=2;
    private int iIteraciones = 10;
    public void run(){
        while (iIteraciones > 0) {
            t= 2;
            while(t != 2){}
            //SC
            System.out.println("Entro en SC de P2, con turno: "+ t);
            t=1;
            iIteraciones--;
        }
    }
}

public class DekkerSimple {
    public static void main (String args[]) throws Exception{

        P2 p2 = new P2();
        P1 p1 = new P1();

        p1.start();p2.start();
        p1.join();p2.join();

    }

}
