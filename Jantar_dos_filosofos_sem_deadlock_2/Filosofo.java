class Filosofo extends Thread {

    private Garfo garfo_esquerda;
    private Garfo garfo_direita;
    private String nome;
    private boolean canhoto = false;

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

    public void setCanhoto(){
        this.canhoto = true;
    }

    public boolean isCanhoto(){
        return this.canhoto;
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
        if(canhoto){
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
    
                } else { //Se o garfo da direita já esta ocupado, aguarda até libera-lo
                    while(!garfo_direita.take()){
                        System.out.printf("\n%s está esperando o garfo da direita(%s) ser liberado", this.nome, this.garfo_direita.getId());
                        try {
                            sleep(2000);
                        } catch (InterruptedException ex) {
                            System.out.println("Erro: " + ex.getMessage());
                        }
                    }

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
                }
            }else{
                System.out.printf("\n%s tentou pegar o garfo da esquerda(%s), mas não conseguiu!", this.nome, this.garfo_esquerda.getId());
            }
        }else{
            if (garfo_direita.take()) { //Tenta pegar o garfo da direita
                System.out.printf("\n%s pegou o seu garfo da direita(%s)", this.nome, this.garfo_direita.getId());
                if (garfo_esquerda.take()) { //Tenta pegar o garfo da esquerda
                    try {
                        System.out.printf("\n%s pegou o seu garfo da esquerda(%s)", this.nome, this.garfo_esquerda.getId());
                        System.out.printf("\n%s está comendo...", this.nome);
                        sleep(2000); // comendo;
                    } catch (InterruptedException ex) {
                        System.out.println("Erro: " + ex.getMessage());
                    }
    
                    //Após comer, devolver os garfos
                    garfo_direita.putDown();
                    garfo_esquerda.putDown();
                    System.out.printf("\n%s devolveu os garfos para a mesa", this.nome);
    
                } else { //Se o garfo da esquerda já esta ocupado, aguarda até libera-lo
                    while(!garfo_esquerda.take()){
                        System.out.printf("\n%s está esperando o garfo da esquerda(%s) ser liberado", this.nome, this.garfo_esquerda.getId());
                        try {
                            sleep(2000);
                        } catch (InterruptedException ex) {
                            System.out.println("Erro: " + ex.getMessage());
                        }
                    }

                    try {
                        System.out.printf("\n%s pegou o seu garfo da esquerda(%s)", this.nome, this.garfo_esquerda.getId());
                        System.out.printf("\n%s está comendo...", this.nome);
                        sleep(2000); // comendo;
                    } catch (InterruptedException ex) {
                        System.out.println("Erro: " + ex.getMessage());
                    }

                    //Após comer, devolver os garfos
                    garfo_direita.putDown();
                    garfo_esquerda.putDown();
                    System.out.printf("\n%s devolveu os garfos para a mesa", this.nome);
                }
            }else{
                System.out.printf("\n%s tentou pegar o garfo da direita(%s), mas não conseguiu!", this.nome, this.garfo_direita.getId());
            }
        }
    }
}