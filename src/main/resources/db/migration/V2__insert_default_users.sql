-- Тестовые пользователи с паролями (пароль: password123)
-- Хеш создан в class PasswordGenerator
INSERT INTO users (login, password_hash)
VALUES ('testuser', '$2a$10$KZjgV7A21mA9wWVshicbXeYW61ufKSeM9vUUf61sClCKyT/fzOOx2'),
       ('admin', '$2a$10$7wrehy229NubgQWM0Ig8HeHYuvtAW9IOJsz0S4d/L4CWT0WIrhy/W');