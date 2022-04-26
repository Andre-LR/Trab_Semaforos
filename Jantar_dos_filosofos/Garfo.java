import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

class Garfo {
    public Semaphore garfo = new Semaphore(1);
    public int id;
    private static Random random = new Random();

    Garfo(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    /**
     * 
     *      Para evitar deadlock, usamos tryAcquire() para que um Filósofo libere o garfo esquerdo caso ele não consiga
     *      pegar o garfo direito. A liberação se dará dentro de um intervalo aleatório de tempo (0 a 400 milisegundos).
     * 
     */
    public boolean take() {
        try {
            return garfo.tryAcquire(random.nextInt(400), TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            return false;
        }
    }

    public void putDown() {
        garfo.release();
    }
}