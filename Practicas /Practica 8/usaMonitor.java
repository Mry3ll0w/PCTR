
class tareaHilo implements Runnable{
    private monitorImpresora m;
    private int id;
    @Override
    public void run(){
        int i = -1 ;
        try{
            i = m.pedirImpresora();
        }catch(Exception e){}
        System.out.println("Imprimiendo con "+id+" desde : "+i);
        m.liberarImpresora(i);
        System.out.println("Salgo de impresora: "+i);
        
    }

    tareaHilo(monitorImpresora m, int id){
        this.m = m;
        this.id = id;
    }

}

public class usaMonitor {
    
    public static void main(String args[]) throws Exception{
        monitorImpresora m = new monitorImpresora();

        Thread h1 = new Thread(new tareaHilo(m,1));
        Thread h2 = new Thread(new tareaHilo(m,2));
        Thread h3 = new Thread(new tareaHilo(m,3));
        Thread h4 = new Thread(new tareaHilo(m,4));
        Thread h5 = new Thread(new tareaHilo(m,5));

        h1.start();h2.start();h3.start();
        h4.start();h5.start();
        
        h1.join();
        h2.join();
        h3.join();
        h4.join();
        h5.join();
        
    }

}
