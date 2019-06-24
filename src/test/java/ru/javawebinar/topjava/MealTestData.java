package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final Meal USER_MEAL_1 = new Meal(100002, LocalDateTime.of(2019, Month.MAY, 30, 7, 0), "Завтрак", 500);
    public static final Meal USER_MEAL_2 = new Meal(100003, LocalDateTime.of(2019, Month.MAY, 30, 12, 0), "Обед", 1000);
    public static final Meal USER_MEAL_3 = new Meal(100004, LocalDateTime.of(2019, Month.MAY, 30, 20, 0), "Ужин", 500);
    public static final Meal USER_MEAL_4 = new Meal(100005, LocalDateTime.of(2019, Month.MAY, 31, 7, 0), "Завтрак", 500);
    public static final Meal USER_MEAL_5 = new Meal(100006, LocalDateTime.of(2019, Month.MAY, 31, 12, 0), "Обед", 1000);
    public static final Meal USER_MEAL_6 = new Meal(100007, LocalDateTime.of(2019, Month.MAY, 31, 20, 0), "Ужин", 510);
    public static final Meal ADMIN_MEAL_1 = new Meal(100008, LocalDateTime.of(2019, Month.MAY, 30, 12, 0), "Обед", 1000);
    public static final Meal ADMIN_MEAL_2 = new Meal(100009, LocalDateTime.of(2019, Month.MAY, 30, 20, 0), "Ужин", 1100);

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
}
