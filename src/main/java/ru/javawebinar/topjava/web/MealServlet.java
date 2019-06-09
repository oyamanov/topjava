package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("MealServlet doGet");

        request.setCharacterEncoding("UTF-8");
        String actionParam = request.getParameter("action");
        String idParam = request.getParameter("mealId");
        if (actionParam == null || actionParam.isEmpty()) {
            List<MealTo> mealTos = MealsUtil.mealsToMealTo(MealsUtil.meals, MealsUtil.CALORIES_PER_DAY);
            request.setAttribute("mealTos", mealTos);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        } else if (actionParam.equals("add")) {
            request.getRequestDispatcher("/add-meal.jsp").forward(request, response);
        } else if (actionParam.equals("edit")) {
            long mealId = Long.parseLong(idParam);
            List<MealTo> mealTos = MealsUtil.mealsToMealTo(MealsUtil.meals, MealsUtil.CALORIES_PER_DAY);
            MealTo mealTo = new MealTo(0, LocalDateTime.now(), "", 0, false);
            for (MealTo m : mealTos) {
                if (m.getId() == mealId) {
                    mealTo = m;
                }
            }
            request.setAttribute("mealTo", mealTo);
            request.getRequestDispatcher("/edit-meal.jsp").forward(request, response);
        } else if (actionParam.equals("delete")) {
            int mealId = Integer.parseInt(idParam);
            List<Meal> meals = MealsUtil.meals;
            meals.removeIf(meal -> meal.getId() == mealId);
            List<MealTo> mealTos = MealsUtil.mealsToMealTo(meals, MealsUtil.CALORIES_PER_DAY);
            request.setAttribute("mealTos", mealTos);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("MealServlet doPost");

        request.setCharacterEncoding("UTF-8");
        String actionParam = request.getParameter("action");
        String dateTimeParam = request.getParameter("datetime");
        String descriptionParam = request.getParameter("description");
        String caloriesParam = request.getParameter("calories");
        if (actionParam != null && actionParam.equals("update")) {
            String idParam = request.getParameter("id");
            long mealId = Long.parseLong(idParam);
            List<Meal> meals = MealsUtil.meals;
            for (int i = 0; i < meals.size(); i++) {
                if (meals.get(i).getId() == mealId) {
                    dateTimeParam = dateTimeParam.replace("T", " ");
                    LocalDateTime dateTime = LocalDateTime.parse(dateTimeParam, TimeUtil.dateTimeFormatter);
                    int calories = Integer.parseInt(caloriesParam);
                    Meal updatedMeal = new Meal(mealId, dateTime, descriptionParam, calories);
                    meals.set(i, updatedMeal);
                }
            }
            response.sendRedirect(request.getContextPath() + "/meals");
        } else if (actionParam != null && actionParam.equals("save-new")) {
            dateTimeParam = dateTimeParam.replace("T", " ");
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeParam, TimeUtil.dateTimeFormatter);
            int calories = Integer.parseInt(caloriesParam);
            List<Meal> meals = MealsUtil.meals;
            Meal newMeal = new Meal(MealsUtil.getNextId(meals), dateTime, descriptionParam, calories);
            meals.add(newMeal);
            response.sendRedirect(request.getContextPath() + "/meals");
        }
    }
}