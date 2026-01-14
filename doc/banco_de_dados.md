# Estrutura do Banco de Dados SQL

O aplicativo utiliza o banco de dados nativo do Android, **SQLite**, gerenciado através da classe `FinanceDbHelper`.

## Configurações Gerais
- **Nome do Banco:** `financeflow.db`
- **Versão Atual:** 4
- **Estratégia de Upgrade:** Ao incrementar a versão, a tabela atual é deletada (`DROP TABLE`) e recriada (`onCreate`). *Nota: Isso causa perda de dados na atualização.*

## Tabela: `transacoes`

Esta tabela armazena tanto receitas quanto despesas, utilizando um padrão de tabela única com coluna discriminadora (`tipo`).

| Coluna | Tipo SQL | Descrição |
| :--- | :--- | :--- |
| `id` | `INTEGER PRIMARY KEY` | Identificador único auto-incrementável da transação. |
| `tipo` | `INTEGER` | Discriminador do tipo de registro:<br>**1**: Despesa<br>**2**: Receita |
| `nome` | `TEXT` | Descrição ou título da transação. |
| `valor` | `REAL` | Valor monetário da transação. |
| `categoria` | `TEXT` | Categoria selecionada (ex: Alimentação, Salário). |
| `data` | `INTEGER` | Timestamp da data (em milissegundos, formato Long). |
| `tipo_pagamento` | `TEXT` | Método de pagamento (apenas para despesas, ex: Crédito). Pode ser nulo/vazio para receitas. |

## Mapeamento de Objetos (ORM Manual)

### Leitura (`getAllTransacoes`)
O método percorre o cursor SQL e decide qual objeto instanciar baseado na coluna `tipo`:
- Se `tipo == 2 (RECEITA)`: Instancia objeto `Receita`.
- Se `tipo == 1 (DESPESA)`: Instancia objeto `Despesa`. Tenta ler `tipo_pagamento`; se não existir ou for nulo, assume valor padrão "Dinheiro".

### Escrita (`addTransacao`)
O método recebe um objeto genérico `Transacao`:
- Verifica se é instância de `Receita` ou `Despesa` para definir a coluna `tipo`.
- Preenche `ContentValues`.
- Se for `Despesa`, inclui o `tipo_pagamento`.
