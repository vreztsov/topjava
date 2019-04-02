package ru.javawebinar.topjava.util;

import org.springframework.format.FormatterRegistrar;
import org.springframework.format.FormatterRegistry;

public class CustomFormatterRegistrar implements FormatterRegistrar {

    private String datePattern;
    private String timePattern;

    public CustomFormatterRegistrar(
            String datePattern,
            String timePattern) {
        this.datePattern = datePattern;
        this.timePattern = timePattern;
    }

    @Override
    public void registerFormatters(FormatterRegistry aRegistry) {
        aRegistry.addFormatter(new LocalDateFormatter(datePattern));
        aRegistry.addFormatter(new LocalTimeFormatter(timePattern));
    }
}
