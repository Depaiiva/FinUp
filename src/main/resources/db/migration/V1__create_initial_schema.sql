-- 1. Tabela de Usuários (Base para Autenticação JWT)
CREATE TABLE users (
    id UUID PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    preferences JSONB, -- Para armazenar preferências do usuário
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 2. Tabela de Categorias (Com auto-relacionamento para subcategorias)
CREATE TABLE categories (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES users(id),
    parent_id UUID REFERENCES categories(id), -- Relacionamento para subcategorias
    name VARCHAR(50) NOT NULL
);

-- 3. Tabela de Contas (Corrente, Poupança, Cartão)
CREATE TABLE accounts (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES users(id),
    name VARCHAR(100) NOT NULL,
    type VARCHAR(20) NOT NULL, -- Ex: CHECKING, SAVINGS, CREDIT_CARD
    currency VARCHAR(3) DEFAULT 'BRL',
    external_id VARCHAR(255) -- Para integração futura (Plaid/Open Finance)
);

-- 4. Tabela de Transações (Onde o volume de dados é maior)
CREATE TABLE transactions (
    id UUID PRIMARY KEY,
    account_id UUID NOT NULL REFERENCES accounts(id),
    category_id UUID REFERENCES categories(id),
    amount NUMERIC(19, 2) NOT NULL, -- NUMERIC evita erros de precisão financeira
    posted_at TIMESTAMP NOT NULL,
    type VARCHAR(10) NOT NULL, -- CREDIT ou DEBIT
    description TEXT,
    external_id VARCHAR(255) UNIQUE, -- Para evitar duplicidade no upload de CSV/OFX
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
