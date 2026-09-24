package ru.netology.cloudstorage.dto;

import jakarta.validation.constraints.NotBlank;

public record RenameFileRequest(
        @NotBlank String name
) {}