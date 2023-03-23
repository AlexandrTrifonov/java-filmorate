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
// @RequiredArgsConstructor
@NoArgsConstructor
public class User {

    private Integer id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
    private Set<Long> friendsUser = new HashSet<>();

/*    public User (String email, String login, String name, LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
        friendsUser = new HashSet<>();
    }*/
}
