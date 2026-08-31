-- Таблица пользователей
CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       login VARCHAR(255) NOT NULL UNIQUE,
                       password_hash VARCHAR(255) NOT NULL,
                       auth_token VARCHAR(255) UNIQUE
);

-- Таблица метаданных файлов
CREATE TABLE files (
                       id BIGSERIAL PRIMARY KEY,
                       user_id BIGINT NOT NULL,
                       filename VARCHAR(255) NOT NULL,
                       size BIGINT NOT NULL,
                       file_path VARCHAR(500) NOT NULL,
                       CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Индекс для быстрого поиска файлов пользователя
CREATE INDEX idx_files_user_id ON files(user_id);