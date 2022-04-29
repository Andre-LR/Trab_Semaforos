class ConsumerThread extends Thread {
    private int id;
    private FiniteBuffer buffer;
    private boolean buscaPar;

    public ConsumerThread(int _id, FiniteBuffer _fb, boolean _buscaPar) {
      this.id = _id;
      this.buffer = _fb;
      this.buscaPar = _buscaPar;
    }

    public void run() {
      int v;
      while (true) {
        if (buscaPar) {
          v = buffer.deletePar();
        } else {
          v = buffer.deleteImpar();
        }

        if (v != -1) { // se o elemento foi deletado
          System.out.println("                                  Cons " + id + "  val " + v);
        }

        try {
          sleep(2500);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }