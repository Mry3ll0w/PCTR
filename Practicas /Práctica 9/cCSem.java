import java.util.concurrent.Semaphore;

public class cCSem {
    public static void main(String args[]){

    }
}

class semCuentaAhorros{

    private Double dSaldo;
    private Semaphore s = new Semaphore(1), 
    sSaldoInsuficiente = new Semaphore(0);//Ya que incialmente no hay Saldo

    void ingresar(Double d) throws InterruptedException{
        s.acquire();
        dSaldo+= d;
        s.release();
    }

    void sacarDinero(Double d) throws InterruptedException{
        s.acquire();
        while(dSaldo < d) sSaldoInsuficiente.wait();
        dSaldo-=d;
        sSaldoInsuficiente.release();
        s.release();
    }
}

class semImpresoras{
    
    private Semaphore s = new Semaphore(1), sImpresorasOcupadas = new Semaphore(3);
    private Integer n = 3;
    private Boolean[] vbImpresorasOcupadas = {false, false, false};

    public void pedirImpresora() throws InterruptedException {
        s.acquire();
        while(n == 0) sImpresorasOcupadas.acquire();//wait
        
        int iNumeroImpresora = 0;
        for(;iNumeroImpresora < 3 && !vbImpresorasOcupadas[iNumeroImpresora]; iNumeroImpresora++){}
        //Pillo y marco la impresora disponible
        vbImpresorasOcupadas[iNumeroImpresora]= true;//Marco ocupada
        n--;
        sImpresorasOcupadas.release();
        System.out.println("Procediendo a la impresion con la impresora "+ iNumeroImpresora + 1);
        s.release();
    }

    public void liberarImpresora(int i) throws InterruptedException{
        if(i > 3){
            System.out.println("Esa impresora no existe");
        }
        while(n == 3) sImpresorasOcupadas.acquire();
        vbImpresorasOcupadas[i]=false;
        n++;
        sImpresorasOcupadas.release();
        s.release();
    }
}
