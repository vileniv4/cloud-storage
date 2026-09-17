package ru.netology.cloudstorage;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGeneratorTest {
    @Test
    public void generatePassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("Хеш для 'password': " + encoder.encode("password"));
        System.out.println("Хеш для 'admin123': " + encoder.encode("admin123"));
    }
}