import java.rmi.*;

public class ClienteLoto {
    public static void main(String[] args) throws Exception {
        int a = 10;
        int b = -10;
        // SIEMPRE debe convertirse el retorno del metodo Naming.lookup a la interfaz
        // remota

        IServidorLoto RefObRemoto = (IServidorLoto) Naming.lookup("//localhost/Servidor");
        int[] apuesta = new int[6];
        apuesta[0] = 44;
        apuesta[1] = 9;
        apuesta[2] = 39;
        apuesta[3] = 5;
        apuesta[4] = 31;
        apuesta[5] = 34;
        System.out.println(RefObRemoto.numeroPremiado(apuesta));

    }
}
