package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public Collection<User> findAllUsers() {
        return userService.findAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
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
    public void addFriendUser(@PathVariable (name = "id") Integer userId, @PathVariable Integer friendId) {
        userService.addFriendUser(userId, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriendUser(@PathVariable (name = "id") Integer userId, @PathVariable Integer friendId) {
        userService.deleteFriendUser(userId, friendId);
    }

    @GetMapping("/{id}/friends")
 //   @ResponseBody
    public Collection<User> getFriendsUser(@PathVariable (name = "id", required = false) Integer userId) {
        return userService.getFriendsUser(userId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    @ResponseBody
    public Collection<User> getCommonFriends(@PathVariable (name = "id") Integer userId, @PathVariable Integer otherId) {
        return userService.getCommonFriends(userId, otherId);
    }
}
