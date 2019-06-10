package ru.javawebinar.topjava.model;

import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class MealsCrud implements MealsCrudInterface {
    public static AtomicLong idCounter = new AtomicLong(MealsUtil.getHardcodedMeals().size() + 1);

    @Override
    public Meal findById(long id) {
        Map<Long, Meal> mealsMap = MealsUtil.mealsMap;
        return mealsMap.get(id);
    }

    @Override
    public List<Meal> findAll() {
        List<Meal> meals = new ArrayList<>(MealsUtil.mealsMap.values());
        return meals;
    }

    @Override
    public void add(LocalDateTime dateTime, String description, int calories) {
        Map<Long, Meal> mealsMap = MealsUtil.mealsMap;
        Meal newMeal = new Meal(idCounter.getAndIncrement(), dateTime, description, calories);
        mealsMap.put(newMeal.getId(), newMeal);
    }

    @Override
    public void update(long id, LocalDateTime dateTime, String description, int calories) {
        Meal updatedMeal = new Meal(id, dateTime, description, calories);
        MealsUtil.mealsMap.replace(id, updatedMeal);
    }

    @Override
    public void delete(long id) {
        Map<Long, Meal> mealsMap = MealsUtil.mealsMap;
        mealsMap.remove(id);
    }
}
