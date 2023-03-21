package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.validations.ValidateUser;

import java.util.*;

import static ru.yandex.practicum.filmorate.validations.ValidateUser.validateUser;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users")
public class UserController {

/*    private Integer idUser = 1;
    private final Map<Integer, User> users = new HashMap<>();*/
    private final UserService userService;

    @GetMapping
    public Collection<User> findAllUsers() {
        return userService.findAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        validateUser(user);
    /*    String email = user.getEmail();
        for (User user1 : users.values()) {
            if (user1.getEmail().equals(email)) {
                log.warn("Пользователь с {} уже зарегистрирован", email);
                throw new ValidationException("Пользователь с таким e-mail уже зарегистрирован");
            }
        }
        String login = user.getLogin();
        for (User userLogin : users.values()) {
            if (userLogin.getLogin().equals(login)) {
                log.warn("Пользователь с логином {} уже зарегистрирован", login);
                throw new ValidationException("Пользователь с таким логином уже зарегистрирован");
            }
        }
        user.setId(idUser);
        users.put(user.getId(), user);
        log.info("Добавлен пользователь: '{}'", user);
        this.idUser ++;
        return user;*/
        return userService.createUser(user);
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        validateUser(user);
    /*    if (!users.containsKey(user.getId())) {
            log.warn("Пользователь с id={} не существует.", user.getId());
            throw new ValidationException("Пользователь с введеным id не существует");
        }
        users.put(user.getId(), user);
        log.info("Обновлен пользователь: '{}'", user);
        return user;*/
        return userService.updateUser(user);
    }

    @DeleteMapping
    public void deleteUser(@RequestBody User user) {
        userService.deleteUser(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable (name = "id", required = false) Integer id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriendUser(@PathVariable (name = "id") Integer userId, @PathVariable Integer friendId) {
        return userService.addFriendUser(userId, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User deleteFriendUser(@PathVariable (name = "id") Integer userId, @PathVariable Integer friendId) {
        return userService.deleteFriendUser(userId, friendId);
    }

    @GetMapping("/{id}/friends")
    @ResponseBody
    public Collection<User> getFriendsUser(@PathVariable (name = "id", required = false) Integer userId) {
        return userService.getFriendsUser(userId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    @ResponseBody
    public Collection<User> getCommonFriends(@PathVariable (name = "id") Integer userId, @PathVariable Integer otherId) {
        return userService.getCommonFriends(userId, otherId);
    }
}
