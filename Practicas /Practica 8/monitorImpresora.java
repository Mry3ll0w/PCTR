

public class monitorImpresora {
    private boolean bImpresoras [] = {true, true,true};
    private int iImpresorasOcupadas = 0;
    private final int N = 3;
    synchronized int pedirImpresora(){

        
        while(iImpresorasOcupadas == N){//Usamos condiciones de guarda en vez de if, es SC
            try{
                wait();
            }catch(Exception e){}
        }
        //Si no hay 3 impresoras ocupadas, busco aquella que esta libre
        int libre = 0;
        while (!bImpresoras[libre]) {
            libre ++;
        }
        //Una vez tenemos la impresora decrementamos el numero de impresoras en uso.
        bImpresoras[libre]=false;
        iImpresorasOcupadas++;
        return libre;
    }

    synchronized void liberarImpresora(int i){
        bImpresoras[i]= true;
        iImpresorasOcupadas--;
        notifyAll();
    }

    monitorImpresora(){
        
    }

}
