package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Meal {

    private Integer id;

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    public static final Meal EMPTY = new Meal();

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    private Meal() {
        this(null, null, 0);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        this(dateTime, description, calories);
        this.setId(id);
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public boolean isNew(){
        return id == null;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
