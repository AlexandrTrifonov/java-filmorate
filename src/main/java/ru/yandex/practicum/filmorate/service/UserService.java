package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.impl.UserDbStorage;

import java.util.*;

import static ru.yandex.practicum.filmorate.service.ValidateUser.validateUser;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserDbStorage userDbStorage;

    public Collection<User> findAllUsers() {
        return userDbStorage.findAllUsers();
    }

    public User createUser(User user) {
        validateUser(user);
        return userDbStorage.createUser(user);
    }

    public User updateUser(User user) {
        User userCheck = userDbStorage.getUserById(user.getId());
        if (userCheck == null) {
            log.warn("Ошибка при добавлении в друзья");
            NotFoundException.throwException("Ошибка при добавлении в друзья", user.getId());
        }
        validateUser(user);
        return userDbStorage.updateUser(user);
    }

    public void deleteUser(User user) {
        User userCheck = userDbStorage.getUserById(user.getId());
        if (userCheck == null) {
            log.warn("Ошибка при добавлении в друзья");
            NotFoundException.throwException("Ошибка при добавлении в друзья", user.getId());
        }
        userDbStorage.deleteUser(user);
    }

    public User getUserById(Integer id)  {
        User user = userDbStorage.getUserById(id);
        if (user == null) {
            log.warn("Ошибка при получении пользователя с id={}", id);
            NotFoundException.throwException("Ошибка при получении пользователя", id);
        }
        return user;
    }

    public void addFriendUser(Integer userId, Integer friendId)   {
        if (userId == friendId) {
            log.warn("userId совпадает с friendId");
            throw new ValidationException("Попытка добавить самого себя в друзья");
        }
        User user = userDbStorage.getUserById(userId);
        User friend = userDbStorage.getUserById(friendId);
        if (user == null) {
            log.warn("Ошибка при добавлении в друзья");
            NotFoundException.throwException("Ошибка при добавлении в друзья", userId);
        }
        if (friend == null) {
            log.warn("Ошибка при добавлении в друзья");
            NotFoundException.throwException("Ошибка при добавлении в друзья", userId);
        }
        userDbStorage.addFriendUser(userId, friendId);
    }

    public void deleteFriendUser(Integer userId, Integer friendId)   {
        User user = userDbStorage.getUserById(userId);
        User friend = userDbStorage.getUserById(friendId);
        if (user == null) {
            log.warn("Ошибка при удалении из друзей");
            NotFoundException.throwException("Ошибка при удалении из друзей", userId);
        }
        if (friend == null) {
            log.warn("Ошибка при удалении из друзей");
            NotFoundException.throwException("Ошибка при удалении из друзей", userId);
        }
        userDbStorage.deleteFriendUser(userId, friendId);
    }

    public Collection<User> getFriendsUser(Integer userId)   {
        User userCheck = userDbStorage.getUserById(userId);
        if (userCheck == null) {
            log.warn("Ошибка получения списка друзей пользователя {}", userId);
            NotFoundException.throwException("Ошибка получения списка друзей пользователя", userId);
        }
        return userDbStorage.getFriendsUser(userId);
    }

    public List<User> getCommonFriends(Integer userId, Integer otherId) {
        User user = userDbStorage.getUserById(userId);
        User other = userDbStorage.getUserById(otherId);

        if (user == null) {
            log.warn("Ошибка при получении пользователя с id={}", userId);
            NotFoundException.throwException("Ошибка при получении пользователя", userId);
        }
        if (other == null) {
            log.warn("Ошибка при получении пользователя с id={}", otherId);
            NotFoundException.throwException("Ошибка при получении пользователя", otherId);
        }

        Set<Integer> userFriends = user.getFriendsUser();
        Set<Integer> otherFriends = other.getFriendsUser();

        final List<User> commonFriends = new ArrayList<>();
        if (userFriends == null || otherFriends == null) {
            return commonFriends;
        }

        userFriends.stream()
                .filter(otherFriends::contains)
                .sorted()
                .map(userDbStorage::getUserById)
                .forEach(commonFriends::add);
        return commonFriends;
    }
}
