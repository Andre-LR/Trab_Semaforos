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
                sleep(2000); //Pensando
                eat(); //Tentar comer
            } catch (InterruptedException ex) {
                System.out.println("Erro: " + ex.getMessage());
            }
        }
    }

    private void eat() {
        if (garfo_esquerda.take()) { //Tenta pegar o garfo da esquerda
            System.out.printf("\n%s pegou o seu garfo da esquerda(%s)", this.nome, this.garfo_esquerda.getId());
            if (garfo_direita.take()) { //Tenta pegar o garfo da direita
                try {
                    System.out.printf("\n%s pegou o seu garfo da direita(%s)", this.nome, this.garfo_direita.getId());
                    System.out.printf("\n%s está comendo...", this.nome);
                    sleep(2000); // comendo;
                } catch (InterruptedException ex) {
                    System.out.println("Erro: " + ex.getMessage());
                }

                //Após comer, devolver os garfos
                garfo_esquerda.putDown();
                garfo_direita.putDown();
                System.out.printf("\n%s devolveu os garfos para a mesa", this.nome);

            } else { //Se o garfo da direita já esta ocupado, devolve o garfo da esquerda
                System.out.printf("\n%s viu que seu Garfo da direita(%s) está ocupado e Devolveu o garfo da esquerda(%s) para a mesa", this.nome, this.garfo_direita.getId(), this.garfo_esquerda.getId());
                garfo_esquerda.putDown();
            }
        }else{
            System.out.printf("\n%s tentou pegar o garfo da esquerda(%s), mas não conseguiu!", this.nome, this.garfo_esquerda.getId());
        }
    }
}