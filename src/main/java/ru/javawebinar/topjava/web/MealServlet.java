package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.MealsInMemoryCrud;
import ru.javawebinar.topjava.repository.MealsCrudInterface;
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

    private MealsCrudInterface mealsCrud = new MealsInMemoryCrud();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("MealServlet doGet");

        request.setCharacterEncoding("UTF-8");
        String actionParam = request.getParameter("action");
        String idParam = request.getParameter("mealId");

        List<Meal> meals;
        List<MealTo> mealTos;
        if (actionParam == null || actionParam.isEmpty()) {
            meals = mealsCrud.findAll();
            mealTos = MealsUtil.mealsToMealTo(meals, MealsUtil.CALORIES_PER_DAY);
            request.setAttribute("mealTos", mealTos);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
            return;
        }

        long mealId;
        switch (actionParam) {
            case "add":
                Meal emptyMeal = new Meal(0, null, "", 0);
                request.setAttribute("meal", emptyMeal);
                request.getRequestDispatcher("/add-edit-meal.jsp").forward(request, response);
                break;
            case "edit":
                mealId = Long.parseLong(idParam);
                Meal meal = mealsCrud.findById(mealId);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/add-edit-meal.jsp").forward(request, response);
                break;
            case "delete":
                mealId = Long.parseLong(idParam);
                mealsCrud.delete(mealId);
                response.sendRedirect(request.getContextPath() + "/meals");
                break;
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
        dateTimeParam = dateTimeParam.replace("T", " ");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeParam, TimeUtil.dateTimeFormatter);
        int calories = Integer.parseInt(caloriesParam);
        if (actionParam != null && actionParam.equals("update")) {
            String idParam = request.getParameter("id");
            long mealId = Long.parseLong(idParam);
            Meal mealToUpdate = new Meal(mealId, dateTime, descriptionParam, calories);
            mealsCrud.update(mealId, mealToUpdate);
        } else if (actionParam != null && actionParam.equals("save-new")) {
            Meal newMeal = new Meal(0, dateTime, descriptionParam, calories); // Meal with a temporary ID
            mealsCrud.add(newMeal);
        }
        response.sendRedirect(request.getContextPath() + "/meals");
    }
}