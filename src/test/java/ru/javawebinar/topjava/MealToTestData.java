package ru.javawebinar.topjava;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.MealTestData.MEALS;
import static ru.javawebinar.topjava.TestUtil.readListFromJsonMvcResult;
import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class MealToTestData {

    public static final List<MealTo> MEALS_TO = MealsUtil.getWithExcess(MEALS, DEFAULT_CALORIES_PER_DAY);

    public static void assertMatch(MealTo actual, MealTo expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<MealTo> actual, Iterable<MealTo> expected) {
        assertThat(actual).usingDefaultElementComparator().isEqualTo(expected);
    }

    public static ResultMatcher contentJson(Iterable<MealTo> expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, MealTo.class), expected);
    }

}