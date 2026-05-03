# Documentação de Negócio e Requisitos: FinUp

## 1. Visão Geral e Propósito

O **FinUp** tem como objetivo principal centralizar informações financeiras espalhadas em diversas instituições, permitindo que o usuário tenha uma visão consolidada de seu patrimônio e fluxo de caixa.

O sistema foi concebido para evoluir progressivamente: começa simples com controle manual, ganha automação via integração bancária, expande para múltiplas interfaces (web e voz) e evolui para um sistema robusto de nível profissional.

---

## 2. Problemas Identificados (Dores do Usuário)

- Fragmentação de dados em múltiplos aplicativos bancários
- Dificuldade em identificar padrões de gastos mensais
- Ausência de uma ferramenta de planejamento estruturado de metas
- Falta de acessibilidade para consultas rápidas de saldo e gastos
- Necessidade de registro manual repetitivo de transações recorrentes

---

## 3. Escopo Funcional

### 3.1 MVP (Produto Mínimo Viável)

#### Autenticação Segura
- Cadastro e login de usuários
- Gestão de tokens JWT com refresh token

#### Gestão de Contas
- Suporte para conta corrente, poupança e cartões
- Listagem e edição de contas cadastradas

#### Gestão de Transações
- Registro e classificação de receitas e despesas
- Edição, exclusão e filtragem por período
- Associação de categoria a cada transação

#### Categorização
- Criação de categorias e subcategorias personalizadas
- Organização hierárquica de gastos por domínio (ex: Alimentação > Restaurantes)

#### Dashboard
- Visualização de saldo consolidado por conta
- Gráfico de gastos por categoria
- Evolução do saldo ao longo do tempo (mensal)

#### Importação Manual de Extratos
- Upload de arquivos CSV e OFX
- Parser de extratos bancários
- Regras automáticas simples de categorização baseadas em descrição

### 3.2 Escopo Pós-MVP

| Funcionalidade | Descrição |
|---|---|
| Integração automática com bancos | Sincronização via Open Finance Brasil ou agregador |
| Conciliação automática inteligente | Matching entre transações importadas e registradas |
| Sistema de metas financeiras | Definição e acompanhamento de objetivos financeiros |
| Orçamento mensal | Limites de gastos por categoria com alertas |
| Multiusuário (família) | Compartilhamento de contas entre membros da família |
| PWA / Aplicativo mobile | Acesso nativo em dispositivos móveis |
| Categorização inteligente com ML | Sugestão automática de categorias com machine learning |
| Integração com assistente de voz | Consulta e registro de dados financeiros via Alexa |

---

## 4. Requisitos do Sistema

### 4.1 Requisitos Funcionais

- O sistema deve permitir o registro manual e via importação de arquivos (CSV/OFX) de transações
- O sistema deve calcular o saldo atualizado automaticamente com base no histórico de movimentações
- O sistema deve permitir a integração automática com instituições financeiras via Open Finance
- O sistema deve suportar categorias hierárquicas (categorias e subcategorias)
- O sistema deve fornecer dashboards com evolução financeira mensal

### 4.2 Requisitos Não Funcionais

#### Segurança
- Comunicação HTTPS obrigatória em todos os endpoints
- Tokens armazenados com criptografia AES-256
- Rate limiting para proteção contra abuso
- Logs estruturados sem dados sensíveis (PII mascarada)

#### Conformidade Legal
- **LGPD**: Garantia de consentimento explícito, possibilidade de exclusão e exportação de dados do usuário
- **Open Finance Brasil**: Integração bancária regulada pelo Banco Central do Brasil, seguindo os padrões do ecossistema Open Finance Brasil (OAuth2, OpenID Connect, mTLS, FAPI Advanced)

#### Disponibilidade
- Alvo de **99% de uptime** — adequado para uso pessoal

#### Observabilidade
- Logs estruturados em JSON para auditoria e debugging
- Métricas de negócio e infraestrutura com alertas configuráveis
- Monitoramento de falhas com rastreamento de erros

---

## 5. Roadmap de Produto (Milestones)

### Milestone 1 — Fundação Técnica
**Objetivo:** Sistema funcional básico com persistência e autenticação.

- [x] Configuração do projeto Spring Boot
- [x] Modelagem do banco de dados
- [x] Implementação de autenticação JWT
- [ ] CRUD de contas e transações
- [ ] Testes unitários e de integração básicos

**Critério de aceite:** Usuário consegue se cadastrar, autenticar e registrar transações manualmente.

---

### Milestone 2 — Camada de Visualização e Importação
**Objetivo:** Sistema utilizável no dia a dia de forma manual.

- [ ] Dashboard inicial com saldo consolidado e gastos por categoria
- [ ] Parser de arquivos CSV e OFX
- [ ] Sistema de categorização automática simples
- [ ] Melhorias de UX no frontend

**Critério de aceite:** Usuário consegue importar um extrato bancário e visualizar seus gastos no dashboard.

---

### Milestone 3 — Integração Bancária
**Objetivo:** Automatização da entrada de dados financeiros.

- [ ] Integração com agregador financeiro (ex: Plaid)
- [ ] Fluxo OAuth2 para autorização de acesso bancário
- [ ] Sincronização automática de transações
- [ ] Armazenamento seguro de tokens com AES-256
- [ ] Sincronização automática com bancos brasileiros via Open Finance

**Critério de aceite:** Usuário conecta sua conta bancária e visualiza transações sincronizadas automaticamente.

---

### Milestone 4 — Robustez e Produção
**Objetivo:** Aplicação preparada para ambiente real.

- [ ] Logs estruturados em JSON
- [ ] Métricas com Prometheus e dashboards no Grafana
- [ ] Deploy com Docker e pipeline CI/CD via GitHub Actions
- [ ] Hardening de segurança (rate limiting, revisão de exposição de dados)
- [ ] Conformidade com LGPD (consentimento, exclusão e exportação de dados)

**Critério de aceite:** Aplicação rodando em produção com monitoramento ativo e segurança validada.

---

### Milestone 5 — Interface de Voz
**Objetivo:** Usuários podem consultar e registrar dados financeiros usando a voz.

- [ ] Criação da Alexa Skill para o FinUp
- [ ] Implementação de intents financeiros (saldo, gastos do dia, registro de despesa)
- [ ] Integração com a API FinUp via AWS Lambda
- [ ] Account Linking com OAuth2
- [ ] Testes de interação por voz

**Critério de aceite:** Usuário consegue perguntar "Alexa, qual meu saldo?" e receber resposta atualizada.

---

### Milestone 6 — Evolução Avançada
**Objetivo:** Sistema completo e arquiteturalmente maduro.

- [ ] Sistema de metas financeiras com acompanhamento de progresso
- [ ] Orçamento mensal inteligente com alertas de limite
- [ ] Conciliação automática entre transações manuais e importadas
- [ ] Arquitetura orientada a eventos (event-driven)
- [ ] Suporte multiusuário (família)
- [ ] Categorização inteligente com machine learning
- [ ] Preparação estrutural para decomposição em microsserviços

**Critério de aceite:** Sistema cobre o ciclo completo de gestão financeira pessoal com automação avançada.

---

## 6. Premissas e Restrições

- O projeto é iniciado como **uso pessoal**, portanto 99% de disponibilidade é suficiente para o MVP
- A integração bancária no MVP deve usar um **agregador** para reduzir a complexidade regulatória
- A integração direta com Open Finance Brasil requer certificação e processo formal junto ao Banco Central — planejada para fases avançadas
- Todos os dados financeiros sensíveis devem ser criptografados em repouso e em trânsito
