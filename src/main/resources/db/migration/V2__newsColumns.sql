-- adicionar colunas em users
ALTER TABLE users ADD COLUMN pref_theme VARCHAR(255) DEFAULT 'light';
ALTER TABLE users ADD COLUMN pref_currency VARCHAR(255) DEFAULT 'BRL';
ALTER TABLE users ADD COLUMN pref_notification_enabled BOOLEAN DEFAULT FALSE;

-- alterar accounts
ALTER TABLE accounts ADD COLUMN balance NUMERIC;

-- adicionar constraint
ALTER TABLE accounts ADD CONSTRAINT chk_type 
CHECK (type IN ('CURRENT_ACCOUNT', 'SAVINGS_ACCOUNT', 'JOINT_ACCOUNT', 'SALARY_ACCOUNT'));
