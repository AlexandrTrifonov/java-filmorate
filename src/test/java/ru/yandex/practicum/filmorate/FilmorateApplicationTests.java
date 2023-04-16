package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.impl.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmoRateApplicationTests {

	private final UserDbStorage userDBStorage;
	private final FilmDbStorage filmDBStorage;
	private final GenreDbStorage genreDbStorage;
	private final MpaDbStorage mpaDbStorage;
	private final LikeFilmDbStorage likeFilmDbStorage;

	@Test
	void testGetGenres() {
		Collection<Genre> genres = genreDbStorage.getGenres();
		assertThat(genres.size()).isEqualTo(6);
	}
	@Test
	void testGetGenreById() {
		Genre genres = genreDbStorage.getGenreById(1);
		assertThat(genres.getId()).isEqualTo(1);
		assertThat(genres.getName()).isEqualTo("Комедия");
	}
	@Test
	void testGetMpa() {
		Collection<Mpa> mpa = mpaDbStorage.getMpa();
		assertThat(mpa.size()).isEqualTo(5);
	}
	@Test
	void testGetMpaById() {
		Mpa mpa = mpaDbStorage.getMpaById(1);
		assertThat(mpa.getId()).isEqualTo(1);
		assertThat(mpa.getName()).isEqualTo("G");
	}

	@Test
	void testCreateFilm() {
		Mpa mpa = new Mpa(1, "G");
		Film film = Film.builder()
				.name("222")
				.description("Фильм известного режиссера Рязанова ..")
				.releaseDate(LocalDate.of(1975,01,01))
				.duration(184)
				.mpa(mpa)
				.build();
		Film filmDb = filmDBStorage.createFilm(film);
		Integer id = filmDb.getId();
		Film filmTest = filmDBStorage.getFilmById(id);
		assertThat(filmTest.getName()).isEqualTo("222");
	}
	@Test
	void testUpdateFilm() {
		Mpa mpa = new Mpa(1, "G");
		Film film = Film.builder()
				.name("333")
				.description("Фильм известного режиссера Рязанова ..")
				.releaseDate(LocalDate.of(1975,01,01))
				.duration(184)
				.mpa(mpa)
				.build();
		Film filmDb = filmDBStorage.createFilm(film);
		Integer id = filmDb.getId();
		Film filmUpdate = Film.builder()
				.name("444")
				.description("Описание фильма изменено")
				.releaseDate(LocalDate.of(1975,01,01))
				.duration(184)
				.mpa(mpa)
				.build();
		filmUpdate.setId(id);
		Film filmTest = filmDBStorage.updateFilm(filmUpdate);

		assertThat(filmTest.getDescription()).isEqualTo("Описание фильма изменено");
	}

	@Test
	void testDeleteFilm() {
		Mpa mpa = new Mpa(1, "G");
		Film film = Film.builder()
				.name("555")
				.description("Фильм известного режиссера Рязанова ..")
				.releaseDate(LocalDate.of(1975,01,01))
				.duration(184)
				.mpa(mpa)
				.build();
		Film filmDb = filmDBStorage.createFilm(film);

		filmDBStorage.deleteFilm(filmDb);
		List<Film> films = (List<Film>) filmDBStorage.findAllFilms();

		assertThat(films.size()).isEqualTo(5);
	}

	@Test
	void testFindAllFilms() {
		Mpa mpa = new Mpa(1, "G");
		Film film = Film.builder()
				.name("666")
				.description("Фильм известного режиссера Рязанова ..")
				.releaseDate(LocalDate.of(1975,01,01))
				.duration(184)
				.mpa(mpa)
				.build();
		Film film1 = Film.builder()
				.name("777")
				.description("Фильм известного режиссера Рязанова1 ..")
				.releaseDate(LocalDate.of(1985,01,01))
				.duration(104)
				.mpa(mpa)
				.build();
		filmDBStorage.createFilm(film);
		filmDBStorage.createFilm(film1);

		List<Film> films = (List<Film>) filmDBStorage.findAllFilms();

		assertThat(films.size()).isEqualTo(2);
		assertThat(films.get(0).getName()).isEqualTo("666");
		assertThat(films.get(1).getName()).isEqualTo("777");
	}

	@Test
	void testGetFilmById() {
		Mpa mpa = new Mpa(1, "G");
		Film film = Film.builder()
				.name("888")
				.description("Фильм известного режиссера Рязанова ..")
				.releaseDate(LocalDate.of(1975,01,01))
				.duration(184)
				.mpa(mpa)
				.build();
		Film filmDb = filmDBStorage.createFilm(film);
		Film filmTest = filmDBStorage.getFilmById(filmDb.getId());

		assertThat(filmTest.getName()).isEqualTo("888");
		assertThat(filmTest.getDescription()).isEqualTo("Фильм известного режиссера Рязанова ..");
	}
	@Test
	void testAddAndDeleteLikeFilm() {

		Mpa mpa = new Mpa(1, "G");
		Film film = Film.builder()
				.name("999")
				.description("Фильм известного режиссера Рязанова ..")
				.releaseDate(LocalDate.of(1975,01,01))
				.duration(184)
				.mpa(mpa)
				.build();
		User user = User.builder()
				.email("9@ya.ru")
				.login("Логин9")
				.name("Имя пользователя9")
				.birthday(LocalDate.of(1985,01,01))
				.build();

		Film filmDb = filmDBStorage.createFilm(film);
		User userDb = userDBStorage.createUser(user);

		likeFilmDbStorage.addLikeFilm(filmDb.getId(), userDb.getId());
		Film filmTest = filmDBStorage.getFilmById(filmDb.getId());

		assertThat(filmTest.getLikesFilm().size()).isEqualTo(1);

		likeFilmDbStorage.deleteLikeFilm(filmTest.getId(), userDb.getId());
		Film filmTest1 = filmDBStorage.getFilmById(filmTest.getId());

		assertThat(filmTest1.getLikesFilm().size()).isEqualTo(0);
	}
	@Test
	void testCreateUser() {
		User user = User.builder()
				.email("2@ya.ru")
				.login("Логин")
				.name("Имя пользователя")
				.birthday(LocalDate.of(1975,01,01))
				.build();
		User userDb = userDBStorage.createUser(user);
		Integer id = userDb.getId();
		User userTest = userDBStorage.getUserById(id);
		assertThat(userTest.getName()).isEqualTo("Имя пользователя");
	}

	@Test
	void testFindAllUsers() {

		List<User> users = (List<User>) userDBStorage.findAllUsers();

		assertThat(users.size()).isEqualTo(0);
	}

	@Test
	void testUpdateUser() {
		User user = User.builder()
				.email("4@ya.ru")
				.login("Логин4")
				.name("Имя пользователя4")
				.birthday(LocalDate.of(1975,04,01))
				.build();
		User userDb = userDBStorage.createUser(user);
		Integer id = userDb.getId();
		User userUpdate = User.builder()
				.email("4@ya.ru")
				.login("Логин4")
				.name("Имя пользователя5")
				.birthday(LocalDate.of(1975,04,01))
				.build();
		userUpdate.setId(id);
		User userTest = userDBStorage.updateUser(userUpdate);

		assertThat(userTest.getName()).isEqualTo("Имя пользователя5");
	}

	@Test
	void testDeleteUser() {
		User user = User.builder()
				.email("6@ya.ru")
				.login("Логин6")
				.name("Имя пользователя6")
				.birthday(LocalDate.of(1975,04,01))
				.build();
		User userDb = userDBStorage.createUser(user);

		userDBStorage.deleteUser(userDb);
		List<User> users = (List<User>) userDBStorage.findAllUsers();

		assertThat(users.size()).isEqualTo(2);
	}
	@Test
	void testGetUserById() {

		User user = User.builder()
				.email("3@ya.ru")
				.login("Логин3")
				.name("Имя пользователя3")
				.birthday(LocalDate.of(1985,01,01))
				.build();
		User userDb = userDBStorage.createUser(user);
		Integer id = userDb.getId();
		User userTest = userDBStorage.getUserById(id);

		assertThat(userTest.getName()).isEqualTo("Имя пользователя3");
	}

	@Test
	void testAddAndGetFriendsUserAndFriendUser() {

		User user = User.builder()
				.email("11@ya.ru")
				.login("Логин11")
				.name("Имя пользователя11")
				.birthday(LocalDate.of(1985,01,01))
				.build();
		User friend = User.builder()
				.email("12@ya.ru")
				.login("Логин12")
				.name("Имя пользователя12")
				.birthday(LocalDate.of(1995,01,01))
				.build();
		User friend1 = User.builder()
				.email("13@ya.ru")
				.login("Логин13")
				.name("Имя пользователя13")
				.birthday(LocalDate.of(1986,01,01))
				.build();

		User userDb = userDBStorage.createUser(user);
		User friendDb = userDBStorage.createUser(friend);
		User friend1Db = userDBStorage.createUser(friend1);
		userDBStorage.addFriendUser(userDb.getId(), friendDb.getId());
		userDBStorage.addFriendUser(userDb.getId(), friend1Db.getId());

		User userTest = userDBStorage.getUserById(userDb.getId());
		assertThat(userTest.getFriendsUser().size()).isEqualTo(2);

		userDBStorage.deleteFriendUser(userTest.getId(), friendDb.getId());

		User user1 = userDBStorage.getUserById(userTest.getId());
		assertThat(user1.getFriendsUser().size()).isEqualTo(1);

	}
}

/*@SpringBootTest
class FilmorateApplicationTests {

	@Test
	void contextLoads() {
	}

}*/
