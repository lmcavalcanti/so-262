Este exercício prático aborda o gerenciamento de arquivos e diretórios em ambiente Linux, cobrindo navegação, manipulação de arquivos, cópia, movimentação e automação por meio de Shell Script no sistema de arquivos.

### Cenário do Exercício

Você é o administrador de sistemas de uma empresa e precisa organizar a estrutura de pastas do **"Projeto_A"**, gerar arquivos de relatório e log, organizar os arquivos entre diretórios e desenvolver um script em Shell Script para automatizar a cópia de segurança (backup).

### Etapa 1: Navegação e Inspeção do Ambiente

Antes de criar qualquer pasta, é essencial identificar em qual ponto da árvore do sistema de arquivos você está localizado.

1. Verifique o seu diretório atual de trabalho usando o comando `pwd`.
2. Navegue para o seu diretório pessoal (*home*) com o comando `cd ~`.
3. Liste todo o conteúdo do seu diretório em formato detalhado, incluindo arquivos ocultos, utilizando `ls -la`.

```bash
pwd  
cd ~  
ls -la
```

* **Conceito:** O comando `pwd` exibe o caminho absoluto do diretório ativo no terminal. O caractere `~` é um atalho direto para a pasta pessoal do usuário (`/home/usuario`). O comando `ls -la` exibe permissões, proprietário, tamanho e arquivos ocultos (iniciados com `.`).

---

### Etapa 2: Criação da Estrutura de Diretórios

Crie a estrutura do **"Projeto_A"** e seus subdiretórios usando caminhos relativos.

1. Crie o diretório principal chamado `Projeto_A`.
2. Entre no diretório `Projeto_A`.
3. Crie três subdiretórios simultaneamente: `documentos`, `logs` e `scripts`.

```bash
mkdir Projeto_A  
cd Projeto_A  
mkdir documentos logs scripts  
ls -l
```

* **Conceito:** O comando `mkdir` cria novos diretórios. É possível passar múltiplos nomes como argumento para criar vários diretórios de uma vez. O comando `cd` com caminho relativo permite navegar a partir do diretório atual sem precisar digitar o caminho completo desde a raiz `/`.

---

### Etapa 3: Criação e Edição de Arquivos de Texto

Nesta etapa, você criará arquivos de texto e inserirá conteúdo neles.

1. Crie um arquivo vazio chamado `relatorio_inicial.txt` dentro da pasta `documentos` usando o comando `touch`.
2. Grave uma linha de texto dentro de um novo arquivo `sistema.log` na pasta `logs` usando `echo` com o redirecionador `>`.
3. Visualize o conteúdo do arquivo gravado usando o comando `cat`.

```bash
touch documentos/relatorio_inicial.txt  
echo "Log de inicialização do sistema - Projeto A" > logs/sistema.log  
cat logs/sistema.log
```

* **Conceito:** O `touch` altera a data de modificação ou cria um arquivo vazio. O operador `>` redireciona a saída do comando `echo` diretamente para um arquivo de texto. O comando `cat` imprime o conteúdo do arquivo no terminal.

---

### Etapa 4: Cópia e Movimentação de Arquivos

Gerencie os arquivos copiando e movendo-os entre as pastas criadas.

1. Faça uma cópia do arquivo `relatorio_inicial.txt` na pasta `logs` com o nome `relatorio_backup.txt` usando `cp`.
2. Mova o arquivo `sistema.log` do diretório `logs` para o diretório `documentos` usando `mv`.
3. Confirme a alteração listando o conteúdo dos dois diretórios.

```bash
cp documentos/relatorio_inicial.txt logs/relatorio_backup.txt  
mv logs/sistema.log documentos/  
ls -l documentos  
ls -l logs
```

* **Conceito:** O comando `cp` duplica o arquivo, mantendo o original intacto. O comando `mv` transfere o arquivo para o destino (ou o renomeia), removendo-o da localização de origem.

---

### Etapa 5: Criação e Execução de um Script Shell

Desenvolva um script na pasta `scripts` para automatizar a cópia de segurança dos documentos.

1. Entre no diretório `scripts`.
2. Crie o arquivo `fazer_backup.sh` com um editor de texto (`nano` ou `vim`).
3. Escreva o seguinte código dentro do script:

```bash
#!/bin/bash  
# Script de automação de backup do Projeto_A

echo "Iniciando o processo de backup..."  
mkdir -p ~/Projeto_A/backup_geral  
cp -r ~/Projeto_A/documentos/* ~/Projeto_A/backup_geral/  
echo "Backup concluído com sucesso em: $(date)"
```

4. Adicione permissão de execução ao script com `chmod +x`.
5. Execute o script no diretório atual utilizando `./fazer_backup.sh`.

```bash
cd ~/Projeto_A/scripts  
chmod +x fazer_backup.sh  
./fazer_backup.sh
```

* **Conceito:** A primeira linha `#!/bin/bash` (chamada *shebang*) define qual interpretador executará as instruções do arquivo. Por padrão, arquivos recém-criados não possuem permissão de execução; o comando `chmod +x` adiciona essa permissão. O caractere `./` sinaliza ao shell que o executável está localizado no diretório corrente.

---

### Etapa 6: Verificação do Backup

Confirme se o script executou a tarefa corretamente.

```bash
cd ~/Projeto_A/backup_geral  
ls -l
```

* **Resultado Esperado:** O diretório `backup_geral` deve conter as cópias de todos os arquivos do diretório `documentos`, validando a automação desenvolvida.

---

💡 Deseja expandir este exercício adicionando tarefas de compactação do backup com `tar.gz` ou agendamento de execução automática com o `cron`?
