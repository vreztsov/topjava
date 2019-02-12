package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MemoryMealDao;
import ru.javawebinar.topjava.util.MealsUtil;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Viktor on 10.02.2019.
 */
public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final String INSERT_OR_UPDATE = "/mealsFormEdit.jsp";
    private static final String LIST_USER = "/meals.jsp";
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private MealDao mealDao;
    private int caloriesPerDay = 2000;

    @Override
    public void init() throws ServletException {
        mealDao = new MemoryMealDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        action = (action == null) ? "all" : action;
        if (action.equalsIgnoreCase("delete")){
            log.info("delete meal");
            int mealId = Integer.parseInt(request.getParameter("id"));
            mealDao.delete(mealId);
            response.sendRedirect("/topjava/meals");
        } else if (action.equalsIgnoreCase("update")){
            log.info("update meal");
            int mealId = Integer.parseInt(request.getParameter("id"));
            Meal meal = mealDao.get(mealId);
            request.setAttribute("meal", meal);
            forwardTo(INSERT_OR_UPDATE, request, response);
        } else if (action.equalsIgnoreCase("all")){
            log.info("give all meal");
            request.setAttribute("mealList", MealsUtil.getFilteredWithExcess(mealDao.getAll(),
                    LocalTime.MIN, LocalTime.MAX, caloriesPerDay));
            request.setAttribute("dateTimeFormatter", formatter);
            forwardTo(LIST_USER, request, response);
        } else {
            log.info("create meal");
            request.setAttribute("meal", Meal.EMPTY);
            forwardTo(INSERT_OR_UPDATE, request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        log.info("save");
        mealDao.save(new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories"))));
        response.sendRedirect("/topjava/meals");
    }

    private void forwardTo(String jsp, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(jsp).forward(request, response);
    }
}
