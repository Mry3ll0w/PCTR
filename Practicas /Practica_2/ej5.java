public class ej5 {
    
    public static void main(String args[]) throws Exception{
        Cajero c = new Cajero(1000);
        TareaHebra t1 = new TareaHebra(c, false);
        TareaHebra t2 = new TareaHebra(c, true);
        Thread h1 = new Thread(t1);
        Thread h2 = new Thread(t2);
        h1.start();h2.start();
        h1.join();h2.join();
        System.out.println("Valor guardado en el cajero " + c.saldo() );
    }

}


class Cajero {
    private int iSaldo;
    Cajero(int saldo){
        this.iSaldo = saldo;
    }
    public void retirarDinero(){
        iSaldo-=10;
    }
    public void meterDinero(){
        iSaldo+=10;
    }
    public int saldo(){return iSaldo;}
}

class TareaHebra implements Runnable{
    private Cajero c;
    private boolean bRetirada = false;
    TareaHebra(Cajero ca, boolean b){
        this.c = ca;
        this.bRetirada = b;
    }

    @Override
    public void run(){
        
        for(int i = 0; i < 999; i++){
            if(bRetirada){
                c.retirarDinero();
            }else
                c.meterDinero();
        }
    } 
}