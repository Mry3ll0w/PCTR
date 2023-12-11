
class monitorCuentaCompartida{
    private Integer IBalance = 0;


    synchronized void depositos(Integer c){
        //El deposito siempre puede ingresar puesto que no tiene condiciones de guarda
        IBalance += c;
    }

    synchronized void reintegro(Integer c){
        while(c > IBalance){
            try{
                wait();
            }catch(Exception e){}
        }
    }
    
}


public class cuentaCompartida {
    


}
