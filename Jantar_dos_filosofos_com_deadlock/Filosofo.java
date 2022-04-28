import java.util.concurrent.Semaphore;

class Filosofo extends Thread {

    private Garfo garfo_esquerda;
    private Garfo garfo_direita;
    private String nome;

    public Filosofo(String nome, Garfo garfo_esquerda, Garfo garfo_direita) {
        this.nome = nome;
        this.garfo_esquerda = garfo_esquerda;
        this.garfo_direita = garfo_direita;
    }

    public String getNome() {
        return nome;
    }

    public Garfo getGarfo_esquerda() {
        return garfo_esquerda;
    }

    public Garfo getGarfo_direita() {
        return garfo_direita;
    }

    // Executa um processo concorrente
    // É iniciado com o método .start()
    public void run() {
        while(true){
            try {
                System.out.printf("\n%s está pensando", this.nome);
                sleep(1); //Pensando
                eat(); //Tentar comer
            } catch (InterruptedException ex) {
                System.out.println("Erro: " + ex.getMessage());
            }
        }
    }

    private void eat() {
        //Tenta pegar o garfo da esquerda
        System.out.println("\n" + this.nome + " está tentando pegar o garfo da esquerda");
        try {
            ((Semaphore) garfo_esquerda.getSemaphore()).acquire();
            System.out.println("\n" + this.nome + " pegou o garfo da esquerda");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Tenta pegar o garfo da direita
        System.out.println("\n" + this.nome + " está tentando pegar o garfo da direita");
        try {
            ((Semaphore) garfo_direita.getSemaphore()).acquire();
            System.out.println("\n" + this.nome + " pegou o garfo da direita");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Come
        System.out.println("\n" + this.nome + " está comendo");
        try {
            sleep(1); //Comendo
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Libera o garfo da esquerda
        System.out.println("\n" + this.nome + " está liberando o garfo da esquerda");
        ((Semaphore) garfo_esquerda.getSemaphore()).release();
        System.out.println("\n" + this.nome + " liberou o garfo da direita");
        ((Semaphore) garfo_direita.getSemaphore()).release();
    }
}