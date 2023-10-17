public class ej1 {
    
    public static void main(String args[]) throws Exception{
        HebraEj1 hebra1 = new HebraEj1();
        HebraEj1 hebra2 = new HebraEj1();
        
        //Empezamos ambas hebras
        hebra1.start();hebra2.start();
        hebra1.join();hebra2.join();
        //Comprobamos el valor que irá tomando n
        System.out.println("Valor de n "+ hebra1.n());
    }
}
