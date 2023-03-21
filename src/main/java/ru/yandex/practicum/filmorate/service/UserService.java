package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserStorage userStorage;

    public Collection<User> findAllUsers() {
        return userStorage.getUsers().values();
    }

    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    public void deleteUser(User user) {
        userStorage.deleteUser(user);
    }

    public User getUserById(Integer id) {
    //    String email = userStorage.getUsers().get(id).getEmail();
        if (!userStorage.getUsers().containsKey(id)) {
            log.warn("Пользователь id={} не зарегистрирован.", id);
            throw new NotFoundException(String.format("Пользователь id=%s не зарегистрирован.", id));
        }
        return userStorage.getUsers().get(id);
    }

    public User addFriendUser(Integer userId, Integer friendId) {
        getUserById(friendId); // если такого пользователя нет, то вылетит исключение
        getUserById(userId).getFriendsUser().add(Long.valueOf(friendId));
        getUserById(friendId).getFriendsUser().add(Long.valueOf(userId)); // добавление данного пользователя к друзьям его друга
        log.info("Пользователь {} добавил пользователя {} в друзья", getUserById(userId).getName(), getUserById(friendId).getName());
        return getUserById(userId);
    }

    public User deleteFriendUser(Integer userId, Integer friendId) {
        getUserById(friendId); // если такого пользователя нет, то вылетит исключение
        getUserById(userId).getFriendsUser().remove(Long.valueOf(friendId));
        getUserById(friendId).getFriendsUser().remove(Long.valueOf(userId)); // удаление данного пользователя из друзей его друга
        log.info("Пользователь {} удалил пользователя {} из друзей", getUserById(userId).getName(), getUserById(friendId).getName());
        return getUserById(userId);
    }

    public Collection<User> getFriendsUser(Integer userId) {
        Collection<User> listUser = new ArrayList<>();
        for (Long idFriends : userStorage.getUsers().get(userId).getFriendsUser()) {
            listUser.add(getUserById(Math.toIntExact(idFriends)));
        }
        return listUser;
    }

    public Collection<User> getCommonFriends(Integer userId, Integer otherId) {
        Collection<User> listCommonFriends = new ArrayList<>();
        for (Long idFriends : userStorage.getUsers().get(userId).getFriendsUser()) {
            for (Long idFriendsOtherUser : userStorage.getUsers().get(otherId).getFriendsUser()) {
                if (Objects.equals(idFriendsOtherUser, idFriends)) {
                    listCommonFriends.add(getUserById(Math.toIntExact(idFriends)));
                    break;
                }
            }
        }
        log.info("Общие друзья" + listCommonFriends);
        return listCommonFriends;
    }
}
