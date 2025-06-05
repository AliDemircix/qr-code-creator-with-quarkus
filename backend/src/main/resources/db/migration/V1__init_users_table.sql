CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255),
    is_admin BOOLEAN DEFAULT FALSE,
    subscription_type VARCHAR(50) DEFAULT 'free',
    qr_code_limit INTEGER DEFAULT 3,
    created_at TIMESTAMP
);
