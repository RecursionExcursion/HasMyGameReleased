package com.example.hasmygamereleased.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.function.BiFunction;
import java.util.function.Function;

public class DTFormatter {

    private final static LocalDate START_DATE = LocalDate.of(2000, 1, 1);

    /*
     * Add additional DateTimeFormatter's here
     */
    private final static DateTimeFormatter[] DATE_TIME_FORMATTERS = new DateTimeFormatter[]{
            DateTimeFormatter.ofPattern("MMM dd, yyyy"),
            DateTimeFormatter.ofPattern("MMM d, yyyy"),
            DateTimeFormatter.ofPattern("dd MMM, yyyy")
    };

    public static LocalDate format(String s) {
        return getLocalDateFunction().apply(s);
    }

    private static Function<String, LocalDate> getLocalDateFunction() {
        BiFunction<DateTimeFormatter, String, LocalDate> parseDateOrNull = (f, s) -> {
            try {
                return LocalDate.parse(s, f);
            } catch (DateTimeParseException ignored) {
                return null;
            }
        };

        return dateString -> {
            for (DateTimeFormatter formatter : DATE_TIME_FORMATTERS) {
                LocalDate localDate = parseDateOrNull.apply(formatter, dateString);
                if (localDate != null) return localDate;
            }
            //If formatters are not satisfied, return this date
            return START_DATE;
        };
    }
}
