public class ej1 {
    public static void main(String args[]) throws Exception{
        //Creamos Los hilos
        Hebra h1 = new Hebra(0, 0, 4);
        Hebra h2 = new Hebra(1, 4, 8);
        h1.start();h2.start();
        h1.join();h2.join();
        System.out.println(Hebra.vResultadoParcial[0] + Hebra.vResultadoParcial[1]);
    }
}


class Hebra extends Thread{

    int id=0;
    int inicio=0, fin=0;
    public static int[] vResultadoParcial = {0,0}; 
    static int[] vArray1 = {1,1,1,1,1,1,1,1}, vArray2 = {1,1,1,1,1,1,1,1};
    public Hebra(int id, int inicio, int fin){
        this.id=id;
        this.inicio = inicio;
        this.fin = fin;
    }

    public void prodEscalar(int inicio, int fin){
        for(int i=inicio; i<fin ; i++){
            vResultadoParcial[id] += vArray1[i] * vArray2[i];
        }
    }

    public void run(){
        prodEscalar(inicio, fin);
    }
}
