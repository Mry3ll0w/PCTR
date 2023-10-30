public class tryOne extends Thread{
	private int tipoHilo;
	private static volatile int Turno = 1;
	private static volatile int nVueltas = 10000;
	private static volatile int n = 0;

    public tryOne(int tipoHilo)
    {this.tipoHilo=tipoHilo;}

    public void run()    {
      switch(tipoHilo){
        case 1:{
          for(int i=0; i<nVueltas; i++){//No forma parte del protocolo

            //Zona no critica
            //Este metodo no cumple la 4a condicion ya que si en esta zona no se avanza nunca
            //deja pasar al siguiente hilo aunque el recurso este vacio 
            //==> Problema de Alternacia Estricta

            while(Turno!=1);//Espera ocupada, ya que estoy constantemente esperando a turno
              n++;
              Turno = 2;
            }
        break;
        }
        case 2: {
          for(int i=0; i<nVueltas;i++){
        	  while(Turno!=2);
        	    n--;
        	    Turno = 1;
          }
        	}
          break;
      }
    }

    public static void main(String[] args) throws InterruptedException{
      tryOne h1 = new tryOne(1);
      tryOne h2 = new tryOne(2);
      h1.start(); h2.start();
      h1.join(); h2.join();
      System.out.println(n);
    }
}
