package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class MealsUtil {
    public static final int CALORIES_PER_DAY = 2000; // TODO: refactor later

    public static void main(String[] args) {
        List<Meal> meals = Arrays.asList(
                new Meal(1, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new Meal(2, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new Meal(3, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new Meal(4, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new Meal(5, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new Meal(6, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        List<MealTo> mealsWithExcess = getFilteredWithExcess(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsWithExcess.forEach(System.out::println);
    }

    public static List<Meal> getHardcodedMeals() {
        List<Meal> mealList = Arrays.asList(
                new Meal(1, LocalDateTime.of(2019, Month.JUNE, 1, 10, 0), "Завтрак", 500),
                new Meal(2, LocalDateTime.of(2019, Month.JUNE, 1, 15, 0), "Обед", 1000),
                new Meal(3, LocalDateTime.of(2019, Month.JUNE, 1, 20, 0), "Ужин", 500),
                new Meal(4, LocalDateTime.of(2019, Month.JUNE, 2, 10, 0), "Завтрак", 510),
                new Meal(5, LocalDateTime.of(2019, Month.JUNE, 2, 15, 0), "Обед", 1000),
                new Meal(6, LocalDateTime.of(2019, Month.JUNE, 2, 20, 0), "Ужин", 500),
                new Meal(7, LocalDateTime.of(2019, Month.JUNE, 3, 10, 0), "Завтрак", 500),
                new Meal(8, LocalDateTime.of(2019, Month.JUNE, 3, 15, 0), "Обед", 1000),
                new Meal(9, LocalDateTime.of(2019, Month.JUNE, 3, 20, 0), "Ужин", 500),
                new Meal(10, LocalDateTime.of(2019, Month.JUNE, 4, 10, 0), "Завтрак", 510),
                new Meal(11, LocalDateTime.of(2019, Month.JUNE, 4, 15, 0), "Обед", 1000),
                new Meal(12, LocalDateTime.of(2019, Month.JUNE, 4, 20, 0), "Ужин", 500),
                new Meal(13, LocalDateTime.of(2019, Month.JUNE, 5, 10, 0), "Завтрак", 500),
                new Meal(14, LocalDateTime.of(2019, Month.JUNE, 5, 15, 0), "Обед", 1000),
                new Meal(15, LocalDateTime.of(2019, Month.JUNE, 5, 20, 0), "Ужин", 500),
                new Meal(16, LocalDateTime.of(2019, Month.JUNE, 6, 10, 0), "Завтрак", 510),
                new Meal(17, LocalDateTime.of(2019, Month.JUNE, 6, 15, 0), "Обед", 1000),
                new Meal(18, LocalDateTime.of(2019, Month.JUNE, 6, 20, 0), "Ужин", 500)
        ); // Creates a fixed-size ArrayList, which is not mutable
        return new ArrayList<>(mealList);
    }

    public static List<MealTo> mealsToMealTo(List<Meal> meals, int caloriesPerDay) {
        return getFilteredWithExcess(meals, LocalTime.MIN, LocalTime.MAX, caloriesPerDay);
    }

    public static List<MealTo> getFilteredWithExcess(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories)));

        return meals.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getTime(), startTime, endTime))
                .map(meal -> createWithExcess(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static MealTo createWithExcess(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}