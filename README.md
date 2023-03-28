# Implementação da solução do jantar dos filósofos
Este trabalho teve como objetivo implementar a solução do jantar dos filósofos, um problema clássico de concorrência em programação paralela. 
A solução foi implementada utilizando semáforos e a linguagem de programação Python.

## O problema do jantar dos filósofos
O problema do jantar dos filósofos consiste em um grupo de filósofos sentados em uma mesa redonda, cada um com um garfo à sua esquerda e à sua direita. 
Os filósofos alternam entre pensar e comer. Para comer, um filósofo precisa de dois garfos, um em cada lado. 
O problema surge quando todos os filósofos pegam o garfo à sua esquerda ao mesmo tempo, resultando em um impasse conhecido como deadlock.

### Implementação com deadlock
A primeira implementação que fizemos utilizou semáforos para controlar o acesso aos garfos. 
Cada garfo foi representado por um semáforo, e um filósofo só podia pegar um garfo se os dois garfos estivessem disponíveis. 
No entanto, essa implementação apresentou um problema de deadlock, que ocorreu quando todos os filósofos pegaram o garfo à sua esquerda ao mesmo tempo, 
e nenhum garfo ficou disponível para que um filósofo pudesse comer.

### Primeira implementação sem deadlock
Para resolver o problema de deadlock, implementamos uma solução onde cada filósofo é executado como uma thread separada e possui dois garfos à sua disposição 
- um garfo à esquerda
- um garfo à direita

O método *run()* é o método executado pela thread filósofo e é responsável por controlar o ciclo de pensamento e tentativa de comer. 
O filósofo começa pensando por 2 segundos e, em seguida, chama o método 'eat()'. 
Se o filósofo conseguir pegar ambos os garfos, ele come por 2 segundos e em seguida devolve os garfos. 
Caso contrário, ele devolve o garfo que conseguiu pegar e continua pensando.

O método *eat()* é responsável por tentar pegar os garfos da esquerda e da direita. 
Ele tenta pegar o garfo da esquerda primeiro e, se tiver sucesso, tenta pegar o garfo da direita. 
Se conseguir pegar ambos os garfos, o filósofo come por 2 segundos e, em seguida, devolve os garfos. 
Caso contrário, o filósofo devolve o garfo que conseguiu pegar e continua pensando.

Essa implementação garante que dois filósofos adjacentes não peguem garfos ao mesmo tempo, pois cada filósofo tenta pegar os garfos em uma 
ordem fixa (esquerda primeiro, depois direita). 
Isso impede que um filósofo fique com um garfo e bloqueie outro filósofo, o que levaria a um deadlock.

Em resumo, a implementação utiliza uma solução simples e eficaz para garantir a corretude do código e evitar deadlock no problema do jantar dos filósofos.


### Segunda implementação sem deadlock
Essa solução é uma tentativa de evitar o deadlock utilizando uma estratégia de prevenção, na qual os filósofos que possuem uma 
preferência de garfo (canhotos ou destros) são configurados para pegar os garfos na ordem correspondente à sua preferência.
Os filósofos são representados como threads, cada um executando o método run() em loop infinito. 
A thread começa pensando e, depois de um tempo, tenta pegar os garfos e comer. 
Se conseguir pegar ambos os garfos, come durante um determinado tempo e depois devolve os garfos à mesa. 
Se um garfo estiver ocupado, a thread fica em espera até que ele esteja disponível.
Os garfos são representados por objetos Garfo, que contêm uma variável booleana indicando se o garfo está disponível ou não, 
e dois métodos take() e putDown() que são usados para pegar e devolver os garfos à mesa.

O problema do deadlock ocorre quando todos os filósofos tentam pegar o garfo à sua esquerda ao mesmo tempo, 
deixando o garfo à sua direita inacessível para o filósofo à sua direita. 
Na solução proposta, o deadlock é evitado dando prioridade aos filósofos canhotos ou destros, 
fazendo com que eles tentem pegar os garfos na ordem preferencial correspondente.

Por exemplo, se um filósofo for canhoto, ele tentará pegar primeiro o garfo à sua esquerda e depois o garfo à sua direita. 
Se o garfo à sua esquerda estiver ocupado, ele esperará até que esteja livre, mas o garfo à sua direita pode estar livre e disponível para outros filósofos pegarem.
Dessa forma, os filósofos canhotos e destros nunca tentarão pegar os mesmos garfos simultaneamente, evitando o deadlock. 
No entanto, ainda pode haver situações em que vários filósofos canhotos ou destros tentem pegar seus garfos ao mesmo tempo, levando a um bloqueio parcial. 
Porém, o bloqueio parcial pode ser resolvido assim que um dos filósofos consiga pegar os garfos e desbloquear a situação para os demais filósofos.
