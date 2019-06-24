package ru.javawebinar.topjava.repository.jdbc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class JdbcMealRepositoryTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private JdbcMealRepository repository;

    @Test
    public void saveUpdateNotOwnFood() {
        Meal updateResult = repository.save(USER_MEAL_1, ADMIN_ID);
        assertNull(updateResult);
    }

    @Test
    public void saveUpdate() {
        Meal updatedMeal = new Meal(USER_MEAL_1.getId(), LocalDateTime.of(2019, Month.JUNE, 23, 7, 0), "Завтраккк", 2000);
        repository.save(updatedMeal, USER_ID);
        Meal meal = repository.get(USER_MEAL_1.getId(), USER_ID);
        assertThat(meal).isEqualToComparingFieldByField(updatedMeal);
    }

    @Test
    public void saveCreate() {
        Meal newMeal = new Meal(null, LocalDateTime.now(), "", 2000);
        Meal createdMeal = repository.save(newMeal, ADMIN_ID);
        newMeal.setId(createdMeal.getId());
        assertThat(repository.getAll(ADMIN_ID)).usingFieldByFieldElementComparator().isEqualTo(Arrays.asList(newMeal, ADMIN_MEAL_2, ADMIN_MEAL_1));
    }

    @Test
    public void deleteNotOwnFood() {
        boolean deleteResult = repository.delete(USER_MEAL_1.getId(), ADMIN_ID);
        assertFalse(deleteResult);
    }

    @Test
    public void delete() {
        repository.delete(ADMIN_MEAL_2.getId(), ADMIN_ID);
        assertThat(repository.getAll(ADMIN_ID)).usingFieldByFieldElementComparator().isEqualTo(Arrays.asList(ADMIN_MEAL_1));
    }

    @Test
    public void getNotOwnFood() {
        Meal notOwnMeal = repository.get(USER_MEAL_1.getId(), ADMIN_ID);
        assertNull(notOwnMeal);
    }

    @Test
    public void get() {
        Meal userMeal1 = repository.get(USER_MEAL_1.getId(), USER_ID);
        assertThat(userMeal1).isEqualToComparingFieldByField(USER_MEAL_1);
    }

    @Test
    public void getAll() {
        List<Meal> adminMeals = repository.getAll(ADMIN_ID);
        assertThat(adminMeals).usingFieldByFieldElementComparator().isEqualTo(Arrays.asList(ADMIN_MEAL_2, ADMIN_MEAL_1));
    }

    @Test
    public void getBetween() {
        List<Meal> filteredMeals = repository.getBetween(LocalDateTime.of(2019, Month.MAY, 30, 19, 0),
                LocalDateTime.of(2019, Month.MAY, 30, 21, 0),
                USER_ID);
        assertThat(filteredMeals).usingFieldByFieldElementComparator().isEqualTo(Arrays.asList(USER_MEAL_3));
    }
}