package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealServiceImpl implements MealService {

    private MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal create(int authUserId, Meal meal) {
        return repository.save(authUserId, meal);
    }

    @Override
    public void delete(int authUserId, int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(authUserId, id), id);
    }

    @Override
    public Meal get(int authUserId, int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(authUserId, id), id);
    }

    @Override
    public void update(int authUserId, Meal meal) throws NotFoundException {
        checkNotFoundWithId(repository.save(authUserId, meal), meal.getId());
    }

    @Override
    public List<Meal> getAll(int authUserId) {
        return repository.getAll(authUserId);
    }

    @Override
    public List<Meal> getAllForPeriod(int authUserId, LocalDate startDate, LocalDate endDate) {
        return repository.getAllForPeriod(authUserId, startDate, endDate);
    }
}