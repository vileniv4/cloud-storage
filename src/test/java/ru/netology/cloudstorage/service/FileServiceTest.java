package ru.netology.cloudstorage.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import ru.netology.cloudstorage.entity.FileMetadata;
import ru.netology.cloudstorage.entity.User;
import ru.netology.cloudstorage.repository.FileRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileServiceTest {

    @Mock
    private FileRepository fileRepository;

    @Mock
    private AuthService authService;

    @InjectMocks
    private FileService fileService;

    @Test
    void uploadFile_ShouldThrowException_WhenFileAlreadyExists() throws Exception {
        // Arrange (Подготовка данных)
        String token = "valid-token";
        String filename = "test.txt";
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setLogin("testuser");

        MockMultipartFile mockFile = new MockMultipartFile(
                "file", "test.txt", "text/plain", "Hello World".getBytes()
        );

        // Настраиваем поведение моков
        when(authService.getUserByToken(token)).thenReturn(mockUser);
        when(fileRepository.findByUserAndFilename(mockUser, filename))
                .thenReturn(Optional.of(new FileMetadata())); // Имитируем, что файл уже есть

        // Устанавливаем путь для хранения через рефлексию (@Value не работает в юнит-тестах)
        ReflectionTestUtils.setField(fileService, "storagePath", "/tmp/test-uploads");

        // Act & Assert (Действие и Проверка)
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fileService.uploadFile(token, filename, mockFile);
        });

        // Проверяем, что исключение содержит правильный текст
        assert exception.getMessage().equals("File already exists");

        // Проверяем, что метод save НЕ был вызван, так как мы выбросили ошибку раньше
        verify(fileRepository, never()).save(any(FileMetadata.class));
    }

    // Здесь можно добавить
    // uploadFile_ShouldSaveFile_WhenSuccess
    // deleteFile_ShouldThrowException_WhenFileNotFound
}