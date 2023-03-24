package ru.yandex.practicum.filmorate.storage.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class InMemoryUserStorage implements UserStorage {

    private Integer idUser = 1;

    @Getter
    private final Map<Integer, User> users;
    @Override
    public User createUser(User user) {
        String email = user.getEmail();
        for (User user1 : users.values()) {
            if (user1.getEmail().equals(email)) {
                log.warn("Пользователь с {} уже зарегистрирован.", email);
                throw new ValidationException(String.format("Пользователь с \"%s\" уже зарегистрирован.", email));
            }
        }
        String login = user.getLogin();
        for (User userLogin : users.values()) {
            if (userLogin.getLogin().equals(login)) {
                log.warn("Пользователь с логином {} уже зарегистрирован.", login);
                throw new ValidationException(String.format("Пользователь с логином \"%s\" уже зарегистрирован.", login));
            }
        }
        user.setId(idUser);
        users.put(user.getId(), user);
        log.info("Добавлен пользователь: '{}'", user);
        this.idUser ++;
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (!users.containsKey(user.getId())) {
            log.warn("Пользователь с id={} не существует.", user.getId());
            throw new NotFoundException(String.format("Пользователь с id=%s не существует.", user.getId()));
        }
        users.put(user.getId(), user);
        log.info("Обновлен пользователь: '{}'", user);
        return user;
    }

    @Override
    public void deleteUser(User user) {
        if (!users.containsKey(user.getId())) {
            log.warn("Удалить пользователя не удалось. Пользователь с id={} отсутствует", user.getId());
            throw new NotFoundException(String.format("Пользователь с id=\"%s\" не существует.", user.getId()));
        }
        for (User userName : users.values()) {
            if (userName.getId().equals(user.getId())) {
                users.remove(userName.getId());
                log.info("Удален пользователь с email: '{}'", user.getEmail());
                break;
            }
        }
    }
}
