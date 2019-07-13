package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    int deleteByIdAndUser(int id, User user);

    Meal getByIdAndUser(int id, User user);

    List<Meal> getAllByUser(User user);

    List<Meal> getAllByUserAndDateTimeBetween(User user, LocalDateTime startDate, LocalDateTime endDate);
}
