package ru.netology.cloudstorage.dto;

public record FileMetadataResponse(
        String filename,
        Long size
) {}