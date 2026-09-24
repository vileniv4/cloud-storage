FROM eclipse-temurin:17-jre

WORKDIR /app

# Копируем собранный jar-файл из папки target
COPY target/cloud-storage-0.0.1-SNAPSHOT.jar app.jar

# Создаем папку для загрузки файлов внутри контейнера
RUN mkdir -p /app/uploads

EXPOSE 8080

# Команда для запуска приложения
ENTRYPOINT ["java", "-jar", "app.jar"]