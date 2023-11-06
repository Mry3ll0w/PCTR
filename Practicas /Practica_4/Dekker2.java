/* Copyright (C) 2006 M. Ben-Ari. See copyright.txt */
/* Programmed by Panu Pitkämäki */

/* Dekker's algorithm */
class Dekker2 {
    /* Number of processes currently in critical section */
    static volatile int inCS = 0;
    /* Process p wants to enter critical section */
    static volatile boolean wantp = false;
    /* Process q wants to enter critical section */    
    static volatile boolean wantq = false;
    /* Which processes turn is it */
    static volatile int turn = 1;


    static int iIterations =10;
    

    class P extends Thread {
        private int iIterations;
        P(int i){this.iIterations=i;}
        public void run() {
            for(int i=0; i< iIterations;){
                /* Non-critical section */
                wantp = true;
                while (wantq) {
                    if (turn == 2) {
                        wantp = false;
                        while (turn == 2)
                            Thread.yield();
                        wantp = true;
                    }
                }
                inCS++;
                Thread.yield();
                /* Critical section */
                if(iIterations < 20){
                    System.out.println("Value of iIterations: "+ iIterations);
                    iIterations++;
                }
                //System.out.println("Number of processes in critical section: " + inCS);
                inCS--;
                turn = 2;
                wantp = false;
            }
        }
    }
    
    class Q extends Thread {
        private int iIterations;
        Q(int i){this.iIterations=i;}
        public void run() {
            for(int i=0; i< iIterations;) {
                /* Non-critical section */
                wantq = true;
                while (wantp) {
                    if (turn == 1) {
                        wantq = false;
                        while (turn == 1)
                            Thread.yield();
                        wantq = true;
                    }
                }
                inCS++;
                Thread.yield();
                /* Critical section */
                if(iIterations < 20){
                    System.out.println("Value of iIterations: "+ iIterations);
                    iIterations++;
                }
                //End of Critical Section
                //System.out.println("Number of processes in critical section: "+ inCS);
                inCS--;
                turn = 1;
                wantq = false;
            }
        }
    }

    Dekker2() throws Exception{
        
        Thread p = new P(Dekker2.iIterations);
        Thread q = new Q(Dekker2.iIterations);
        p.start();
        q.start();
        p.join();q.join();
        System.out.println(Dekker2.iIterations);
        
    }

    public static void main(String[] args) throws Exception{
        new Dekker2();
    }
}
