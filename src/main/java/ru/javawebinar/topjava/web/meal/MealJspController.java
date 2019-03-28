package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping("/meals")
public class MealJspController extends AbstractMealController{

    @GetMapping
    public String all(Model model) {
        model.addAttribute("meals", getAll());
        return "meals";
    }

    @PostMapping("/filter")
    public String getFiltered(HttpServletRequest request) {
        request.setAttribute("meals", getBetween(parseLocalDate(request.getParameter("startDate")),
                parseLocalTime(request.getParameter("startTime")),
                parseLocalDate(request.getParameter("endDate")),
                parseLocalTime(request.getParameter("endTime"))));
        return "meals";
    }

    @GetMapping("/mealForm")
    public String showMealForm(@RequestParam Integer id, Model model) {
        Meal meal = (id == null) ? new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) : get(id);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @PostMapping("/save")
    public String saveCustomer(HttpServletRequest request) {
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        if (request.getParameter("id").equals(""))
            create(meal);
        else {
            update(meal, getId(request));
        }
        return "redirect:/meals";
    }

    @GetMapping("/delete")
    public String deleteJsp(@RequestParam("id") int id) {
        delete(id);
        return "redirect:/meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}