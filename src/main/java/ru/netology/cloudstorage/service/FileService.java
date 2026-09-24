package ru.netology.cloudstorage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloudstorage.dto.FileMetadataResponse;
import ru.netology.cloudstorage.entity.FileMetadata;
import ru.netology.cloudstorage.entity.User;
import ru.netology.cloudstorage.repository.FileRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final AuthService authService;

    @Value("${app.file-storage-path}")
    private String storagePath;

    @Transactional
    public void uploadFile(String token, String filename, MultipartFile file) throws IOException {
        User user = authService.getUserByToken(token);

        if (fileRepository.findByUserAndFilename(user, filename).isPresent()) {
            throw new RuntimeException("File already exists");
        }

        String uniqueFilename = UUID.randomUUID().toString();
        Path userDir = Paths.get(storagePath, user.getId().toString());

        if (!Files.exists(userDir)) {
            Files.createDirectories(userDir);
        }

        Path filePath = userDir.resolve(uniqueFilename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        FileMetadata metadata = new FileMetadata(null, user, filename, filePath.toString(), file.getSize(), null);
    }

    @Transactional(readOnly = true)
    public byte[] downloadFile(String token, String filename) throws IOException {
        User user = authService.getUserByToken(token);

        FileMetadata metadata = fileRepository.findByUserAndFilename(user, filename)
                .orElseThrow(() -> new RuntimeException("File not found"));

        Path filePath = Paths.get(metadata.getFilePath());
        return Files.readAllBytes(filePath);
    }

    @Transactional
    public void deleteFile(String token, String filename) {
        User user = authService.getUserByToken(token);

        FileMetadata metadata = fileRepository.findByUserAndFilename(user, filename)
                .orElseThrow(() -> new RuntimeException("File not found"));

        try {
            Files.deleteIfExists(Paths.get(metadata.getFilePath()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file from disk");
        }

        fileRepository.delete(metadata);
    }

    @Transactional
    public void renameFile(String token, String oldFilename, String newFilename) {
        User user = authService.getUserByToken(token);

        FileMetadata metadata = fileRepository.findByUserAndFilename(user, oldFilename)
                .orElseThrow(() -> new RuntimeException("File not found"));

        metadata.setFilename(newFilename);
        fileRepository.save(metadata);
    }

    @Transactional(readOnly = true)
    public List<FileMetadataResponse> getFileList(String token, int limit) {
        User user = authService.getUserByToken(token);

        return fileRepository.findByUser(user).stream()
                .limit(limit)
                .map(f -> new FileMetadataResponse(f.getFilename(), f.getSize()))
                .collect(Collectors.toList());
    }
}