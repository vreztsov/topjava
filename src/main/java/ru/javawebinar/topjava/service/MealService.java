package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface MealService {

    Meal create(int authUserId, Meal meal);

    void delete(int authUserId, int id) throws NotFoundException;

    Meal get(int authUserId, int id) throws NotFoundException;

    void update(int authUserId, Meal meal) throws NotFoundException;

    List<Meal> getAll(int authUserId);

    List<Meal> getAllForPeriod(int authUserId, LocalDate startDate, LocalDate endDate);
}