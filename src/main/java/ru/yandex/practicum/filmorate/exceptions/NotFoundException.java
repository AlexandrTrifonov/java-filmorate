package ru.yandex.practicum.filmorate.exceptions;

public class NotFoundException extends RuntimeException{

    public static void throwException(String format, Integer id) {
        String message = format + " c id=" + id;
        throw new NotFoundException(message);
    }

    public NotFoundException(String message) {
        super(message);
    }
}
