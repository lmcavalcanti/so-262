# Laboratório de Gerência do Processador — SOsim

---

## 📋 Visão Geral

O objetivo principal destes exercícios é analisar e comparar o comportamento de processos de diferentes perfis (**CPU-bound** e **I/O-bound**) submetidos a diferentes algoritmos e políticas de escalonamento.

### Conceitos Abordados
- **Escalonamento Circular (*Round-Robin*)** e impacto do *Time Slice* (Quantum).
- **Escalonamento por Prioridades** (estáticas e dinâmicas).
- **Troca de Contexto e Mudança de Estados** dos processos (Pronto, Executando, Espera).
- **Distribuição e Eficiência do Uso da UCP** (CPU).
- **Mecanismos Adaptativos** e balanceamento do sistema.

---

## 🧪 Exercícios Realizados

### 🔹 Exercício 1: Escalonamento Circular (Sem Prioridade)
- **Configuração:** Escalonamento Circular com *Time Slice* padrão.
- **Cenário:** Criação de dois processos de mesma prioridade (1 CPU-bound e 1 I/O-bound).
- **Análise:** Observação do tempo de processador e transições de estado durante 3 minutos.
- **Questão Chave:** *Qual o impacto do aumento ou diminuição do tempo de time slice (quantum) no desempenho geral e na responsividade do sistema?*

---

### 🔹 Exercício 2: Escalonamento Circular com Prioridades (Prioridade Maior para I/O-bound)
- **Configuração:** Escalonamento por Prioridades.
- **Cenário:** 
  - Processo **CPU-bound** com prioridade **3**.
  - Processo **I/O-bound** com prioridade **4** *(maior prioridade)*.
- **Análise:** Comparação do tempo de processamento e uso da UCP em relação ao Exercício 1.
- **Questão Chave:** *O que acontece com a distribuição da UCP e o tempo de resposta se o tempo de espera (E/S) do processo I/O-bound aumentar ou diminuir?*

---

### 🔹 Exercício 3: Escalonamento Circular com Prioridades (Prioridade Maior para CPU-bound)
- **Configuração:** Escalonamento por Prioridades.
- **Cenário:** 
  - Processo **CPU-bound** com prioridade **4** *(maior prioridade)*.
  - Processo **I/O-bound** com prioridade **3**.
- **Análise:** Avaliação de cenários de *starvation* (indefinição/postergação de execução) de processos de menor prioridade ou I/O-bound.
- **Questão Chave:** *Quais critérios técnicos e operacionais devem ser utilizados para definir adequadamente as prioridades dos processos?*

---

### 🔹 Exercício 4: Escalonamento com Prioridade Dinâmica (Mecanismo Adaptativo)
- **Configuração:** Escalonamento por Prioridade Dinâmica / Mecanismo Adaptativo.
- **Cenário:** Dois processos criados com a mesma prioridade inicial (1 CPU-bound e 1 I/O-bound).
- **Análise:** Comparação da evolução temporal e ajuste dinâmico de prioridades em relação ao Exercício 2.
- **Questão Chave:** *Qual a vantagem do escalonamento dinâmico ao lidar com processos I/O-bound de perfis e comportamentos variados?*

---

## 🛠️ Ferramentas Utilizadas

- **Simulador:** [SOsim](http://www.training.com.br/sosim) (Software de Simulação de Sistemas Operacionais)
- **Módulo:** Gerência do Processador & Console SOsim

---