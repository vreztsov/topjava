package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
public class MealJspController {

    @Autowired
    private MealService mealService;

    @GetMapping
    public String all(Model model) {
        model.addAttribute("meals", MealsUtil.getWithExcess(mealService.getAll(SecurityUtil.authUserId()),
                SecurityUtil.authUserCaloriesPerDay()));
        return "meals";
    }

    @PostMapping("/filter")
    public String getFiltered(HttpServletRequest request) {
        request.setAttribute("meals", MealsUtil.getFilteredWithExcess(
                mealService.getBetweenDates(parseLocalDate(request.getParameter("startDate")), parseLocalDate(request.getParameter("endDate")), SecurityUtil.authUserId()),
                SecurityUtil.authUserCaloriesPerDay(), parseLocalTime(request.getParameter("startTime")), parseLocalTime(request.getParameter("endTime"))));
        return "meals";
    }

    @GetMapping("/update")
    public String updateJsp(@RequestParam int id, Model model) {
        Meal meal = mealService.get(id, SecurityUtil.authUserId());
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/create")
    public String createJsp(Model model) {
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
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
            mealService.create(meal, SecurityUtil.authUserId());
        else {
            meal.setId(getId(request));
            mealService.update(meal, SecurityUtil.authUserId());
        }
        return "redirect:/meals";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") int id) {
        mealService.delete(id, SecurityUtil.authUserId());
        return "redirect:/meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}