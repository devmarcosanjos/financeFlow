# Documentação Geral e Informações Adicionais

Este documento contém informações complementares sobre a configuração do projeto, bibliotecas e boas práticas adotadas.

## Configuração do Projeto
- **Linguagem:** Kotlin
- **SDK Mínimo:** Android API 24 (Android 7.0) - *Confirmar no build.gradle*
- **SDK Alvo:** Android API 34 (Android 14) - *Confirmar no build.gradle*
- **Build System:** Gradle

## Estrutura de Pacotes
O código fonte está organizado em `com.marcosanjos.financeflow`:

- `controller/`: Contém a lógica de controle e regras de negócio.
- `model/`: Contém as classes de dados (DTOs) e a camada de acesso ao banco (DAO/Helper).
- `view/`: Contém as Activities e Adapters da interface.

## Boas Práticas e Observações
- **Data Binding:** O projeto utiliza `findViewById` tradicional. Uma melhoria futura seria migrar para *ViewBinding* para maior segurança de tipos e performance.
- **Tratamento de Datas:** O armazenamento é feito em `Long` (timestamp), o que facilita a ordenação e cálculos. A formatação para exibição é feita via `SimpleDateFormat` com Locale `pt-BR`.
- **Injeção de Dependências:** Atualmente a instanciação de controllers e helpers é feita manualmente nas Activities (`val repo = Conrtoller()`). Para escalar o projeto, recomenda-se o uso de Hilt ou Koin.
- **Corrotinas:** As operações de banco de dados estão sendo executadas na Thread Principal (UI Thread). Para evitar travamentos em grandes volumes de dados, é recomendada a migração para *Kotlin Coroutines* (`Dispatchers.IO`).

## Próximos Passos (Sugestões)
1. Implementar edição e exclusão de transações.
2. Adicionar gráficos na tela de extrato.
3. Migrar para Room Database (ORM oficial do Google) para simplificar o manejo do SQL.
