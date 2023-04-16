package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;

public interface MpaStorage {

    public Collection<Mpa> getMpa();

    public Mpa getMpaById(Integer id);

}
