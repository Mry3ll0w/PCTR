
import java.rmi.*;

public interface IServidorLoto extends Remote {

    Boolean numeroPremiado(int[] apuesta) throws RemoteException;

}
