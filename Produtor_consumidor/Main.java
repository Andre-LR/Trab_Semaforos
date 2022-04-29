class Main {
	public static void main(String[] args) {
    FiniteBuffer buffer = new FiniteBuffer(10);

    ProducerThread produtor01 = new ProducerThread(1, buffer);
    ProducerThread produtor02 = new ProducerThread(2, buffer);
    ProducerThread produtor03 = new ProducerThread(3, buffer);

    ConsumerThread consumidor01 = new ConsumerThread(1, buffer, false);
    ConsumerThread consumidor02 = new ConsumerThread(2, buffer, true);
    //ConsumerThread consumidor03 = new ConsumerThread(3, buffer, false);
    //ConsumerThread consumidor04 = new ConsumerThread(4, buffer, true);

    produtor01.start();
    produtor02.start();
    produtor03.start();
    consumidor01.start();
    consumidor02.start();
    //consumidor03.start();
    //consumidor04.start();
  }
}

/**
 * Sequência resultados "Buffer Cheio"
 *    Ocorre quando uma série de processos produtores em sequência ganham acesso ao buffer e tentam inserir um elemento,
 * 
 * 
 * Sequência resultados "Buffer Vazio"
 *    Ocorre quando um processo consumidor tem acesso ao buffer, e os demias entram de "carona" na sequência, mas não há elementos para retirar
 */
