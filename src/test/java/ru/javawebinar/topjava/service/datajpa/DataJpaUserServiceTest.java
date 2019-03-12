package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles({Profiles.DATAJPA})
public class DataJpaUserServiceTest extends AbstractUserServiceTest {
    @Test
    public void getWithMealById() throws Exception {
        User user = service.getWithMeals(ADMIN_ID);
        assertMatch(user, ADMIN_WITH_MEAL);
        assertMatchMeal(user.getMeals(), ADMIN_WITH_MEAL.getMeals());
    }
}
