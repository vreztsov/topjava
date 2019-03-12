package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Transactional
    @Modifying
//    @Query(name = User.DELETE)
    @Query("DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:user_id")
    int delete(@Param("id") int id, @Param("user_id") int userId);

    @Override
    @Transactional
    Meal save(Meal meal);

    @Query("SELECT m FROM Meal m WHERE m.id=:id and m.user.id=:user_id")
    Meal findByIdAndUserId(@Param("id") Integer id, @Param("user_id") Integer userId);

    @Query("SELECT m FROM Meal m WHERE m.user.id=:user_id ORDER BY m.dateTime DESC")
    List<Meal> findByUserId(@Param("user_id") Integer userId);


    @Query("SELECT m FROM Meal m " +
            "WHERE m.user.id=:user_id AND m.dateTime BETWEEN :startDate AND :endDate ORDER BY m.dateTime DESC")
    List<Meal> findByDateTimeBetweenAndUserId(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("user_id") int userId);


    @Query("SELECT m FROM Meal m LEFT JOIN FETCH m.user WHERE m.id =:id and m.user.id =:user_id")
    Meal findByIdAndFetchUserEagerly(@Param("id") Integer id, @Param("user_id") Integer userId);
}
