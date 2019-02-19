package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

@Controller
public class MealRestController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        return service.create(SecurityUtil.getAuthUserId(), meal);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(SecurityUtil.getAuthUserId(), id);
    }

    public Meal get(int id) throws NotFoundException {
        log.info("get {}", id);
        return service.get(SecurityUtil.getAuthUserId(), id);
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(SecurityUtil.getAuthUserId(), meal);
    }

    public List<MealTo> getAll() {
        log.info("getAll");
        List<MealTo> list = MealsUtil.getWithExcess(
                service.getAll(SecurityUtil.getAuthUserId()),
                SecurityUtil.authUserCaloriesPerDay());
        logList(list);
        return list;
    }

    public List<MealTo> getAllWithFilter(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        log.info("getAllWithFilter startDate = {}, startTime = {}, endDate = {}, endTime = {}", startDate, startTime, endDate, endTime);
        List<MealTo> list = MealsUtil.getFilteredWithExcess(service.getAllForPeriod(
                SecurityUtil.getAuthUserId(),
                startDate == null ? LocalDate.MIN : startDate,
                endDate == null ? LocalDate.MAX : endDate),
                SecurityUtil.authUserCaloriesPerDay(),
                startTime == null ? LocalTime.MIN : startTime,
                endTime == null ? LocalTime.MAX : endTime);
        logList(list);
        return list;
    }



    private void logList(List<MealTo> list) {
        list.forEach(mealTo -> log.info("listed {}", mealTo));
    }
}