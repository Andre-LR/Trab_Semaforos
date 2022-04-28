import java.util.concurrent.Semaphore;

class Garfo {
    public Semaphore semaphore = new Semaphore(1);
    public int id;

    Garfo(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Semaphore getSemaphore() {
        return this.semaphore;
    }

    /**
     * 
     *      Para evitar deadlock, usamos tryAcquire() para que um Filósofo libere o garfo esquerdo caso ele não consiga
     *      pegar o garfo direito. A liberação se dará dentro de um intervalo aleatório de tempo (0 a 400 milisegundos).
     * 
     */
   

    public void putDown() {
        semaphore.release();
    }
}