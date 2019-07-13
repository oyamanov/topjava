package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepository implements MealRepository {

    @Autowired
    private CrudMealRepository crudRepository;

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.getId() != null && get(meal.getId(), userId) == null)
            return null;

        User user = new User();
        user.setId(userId);
        meal.setUser(user);
        return crudRepository.save(meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        User user = new User();
        user.setId(userId);
        return crudRepository.deleteByIdAndUser(id, user) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        User user = new User();
        user.setId(userId);
        return crudRepository.getByIdAndUser(id, user);
    }

    @Override
    public List<Meal> getAll(int userId) {
        User user = new User();
        user.setId(userId);
        return crudRepository.getAllByUser(user);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        User user = new User();
        user.setId(userId);
        return crudRepository.getAllByUserAndDateTimeBetween(user, startDate, endDate);
    }
}
