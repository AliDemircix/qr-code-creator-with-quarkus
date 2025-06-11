CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    fullName VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) default 'user',
    registerTime TIMESTAMP,
    subscriptionType VARCHAR(100) default 'free',
    qrCodeLimit INTEGER default 3
);