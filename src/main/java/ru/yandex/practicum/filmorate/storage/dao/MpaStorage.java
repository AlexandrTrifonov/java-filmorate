package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;

public interface MpaStorage {

    Collection<Mpa> getMpa();

    Mpa getMpaById(Integer id);

}
