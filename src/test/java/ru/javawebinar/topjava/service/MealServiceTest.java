package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static org.assertj.core.api.Assertions.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test(expected = NotFoundException.class)
    public void getNotOwnFood() {
        service.get(USER_MEAL_1.getId(), ADMIN_ID);
    }

    @Test
    public void get() {
        Meal userMeal1 = service.get(USER_MEAL_1.getId(), USER_ID);
        assertThat(userMeal1).isEqualToComparingFieldByField(USER_MEAL_1);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotOwnFood() {
        service.delete(USER_MEAL_1.getId(), ADMIN_ID);
    }

    @Test
    public void delete() {
        service.delete(ADMIN_MEAL_2.getId(), ADMIN_ID);
        assertThat(service.getAll(ADMIN_ID)).usingFieldByFieldElementComparator().isEqualTo(Arrays.asList(ADMIN_MEAL_1));
    }

    @Test
    public void getBetweenDates() {
        List<Meal> filteredMeals = service.getBetweenDates(LocalDate.of(2019, Month.MAY, 29), LocalDate.of(2019, Month.MAY, 30), USER_ID);
        assertThat(filteredMeals).usingFieldByFieldElementComparator().isEqualTo(Arrays.asList(USER_MEAL_3, USER_MEAL_2, USER_MEAL_1));
    }

    @Test
    public void getBetweenDateTimes() {
        List<Meal> filteredMeals = service.getBetweenDateTimes(LocalDateTime.of(2019, Month.MAY, 30, 19, 0),
                LocalDateTime.of(2019, Month.MAY, 30, 21, 0),
                USER_ID);
        assertThat(filteredMeals).usingFieldByFieldElementComparator().isEqualTo(Arrays.asList(USER_MEAL_3));
    }

    @Test
    public void getAll() {
        List<Meal> adminMeals = service.getAll(ADMIN_ID);
        assertThat(adminMeals).usingFieldByFieldElementComparator().isEqualTo(Arrays.asList(ADMIN_MEAL_2, ADMIN_MEAL_1));
    }

    @Test(expected = NotFoundException.class)
    public void updateNotOwnFood() {
        service.update(USER_MEAL_1, ADMIN_ID);
    }

    @Test
    public void update() {
        Meal updatedMeal = new Meal(USER_MEAL_1.getId(), LocalDateTime.of(2019, Month.JUNE, 23, 7, 0), "Завтраккк", 2000);
        service.update(updatedMeal, USER_ID);
        Meal meal = service.get(USER_MEAL_1.getId(), USER_ID);
        assertThat(meal).isEqualToComparingFieldByField(updatedMeal);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(null, LocalDateTime.now(), "", 2000);
        Meal createdMeal = service.create(newMeal, ADMIN_ID);
        newMeal.setId(createdMeal.getId());
        assertThat(service.getAll(ADMIN_ID)).usingFieldByFieldElementComparator().isEqualTo(Arrays.asList(newMeal, ADMIN_MEAL_2, ADMIN_MEAL_1));
    }
}