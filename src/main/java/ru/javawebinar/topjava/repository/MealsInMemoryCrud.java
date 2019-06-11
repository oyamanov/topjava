package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MealsInMemoryCrud implements MealsInMemoryCrudInterface {
    private static AtomicLong idCounter = new AtomicLong(1);

    private static Map<Long, Meal> mealsMap = new ConcurrentHashMap<>();

    static {
        MealsInMemoryCrud mealsCrud = new MealsInMemoryCrud();
        mealsCrud.initTestData();
    }

    private void initTestData() {
        add(new Meal(0, LocalDateTime.of(2019, Month.JUNE, 1, 10, 0), "Завтрак", 500));
        add(new Meal(0, LocalDateTime.of(2019, Month.JUNE, 1, 15, 0), "Обед", 1000));
        add(new Meal(0, LocalDateTime.of(2019, Month.JUNE, 1, 20, 0), "Ужин", 500));
        add(new Meal(0, LocalDateTime.of(2019, Month.JUNE, 2, 10, 0), "Завтрак", 510));
        add(new Meal(0, LocalDateTime.of(2019, Month.JUNE, 2, 15, 0), "Обед", 1000));
        add(new Meal(0, LocalDateTime.of(2019, Month.JUNE, 2, 20, 0), "Ужин", 500));
        add(new Meal(0, LocalDateTime.of(2019, Month.JUNE, 3, 10, 0), "Завтрак", 500));
        add(new Meal(0, LocalDateTime.of(2019, Month.JUNE, 3, 15, 0), "Обед", 1000));
        add(new Meal(0, LocalDateTime.of(2019, Month.JUNE, 3, 20, 0), "Ужин", 500));
        add(new Meal(0, LocalDateTime.of(2019, Month.JUNE, 4, 10, 0), "Завтрак", 510));
        add(new Meal(0, LocalDateTime.of(2019, Month.JUNE, 4, 15, 0), "Обед", 1000));
        add(new Meal(0, LocalDateTime.of(2019, Month.JUNE, 4, 20, 0), "Ужин", 500));
        add(new Meal(0, LocalDateTime.of(2019, Month.JUNE, 5, 10, 0), "Завтрак", 500));
        add(new Meal(0, LocalDateTime.of(2019, Month.JUNE, 5, 15, 0), "Обед", 1000));
        add(new Meal(0, LocalDateTime.of(2019, Month.JUNE, 5, 20, 0), "Ужин", 500));
        add(new Meal(0, LocalDateTime.of(2019, Month.JUNE, 6, 10, 0), "Завтрак", 510));
        add(new Meal(0, LocalDateTime.of(2019, Month.JUNE, 6, 15, 0), "Обед", 1000));
        add(new Meal(0, LocalDateTime.of(2019, Month.JUNE, 6, 20, 0), "Ужин", 500));
    }

    @Override
    public Meal findById(long id) {
        return mealsMap.get(id);
    }

    @Override
    public List<Meal> findAll() {
        return new ArrayList<>(mealsMap.values());
    }

    @Override
    public void add(Meal meal) {
        Meal newMeal = new Meal(idCounter.getAndIncrement(), meal.getDateTime(), meal.getDescription(), meal.getCalories());
        mealsMap.put(newMeal.getId(), newMeal);
    }

    @Override
    public void update(long id, Meal updatedMeal) {
        mealsMap.replace(id, updatedMeal);
    }

    @Override
    public void delete(long id) {
        mealsMap.remove(id);
    }
}
