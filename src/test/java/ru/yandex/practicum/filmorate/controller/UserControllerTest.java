package ru.yandex.practicum.filmorate.controller;

/*import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;*/
import org.springframework.boot.test.context.SpringBootTest;
/*import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;*/

@SpringBootTest
class UserControllerTest {

/*    @Autowired
    private UserController userController;

    @BeforeEach
    private void afterEach() {
        userController.findAllUsers().clear();
    }

    @Test
    void shouldThrowValidationExceptionIfUserCreateWithAlreadyExistEmail() {
        User user = User.builder()
                .id(1)
                .email("222@ya.ru")
                .login("Логин")
                .name("Имя пользователя")
                .birthday(LocalDate.of(1977,11,11))
                .build();
        userController.createUser(user);
        User userNew = User.builder()
                .id(2)
                .email("222@ya.ru")
                .login("ЛогинNew")
                .name("Имя пользователяNew")
                .birthday(LocalDate.of(1997,11,11))
                .build();
        Assertions.assertThrows(ValidationException.class, () -> userController.createUser(userNew));
    }

    @Test
    void shouldThrowNotFoundExceptionIfUserUpdateWithWrongId() {
        User user = User.builder()
                .id(1)
                .email("222@ya.ru")
                .login("Логин")
                .name("Имя пользователя")
                .birthday(LocalDate.of(1977,11,11))
                .build();
        userController.createUser(user);
        User userUpdate = User.builder()
                .id(99)
                .email("222@ya.ru")
                .login("Логин")
                .name("Имя пользователя")
                .birthday(LocalDate.of(1977,11,11))
                .build();
        Assertions.assertThrows(NotFoundException.class, () -> userController.updateUser(userUpdate));
    }

    @Test
    void shouldThrowNotFoundExceptionIfUserDeleteWithWrongId() {
        User user = User.builder()
                .id(1)
                .email("222@ya.ru")
                .login("Логин")
                .name("Имя пользователя")
                .birthday(LocalDate.of(1977,11,11))
                .build();
        userController.createUser(user);
        User userDelete = User.builder()
                .id(99)
                .email("222@ya.ru")
                .login("Логин")
                .name("Имя пользователя")
                .birthday(LocalDate.of(1977,11,11))
                .build();
        Assertions.assertThrows(NotFoundException.class, () -> userController.deleteUser(userDelete));
    }

    @Test
    void shouldThrowNotFoundExceptionIfGetUserByIdWithWrongId() {
        User user = User.builder()
                .id(1)
                .email("222@ya.ru")
                .login("Логин")
                .name("Имя пользователя")
                .birthday(LocalDate.of(1977,11,11))
                .build();
        userController.createUser(user);
        Integer wrongId = 99;
        Assertions.assertThrows(NotFoundException.class, () -> userController.getUserById(wrongId));
    }

    @Test
    void shouldThrowNotFoundExceptionIfAddFriendUserWithWrongIdUserOrFriendUser() {
        User user = User.builder()
                .id(1)
                .email("222@ya.ru")
                .login("Логин")
                .name("Имя пользователя")
                .birthday(LocalDate.of(1977,11,11))
                .build();
        User userFriend = User.builder()
                .id(2)
                .email("2222@ya.ru")
                .login("Логин2")
                .name("Имя пользователя2")
                .birthday(LocalDate.of(1997,11,11))
                .build();
        userController.createUser(user);
        userController.createUser(userFriend);
        Integer wrongIdUser = 99;
        Assertions.assertThrows(NotFoundException.class, () -> userController.addFriendUser(wrongIdUser, 2));
        Integer wrongIdUserFriend = 99;
        Assertions.assertThrows(NotFoundException.class, () -> userController.addFriendUser(1, wrongIdUserFriend));
    }

    @Test
    void shouldThrowNotFoundExceptionIfDeleteFriendUserWithWrongIdUserOrUserFriend() {
        User user = User.builder()
                .id(1)
                .email("222@ya.ru")
                .login("Логин")
                .name("Имя пользователя")
                .birthday(LocalDate.of(1977,11,11))
                .build();
        User userFriend = User.builder()
                .id(2)
                .email("2222@ya.ru")
                .login("Логин2")
                .name("Имя пользователя2")
                .birthday(LocalDate.of(1997,11,11))
                .build();
        userController.createUser(user);
        userController.createUser(userFriend);
        Integer wrongIdFilm = 99;
        Assertions.assertThrows(NotFoundException.class, () -> userController.deleteFriendUser(wrongIdFilm, 2));
        Integer wrongIdUser = 99;
        Assertions.assertThrows(NotFoundException.class, () -> userController.deleteFriendUser(1, wrongIdUser));
    }*/

/*    @Test
    void shouldGetFriendsUser() {
        User user = new User(1, "2@mail.ru", "Login", "UserName", LocalDate.of(1977, 11, 11));
        User user1 = new User(2, "22@mail.ru", "Login2", "UserName2", LocalDate.of(1977, 11, 12));
        User user2 = new User(3, "222@mail.ru", "Login22", "UserName22", LocalDate.of(1977, 11, 12));

        userController.createUser(user);
        userController.createUser(user1);
        userController.createUser(user2);

        userController.addFriendUser(user.getId(), user2.getId());
        final Collection<Long> userIdFriends = new HashSet<>();
        Integer idUser2 = user2.getId();
        userIdFriends.add(Long.valueOf(idUser2));
        assertEquals(userIdFriends, user.getFriendsUser(), "Ошибка при добавлении в друзья");

        final Collection<Long> FriendIdFriends = new HashSet<>();
        Integer idUser = user.getId();
        FriendIdFriends.add(Long.valueOf(idUser));
        assertEquals(FriendIdFriends, user2.getFriendsUser(), "Ошибка при добавлении в друзья");

        assertTrue(user1.getFriendsUser().isEmpty(), "Список друзей не пустой");
    }

    @Test
    void shouldGetCommonFriends() {
        User user = new User(1, "2@mail.ru", "Login", "UserName", LocalDate.of(1977, 11, 11));
        User user1 = new User(2, "22@mail.ru", "Login2", "UserName2", LocalDate.of(1977, 11, 12));
        User user2 = new User(3, "222@mail.ru", "Login22", "UserName22", LocalDate.of(1977, 11, 12));

        userController.createUser(user);
        userController.createUser(user1);
        userController.createUser(user2);

        userController.addFriendUser(user.getId(), user2.getId());
        userController.addFriendUser(user1.getId(), user2.getId());

        Collection<User> actualFriends = userController.getCommonFriends(user.getId(), user1.getId());

        assertTrue(actualFriends.contains(user2), "Ошибка в общих друзьях");
    }*/
}