package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int USER_WITH_NO_MEALS_ID = START_SEQ + 2;

    public static final User USER = new User(USER_ID, "User", "user@yandex.ru", "password", Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN);
    public static final User ADMIN_WITH_MEAL = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN);
    public static final User USER_WITH_NO_MEALS = new User(USER_WITH_NO_MEALS_ID, "User With No Meals", "user0@yandex.ru", "password", Role.ROLE_USER);

    static {
        ADMIN_WITH_MEAL.setMeals(List.of(
                new Meal(100009, LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510),
                new Meal(100010, LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Админ ужин", 1500)
        ));
    }

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered", "roles", "meals");
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("registered", "roles", "meals").isEqualTo(expected);
    }

    public static void assertMatchMeal(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("user").isEqualTo(expected);
    }
}
