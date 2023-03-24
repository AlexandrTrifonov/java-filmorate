package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class User {

    private Integer id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
    private final Set<Long> friendsUser = new HashSet<>();
}
