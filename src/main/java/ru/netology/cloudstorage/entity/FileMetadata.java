package ru.netology.cloudstorage.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "files")
public class FileMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "filename", nullable = false)
    private String filename;

    @Column(name = "size", nullable = false)
    private Long size;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    public FileMetadata() {}

    public FileMetadata(User user, String filename, Long size, String filePath) {
        this.user = user;
        this.filename = filename;
        this.size = size;
        this.filePath = filePath;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getFilename() { return filename; }
    public void setFilename(String filename) { this.filename = filename; }
    public Long getSize() { return size; }
    public void setSize(Long size) { this.size = size; }
    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
}