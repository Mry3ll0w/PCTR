import java.util.concurrent.Callable;

public class tareaPrimos implements Callable<Long> {

  // Long no es igual que long, Long es una API de Java un wrapper de long
  // "normal"
  // Runnable y Callable son capsulas que engloban codigo concurrente que no es
  // capaz
  // de ejecutar codigo por si mismo, runnable es void y callable no.

  private final long linf;
  private final long lsup;
  private long total;// No se comparte ya que cada hilo encuentra x primos

  public tareaPrimos(long linf, long lsup) {
    this.linf = linf;
    this.lsup = lsup;
    System.out.println("Rango de analisis: " + "[" + linf + "-" + lsup + "]");
  }

  public boolean esPrimo(long n) {
    if (n <= 1)
      return (false);
    for (long i = 2; i <= Math.sqrt(n); i++)
      if (n % i == 0)
        return (false);
    return (true);
  }

  public Long call() {// void run de callable
    for (long i = linf; i <= lsup; i++)
      if (esPrimo(i))
        total++;

    return (total);
  }
}
