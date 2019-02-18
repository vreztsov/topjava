package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.util.List;

public interface MealRepository {
    Meal save(int authUserId, Meal meal);

    boolean delete(int authUserId, int id);

    Meal get(int authUserId, int id);

    List<Meal> getAll(int authUserId);

    List<Meal> getAllForPeriod(int authUserId, LocalDate startDate, LocalDate endDate);
}
