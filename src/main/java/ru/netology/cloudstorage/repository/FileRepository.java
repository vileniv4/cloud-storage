package ru.netology.cloudstorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.netology.cloudstorage.entity.FileMetadata;
import ru.netology.cloudstorage.entity.User;
import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileMetadata, Long> {
    List<FileMetadata> findByUser(User user);
    Optional<FileMetadata> findByUserAndFilename(User user, String filename);
}