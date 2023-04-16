package ru.yandex.practicum.filmorate.storage.dao;

public interface LikeFilmStorage {

    public void addLikeFilm(Integer filmId, Integer userId);
    public void deleteLikeFilm(Integer filmId, Integer userId);
}
