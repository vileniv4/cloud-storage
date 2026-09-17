-- Таблица пользователей
CREATE TABLE users
(
    id            BIGSERIAL PRIMARY KEY,
    login         VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Таблица сессий/токенов
CREATE TABLE user_sessions
(
    id         BIGSERIAL PRIMARY KEY,
    user_id    BIGINT       NOT NULL,
    token      VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP    NOT NULL,
    CONSTRAINT fk_session_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

-- Таблица метаданных файлов
CREATE TABLE files
(
    id         BIGSERIAL PRIMARY KEY,
    user_id    BIGINT       NOT NULL,
    filename   VARCHAR(255) NOT NULL,
    file_path  VARCHAR(500) NOT NULL,
    size       BIGINT       NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT unique_user_filename UNIQUE (user_id, filename)
);

-- Индексы
CREATE INDEX idx_files_user_id ON files (user_id);
CREATE INDEX idx_sessions_token ON user_sessions (token);
CREATE INDEX idx_sessions_user_id ON user_sessions (user_id);
CREATE INDEX idx_sessions_expires ON user_sessions (expires_at);