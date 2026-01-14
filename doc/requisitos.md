# Requisitos do Sistema FinanceFlow

O projeto **FinanceFlow** é um aplicativo Android nativo desenvolvido em Kotlin para gestão financeira pessoal. Abaixo estão os requisitos funcionais e não funcionais identificados na versão atual do código.

## Requisitos Funcionais

### 1. Gestão de Transações
- **Lançar Receitas:** O usuário deve ser capaz de registrar entradas de dinheiro, informando:
  - Valor (obrigatório)
  - Descrição (obrigatório)
  - Categoria (ex: Salário, Rendimentos, etc.)
  - Data do recebimento
- **Lançar Despesas:** O usuário deve ser capaz de registrar saídas de dinheiro, informando:
  - Valor (obrigatório)
  - Descrição (obrigatório)
  - Categoria (ex: Moradia, Alimentação, Transporte, etc.)
  - Tipo de Pagamento (ex: Dinheiro, Cartão de Crédito, Débito)
  - Data do pagamento
- **Validação:** O sistema não deve permitir lançamentos com valor ou descrição vazios.

### 2. Visualização de Dados
- **Saldo Atual:** O aplicativo deve calcular e exibir o saldo total atualizado na tela de extrato, subtraindo as despesas das receitas.
- **Extrato de Transações:** O usuário deve conseguir visualizar uma lista de todas as transações cadastradas.
- **Filtros de Visualização:**
  - Filtrar por **Todas** as transações.
  - Filtrar apenas **Receitas**.
  - Filtrar apenas **Despesas**.
  - Filtrar pelo **Mês Atual** (funcionalidade prevista na interface).

### 3. Navegação
- **Tela Principal:** Acesso rápido ao formulário de lançamento.
- **Tela de Extrato:** Acesso via botão "Ver Lançamentos" na tela principal.

## Requisitos Não Funcionais
- **Persistência de Dados:** Os dados devem ser salvos localmente em um banco de dados SQLite (`financeflow.db`), garantindo que as informações não sejam perdidas ao fechar o app.
- **Usabilidade:** Interface intuitiva com feedback visual (Toasts) para ações de sucesso ou erro.
