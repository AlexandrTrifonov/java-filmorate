package ru.yandex.practicum.filmorate.storage.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.*;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
@Qualifier
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public User createUser(User user) {
        try {
            String query = "INSERT INTO users (email, login, name, birthday) " +
                    "VALUES (?, ?, ?, ?)";

            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(query, new String[]{"user_id"});
                stmt.setString(1, user.getEmail());
                stmt.setString(2, user.getLogin());
                stmt.setString(3, user.getName());
                stmt.setDate(4, Date.valueOf(user.getBirthday()));
                return stmt;
            }, keyHolder);

            Integer userId = Objects.requireNonNull(keyHolder.getKey()).intValue();

            log.info("Пользователь {} добавлен, id={}", user.getName(), userId);
            return getUserById(userId);
        } catch (DataAccessException e) {
            log.warn("Ошибка добавления пользователя email {}, логин {}", user.getEmail(), user.getLogin());
            return null;
        }
    }

    @Override
    public User updateUser(User user) {
        try {
            String query = "UPDATE users SET email = ?, login = ?, name = ?, birthday = ?" +
                    "WHERE user_id = ?";
            jdbcTemplate.update(
                    query,
                    user.getEmail(),
                    user.getLogin(),
                    user.getName(),
                    user.getBirthday(),
                    user.getId()
            );

            if (user.getFriendsUser() != null) {
                String queryFriends = "UPDATE friends_user SET user_id = ?, friends_id = ? VALUES (?, ?)";
                for (Integer friendsUser : user.getFriendsUser()) {
                    jdbcTemplate.update(queryFriends, user.getId(), friendsUser);
                }
            }

            log.info("Пользователь {} обновлен, id={}", user.getName(), user.getId());
            return getUserById(user.getId());
        } catch (DataAccessException e) {
            log.warn("Ошибка обновления пользователя");
            return null;
        }
    }

    @Override
    public void deleteUser(User user) {

        try {
            String query = "DELETE FROM users WHERE user_id = ?";
            jdbcTemplate.update(query, user.getId());
            String query1 = "DELETE FROM friends_user WHERE user_id = ?";
            jdbcTemplate.update(query1, user.getId());
            log.info("Пользователь {} удален", user.getName());
        } catch (DataAccessException e) {
            log.warn("Ошибка обновления пользователя");
        }
    }

    @Override
    public Collection<User> findAllUsers() {
        String query = "SELECT * FROM users";
        List<User> users = jdbcTemplate.query(query, new makeUser());
        addFriendsToUsers(users);
        log.info("Получен список пользователей");
        return users;
    }

    private void addFriendsToUsers(List<User> users) {
        for (User user : users) {
            String query = "SELECT friends_id FROM friends_user WHERE user_id = ?";
            List<Integer> friendsId = jdbcTemplate.queryForList(query, Integer.class, user.getId());
            Set<Integer> friendsUser = new HashSet<>(friendsId);
            user.setFriendsUser(friendsUser);
        }
        log.info("Добавлены друзья с списку пользователей");
    }

    @Override
    public User getUserById(Integer id) {

        try {
            String query = "SELECT * FROM users WHERE user_id = ?";
            User user = jdbcTemplate.queryForObject(query, new makeUser(), id);

            Set<Integer> friendsUser = addFriendsUser(id);
            assert user != null;
            user.setFriendsUser(friendsUser);

            log.info("Пользователь {} получен по id={}", user.getName(), id);
            return user;
        } catch (DataAccessException e) {
            log.warn("Ошибка получения пользователя по id");
            return null;
        }
    }

    private Set<Integer> addFriendsUser(Integer id) {
        String query = "SELECT friends_id FROM friends_user WHERE user_id = ?";
        List<Integer> friendsId = jdbcTemplate.queryForList(query, Integer.class, id);
        return new HashSet<>(friendsId);
    }

    public void addFriendUser(Integer userId, Integer friendId) {
        String query = "INSERT INTO friends_user (user_id, friends_id) VALUES (?, ?)";
        jdbcTemplate.update(query, userId, friendId);
        log.info("Пользователю с id={} добавлен друг с id={}", userId, friendId);
    }

    public void deleteFriendUser(Integer userId, Integer friendId) {
        String query = "DELETE FROM friends_user WHERE user_id = ? AND friends_id = ?";
        jdbcTemplate.update(query, userId, friendId);
        log.info("У пользователя с id={} удален друг с id={}", userId, friendId);
    }

    public Collection<User> getFriendsUser(Integer id) {
        String query = "SELECT * FROM users WHERE user_id IN (SELECT friends_id FROM friends_user WHERE user_id = ?)";
        List<User> friends = jdbcTemplate.query(query, new makeUser(), id);
        List<User> friendsReturn = new ArrayList<>();
        for (User user : friends) {
            Integer idUser = user.getId();
            Set<Integer> friendsUser = addFriendsUser(idUser);
            user.setFriendsUser(friendsUser);
            friendsReturn.add(user);
        }

        log.info("Получен список друзей пользователя с id={}", id);
        return friendsReturn;
    }
}