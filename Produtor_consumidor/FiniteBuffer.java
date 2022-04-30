import java.util.concurrent.Semaphore;

class FiniteBuffer {

  private int size;
  private int in = 0;
  private int out = 0;
  private int[] buffer;

  private Semaphore naoCheio;
  private Semaphore naoVazioPar;
  private Semaphore naoVazioImpar;
  private Semaphore mutex;

  private void incrIn() {
    in = (in + 1) % size;
  }

  private void incrOut() {
    out = (out + 1) % size;
  }

  public FiniteBuffer(int _size) {
    size = _size;
    buffer = new int[size]; // armazena os itens

    mutex = new Semaphore(1); // para exclusao mutua (sc)
    naoCheio = new Semaphore(size); // controle de espaco disponivel
    naoVazioPar = new Semaphore(0); // controle de itens pares
    naoVazioImpar = new Semaphore(0); // controle de itens impares
  }

  public int insert(int v) {
    try { 
      naoCheio.acquire();  //  espera ter espaco no buffer
      mutex.acquire();     //  entra sc
    } catch (InterruptedException ie) {}  
    buffer[in]=v;          //  sc: insere elemento
    incrIn();              //  sc: insere elemento
    mutex.release();         //  sai sc 
    
    if(v%2==0){ // avisa que contém elemento par
      naoVazioPar.release();
    }else{
      naoVazioImpar.release(); // avisa que contém elemento impar
    }

    return v;
  }

  public int deletePar() {
    int val = -1;
    try {  
      naoVazioPar.acquire(); //  espera o buffer conter pelo menos um item par
      mutex.acquire();    //  entra na sc
		}  catch (InterruptedException ie) {}  

    for (int i = 0; i < buffer.length; i++) { // retira o primeiro elemento par do buffer que encontrar
      if (buffer[i] % 2 == 0) { // se for par
        val = buffer[out];
        incrOut(); // sc: remove elemento
        naoCheio.release(); // avisa que tem espaco
        break;
      }

      if (i == buffer.length - 1) { // se não encontrar nenhum par
        System.out.println("\nNão há nenhum número par no buffer!\n");
      }
    }
    mutex.release(); // sai da sc

    return val;
  }

  public int deleteImpar() {
    int val = -1;
    try {  
      naoVazioImpar.acquire(); //  espera o buffer conter pelo menos um item impar
      mutex.acquire();    //  entra na sc
		}  catch (InterruptedException ie) {}  

    for (int i = 0; i < buffer.length; i++) { // retira o primeiro elemento impar do buffer que encontrar
      if (buffer[i] % 2 != 0) { // se for impar
        val = buffer[out];
        incrOut(); // sc: remove elemento
        naoCheio.release(); // avisa que tem espaco
        break;
      }

      if (i == buffer.length - 1) { // se não encontrar nenhum impar
        System.out.println("\nNão há nenhum número impar no buffer!\n");
      }
    }
    mutex.release(); // sai da sc
    return val;
  }

  public static void sleep(int ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}