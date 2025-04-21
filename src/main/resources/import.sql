/* Populate tables */
CREATE TABLE IF NOT EXISTS usuario (
                                       id VARCHAR(36) NOT NULL PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    created DATE,
    modified DATE,
    last_login DATE,
    token VARCHAR(255),
    is_active BOOLEAN
    );

-- Inserciones corregidas con RANDOM_UUID()
INSERT INTO usuario (id, name, email, password, created, modified, last_login, token, is_active)
VALUES
    (RANDOM_UUID(), 'francisco', 'francisco@gmail.com', 'xxx', NOW(), NOW(), NOW(), 'http://imagen1', true),
    (RANDOM_UUID(), 'miguel', 'miguel@gmail.com', 'xxy', NOW(), NOW(), NOW(), 'http://imagen2', true),
    (RANDOM_UUID(), 'claudia', 'claudia@gmail.com', 'xxc', NOW(), NOW(), NOW(), 'http://imagen3', true),
    (RANDOM_UUID(), 'mirella', 'mirella@gmail.com', 'xxd', NOW(), NOW(), NOW(), 'http://imagen4', true);
