public class secureObject{
  public long n = 0;
   
  public secureObject(){}
  public synchronized void inc(){n++;}
  public synchronized long get(){return this.n;}
  //Que significa Sincronized ==> Los metodos estan sincronizados para acceso a exclusion mutua
}

