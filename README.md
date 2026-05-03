# FinUp — Plataforma de Controle Financeiro Pessoal

![Java](https://img.shields.io/badge/Java-17+-ED8B00?style=flat&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3-6DB33F?style=flat&logo=spring-boot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-4169E1?style=flat&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?style=flat&logo=docker&logoColor=white)
![Status](https://img.shields.io/badge/Status-Milestone_1-yellow?style=flat)

> Aplicação web para centralizar a gestão financeira pessoal — controle de receitas e despesas, planejamento de metas e integração com Open Finance e assistentes de voz.

---

## Status do Desenvolvimento

**Milestone 1 — Fundação Técnica** `em andamento`

- [x] Configuração do projeto Spring Boot
- [x] Modelagem e versionamento do banco de dados (Flyway)
- [x] Sistema de autenticação via JWT com Refresh Token
- [x] CRUD de Contas e Categorias
- [ ] CRUD de Transações
- [ ] Testes unitários e de integração

---

## Documentação

| Documento | Descrição |
|---|---|
| [Requisitos e Negócio](docs/BUSINESS.md) | Propósito, dores do usuário, requisitos funcionais e roadmap de produto |
| [Arquitetura e Técnico](docs/TECHNICAL.md) | Stack tecnológica, design de API, modelo de dados e decisões arquiteturais |

---

## Como Executar

### Pré-requisitos

- [Java 17+](https://adoptium.net/)
- [Docker e Docker Compose](https://docs.docker.com/get-docker/)
- [Maven](https://maven.apache.org/) (ou use o wrapper `./mvnw` incluído)

### Configuração de Variáveis de Ambiente

Crie um arquivo `.env` na raiz do projeto com base no exemplo abaixo:

```env
# Banco de Dados
DB_URL=jdbc:postgresql://localhost:5432/finup
DB_USERNAME=finup_user
DB_PASSWORD=finup_pass

# JWT
JWT_SECRET=sua-chave-secreta-aqui
JWT_EXPIRATION_MS=3600000
JWT_REFRESH_EXPIRATION_MS=86400000
```

> ⚠️ Nunca comite o arquivo `.env`. Ele já está incluído no `.gitignore`.

### Passo a Passo

**1. Clone o repositório**
```bash
git clone https://github.com/seu-usuario/finup.git
cd finup
```

**2. Suba a infraestrutura (PostgreSQL)**
```bash
docker-compose up -d
```

**3. Execute a aplicação**
```bash
./mvnw spring-boot:run
```

**4. Acesse a documentação interativa da API**
```
http://localhost:8080/swagger-ui.html
```

---

## Estrutura do Projeto

```
finup/
├── src/
│   └── main/
│       └── java/br/com/finup/
│           ├── auth/          # Autenticação e tokens JWT
│           ├── account/       # Gestão de contas
│           ├── transaction/   # Movimentações financeiras
│           ├── category/      # Categorias e subcategorias
│           └── core/          # Componentes transversais
├── docs/
│   ├── BUSINESS.md
│   ├── TECHNICAL.md
│   └── diagrams/
│       └── architecture-milestone1.png
├── docker-compose.yml
└── README.md
```

---

## Tecnologias

- **Backend:** Java 17, Spring Boot 3, Spring Security, Spring Data JPA
- **Banco de Dados:** PostgreSQL, Flyway
- **Segurança:** JWT, AES-256, TLS
- **Documentação:** OpenAPI / Swagger
- **Infraestrutura:** Docker, Docker Compose

---

## Licença

Este projeto está sob a licença MIT. Consulte o arquivo [LICENSE](LICENSE) para mais detalhes.
