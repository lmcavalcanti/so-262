# Especificação de Projeto: Simulador de Gerenciador de Processos

## 1. Visão Geral e Arquitetura do Simulador

### 1.1 Contexto do Simulador

Este projeto consiste em um simulador de gerenciamento de processos e threads rodando em **modo usuário**. 

Isso é, o simulador é um programa comum (como qualquer outro que criamos em C, Java ou Python), mas ele simulará internamente as decisões que o núcleo de um Sistema Operacional (Kernel) toma para gerenciar a CPU. 

### 1.2 Estrutura do Hardware Simulado

Como não estamos trabalhando no hardware real, o simulador criará uma "CPU Virtual" utilizando estruturas de dados simples. O hardware simulado será composto por:

* **Relógio Lógico (Clock):** Uma variável int global (ex: `tempo_atual = 0`) que será incrementada a cada ciclo do simulador, representando a passagem do tempo.

* **CPU Virtual:** Uma estrutura que representa o processador que só pode executar um processo por vez.

* **Registradores Básicos e Contador de Programa (PC):** Variáveis que guardam onde o processo parou. Quando um processo sai da CPU (troca de contexto), os valores de seus registradores e do PC são salvos para que ele possa continuar de onde parou no futuro.

### 1.3 Fluxo Geral de Execução

O simulador funcionará através de um loop principal contínuo. A cada ciclo do relógio lógico, o simulador irá:

1. Ler processos que chegaram no tempo atual.

2. Verificar se o processo em execução terminou ou precisa fazer Entrada/Saída (E/S).

3. Verificar se há processos bloqueados que terminaram sua E/S e podem voltar para a fila de Prontos.

4. Chamar o **Escalonador** para decidir quem usará a CPU no próximo ciclo.

5. Incrementar o relógio lógico (+1).

---

## 2. Especificação do Bloco de Controle de Processo (PCB) e Tabela de Processos

### 2.1 Estrutura do PCB (Process Control Block)

O Bloco de Controle de Processo é a estrutura de dados encarregada de armazenar todas as informações relativas a um processo individual. Cada processo no simulador possuirá uma instância única de PCB composta pelos seguintes campos:

| Campo / Atributo | Tipo de Dado | Descrição |
| :--- | :--- | :--- |
| `PID` | Inteiro | Identificador único e exclusivo do processo no sistema. |
| `estado` | Enumeração | Estado atual do processo (`PRONTO`, `EXECUTANDO`, `BLOQUEADO`, `TERMINADO`). |
| `prioridade` | Inteiro | Nível de prioridade para algoritmos de escalonamento prioritários. |
| `PC_salvo` | Inteiro | Contador de programa fictício; indica qual instrução o processo executará a seguir. |
| `registradores` | Vetor / Objeto | Guardará os dados fictícios dos registradores salvos durante uma troca de contexto. |
| `tempo_cpu_gasto` | Inteiro | Contador acumulado de quantos ciclos de relógio o processo passou na CPU. |
| `tempo_espera` | Inteiro | Contador acumulado de quantos ciclos o processo passou aguardando na fila de prontos. |
| `tempo_bloqueado` | Inteiro | Tempo restante para a conclusão de uma operação de Entrada/Saída (E/S). |

### 2.2 Tabela de Processos

A Tabela de Processos consiste na estrutura global (como uma Lista Encadeada, Vetor dinamico ou Dicionário) gerenciada pelo núcleo do simulador. 

* **Função:** Armazenar e gerenciar a referência de todos os PCBs em execução ou aguardando finalização.

* **Operações de Busca:** Permitir a busca rápida de um processo por meio de seu `PID`.

* **Gerenciamento de Memória:** Liberar da tabela os dados do PCB quando um processo executa a chamada `exit` e entra no estado `TERMINADO`.

---

## 3. Ciclo de Vida e Grafo de Transição de Estados

### 3.1 Estados Fundamentais do Processo

O simulador implementará uma máquina de estados finitos contendo três estados principais de execução, além das etapas de inicialização e encerramento:

* **Pronto (Ready):** O processo está carregado na memória e pronto para usar a CPU, aguardando ser selecionado pelo escalonador.

* **Em Execução (Running):** O processo está usando a CPU Virtual ativamente (apenas 1 processo por vez no simulador).

* **Bloqueado (Waiting / Blocked):** O processo está suspenso aguardando a finalização de uma operação fictícia de Entrada/Saída.

* **Novo / Criado (New):** Estado inicial durante a leitura e alocação do PCB.

* **Terminado (Exit):** O processo concluiu sua execução e aguarda a remoção dos seus dados da Tabela de Processos.

### 3.2 Tabela de Transições e Eventos Gatilhos

| Estado Origem | Estado Destino | Evento Gatilho | Descrição da Transição |
| :--- | :--- | :--- | :--- |
| `Novo` | `Pronto` | **Criação / Fork** | O processo é instanciado na Tabela de Processos e entra na fila de Prontos. |
| `Pronto` | `Em Execução` | **Escalonamento (Dispatch)** | O Escalonador de CPU seleciona o processo para ocupar a CPU Virtual. |
| `Em Execução` | `Pronto` | **Preempção (Quantum)** | O tempo máximo de CPU (*quantum*) expira e o processo devolve a CPU. |
| `Em Execução` | `Bloqueado` | **Solicitação de E/S** | O processo solicita um recurso lento (disco, rede) e abre mão da CPU. |
| `Bloqueado` | `Pronto` | **Conclusão de E/S** | O dispositivo de E/S finalizou o trabalho (`tempo_bloqueado == 0`). |
| `Em Execução` | `Terminado` | **Chamada Exit** | O processo encerra todas as suas instruções com sucesso ou por erro. |

### 3.3 Modelo Visual do Grafo de Estados

           (Criação)
               |
               v
        +--------------+   Escalonamento   +--------------+
        |    PRONTO    | ----------------> | EM EXECUÇÃO  | -------> (Exit) Terminado
        +--------------+                   +--------------+
               ^                                  |
               |       Expiração de Quantum       |
               +----------------------------------+
               ^                                  |
               |         Solicitação E/S          |
               |                                  v
               +--------------------------- +--------------+
                       Conclusão E/S        |  BLOQUEADO   |
                                            +--------------+

---

## 4. Especificação do Escalonador de CPU

### 4.1 Função do Escalonador

O Escalonador de CPU é o módulo do simulador responsável por decidir qual processo do estado `Pronto` assumirá a CPU Virtual. O simulador suportará dois algoritmos de escalonamento intercambiáveis, que poderão ser definidos na inicialização do sistema.

### 4.2 Algoritmos de Escalonamento

#### Algoritmo 1: Round Robin (Circular com Quantum)

* **Estrutura de Fila:** Fila FIFO (*First-In, First-Out*). Processos recém-chegados ou liberados da E/S entram no final da fila.

* **Mecanismo de Preempção:** Define-se uma fatia fixa de tempo chamada *quantum* (ex: 2 ou 4 ciclos de relógio).

* **Regra:** O processo em execução permanece na CPU até terminar sua tarefa, solicitar E/S ou atingir o limite do *quantum*. Ao atingir o limite, o processo sofre preempção e retorna para o final da fila de Prontos.

#### Algoritmo 2: Prioridades Dinâmicas com Aging (Prevenção de Starvation)

* **Estrutura de Fila:** Fila de prioridades onde processos com maior nível de prioridade (ex: nível 1 = prioridade máxima) são executados primeiro.

* **Problema da Inanição (Starvation):** Se processos de alta prioridade entrarem constantemente no sistema, processos de baixa prioridade nunca receberão a CPU.

* **Solução via Envelhecimento (Aging):** 
  * A cada ciclo de relógio em que um processo permanece na fila de `Pronto`, o sistema incrementa seu nível de prioridade.
  * Quando o processo é finalmente selecionado para execução, sua prioridade retorna ao valor base original registrado em seu PCB.

### 4.3 Troca de Contexto Simulatória

A cada troca de processo na CPU, o simulador deve registrar a execução dos seguintes passos:

1. Salvar o estado dos registradores e o contador de programa (`PC_salvo`) no PCB do processo que sai.

2. Atualizar o estado do processo sainte (`Pronto`, `Bloqueado` ou `Terminado`).

3. Carregar o contexto (registradores e `PC_salvo`) contido no PCB do novo processo selecionado.

4. Alterar o estado do novo processo para `Em Execução`.

---

## 5. Entradas, Casos de Teste e Diretrizes de Entrega

### 5.1 Formato do Arquivo de Entrada

O simulador receberá como entrada um arquivo de texto plano (`.txt`) contendo a lista de processos e a sequência de suas operações.

Cada linha do arquivo descreverá um processo no seguinte formato:
`<PID> <Tempo_Chegada> <Prioridade_Base> <Surto_CPU_1> <Tempo_ES> <Surto_CPU_2>`

**Exemplo de Arquivo (`processos.txt`):**

1 0 2 5 3 2
2 2 1 3 0 0
3 4 3 2 2 4

- **Processo 1 (1 0 2 5 3 2):** Chega no tempo 0, prioridade 2. Executa 5 ciclos de CPU, bloqueia por 3 ciclos de E/S, executa mais 2 ciclos de CPU e encerra.

- **Processo 2 (2 2 1 3 0 0):** Chega no tempo 2, prioridade 1. Executa 3 ciclos de CPU e encerra diretamente (sem E/S).

## 5. Entradas, Casos de Teste e Diretrizes de Entrega

### 5.1 Formato do Arquivo de Entrada

O simulador receberá como entrada um arquivo de texto plano (`.txt`) contendo a lista de processos e a sequência de suas operações.

Cada linha do arquivo descreverá um processo no seguinte formato:
`<PID> <Tempo_Chegada> <Prioridade_Base> <Surto_CPU_1> <Tempo_ES> <Surto_CPU_2>`

**Exemplo de Arquivo (`processos.txt`):**

1 0 2 5 3 2
2 2 1 3 0 0
3 4 3 2 2 4

* **Processo 1 (1 0 2 5 3 2):** Chega no tempo 0, prioridade 2. Executa 5 ciclos de CPU, bloqueia por 3 ciclos de E/S, executa mais 2 ciclos de CPU e encerra.
* **Processo 2 (2 2 1 3 0 0):** Chega no tempo 2, prioridade 1. Executa 3 ciclos de CPU e encerra diretamente (sem E/S).
* **Processo 3 (3 4 3 2 2 4):** Chega no tempo 4, prioridade 3. Executa 2 ciclos de CPU, bloqueia por 2 ciclos de E/S, executa mais 4 ciclos de CPU e encerra.

### 5.2 Saídas e Logs de Execução

Durante e ao término da simulação, o sistema gerará três relatórios de acompanhamento:

1. **Log de Eventos (Terminal):** Impressão passo a passo a cada ciclo do relógio informando trocas de estado (ex: `[Tempo 5] Processo 1 passou de EXECUTANDO para BLOQUEADO`).

2. **Gráfico de Gantt Textual:** Representação visual da ocupação da CPU ao longo do tempo.

3. **Estatísticas de Desempenho:** Relatório final consolidado com as métricas do sistema.

**Exemplo de Gráfico de Gantt Textual:**

Tempo: | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10|
CPU:   | P1| P1| P1| P1| P1| P2| P2| P2| P3| P3| P1|

**Métricas Globais de Desempenho:**

* **Tempo de Espera Médio:** Média do tempo acumulado por todos os processos aguardando na fila de `Pronto`.

* **Tempo de Retorno Médio (Turnaround Time):** Média do tempo total que os processos levaram desde a chegada ao sistema até o encerramento final (`Tempo_Encerramento - Tempo_Chegada`).

* **Taxa de Utilização da CPU (%):** Percentual de tempo em que a CPU esteve ocupada executando processos versus o tempo total de simulação.

### 5.3 Casos de Teste

Para validar o simulador, a especificação exige a execução de dois cenários obrigatórios:

* **Teste 1 (Preempção por Quantum - Round Robin):** Avaliar se processos longos sofrem preempção corretamente e cedem a CPU quando o *quantum* expira.

* **Teste 2 (Prevenção de Inanição - Aging):** Injetar continuamente processos de alta prioridade enquanto um processo de baixa prioridade aguarda na fila. O teste é bem-sucedido se o mecanismo de *aging* aumentar gradualmente a prioridade do processo negligenciado até que ele consiga executar.