import java.util.concurrent.Semaphore;

class FiniteBuffer {

  private int size;
  private int in = 0;
  private int out = 0;
  private int[] buffer;

  private Semaphore naoCheio;
  private Semaphore naoVazio;
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
    naoVazio = new Semaphore(0); // controle de itens
  }

  public int insert(int v) {

    if (mutex.tryAcquire()) { // tenta entrar na regiao critica
      if (naoCheio.tryAcquire()) { // verifica se tem espaco no buffer
        buffer[in] = v; // sc: insere elemento
        incrIn(); // sc: insere elemento
        naoVazio.release(); // avisa que nao esta vazio
      } else {
        System.out.println("\nBuffer cheio!\n");
        sleep(2500);
        v = -1;
      }
      mutex.release(); // sai sc
    } else {
      v = -1;
    }

    return v;
  }

  public int deletePar() {
    int val = -1;

    if (mutex.tryAcquire()) { // tenta entrar na regiao critica
      if (naoVazio.tryAcquire()) { // verifica buffer não está vazio
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
      } else {
        System.out.println("\nBuffer vazio!\n");
        sleep(2500);
      }
      mutex.release(); // sai da sc
    }

    return val;
  }

  public int deleteImpar() {
    int val = -1;

    if (mutex.tryAcquire()) { // tenta entrar na regiao critica
      if (naoVazio.tryAcquire()) { // verifica buffer não está vazio
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
      } else {
        System.out.println("\nBuffer vazio!\n");
        sleep(2500);
      }
      mutex.release(); // sai da sc
    }

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