package ru.netology.cloudstorage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthTokenResponse(
        @JsonProperty("auth-token") String authToken
) {}