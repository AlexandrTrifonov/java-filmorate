package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;

import static ru.yandex.practicum.filmorate.service.ValidateUser.validateUser;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserStorage userStorage;

    public Collection<User> findAllUsers() {
        return userStorage.getUsers().values();
    }

    public User createUser(User user) {
        validateUser(user);
        return userStorage.createUser(user);
    }

    public User updateUser(User user) {
        validateUser(user);
        return userStorage.updateUser(user);
    }

    public void deleteUser(User user) {
        userStorage.deleteUser(user);
    }

    public User getUserById(Integer id) {
        if (!userStorage.getUsers().containsKey(id)) {
            log.warn("Пользователь id={} не зарегистрирован.", id);
            throw new NotFoundException(String.format("Пользователь id=%s не зарегистрирован.", id));
        }
        return userStorage.getUsers().get(id);
    }

    public User addFriendUser(Integer userId, Integer friendId) {
        final User user = getUserById(userId);
        final User userFriend = getUserById(friendId);
        user.getFriendsUser().add(Long.valueOf(friendId));
        userFriend.getFriendsUser().add(Long.valueOf(userId)); // добавление данного пользователя к друзьям его друга
        log.info("Пользователь {} добавил пользователя {} в друзья", user.getName(), userFriend.getName());
        return user;
    }

    public User deleteFriendUser(Integer userId, Integer friendId) {
        final User user = getUserById(userId);
        final User userFriend = getUserById(friendId);
        user.getFriendsUser().remove(Long.valueOf(friendId));
        userFriend.getFriendsUser().remove(Long.valueOf(userId)); // удаление данного пользователя из друзей его друга
        log.info("Пользователь {} удалил пользователя {} из друзей", user.getName(), userFriend.getName());
        return user;
    }

    public Collection<User> getFriendsUser(Integer userId) {
        Collection<User> listUser = new ArrayList<>();
 //       for (Long idFriends : userStorage.getUsers().get(userId).getFriendsUser()) {
        for (Long idFriends : getUserById(userId).getFriendsUser()) {
                listUser.add(getUserById(Math.toIntExact(idFriends)));
        }
        return listUser;
    }

    public Collection<User> getCommonFriends(Integer userId, Integer otherId) {
        Collection<User> listCommonFriends = new ArrayList<>();
        final Set<Long> userFriendsId = getUserById(userId).getFriendsUser();
        final Set<Long> otherUserFriendsId = getUserById(otherId).getFriendsUser();
        final Set<Long> listCommonFriendsId = new HashSet<>(userFriendsId);
        listCommonFriendsId.retainAll(otherUserFriendsId);
        for (Long idFriends : listCommonFriendsId) {
            listCommonFriends.add(getUserById(Math.toIntExact(idFriends)));
        }
        log.info("Общие друзья" + listCommonFriends);
        return listCommonFriends;
    }
}
