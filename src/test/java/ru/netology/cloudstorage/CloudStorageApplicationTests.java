//package ru.netology.cloudstorage;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//@SpringBootTest
//@Testcontainers
//class CloudStorageApplicationTests {
//
//    // Поднимаем легковесный контейнер с PostgreSQL специально для тестов
//    @Container
//    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
//            .withDatabaseName("test_db")
//            .withUsername("test")
//            .withPassword("test");
//
//    // Динамически подменяем настройки подключения на те, что выдал Testcontainers
//    @DynamicPropertySource
//    static void properties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", postgres::getJdbcUrl);
//        registry.add("spring.datasource.username", postgres::getUsername);
//        registry.add("spring.datasource.password", postgres::getPassword);
//        // Отключаем Flyway для этого конкретного теста, чтобы он не мешал
//        registry.add("spring.flyway.enabled", () -> false);
//    }
//
//    @Test
//    void contextLoads() {
//        // Этот тест просто проверяет, что Spring-контекст успешно загружается
//        // с тестовой базой данных и всеми нашими бинами
//    }
//}