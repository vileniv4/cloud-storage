package ru.netology.cloudstorage.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.netology.cloudstorage.entity.FileMetadata;
import ru.netology.cloudstorage.entity.User;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // Поднимаем весь контекст Spring, но с тестовой БД
@Testcontainers
class FileRepositoryIT {

    // Объявляем контейнер с PostgreSQL
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("test_db")
            .withUsername("test")
            .withPassword("test");

    // Динамически подменяем свойства подключения на те, что выдал контейнер
    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        // Отключаем Flyway для тестов, чтобы не зависеть от миграций, или оставляем (если надо проверить их)
        registry.add("spring.flyway.enabled", () -> false);
    }

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        // Очищаем базу перед каждым тестом
        fileRepository.deleteAll();
        userRepository.deleteAll();

        // Создаем тестового пользователя
        testUser = new User();
        testUser.setLogin("integration_test_user");
        testUser.setPasswordHash("hashed_password");
        testUser = userRepository.save(testUser);
    }

    @Test
    void findByUserAndFilename_ShouldReturnFile_WhenExists() {
        // Arrange
        FileMetadata file = new FileMetadata(null, testUser, "document.pdf", "/path/to/uuid.pdf", 1024L, null);
        fileRepository.save(file);

        // Act
        Optional<FileMetadata> found = fileRepository.findByUserAndFilename(testUser, "document.pdf");

        // Assert
        assertTrue(found.isPresent());
        assertEquals("document.pdf", found.get().getFilename());
        assertEquals(1024L, found.get().getSize());
    }

    @Test
    void findByUser_ShouldReturnListOfFiles() {
        // Arrange
        fileRepository.save(new FileMetadata(null, testUser, "file1.txt", "/path/1", 100L, null));
        fileRepository.save(new FileMetadata(null, testUser, "file2.txt", "/path/2", 200L, null));

        // Act
        List<FileMetadata> files = fileRepository.findByUser(testUser);

        // Assert
        assertEquals(2, files.size());
    }
}