import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class P11 {

}

// Creamos objeto remoto

interface ICalculadora extends Remote {

    public Double add(Double a, Double b) throws RemoteException;

    public Double substract(Double a, Double b) throws RemoteException;
}

class ServidorCalculadora extends UnicastRemoteObject implements ICalculadora {

    public static void main(String[] args) throws Exception {

        // 1) Binding del Servidor
        ICalculadora remote = new ServidorCalculadora();
        // 2) Naming, para vincular el nombre del servidor con la DNS
        Naming.bind("Server", remote);

        System.out.println("Servidor listo");

    }

    ServidorCalculadora() throws RemoteException {
    }

    public Double add(Double a, Double b) throws RemoteException {
        System.out.println("Recibida suma");
        return a + b;
    }

    public Double substract(Double a, Double b) {
        return a - b;
    }

}

class ClienteCalculadora {
    public static void main(String[] args) throws Exception {
        // 1 Convertimos naming a interfaz remota
        ICalculadora remotoCalc = (ICalculadora) Naming.lookup("//localhost/Server");
        System.out.println("Suma " + remotoCalc.add(12.5, 1.5));
        System.out.println("Resta " + remotoCalc.substract(12.5, 1.5));
    }
}