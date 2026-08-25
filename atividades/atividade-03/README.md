# Atividade 03

Essa atividade será dividida em cinco partes principais, todas fundamentadas diretamente nos conceitos de gerenciamento de processos e threads abordados no Capítulo 2 do livro:

## Estrutura para a Atividade:

1. **Visão Geral e Arquitetura do Simulador:** Apresentação do contexto do simulador (um ambiente em modo usuário que simula o comportamento de um núcleo de SO). Definição do fluxo geral de execução e da estrutura simplificada do hardware simulado (uma CPU virtual com registradores básicos, contador de programa e relógio lógico).

2. **Especificação do Bloco de Controle de Processo (PCB) e Tabela de Processos:** Especificação da estrutura de dados que os alunos devem programar. O PCB conterá informações críticas como identificador do processo (PID), estado atual, registradores salvos, prioridade e tempos de execução (tempo de CPU gasto e tempo de espera).

3. **Ciclo de Vida e Grafo de Transição de Estados:** Modelagem detalhada dos três estados clássicos (Pronto, Em Execução e Bloqueado). Os alunos deverão especificar as transições de estado causadas por: criação de processo (simulação de fork); interrupções periódicas de relógio (expiração de quantum); solicitações fictícias de Entrada/Saída e suas respectivas liberações (E/S concluída); e término do processo (chamada exit).

4. **Especificação do Escalonador de CPU:** Definição de pelo menos dois algoritmos de escalonamento que o simulador deve suportar de forma intercambiável: **Circular (Round Robin)**, com gerenciamento de fila circular e interrupção por fatia de tempo (quantum); e **Prioridades Estáticas ou Dinâmicas**, com mecanismos para prevenção de inanição (starvation).

5. **Entradas, Casos de Teste e Diretrizes de Entrega:** Como o simulador deverá ler o "arquivo de tarefas" (um arquivo contendo sequências de operações e surtos de CPU/E/S) e como deve ser a saída do simulador (gráficos de Gantt textuais, logs de transições de estados e estatísticas de uso de CPU).

Observação: 
1) A atividade poderá ser feita em Equipe (máximo 3 componentes)
2) A especificação deverá ser entregue no formato Markdown;
3) Cada componente da equipe deverá postar o documento no seu Github;
4) A documentação será entrada para a criação de um código gerado a partir de um Harness (Claude Code, Open Code). 
