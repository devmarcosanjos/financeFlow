![Logo da Pós-Graduação](pos.png)

# 💰 FinanceFlow

> **Aplicativo Android para gerenciamento financeiro pessoal, permitindo registrar receitas e despesas, visualizar extratos e acompanhar o saldo em tempo real.**

[![Kotlin](https://img.shields.io/badge/Kotlin-2.1.0-blue.svg)](https://kotlinlang.org/)
[![Android](https://img.shields.io/badge/Android-24%2B-green.svg)](https://www.android.com/)
[![Material Design](https://img.shields.io/badge/Material%20Design-3-orange.svg)](https://m3.material.io/)
[![Architecture](https://img.shields.io/badge/Architecture-MVC-purple.svg)](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller)

---

## 📚 Sobre o Projeto

Este é um projeto de aplicativo Android desenvolvido para a disciplina de **Programação para Dispositivos Móveis da UTFPR**.

- **Instituição:** Universidade Tecnológica Federal do Paraná (UTFPR)
- **Curso:** Especialização em Programação para Dispositivos Móveis
- **Disciplina:** PM-IV - ANDROID AVANÇADO
- **Professor:** Prof. Robison Cris Brito
- **Desenvolvedor:** Marcos Anjos
- **Desenvolvedor:** Marcelo Zanguettin Pereira


O **FinanceFlow** é um aplicativo desenvolvido em Kotlin que ajuda usuários a gerenciar suas finanças pessoais de forma simples e eficiente. O app permite registrar receitas e despesas, visualizar extratos detalhados, aplicar filtros de visualização e acompanhar o saldo atualizado em tempo real.

---

## 📖 Documentação

📄 **[Documentação Completa](doc/)** - Documentação detalhada do projeto

Para informações detalhadas sobre requisitos, arquitetura, banco de dados e especificações técnicas, consulte:

- 📋 **[Requisitos do Sistema](doc/requisitos.md)** - Requisitos funcionais e não funcionais
- 🏗️ **[Lógica MVC](doc/logica_mvc.md)** - Arquitetura MVC implementada
- 📱 **[Telas XML](doc/telas_xml.md)** - Documentação das telas e layouts
- 🗄️ **[Banco de Dados](doc/banco_de_dados.md)** - Especificações do SQLite
- 📝 **[Demais Informações](doc/demais_informacoes.md)** - Informações complementares

---

## ✨ Funcionalidades

### Gestão de Transações
- **Lançar Receitas**: Registre entradas de dinheiro com:
  - Valor (obrigatório)
  - Descrição (obrigatório)
  - Categoria (Salário, Freela, Extra)
  - Data do recebimento

- **Lançar Despesas**: Registre saídas de dinheiro com:
  - Valor (obrigatório)
  - Descrição (obrigatório)
  - Categoria (Mercado, Transporte, Casa, Educação, Outros)
  - Tipo de Pagamento (Dinheiro, Crédito, Débito)
  - Data do pagamento

### Visualização de Dados
- **Extrato Detalhado**: Lista completa de todas as transações em RecyclerView
- **Saldo em Tempo Real**: Cálculo automático do saldo total (Receitas - Despesas)
- **Filtros de Visualização**:
  - Todas as transações
  - Apenas Receitas
  - Apenas Despesas
  - Por Mês/Ano

### Funcionalidades Técnicas
- **Persistência de Dados**: Banco SQLite local (`financeflow.db`)
- **Validação de Campos**: Verificação de campos obrigatórios com feedback visual
- **Interface Intuitiva**: Design Material Design 3 limpo e funcional
- **Feedback Visual**: Indicadores coloridos (verde para receitas, vermelho para despesas)
- **Navegação Fluida**: Transição entre telas principal e de extrato



## 🛠️ Tecnologias e Conceitos Aplicados

### Stack Tecnológica

- **Linguagem:** Kotlin 2.1.0
- **Framework:** Android SDK
- **UI:** Material Design 3
- **Arquitetura:** MVC (Model-View-Controller)
- **Persistência:** SQLite
- **Mínimo SDK:** 24 (Android 7.0)
- **Target SDK:** 36 (Android 14)
- **Compile SDK:** 36
- **Java Version:** 11
- **IDE:** Android Studio

### Componentes de UI Utilizados

- `RecyclerView` - Lista eficiente de transações com adapter otimizado
- `EditText` - Campos de entrada para valor e descrição
- `Spinner` - Seleção de categorias
- `RadioButton` - Seleção de tipo de pagamento
- `Button` - Botões de ação (salvar, ver extrato)
- `TextView` - Exibição de informações formatadas
- `DatePickerDialog` - Seleção de data
- `Toast` - Feedback visual ao usuário
- `ConstraintLayout` - Layout principal responsivo
- `ScrollView` - Para suporte a telas menores

### Funcionalidades Técnicas

- **Navegação entre Activities**: Uso de `Intent` e `startActivity`
- **Persistência SQLite**: Banco de dados local com `SQLiteOpenHelper`
- **Arquitetura MVC**: Separação clara entre Model, View e Controller
- **Kotlin Parcelize**: Serialização eficiente de objetos
- **Sealed Interface**: Type safety para tipos de transação
- **RecyclerView.Adapter**: Otimização de listagem com ViewHolder pattern
- **Validação de Dados**: Verificação de campos obrigatórios
- **Formatação Monetária**: Locale pt_BR para formatação de moeda
- **Cálculo de Saldo**: Soma automática de receitas e despesas
- **Filtragem de Dados**: Queries SQL dinâmicas por tipo e data

---

## 🏗️ Arquitetura MVC

O projeto implementa o padrão **Model-View-Controller (MVC)** para separar responsabilidades:

### Model (Modelo)
- `Transacao` - Sealed interface que define os tipos de transação
- `Receita` - Data class para representar receitas
- `Despesa` - Data class para representar despesas
- `FinanceDbHelper` - Singleton SQLiteOpenHelper para persistência

### View (Visão)
- `MainActivity` - Tela principal com formulários de lançamento
- `ExtratoActivity` - Tela de listagem e filtragem de transações
- `TransacaoAdapter` - Adapter para RecyclerView
- Layouts XML (`activity_main.xml`, `activity_extrato.xml`)

### Controller (Controle)
- `MainController` - Orquestrador principal da lógica de negócio
- `ReceitaController` - Validação e processamento de receitas
- `DespesaController` - Validação e processamento de despesas

### Fluxo de Dados

```
┌─────────────┐      ┌──────────────┐      ┌─────────────┐
│    VIEW     │─────▶│ CONTROLLER   │─────▶│   MODEL     │
│ (Activities │      │ (Controllers │      │ (Classes de │
│   + XML)    │◀─────│  + Lógica)   │◀─────│  Dados)     │
└─────────────┘      └──────────────┘      └─────────────┘
                           │
                           ▼
                    ┌──────────────┐
                    │  SQLite DB   │
                    └──────────────┘
```

---

## 🗄️ Banco de Dados

### Especificações do SQLite

- **Nome do Banco:** `financeflow.db`
- **Versão:** 4
- **Tabela Principal:** `transacoes`

### Estrutura da Tabela `transacoes`

| Coluna | Tipo | Descrição |
|--------|------|-----------|
| `id` | INTEGER | Chave primária (auto-incremento) |
| `tipo` | INTEGER | 1 = Despesa, 2 = Receita |
| `nome` | TEXT | Descrição da transação |
| `valor` | REAL | Valor monetário |
| `categoria` | TEXT | Categoria da transação |
| `data` | INTEGER | Timestamp (milissegundos) |
| `tipo_pagamento` | TEXT | Tipo de pagamento (para despesas) |

### Operações SQL Principais

```sql
-- Inserir transação
INSERT INTO transacoes (tipo, nome, valor, categoria, data, tipo_pagamento)
VALUES (?, ?, ?, ?, ?, ?)

-- Buscar todas as transações
SELECT * FROM transacoes ORDER BY data DESC

-- Filtrar por tipo
SELECT * FROM transacoes WHERE tipo = ? ORDER BY data DESC

-- Filtrar por mês/ano
SELECT * FROM transacoes WHERE data >= ? AND data <= ?
```

---

## 📱 Como Usar

### Passo a Passo

#### 1. Lançar uma Receita

1. Na tela principal, selecione a aba **"Receita"**
2. Preencha os campos obrigatórios:
   - **Valor**: Valor da receita em reais
   - **Descrição**: Descrição da receita
   - **Categoria**: Selecione (Salário, Freela, Extra)
3. Selecione a **data** do recebimento
4. Clique no botão **"Salvar Receita"**
5. Aguarde a confirmação visual (Toast)

#### 2. Lançar uma Despesa

1. Na tela principal, selecione a aba **"Despesa"**
2. Preencha os campos obrigatórios:
   - **Valor**: Valor da despesa em reais
   - **Descrição**: Descrição da despesa
   - **Categoria**: Selecione (Mercado, Transporte, Casa, Educação, Outros)
   - **Tipo de Pagamento**: Selecione (Dinheiro, Crédito, Débito)
3. Selecione a **data** do pagamento
4. Clique no botão **"Salvar Despesa"**
5. Aguarde a confirmação visual (Toast)

#### 3. Visualizar Extrato

1. Na tela principal, clique no botão **"Ver Lançamentos"**
2. Você verá:
   - **Saldo Atual** no topo da tela
   - **Lista de transações** em ordem cronológica
   - Indicadores visuais (verde para receitas, vermelho para despesas)

#### 4. Usar Filtros

Na tela de extrato, use os botões de filtro para:
- **Todas**: Mostrar todas as transações
- **Receitas**: Mostrar apenas receitas
- **Despesas**: Mostrar apenas despesas
- **Mês/Ano**: Filtrar por período específico

---

## 📂 Estrutura do Projeto

```
financeFlow/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/marcosanjos/financeflow/
│   │   │   │   ├── MainActivity.kt              # Tela principal
│   │   │   │   ├── ExtratoActivity.kt           # Tela de extrato
│   │   │   │   ├── MainController.kt            # Controller principal
│   │   │   │   ├── ReceitaController.kt         # Controller de receitas
│   │   │   │   ├── DespesaController.kt         # Controller de despesas
│   │   │   │   ├── model/
│   │   │   │   │   ├── Transacao.kt             # Sealed interface
│   │   │   │   │   ├── Receita.kt               # Data class
│   │   │   │   │   └── Despesa.kt               # Data class
│   │   │   │   ├── adapter/
│   │   │   │   │   └── TransacaoAdapter.kt      # Adapter RecyclerView
│   │   │   │   └── database/
│   │   │   │       └── FinanceDbHelper.kt       # SQLite Helper
│   │   │   ├── res/
│   │   │   │   ├── layout/                      # Layouts XML
│   │   │   │   │   ├── activity_main.xml
│   │   │   │   │   └── activity_extrato.xml
│   │   │   │   ├── values/                      # Cores, temas, strings
│   │   │   │   │   ├── colors.xml
│   │   │   │   │   ├── strings.xml
│   │   │   │   │   └── themes.xml
│   │   │   │   └── drawable/                    # Recursos visuais
│   │   │   └── AndroidManifest.xml
│   │   ├── test/                                # Testes unitários
│   │   └── androidTest/                         # Testes de instrumentação
│   └── build.gradle.kts                         # Configuração do módulo
├── doc/                                         # Documentação do projeto
│   ├── requisitos.md                            # Requisitos funcionais/não funcionais
│   ├── logica_mvc.md                            # Arquitetura MVC detalhada
│   ├── telas_xml.md                             # Documentação das telas
│   ├── banco_de_dados.md                        # Especificações do SQLite
│   └── demais_informacoes.md                    # Informações complementares
├── pos.png                                      # Logo UTFPR
├── build.gradle.kts                             # Configuração do projeto
├── settings.gradle.kts                          # Configuração do Gradle
└── README.md                                    # Este arquivo
```

---

## 🚀 Como Instalar e Executar

### Pré-requisitos

- **Android Studio** Hedgehog (2023.1.1) ou superior
- **JDK 11** ou superior
- **Android SDK** 24 ou superior
- **Gradle** 8.0 ou superior
- Dispositivo Android ou Emulador

### Passos de Instalação

1. **Clone o repositório**
   ```bash
   git clone https://github.com/seu-usuario/financeFlow.git
   cd financeFlow
   ```

2. **Abra o projeto no Android Studio**
   - Abra o Android Studio
   - Selecione "Open an Existing Project"
   - Navegue até a pasta do projeto e selecione

3. **Sincronize o Gradle**
   - O Android Studio irá sincronizar automaticamente
   - Aguarde a conclusão da sincronização
   - Resolva eventuais problemas de dependências

4. **Execute o aplicativo**
   - Conecte um dispositivo Android via USB (com depuração USB ativada)
   - OU inicie um emulador Android
   - Clique em "Run" (▶️) ou pressione `Shift + F10`
   - Selecione o dispositivo/emulador desejado

### Build do APK

Para gerar um APK de debug:

```bash
./gradlew assembleDebug
```

O APK estará em: `app/build/outputs/apk/debug/app-debug.apk`

Para gerar um APK de release:

```bash
./gradlew assembleRelease
```

O APK estará em: `app/build/outputs/apk/release/app-release.apk`

---

## 📋 Requisitos do Sistema

### Requisitos Mínimos

- **Android**: 7.0 (API 24) ou superior
- **RAM**: 2GB mínimo recomendado
- **Armazenamento**: 15MB de espaço livre
- **Permissões**: Não requer permissões especiais

### Dispositivos Compatíveis

- ✅ Smartphones Android 7.0+
- ✅ Tablets Android 7.0+
- ✅ Diferentes tamanhos de tela (responsivo)
- ✅ Temas claros e escuros (DayNight)


## 📖 Documentação Adicional

### Documentos Técnicos

- 📋 **[Requisitos do Sistema](doc/requisitos.md)**
  - Requisitos funcionais detalhados
  - Requisitos não funcionais
  - Critérios de aceite

- 🏗️ **[Lógica MVC](doc/logica_mvc.md)**
  - Arquitetura MVC implementada
  - Responsabilidades de cada camada
  - Fluxo de dados entre componentes

- 📱 **[Telas XML](doc/telas_xml.md)**
  - Especificação das telas
  - Componentes de cada layout
  - Identificadores e referências

- 🗄️ **[Banco de Dados](doc/banco_de_dados.md)**
  - Esquema do banco de dados
  - Estrutura de tabelas
  - Queries e operações SQL

- 📝 **[Demais Informações](doc/demais_informacoes.md)**
  - Informações complementares
  - Decisões técnicas
  - Próximas melhorias

---

## 🤝 Contribuindo

Contribuições são bem-vindas! Sinta-se à vontade para:

1. Fazer um Fork do projeto
2. Criar uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abrir um Pull Request

---

## 📝 Licença

Este projeto foi desenvolvido como trabalho acadêmico para a UTFPR.

---



## 📊 Status do Projeto

✅ **Projeto Completo e Funcional**

- ✅ Cadastro de receitas implementado
- ✅ Cadastro de despesas implementado
- ✅ Extrato detalhado funcionando
- ✅ Filtros de visualização ativos
- ✅ Saldo em tempo real calculado
- ✅ Persistência SQLite funcionando
- ✅ Validação de dados implementada
- ✅ Interface Material Design 3
- ✅ Arquitetura MVC clara e organizada
- ✅ Documentação completa (README + docs)
- ✅ Código limpo e seguindo boas práticas
- ✅ Type safety com sealed interfaces
- ✅ Kotlin Parcelize para serialização



---

**Desenvolvido como projeto prático para consolidar os conhecimentos em desenvolvimento Android com Kotlin, Material Design 3, arquitetura MVC e persistência SQLite.**

⭐ Se este projeto foi útil para você, considere dar uma estrela!
