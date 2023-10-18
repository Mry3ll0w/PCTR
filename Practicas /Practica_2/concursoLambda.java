public class concursoLambda {
    public static int n=0;//Para que poueda acceder sin problemas la funcion lambda
    public static void main(String[] args) throws Exception{
        
        Runnable rInc = () ->{
            for(int i = 0; i< 10000;i++)
                n++;
        };

        Runnable rDesc = () ->{
            for(int i = 0; i< 10000;i++)
                n--;
        };

        Thread h1 = new Thread(rInc);
        Thread h2 = new Thread(rDesc);
        h1.start();
        h2.start();
        h1.join();h2.join();

        System.out.println(n);
        
    }
}

