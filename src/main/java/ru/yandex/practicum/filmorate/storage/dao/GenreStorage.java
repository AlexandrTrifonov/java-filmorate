package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

public interface GenreStorage {

    public Collection<Genre> getGenres();

    public Genre getGenreById(Integer id);

}

