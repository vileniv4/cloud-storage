package ru.netology.cloudstorage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.netology.cloudstorage.entity.User;
import ru.netology.cloudstorage.entity.UserSession;
import ru.netology.cloudstorage.repository.UserRepository;
import ru.netology.cloudstorage.repository.UserSessionRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserSessionRepository sessionRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public String login(String login, String password) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("Bad credentials"));

        // сравниваем обычный пароль с хешем
        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new RuntimeException("Bad credentials");
        }

        // Генерируем новый токен и создаём сессию
        String token = UUID.randomUUID().toString();
        UserSession session = new UserSession();
        session.setUser(user);
        session.setToken(token);

        sessionRepository.save(session);

        return token;
    }

    @Transactional
    public void logout(String token) {
        sessionRepository.deleteByToken(token);
    }

    public User getUserByToken(String token) {
        UserSession session = sessionRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Unauthorized"));

        if (session.isExpired()) {
            sessionRepository.delete(session);
            throw new RuntimeException("Unauthorized");
        }

        return session.getUser();
    }
}