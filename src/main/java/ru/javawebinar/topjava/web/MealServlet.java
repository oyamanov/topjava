package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private ConfigurableApplicationContext appCtx;
    private MealRestController mealController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealController = appCtx.getBean(MealRestController.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action != null && action.equals("filter")) {
            String fromDateParam = request.getParameter("fromDate");
            String toDateParam = request.getParameter("toDate");
            String fromTimeParam = request.getParameter("fromTime");
            String toTimeParam = request.getParameter("toTime");
            if (fromDateParam != null && !fromDateParam.isEmpty() && toDateParam != null && !toDateParam.isEmpty()) {
                LocalDate fromDate = LocalDate.parse(fromDateParam);
                LocalDate toDate = LocalDate.parse(toDateParam);
                List<MealTo> filteredMeals = MealsUtil.getFilteredWithExcess(mealController.getAllMealsByUserId(SecurityUtil.getAuthUserId()),
                        MealsUtil.DEFAULT_CALORIES_PER_DAY,
                        fromDate,
                        toDate);
                request.setAttribute("meals", filteredMeals);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
            } else if (fromTimeParam != null && !fromTimeParam.isEmpty() && toTimeParam != null && !toTimeParam.isEmpty()) {
                LocalTime fromTime = LocalTime.parse(fromTimeParam);
                LocalTime toTime = LocalTime.parse(toTimeParam);
                List<MealTo> filteredMeals = MealsUtil.getFilteredWithExcess(mealController.getAllMealsByUserId(SecurityUtil.getAuthUserId()),
                        MealsUtil.DEFAULT_CALORIES_PER_DAY,
                        fromTime,
                        toTime);
                request.setAttribute("meals", filteredMeals);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
            }
        } else {
            String id = request.getParameter("id");

            Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories")),
                    SecurityUtil.getAuthUserId());

            log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
            if (meal.isNew())
                mealController.create(meal);
            else
                mealController.update(meal, meal.getId());

            response.sendRedirect("meals");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                mealController.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000, SecurityUtil.getAuthUserId()) :
                        mealController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                request.setAttribute("meals", mealController.getAllByUserId(SecurityUtil.getAuthUserId()));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    @Override
    public void destroy() {
        appCtx.close();
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
