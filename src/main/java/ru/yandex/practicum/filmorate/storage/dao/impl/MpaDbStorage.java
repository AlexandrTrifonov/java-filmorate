package ru.yandex.practicum.filmorate.storage.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.dao.MpaStorage;
import ru.yandex.practicum.filmorate.storage.dao.makeMpa;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@Qualifier
public class MpaDbStorage implements MpaStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Collection<Mpa> getMpa() {
        String sqlQuery = "select * from mpa order by mpa_id";
        List<Mpa> mpa = jdbcTemplate.query(sqlQuery, new makeMpa());
        log.info("Получен список MPA");
        return mpa;
    }

    @Override
    public Mpa getMpaById(Integer id) {
        try {
            String query = "select * from mpa where mpa_id = ?";
            log.info("Получен MPA по id={}", id);
            return jdbcTemplate.queryForObject(query, new makeMpa(), id);
        } catch (DataAccessException e) {
            log.warn("Ошибка Mpa");
            return null;
        }
    }
}
