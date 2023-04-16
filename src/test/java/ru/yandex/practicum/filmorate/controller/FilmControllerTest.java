package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FilmControllerTest {

/*    @Autowired
    private FilmController filmController;
    @Autowired
    private UserController userController;

    @BeforeEach
    private void afterEach() {
        filmController.findAllFilms().clear();
        userController.findAllUsers().clear();
    }

    @Test
    void shouldThrowValidationExceptionIfFilmCreateWithAlreadyExistName() {
        Film film = new Film(1, "Name", "Description" , LocalDate.of(1977,11,11), 184);
        filmController.createFilm(film);
        Film filmNew = new Film(1, "Name", "Description" , LocalDate.of(1977,11,11), 184);
        Assertions.assertThrows(ValidationException.class, () -> filmController.createFilm(filmNew));
    }

    @Test
    void shouldThrowNotFoundExceptionIfFilmUpdateWithWrongId() {
        Film film = new Film(1, "Name", "Description" , LocalDate.of(1977,11,11), 184);
        filmController.createFilm(film);
        Film filmUpdate = new Film(99, "NameUpdate", "DescriptionUpdate" , LocalDate.of(1977,11,11), 184);
        Assertions.assertThrows(NotFoundException.class, () -> filmController.updateFilm(filmUpdate));
    }

    @Test
    void shouldThrowNotFoundExceptionIfFilmDeleteWithWrongId() {
        Film film = new Film(1, "Name", "Description" , LocalDate.of(1977,11,11), 184);
        filmController.createFilm(film);
        Film filmDelete = new Film(99, "NameUpdate", "DescriptionUpdate" , LocalDate.of(1977,11,11), 184);
        Assertions.assertThrows(NotFoundException.class, () -> filmController.deleteFilm(filmDelete));
    }

    @Test
    void shouldThrowNotFoundExceptionIfGetFilmByIdWithWrongId() {
        Film film = new Film(1, "Name", "Description" , LocalDate.of(1977,11,11), 184);
        filmController.createFilm(film);
        Integer wrongId = 99;
        Assertions.assertThrows(NotFoundException.class, () -> filmController.getFilmById(wrongId));
    }

    @Test
    void shouldThrowNotFoundExceptionIfAddLikeFilmWithWrongIdFilmOrUser() {
        Film film = new Film(1, "Name", "Description" , LocalDate.of(1977,11,11), 184);
        User user = new User(1, "2@mail.ru", "Login", "UserName", LocalDate.of(1977,11,11));
        filmController.createFilm(film);
        userController.createUser(user);
        Integer wrongIdFilm = 99;
        Assertions.assertThrows(NotFoundException.class, () -> filmController.addLikeFilm(wrongIdFilm, 1));
        Integer wrongIdUser = 99;
        Assertions.assertThrows(NotFoundException.class, () -> filmController.addLikeFilm(1, wrongIdUser));
    }

    @Test
    void shouldThrowNotFoundExceptionIfDeleteLikeFilmWithWrongIdFilmOrUser() {
        Film film = new Film(1, "Name", "Description" , LocalDate.of(1977,11,11), 184);
        User user = new User(1, "2@mail.ru", "Login", "UserName", LocalDate.of(1977,11,11));
        filmController.createFilm(film);
        userController.createUser(user);
        Integer wrongIdFilm = 99;
        Assertions.assertThrows(NotFoundException.class, () -> filmController.addLikeFilm(wrongIdFilm, 1));
        Integer wrongIdUser = 99;
        Assertions.assertThrows(NotFoundException.class, () -> filmController.deleteLikeFilm(1, wrongIdUser));
    }

    @Test
    void shouldGetPopularFilms() {
        Film film = new Film(1, "Name", "Description" , LocalDate.of(1977,11,11), 184);
        Film film1 = new Film(2, "Name1", "Description1" , LocalDate.of(1977,11,12), 184);
        Film film2 = new Film(3, "Name2", "Description2" , LocalDate.of(1977,11,13), 184);

        User user = new User(1, "2@mail.ru", "Login", "UserName", LocalDate.of(1977,11,11));
        User user1 = new User(2, "22@mail.ru", "Login2", "UserName2", LocalDate.of(1977,11,12));

        filmController.createFilm(film);
        filmController.createFilm(film1);
        filmController.createFilm(film2);

        userController.createUser(user);
        userController.createUser(user1);

        filmController.addLikeFilm(film.getId(),user.getId());
        filmController.addLikeFilm(film1.getId(),user.getId());
        filmController.addLikeFilm(film1.getId(),user1.getId());

        final Collection<Film> films = filmController.getPopularFilms(10); // список фильмов по id 2 1 3
        assertNotNull(films, "Список фильмов пустой");
        assertEquals(3, films.size(), "Размер списка не совпадает");
        assertFalse(films.isEmpty(), "Список фильмов не пустой");

        final Collection<Film> films1 = filmController.getPopularFilms(1);
        assertTrue(films.contains(film1), "Фильм с максимальным количеством лайков отсутствует");

        final List<Film> list = new ArrayList<>(films);
        assertEquals(film2, list.get(2), "Это не самый популярный фильм");
    }*/
}