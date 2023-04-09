# java-filmorate
### Схема базы данных *java-filmorate*
![Схема Базы Данных](db_schema.png)
#### *Таблицы с Данными*
- **films** список фильмов
- **mpa** рейтинг Ассоциации кинокомпаний
- **filmGenre** связь фильмов с жанрами
- **genre** список жанров фильмов
- **likesFilm** лайки пользователей
- **users** список пользователей
- **friendsUser** связь дружбы пользователей
#### *Примеры SLQ-запросов*
-     Список всех фильмов
       - SELECT name FROM films;
-     Список всех пользователей
       - SELECT name FROM users;
-     Список 10 наиболее популярных фильмов
       - SELECT name FROM films WHERE id IN (
           SELECT film_id FROM likesFilm
           GROUP BY film_id
           ORDER BY COUNT(user_id) DESC
           LIMIT 10
         );