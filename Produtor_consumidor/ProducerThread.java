import java.util.Random;

class ProducerThread extends Thread {
    private int id;
    private FiniteBuffer buffer;
    private Random rand = new Random();

    public ProducerThread(int _id, FiniteBuffer _fb) {
      this.id = _id;
      this.buffer = _fb;
    }

    public void run() {
      while (true) {
        int val = buffer.insert(rand.nextInt(10));
        if (val != -1) {
          System.out.println("Producer " + id + " produced " + val);
        }
        try {
          sleep(2500);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
}