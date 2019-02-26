package ru.javawebinar.topjava;

import org.junit.Assert;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {

    private static AtomicInteger atomicInteger = new AtomicInteger(1);
    public static final int MEAL_ID = atomicInteger.intValue();
    public static final LocalDateTime START_DATE = LocalDateTime.of(2015, Month.MAY, 30, 0, 0);
    public static final LocalDateTime END_DATE = LocalDateTime.of(2015, Month.MAY, 30, 23, 59);

    public static final List<Meal> USERS_MEAL_LIST = Stream.of(
            new Meal(atomicInteger.get(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(atomicInteger.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(atomicInteger.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(atomicInteger.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 500),
            new Meal(atomicInteger.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 1000),
            new Meal(atomicInteger.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510))
            .sorted(Comparator.comparing(Meal::getDateTime).reversed())
            .collect(Collectors.toList());

    public static final List<Meal> ADMINS_MEAL_LIST = Stream.of(
            new Meal(atomicInteger.incrementAndGet(), LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510),
            new Meal(atomicInteger.incrementAndGet(), LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Админ ужин", 1500))
            .sorted(Comparator.comparing(Meal::getDateTime).reversed())
            .collect(Collectors.toList());


    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(List<Meal> actual, List<Meal> expected) {
        Assert.assertEquals(actual.size(), expected.size());
        for (int i = 0; i < actual.size(); i++) {
            assertMatch(actual.get(i), expected.get(i));
        }
    }
}