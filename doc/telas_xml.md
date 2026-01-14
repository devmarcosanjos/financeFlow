# Documentação das Telas XML

A interface do usuário (UI) do FinanceFlow é construída utilizando arquivos XML de layout do Android. Abaixo a descrição detalhada de cada tela.

## 1. Tela Principal (Lançamento)
**Arquivo:** `res/layout/activity_main.xml`

Esta é a tela inicial do aplicativo, focada na entrada de dados.

- **Estrutura:** `ConstraintLayout`
- **Componentes:**
  - **RadioGroup (`rgTipo`):** Permite alternar entre "Receita" e "Despesa". Controla a visibilidade de campos específicos.
  - **Inputs:**
    - `etValorMain`: Campo numérico para o valor da transação.
    - `etDescricaoMain`: Campo de texto para a descrição.
    - `spCategoriaMain`: Spinner (dropdown) para seleção de categoria.
    - `spTipoPagamentoMain`: Spinner específico para despesas (visível apenas quando "Despesa" está selecionado).
  - **Seleção de Data:**
    - `tvDataValueMain`: TextView clicável que abre um `DatePickerDialog` e exibe a data selecionada.
  - **Ações:**
    - `btnLancar`: Botão para validar e salvar a transação.
    - `btnVerLancamentos`: Botão de navegação para a tela de extrato.

## 2. Tela de Extrato
**Arquivo:** `res/layout/activity_extrato.xml`

Tela responsável por exibir o histórico e o saldo.

- **Estrutura:** `ConstraintLayout`
- **Componentes:**
  - **Cabeçalho de Saldo:**
    - `tvNameSaldo`: Rótulo estático "Saldo Atual".
    - `tvSaldoValue`: Exibe o valor calculado do saldo (ex: "R$ 1.250,00").
  - **Barra de Filtros (`llFiltros`):**
    - `LinearLayout` horizontal contendo botões de filtro:
      - `btnFiltroTodos`
      - `btnFiltroReceitas`
      - `btnFiltroDespesas`
      - `btnFiltroMes`
  - **Lista:**
    - `recyclerViewTransacoes`: `RecyclerView` que renderiza a lista dinâmica de transações usando o `item_despesa.xml` (ou similar) como template para cada linha.

## 3. Item da Lista (Template)
**Arquivo:** `res/layout/item_despesa.xml` (Inferido a partir da estrutura padrão de RecyclerView)

Template usado para renderizar cada linha na lista de extrato. Geralmente contém:
- Ícone indicativo (opcional / por tipo).
- Texto da descrição e categoria.
- Texto do valor (comumente colorido: Verde para receitas, Vermelho para despesas).
- Data da transação.
