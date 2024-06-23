import java.rmi.*;
import java.rmi.registry.*;

public class ClienteEjemploRMI1 {

  public static void main(String[] args) throws Exception {
    int a = 10;
    int b = -10;
    // SIEMPRE debe convertirse el retorno del metodo Naming.lookup a la interfaz
    // remota

    IEjemploRMI1 RefObRemoto = (IEjemploRMI1) Naming.lookup("//localhost/Servidor");
    System.out.println(RefObRemoto.Suma(a, b));
    System.out.println(RefObRemoto.Resta(a, b));
    System.out.println(RefObRemoto.Producto(a, b));
    System.out.println(RefObRemoto.Cociente(a, b));
    int v[] = RefObRemoto.leerVector(1, new int[] { 1, 3, 4, 5 }, 4);
    for (int i = 0; i < 4; i++) {
      System.out.println("v[" + i + "]=" + v[i]);
    }

  }
}