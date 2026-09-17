## Дипломная работа «Облачное хранилище»

PostgreSQL, Файловая система Docker Volume, Flyway, Hibernate, DevTools.

### Готово:

Этап 1: Инициализация проекта и инфраструктура.

Этап 2: База данных и сущности (Flyway миграции, Entity User и FileMetadata, Repositories).

### Доработка от 17.09.2026:

1. Том file_storage теперь у приложения, а не у БД
2. Dockerfile и сервис app в docker-compose
3. Все секреты вынесены в переменные окружения
4. Добавлен UNIQUE constraint на (user_id, filename)
5. Создана отдельная таблица user_sessions для токенов с expiration
6. Добавлена миграция V2 с тестовыми пользователями
7. Использован Lombok (@Data, @NoArgsConstructor, @AllArgsConstructor)
8. Добавлены createdAt и expiresAt для сессий

### Далее (в работе):

Этап 3: Механизм авторизации (Контроллер Login/Logout, Filter или Interceptor для проверки токена).

Этап 4: Работа с файлами (Реализация эндпоинтов: Upload, Download, Delete, Rename, List).

Этап 5: Настройка CORS.

Этап 6: Тестирование (Unit-тесты для сервисов + Integration-тест с Testcontainers).

Этап 7: Проверка с Frontend-приложением.