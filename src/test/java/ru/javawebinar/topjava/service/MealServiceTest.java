package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.Util;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void create() {
        Meal created = new Meal(LocalDateTime.now(), "new meal", 444);
        Meal createdDB = service.create(created, USER_ID);
        created.setId(createdDB.getId());
        assertMatch(createdDB, created);
    }

    @Test
    public void delete() {
        service.delete(MEAL_ID, USER_ID);
        assertMatch(
                service.getAll(USER_ID),
                USERS_MEAL_LIST.subList(0, USERS_MEAL_LIST.size() - 1)
        );
    }

    @Test(expected = NotFoundException.class)
    public void deleteVicarious() {
        service.delete(MEAL_ID, ADMIN_ID);
    }

    @Test
    public void get() {
        Meal meal = service.get(MEAL_ID, USER_ID);
        assertMatch(
                meal,
                USERS_MEAL_LIST.get(USERS_MEAL_LIST.size() - 1)
        );
    }

    @Test(expected = NotFoundException.class)
    public void getVicarious() {
        Meal meal = service.get(MEAL_ID, ADMIN_ID);
    }

    @Test
    public void update() {
        Meal updated = getForUpdate();
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL_ID, USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateVicarious() {
        service.update(getForUpdate(), ADMIN_ID);
    }

    @Test
    public void getAll() {
        assertMatch(
                service.getAll(ADMIN_ID),
                ADMINS_MEAL_LIST
        );
    }

    @Test
    public void getBetweenDates() {
        assertMatch(
                service.getBetweenDateTimes(START_DATE, END_DATE, USER_ID),
                USERS_MEAL_LIST.stream()
                        .filter(meal -> Util.isBetween(meal.getDateTime().toLocalDate(), START_DATE.toLocalDate(), END_DATE.toLocalDate()))
                        .collect(Collectors.toList())
        );
    }

    private Meal getForUpdate() {
        Meal meal = new Meal();
        meal.setId(MEAL_ID);
        meal.setDateTime(LocalDateTime.now());
        meal.setDescription("Новая еда");
        meal.setCalories(505);
        return meal;
    }
}