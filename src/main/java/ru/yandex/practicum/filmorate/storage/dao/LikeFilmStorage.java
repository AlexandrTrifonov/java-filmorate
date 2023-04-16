package ru.yandex.practicum.filmorate.storage.dao;

public interface LikeFilmStorage {

    void addLikeFilm(Integer filmId, Integer userId);

    void deleteLikeFilm(Integer filmId, Integer userId);
}
