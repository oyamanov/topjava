package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealsCrudInterface {
    Meal findById(long id);

    List<Meal> findAll();

    void add(Meal meal);

    void update(long id, Meal meal);

    void delete(long id);
}