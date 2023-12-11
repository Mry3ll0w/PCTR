import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class cCRL {
    
    public static void main(String args[]){
        /* RLockCuenta cuenta = new RLockCuenta();

        // Crear 5 hebras depositantes
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    cuenta.depositar(10.0);
                    System.out.println("Depositado 10.0, saldo total: " + cuenta.dSaldoTotal);
                }
            }).start();
        }

        // Crear 5 hebras reintegrantes
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    try {
                        cuenta.sacarDinero(5.0);
                        System.out.println("Retirado 5.0, saldo total: " + cuenta.dSaldoTotal);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } *///Codigo para comprobar el funcionamiento del problema de la cuenta de ahorros

    }

}


//Seccion relacionada al problema de la cuenta de ahorro usando un reentrantLock

class RLockCuenta{

    private ReentrantLock lock = new ReentrantLock();
    private final Condition cSaldoPositivo= lock.newCondition();
    public Double dSaldoTotal = 100.00;//Por darle un valor

    public void depositar(Double dCantidad){
        lock.lock();//Pillamos el cerrojo
        try{
            dSaldoTotal += dCantidad;
        }finally{lock.unlock();}//Nos aseguramos que en caso de error se desbloquee el seguro.
    }

    public void sacarDinero(Double dCantidad)throws InterruptedException{
        lock.lock();
        try{
            while(dSaldoTotal < dCantidad)cSaldoPositivo.await();//Espera a que el saldo sea el suficiente
            dSaldoTotal-=dCantidad;
        }finally{lock.unlock();}
    }

}


class RLockImpresoras{
    
    private ReentrantLock lock = new ReentrantLock();
    private final Condition cImpresorasOcupadas = lock.newCondition();
    private Integer n = 3;
    private boolean vbImpresorasOcupadas[] = {false,false,false};

    public void pedirImpresora() throws InterruptedException{
        lock.lock();
        try{
            while(n == 0)cImpresorasOcupadas.await();//Primeramente comprueba la disponibilidad de las impresoras
            int iNumeroImpresora = 0;
            for(;iNumeroImpresora < 3 && !vbImpresorasOcupadas[iNumeroImpresora]; iNumeroImpresora++){}
            //Pillo y marco la impresora disponible
            vbImpresorasOcupadas[iNumeroImpresora]= true;//Marco ocupada
            n--;
            System.out.println("Procediendo a la impresion con la impresora "+ iNumeroImpresora + 1);
        }finally{lock.unlock();}
    }

    public void liberarImpresora(int i){
        if(i > 3){
            System.out.println("Esa impresora no existe");
        }
        lock.lock();// Realizamos el acceso en exclusion mutua
        try{
            //! Preguntar Antonio Tomeu sobre como funcionan realmente las condition de reentrantlock
            vbImpresorasOcupadas[i]=false;
            n++;
        }finally{lock.unlock();}
    }

}