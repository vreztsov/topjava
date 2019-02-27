package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int MEAL_ID = START_SEQ + 2;
    public static final LocalDateTime START_DATE = LocalDateTime.of(2015, Month.MAY, 30, 0, 0);
    public static final LocalDateTime END_DATE = LocalDateTime.of(2015, Month.MAY, 30, 23, 59);

    private static final Meal USER_MEAL_1 = new Meal(MEAL_ID, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
    private static final Meal USER_MEAL_2 = new Meal(MEAL_ID + 1, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
    private static final Meal USER_MEAL_3 = new Meal(MEAL_ID + 2, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
    private static final Meal USER_MEAL_4 = new Meal(MEAL_ID + 3, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 500);
    private static final Meal USER_MEAL_5 = new Meal(MEAL_ID + 4, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 1000);
    private static final Meal USER_MEAL_6 = new Meal(MEAL_ID + 5, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);
    private static final Meal ADMIN_MEAL_1 = new Meal(MEAL_ID + 6, LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510);
    private static final Meal ADMIN_MEAL_2 = new Meal(MEAL_ID + 7, LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Админ ужин", 1500);

    public static final List<Meal> USERS_MEAL_LIST = Stream.of(
            USER_MEAL_1, USER_MEAL_2, USER_MEAL_3, USER_MEAL_4, USER_MEAL_5, USER_MEAL_6)
            .sorted(Comparator.comparing(Meal::getDateTime).reversed())
            .collect(Collectors.toList());

    public static final List<Meal> ADMINS_MEAL_LIST = Stream.of(
            ADMIN_MEAL_1, ADMIN_MEAL_2)
            .sorted(Comparator.comparing(Meal::getDateTime).reversed())
            .collect(Collectors.toList());

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }

    public static Meal getForUpdate() {
        Meal meal = new Meal();
        meal.setId(MEAL_ID);
        meal.setDateTime(LocalDateTime.now());
        meal.setDescription("Новая еда");
        meal.setCalories(505);
        return meal;
    }
}