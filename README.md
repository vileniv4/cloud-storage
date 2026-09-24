## Дипломная работа «Облачное хранилище»

PostgreSQL, Файловая система Docker Volume, Flyway, Hibernate, DevTools.

### Готово:

Этап 1: Инициализация проекта и инфраструктура.

Этап 2: База данных и сущности (Flyway миграции, Entity User и FileMetadata, Repositories).

### Готово (доработка от 17.09.2026 по комментариям Максима Воронцова):

1. Том file_storage теперь у приложения, а не у БД
2. Dockerfile и сервис app в docker-compose
3. Все секреты вынесены в переменные окружения
4. Добавлен UNIQUE constraint на (user_id, filename)
5. Создана отдельная таблица user_sessions для токенов с expiration
6. Добавлена миграция V2 с тестовыми пользователями
7. Использован Lombok (@Data, @NoArgsConstructor, @AllArgsConstructor)
8. Добавлены createdAt и expiresAt для сессий

### Далее (в работе):

Этап 3: Совместимость URL с FRONT (/login и /cloud/login)

Этап 4: CORS + корректные 401, если нет токена

Этап 5: Расширить unit-тесты Mockito

Этап 6: Тест с Testcontainers чтобы запускался

Этап 7: Проверка с Frontend-приложением.


## Технологический стек

- Java 17
- Spring Boot 3.x (Web, Data JPA, Security, Validation)
- PostgreSQL 15 (хранение метаданных пользователей и файлов)
- Flyway (управление миграциями базы данных)
- Docker & Docker Compose (контейнеризация приложения и базы данных)
- JUnit 5, Mockito, Testcontainers (unit и интеграционное тестирование)
- Lombok