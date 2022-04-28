import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList<Filosofo> filosofos = new ArrayList<>();
        ArrayList<Garfo> garfos = new ArrayList<>();
        String[] nomes = { "Platão", "Aristóteles", "Nietzsche", "Adam Smith", "Zygmunt Bauman" };
        int tam = nomes.length;

        //Cria os garfos
        for (int i = 0; i < tam; i++) {
            garfos.add(new Garfo(i));       
        }

        //Cria os filosofos com os garfos posicionados na mesa e da start no processo
        //Primeiro filósofo é canhoto
        for (int i = 0; i < tam; i++) {
            if (i == 0) {
                Filosofo novo_filosofo = new Filosofo(nomes[i], garfos.get(tam - 1), garfos.get(i));
                novo_filosofo.setCanhoto();
                filosofos.add(novo_filosofo);
                novo_filosofo.start();

            } else {
                Filosofo novo_filosofo = new Filosofo(nomes[i], garfos.get(i - 1), garfos.get(i));
                filosofos.add(novo_filosofo);
                novo_filosofo.start();
            }
        }
    }
/*
    Semaphore s = new Semaphore(1);


    *Código não crítico...
    s.acquire(); --> método para bloquear o semáforo / bloquear acesso ao recurso compartilhado
    ***Código crítico***
    s.release() --> método para liberar o semáforo / liberar acesso ao recurso compartilhado
    *Código não crítico...

*/
}