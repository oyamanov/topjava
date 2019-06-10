package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.util.List;

public interface MealsCrudInterface {
    Meal findById(long id);

    List<Meal> findAll();

    void add(LocalDateTime dateTime, String description, int calories);

    void update(long id, LocalDateTime dateTime, String description, int calories);

    void delete(long id);
}