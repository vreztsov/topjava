package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MemoryMealStorage;
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
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private MemoryMealStorage storage;

    @Override
    public void init() throws ServletException {
        storage = new MemoryMealStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "all":
                log.info("give all meal");
                request.setAttribute("mealList", MealsUtil.getFilteredWithExcess(storage.getAll(),
                        LocalTime.MIN, LocalTime.MAX, 2000));
                request.setAttribute("dateTimeFormatter", formatter);
                forwardTo("/meals.jsp", request, response);
                break;
            case "update":
                log.info("update meal");
                request.setAttribute("meal", storage.get(Integer.valueOf(request.getParameter("id"))));
                forwardTo("/mealsFormEdit.jsp", request, response);
                break;
            case "create":
                log.info("create meal");
                request.setAttribute("meal", new Meal()); // FIXME времянка
                forwardTo("/mealsFormEdit.jsp", request, response);
                break;
            case "delete":
                log.info("delete meal");
                storage.delete(Integer.valueOf(request.getParameter("id")));
                response.sendRedirect("meals.jsp");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        log.info("save");
        storage.save(new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories"))));
        response.sendRedirect("meals.jsp");
    }

    private void forwardTo(String jsp, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(jsp).forward(request, response);
    }
}
