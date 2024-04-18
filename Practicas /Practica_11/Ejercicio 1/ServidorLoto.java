import java.util.concurrent.ThreadLocalRandom;
import java.rmi.server.*;
import java.rmi.*;
import java.util.HashMap;
import java.util.Map;

public class ServidorLoto extends UnicastRemoteObject implements IServidorLoto {

    private static int[] numeros = new int[6];

    ServidorLoto() throws Exception {
    }

    public static void main(String[] args) throws Exception {// Ejecucion del servidor

        // Generamos el numero premiado una vez.
        numeros = generaNumerosPremiados();

        // Binding del servidor
        IServidorLoto remoto = new ServidorLoto();

        Naming.bind("Servidor", remoto);// El servidor tiene nombre "Servidor" y genera un objeto remoto de nombre
                                        // remoto

        System.out.println("El servidor esta listo, los numeros premiados son: ");

        for (int i = 0; i < 6; i++) {
            System.out.println(i + 1 + " : " + numeros[i]);
        }
    }

    private static int[] generaNumerosPremiados() {

        for (int i = 0; i < 6; i++) {
            numeros[i] = ThreadLocalRandom.current().nextInt(0, 49);
        }

        return numeros;
    }

    public Boolean numeroPremiado(int[] apuesta) throws RemoteException {
        boolean premiado = true;
        for (int i = 0; i < 6 && premiado; i++) {
            premiado = numeros[i] == apuesta[i];
        }

        return premiado;
    }

}
// Abrir antes el dns con rmiregistry [PUERTOs]