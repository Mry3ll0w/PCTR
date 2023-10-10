import java.util.Scanner;
import java.util.*;

public class nAleatorio {
  public static void main(String[] args){
    try (Scanner s = new Scanner(System.in)) {//Lectura por teclado
      int     n = s.nextInt();
      Random  r = new Random();
      //Probamos distintas cosas de la api de Random
      for(int i=0; i<n; i++)
        System.out.println(r.nextFloat());
      for(int i=0; i<n; i++)
        System.out.println(r.nextInt());    
      for(int i=0; i<n; i++)	 	 		     		System.out.println(r.nextBoolean());
      for(int i=0; i<n; i++){
        System.out.println(r.nextGaussian());
      }
    }
  }  
}
