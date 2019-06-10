package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.model.MealsCrud;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("MealServlet doGet");

        request.setCharacterEncoding("UTF-8");
        String actionParam = request.getParameter("action");
        String idParam = request.getParameter("mealId");
        MealsCrud mealsCrud = new MealsCrud();
        if (actionParam == null || actionParam.isEmpty()) {
            List<Meal> meals = mealsCrud.findAll();
            List<MealTo> mealTos = MealsUtil.mealsToMealTo(meals, MealsUtil.CALORIES_PER_DAY);
            request.setAttribute("mealTos", mealTos);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        } else if (actionParam.equals("add")) {
            request.setAttribute("isEdit", false);
            request.getRequestDispatcher("/add-edit-meal.jsp").forward(request, response);
        } else if (actionParam.equals("edit")) {
            List<Meal> meals = mealsCrud.findAll();
            List<MealTo> mealTos = MealsUtil.mealsToMealTo(meals, MealsUtil.CALORIES_PER_DAY);
            MealTo mealTo = new MealTo(0, LocalDateTime.now(), "", 0, false);
            long mealId = Long.parseLong(idParam);
            for (MealTo m : mealTos) {
                if (m.getId() == mealId) {
                    mealTo = m;
                }
            }
            request.setAttribute("mealTo", mealTo);
            request.setAttribute("isEdit", true);
            request.getRequestDispatcher("/add-edit-meal.jsp").forward(request, response);
        } else if (actionParam.equals("delete")) {
            long mealId = Long.parseLong(idParam);
            mealsCrud.delete(mealId);
            List<Meal> meals = new ArrayList<>(MealsUtil.mealsMap.values());
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
        MealsCrud mealsCrud = new MealsCrud();
        if (actionParam != null && actionParam.equals("update")) {
            String idParam = request.getParameter("id");
            long mealId = Long.parseLong(idParam);
            dateTimeParam = dateTimeParam.replace("T", " ");
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeParam, TimeUtil.dateTimeFormatter);
            int calories = Integer.parseInt(caloriesParam);
            mealsCrud.update(mealId, dateTime, descriptionParam, calories);
            response.sendRedirect(request.getContextPath() + "/meals");
        } else if (actionParam != null && actionParam.equals("save-new")) {
            dateTimeParam = dateTimeParam.replace("T", " ");
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeParam, TimeUtil.dateTimeFormatter);
            int calories = Integer.parseInt(caloriesParam);
            mealsCrud.add(dateTime, descriptionParam, calories);
            response.sendRedirect(request.getContextPath() + "/meals");
        }
    }
}