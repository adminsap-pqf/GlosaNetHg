package org.mx.modelo.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

public class UtilJavaTime {

    public final static int TIME_UNIT_DAYS = 0;
    public final static int TIME_UNIT_WEEK = 1;
    public final static int TIME_UNIT_MONTH = 2;
    public final static int TIME_UNIT_YEAR = 3;

    /**
     * Get full weeks between two dates
     * source: https://stackoverflow.com/questions/9963147/get-the-number-of-weeks-between-two-dates
     *
     * @param date1 initial date
     * @param date2 final date
     * @param date2 final date
     * @return number of per
     */
    public static long getDurationBetweenCalendarDates(Calendar date1, Calendar date2, int timeUnit) {

        Instant d1i = Instant.ofEpochMilli(date1.getTimeInMillis());
        Instant d2i = Instant.ofEpochMilli(date2.getTimeInMillis());

        LocalDateTime startDate = LocalDateTime.ofInstant(d1i, ZoneId.systemDefault());
        LocalDateTime endDate = LocalDateTime.ofInstant(d2i, ZoneId.systemDefault());

        return getDurationBetweenDates(startDate, endDate, timeUnit);
    }

    public static long getDurationBetweenDates(LocalDateTime startDate, LocalDateTime endDate, int timeUnit) {

        switch(timeUnit){
            case TIME_UNIT_DAYS: return ChronoUnit.DAYS.between(startDate, endDate);
            case TIME_UNIT_WEEK: return ChronoUnit.WEEKS.between(startDate, endDate);
            case TIME_UNIT_MONTH: return ChronoUnit.MONTHS.between(startDate, endDate);
            case TIME_UNIT_YEAR: return ChronoUnit.YEARS.between(startDate, endDate);
            default:
                throw new IllegalStateException("Unexpected value: " + timeUnit);
        }
    }

}
