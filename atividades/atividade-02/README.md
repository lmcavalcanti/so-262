# Atividade 02

Evidenciar a execução do programa Produtor-Consumidor em Java anexo aos Recursos. Fazer print da execução do programa com o nome do aluno. Postar na pasta atividade-02 do seu GitHub. Copiar o link do repositório/atividade-02 no Google Classroom.

## Obs:

Para evidenciar a Condição de Corrida, eu modifiquei o código base, ao invés de apenas uma thread pro Consumer e uma Producer, esse código cria 20 threads pra cada. 

Além disso, eu fiz o Nap_Time ser igual a zero, dessa forma ficou rápido de ver o problema. Mesmo, com o limite do Buffer sendo 3, teve-se um output que ultrapassou isso, chegando a 4. Não só isso, também ficou bem evidente algumas vezes em que era feito uma ação e não teve mudança no contador, indicando que duas threads leram o buffer e modificaram ele juntos.

Deixando o código rodar por mais tempo é possível chegar a valores ainda mais absurdos como 132 ou mesmo -203, ambos valores que eu presenciei durante meus testes. 
