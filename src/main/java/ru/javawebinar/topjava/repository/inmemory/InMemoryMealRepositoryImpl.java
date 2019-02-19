package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(SecurityUtil.getAuthUserId(), meal));
    }

    @Override
    public Meal save(int authUserId, Meal meal) {

        if (meal.isNew()) {
            meal.setUserId(authUserId);
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        return isOwn(authUserId, meal) ? repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal) : null;
    }

    @Override
    public boolean delete(int authUserId, int id) {
        Meal meal = repository.get(id);
        return meal != null && isOwn(authUserId, meal) && repository.remove(id) != null;
    }

    @Override
    public Meal get(int authUserId, int id) {
        Meal meal = repository.get(id);
        return (meal != null && isOwn(authUserId, meal)) ? meal : null;
    }

    @Override
    public List<Meal> getAll(int authUserId) {
        return repository.values().stream()
                .filter(meal -> meal.getUserId() == authUserId)
                .sorted(Comparator.comparing(Meal::getDate, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getAllForPeriod(int authUserId, LocalDate startDate, LocalDate endDate) {
        return getAll(authUserId).stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getDate(), startDate, endDate))
                .collect(Collectors.toList());
    }

    private boolean isOwn(int authUserId, Meal meal) {
        return meal.getUserId() == authUserId;
    }
}

