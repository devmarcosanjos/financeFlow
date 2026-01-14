# Arquitetura e Lógica MVC

O projeto segue o padrão arquitetural **MVC (Model-View-Controller)** adaptado para o contexto Android.

## 1. Model (O que é manipulado)
Representa os dados e a lógica de negócios fundamental, além do acesso ao banco de dados.

- **Entidades:**
  - `Transacao.kt`: Classe base ou interface para as transações.
  - `Receita.kt`: Representa uma entrada financeira.
  - `Despesa.kt`: Representa uma saída financeira, adicionando atributos como `tipoPagamento`.
- **Acesso a Dados:**
  - `FinanceDbHelper.kt`: Classe que estende `SQLiteOpenHelper`.
    - Responsável por criar e atualizar a tabela `transacoes`.
    - Contém métodos CRUD: `addTransacao` (Create) e `getAllTransacoes` (Read).
    - Mapeia objetos `Receita` e `Despesa` para linhas do banco e vice-versa.

## 2. View (O que o usuário vê)
Responsável pela interface gráfica e interação com o usuário. As Activities atuam como a camada de visualização principal.

- **Activities:**
  - `MainActivity.kt`: Gerencia o formulário de lançamento. Captura eventos de clique, exibe Pickers de data e controla a visibilidade de campos (ex: esconder tipo de pagamento ao selecionar Receita).
  - `ExtratoActivity.kt`: Exibe a lista de transações e o saldo. Gerencia a atualização da UI com base nos filtros selecionados.
- **Adapters:**
  - `TransacaoAdapter.kt`: Converte os objetos do Model em Views (XML) para serem exibidos no `RecyclerView` da tela de extrato.

## 3. Controller (Quem processa)
Atua como intermediário, processando a entrada do usuário vinda da View e atualizando o Model.

- **MainController.kt:**
  - Atua como um orquestrador central.
  - Instancia o `FinanceDbHelper`.
  - Métodos principais:
    - `adicionarTransacao(transacao)`: Recebe o objeto pronto e delega a persistência para o Helper.
    - `buscarTransacoes()`: Recupera a lista completa do banco.
    - `calcularSaldo(lista)`: Contém a regra de negócio para iterar sobre a lista, somar receitas e subtrair despesas, retornando o saldo final.
- **ReceitaController.kt / DespesaController.kt:**
  - Responsáveis pela criação e validação específica das entidades.
  - Exemplos de métodos: `validarECriarReceita(...)`. Esses métodos recebem strings cruas da View, validam (ex: verificam se não estão vazias), convertem tipos (String para Double) e instanciam o objeto correto do Model.

## Fluxo de Dados Exemplo (Lançamento de Despesa)
1. **View (`MainActivity`):** Usuário preenche dados e clica em "Lançar".
2. **Controller (`DespesaController`):** Recebe os dados brutos, valida se o valor e descrição existem. Instancia um objeto `Despesa`.
3. **Controller (`MainController`):** Recebe o objeto `Despesa` e chama o `FinanceDbHelper`.
4. **Model (`FinanceDbHelper`):** Abre conexão com SQLite e insere a linha na tabela `transacoes`.
5. **View:** Recebe confirmação (Toast) e limpa os campos.
