package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

/**
 * Created by Viktor on 11.02.2019.
 */
public interface MealStorageInterface {

    List<Meal> getAll();

    void delete(int id);

    Meal get(Integer id);

    Meal save(Meal meal);
}
