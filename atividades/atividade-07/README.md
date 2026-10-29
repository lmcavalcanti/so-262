# Atividade 07

Execute o algoritmo dos Banqueiros definido na aula anterior. 

Gere arquivos que evidenciam a execução no GitHub na pasta atividade-07 como definido abaixo:
1) Explicando cada teste proposto e sua saída gerada;
2) Print da execução de cada Teste.
3) Identifique o seu nome nos prints.

## Explicação

### 1. Estado Inicial do Sistema

O sistema inicia com **[10, 5, 7]** recursos totais, onde cada posição do vetor representa um tipo de recurso (**R0**, **R1** e **R2**), como núcleos de CPU ou memória RAM. Desses recursos totais, uma parte já está previamente alocada aos 5 processos simulados (**P0**, **P1**, **P2**, **P3** e **P4**). Subtraindo os recursos alocados do total, ficamos com **[3, 3, 2]** recursos livres no vetor de disponíveis.

### 2. Verificação do Estado Seguro (Sequência Inicial)

O sistema realiza uma simulação para encontrar uma sequência segura de execução — isto é, uma ordem de alocação em que não se corra o risco de entrar em *deadlock*. Como o algoritmo assume que um processo concluído libera todos os recursos que detinha, ele encontra uma sequência que garante a execução de todos sem que faltem recursos: **P1 $\rightarrow$ P3 $\rightarrow$ P0 $\rightarrow$ P2 $\rightarrow$ P4**.

### 3. Teste 1: Requisição do Processo P1

Após a verificação inicial, o processo **P1** faz uma solicitação de recursos adicionais (**[1, 0, 2]**). O sistema simula essa alocação de forma provisória e roda o teste de segurança. Como o resultado é positivo e o estado permanece seguro, os recursos são efetivamente concedidos, permitindo que **P1** conclua sua execução, libere seus recursos de volta ao sistema e mantenha o fluxo contínuo.

### 4. Teste 2: Requisição do Processo P4

Em seguida, o processo **P4** solicita recursos (**[3, 3, 0]**). Essa requisição é negada imediatamente porque ele pede mais unidades do recurso **R0** do que o sistema possui livre no momento (solicita 3 unidades, mas só há 2 disponíveis). Dessa forma, **P4** é bloqueado e deve aguardar a liberação de recursos por outros processos.
