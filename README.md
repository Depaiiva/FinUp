# Projeto Finup: Plataforma Pessoal de Controle Financeiro

## 1. Visão de Alto Nível

### 1.1 Propósito

Construir uma aplicação web para controle financeiro pessoal com o objetivo de:
- Centralizar informações financeiras
- Controlar receitas e despesas
- Planejar metas e orçamentos
- Visualizar a evolução financeira
- Integrar contas bancárias para automatizar importações
- Permitir interações com sistemas de **assistente de voz**

### 1.2 Problema que o Sistema Resolve
- Falta de centralização das informações financeiras
- Necessidade de acessar múltiplos aplicativos bancários
- Dificuldade em visualizar padrões de gasto
- Falta de planejamento estruturado
- Pouca acessibilidade para interação rápida com dados financeiros

#### 1.3 Objetivos Técnicos
- Aplicar arquitetura bem estruturada (monólito modular evolutivo)
- Trabalhar com integrações externas reais (Open Finance ou agregadores)
- Implementar autenticação segura e boas práticas de segurança
- Desenvolver uma aplicação com preocupações de produção (logs, métricas, deploy)
- Criar múltiplos canais de interação com o sistema (web + voice)

## 2. Escopo Funcional

### 2.1 MVP (Produto Mínimo Viável)

#### Autenticação
- Cadastro
- Login
- JWT com refresh token

#### Contas
- Criar conta (corrente, poupança, cartão)
- Listar e editar contas

#### Transações
- Registrar receita
- Registrar despesa
- Editar e excluir
- Filtrar por período
- Associar categoria

#### Categorias
- Criar categorias e subcategorias

#### Dashboard
- Saldo consolidado
- Gastos por categoria
- Evolução mensal

#### Importação Manual
- Upload CSV
- Upload OFX
- Parser de extratos
- Regras automáticas simples de categorização

### 2.2 Evolução Pós-MVP
- Integração automática com bancos
- Conciliação automática inteligente
- Sistema de metas financeiras
- Orçamento mensal
- Multiusuário (família)
- PWA ou aplicativo mobile
- Categorização inteligente com machine learning
- Integração com assistente de voz

## 3. Requisitos

### 3.1 Requisitos Funcionais
- O usuário deve registrar receitas e despesas
- O sistema deve calcular saldo automaticamente
- O usuário deve importar extratos
- O sistema deve permitir integração bancária
- O usuário deve visualizar relatórios financeiros

### 3.2 Requisitos Não Funcionais
#### Segurança
- Comunicação HTTPS
- Tokens seguros
- Criptografia de dados sensíveis
- Rate limiting
- Logs sem dados sensíveis

#### Conformidade
A integração bancária no Brasil é regulada pelo
Banco Central do Brasil.O ecossistema é estruturado pela iniciativa
Open Finance Brasil

O projeto deve respeitar princípios da LGPD:
- Consentimento explícito
- Possibilidade de exclusão de dados
- Exportação de dados

#### Disponibilidade
- 99% é suficiente para uso pessoal

#### Observabilidade
- Logs estruturados
- Métricas
- Monitoramento de falhas

## 4. Modelo de Domínio

### 4.1 Entidades Principais

#### User
- id (UUID)
- email
- password_hash
- name
- preferences

#### Account
- id
- user_id
- name
- type
- currency
- external_id

#### Transaction
- id
- account_id
- amount
- posted_at
- type (credit/debit)
- category_id
- description
- external_id
- created_at
- updated_at

#### Category
- id
- user_id
- name
- parent_id

#### ImportJob
- id
- user_id
- source
- status
- created_at

#### BankConnection
- id
- user_id
- provider
- provider_account_id
- access_token_encrypted
- refresh_token_encrypted
- last_sync

## 5. Integração Bancária

### 5.1 Integração Direta com Open Finance

Requer:
- OAuth2
- OpenID Connect
- mTLS
- Dynamic Client Registration
- FAPI Advanced

Alta complexidade técnica e regulatória.

### 5.2 Uso de Agregador Financeiro (Recomendado para MVP)

Exemplo de agregador:
- Plaid

Vantagens:
- Integração simplificada
- Sandbox de testes
- SDKs prontos
- Dados já normalizados

Recomendação: iniciar com agregador para reduzir complexidade.

## 6. Arquitetura do Sistema
### 6.1 Estratégia Inicial

#### Monólito modular com Spring Boot.

Motivos:
- Simplicidade operacional
- Facilidade de manutenção
- Evolução gradual para microsserviços

## 6.2 Stack Tecnológica
#### Backend

- Java 17+
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- OAuth2 Client

#### Banco de Dados
- PostgreSQL

#### Cache
- Redis (opcional)

#### Mensageria (opcional)
- RabbitMQ ou Kafka

#### Integrações
- Agregador financeiro
- Webhooks externos
- Open Finance

#### Interface de Voz
- Alexa Skills Kit
- AWS Lambda
- Python ou Node.js

#### Armazenamento
- S3 ou MinIO

#### Frontend
- React ou Next.js
- Tailwind CSS

#### DevOps
- Docker
- GitHub Actions
- Deploy em VPS ou Cloud

#### Observabilidade
- Micrometer
- Prometheus
- Grafana
- OpenTelemetry

## 7. Estrutura do Projeto (Modularização)

Módulos:
- core
- auth
- user
- account
- transaction
- category
- import
- integration
- web
- voice

package base:
- br.com.finup

## 8. Design da API REST
### Autenticação

- POST /api/auth/signup
- POST /api/auth/login

### Contas
- GET /api/accounts
- POST /api/accounts

### Transações
- GET /api/accounts/{id}/transactions
- POST /api/transactions
- PATCH /api/transactions/{id}

### Importação
- POST /api/imports/upload

### Integração Bancária
- POST /api/bank-connections
- GET /api/bank-connections/{id}/sync
- POST /api/integrations/webhook

### API de Voz
- GET /api/voice/balance
- GET /api/voice/expenses/today
- POST /api/voice/expense

## 9. Segurança Técnica
- JWT com refresh token
- AES-256 para criptografia de tokens
- TLS 1.2+
- UUID público
- Idempotência em importações
- Mascaramento de dados sensíveis em logs

## 10. Pipeline de Sincronização
- Usuário cria conexão com banco
- Tokens armazenados criptografados
- Scheduler executa sincronização periódica
- Dados são normalizados
- Transações persistidas com external_id
- Regras automáticas aplicadas
- Eventos disparados para processamento adicional

## 11. Testes
- Unit tests com JUnit e Mockito
- Integration tests com Spring Boot Test
- Contract tests para integrações externas
- Testes E2E no frontend

## 12. Observabilidade
- Logs estruturados (JSON)
- Métricas com Micrometer
- Coleta com Prometheus
- Visualização com Grafana
- Tracing distribuído com OpenTelemetry

## 13. Milestones do Projeto
### Milestone 1 — Fundação Técnica
- [x] Configuração do projeto Spring Boot
- [x] Modelagem do banco de dados
- [ ] Implementação de autenticação
- [ ] CRUD de contas e transações
- [ ] Testes básicos

Resultado esperado: Sistema funcional básico com persistência e autenticação.

### Milestone 2 — Camada de Visualização e Importação
- [ ] Dashboard inicial
- [ ] Parser CSV e OFX
- [ ] Sistema de categorização automática simples
- [ ] Melhorias de UX

Resultado esperado: Sistema utilizável no dia a dia manualmente.

### Milestone 3 — Integração Bancária
- [ ] Integração com agregador financeiro
- [ ] Fluxo OAuth2
- [ ] Sincronização automática
- [ ] Armazenamento seguro de tokens

Resultado esperado: Automatização da entrada de dados financeiros.

### Milestone 4 — Robustez e Produção
- [ ] Logs estruturados
- [ ] Métricas e monitoramento
- [ ] Deploy com Docker
- [ ] CI/CD
- [ ] Hardening de segurança

Resultado esperado: Aplicação preparada para ambiente real.

### Milestone 5 - Interface de voz
- [ ] Criação da Alexa Skill
- [ ] Implementação de intents financeiras
- [ ] Integração com API FinUp
- [ ] Account de linking OAuth2
- [ ] Testes de interação por voz

Resultado esperado: Usuários podem consultar e registrar dados financeiros usando a voz.

### Milestone 6 — Evolução Avançada
- [ ] Sistema de metas financeiras
- [ ] Orçamento mensal inteligente
- [ ] Conciliação automática
- [ ] Event-driven architecture
- [ ] Preparação para microsserviços
- [ ] Automação e insights financeiros

Resultado esperado: Sistema completo e arquiteturalmente maduro.

# 13. Evolução Arquitetural

O projeto foi concebido para evoluir progressivamente:

1. **Monólito modular simples**
2. **Integrações externas**
3. **Automação financeira**
4. **Interfaces múltiplas (Web + Voice)**
5. **Arquitetura orientada a eventos**
6. **Possível decomposição em microsserviços**

## Conclusão

Este projeto permite evolução progressiva:

- Começa simples
- Ganha automação
- Evolui para integração real com bancos
- Se torna um sistema robusto de nível profissional

Ele consolida conhecimento em Spring Boot, arquitetura de software, segurança, integrações externas, DevOps e engenharia de sistemas.
