package ru.javawebinar.topjava.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NamedQueries({
        @NamedQuery(name = Meal.DELETE, query = "DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:user_id"),
        @NamedQuery(name = Meal.GET_BY_ID_AND_USER_ID, query = "SELECT m FROM Meal m WHERE m.id=:id AND m.user.id=:user_id"),
        @NamedQuery(name = Meal.GET_ALL_BY_USER_ID_SORTED, query = "SELECT m FROM Meal m WHERE m.user.id=:user_id ORDER BY m.dateTime DESC"),
        @NamedQuery(name = Meal.GET_BETWEEN_DATETIMES, query = "SELECT m FROM Meal m WHERE m.user.id=:user_id AND m.dateTime BETWEEN :start_datetime AND :end_datetime ORDER BY m.dateTime DESC")
})
@Entity
@Table(name = "meals", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date_time"}, name = "meals_unique_user_datetime_idx")})
public class Meal extends AbstractBaseEntity {

    public static final String DELETE = "Meal.delete";
    public static final String GET_BY_ID_AND_USER_ID = "Meal.getByIdAndUserId";
    public static final String GET_ALL_BY_USER_ID_SORTED = "Meal.getAllByUserIdSorted";
    public static final String GET_BETWEEN_DATETIMES = "Meal.getBetweenDateTimes";

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "calories", nullable = false)
    private int calories;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        this(id, dateTime, description, calories, null);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories, User user) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.user = user;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
