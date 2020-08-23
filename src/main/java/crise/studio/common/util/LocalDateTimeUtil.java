package crise.studio.common.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.Locale;

public class LocalDateTimeUtil {

	private LocalDateTimeUtil() { }

    private static final String YEAR_MONTH_FORMAT = "yyyyMM";
    private static final String DAY_HOUR_FORMAT = "ddHH";
    private static final String YEAR_MONTH_DAY_FORMAT = YEAR_MONTH_FORMAT + "dd";

    private static final String YEAR_MONTH_DAY_FORMAT_WITH_UNDERBAR = "yyyy-MM-dd";
    private static final String DEFAULT_WEEK_IN_YEAR_FORMAT = "YYYY-ww";

    public static LocalDateTime kstChangeLocalDateTime(String kst) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        return LocalDateTime.parse(kst, dateTimeFormatter);
    }

    public static LocalDateTime localDateTimeChangeLocalDateTime(String localDateTime) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return LocalDateTime.parse(localDateTime, dateTimeFormatter);
    }

    public static boolean localDateTimeBetweenCheck(LocalDateTime startTime, LocalDateTime endTime, LocalDateTime checkTime) {

        if (startTime == null || endTime == null || checkTime == null) {
            return false;
        }

        if (startTime.until(checkTime, ChronoUnit.SECONDS) < 0) {
            return false;
        }
        if (endTime.until(checkTime, ChronoUnit.SECONDS) > 0) {
            return false;
        }
        return true;
    }

    public static String localDateTimeToString(LocalDateTime localDate, String dateFormat) {
        return localDate.format(DateTimeFormatter.ofPattern(dateFormat));
    }
    public static Date convertLocalDateTimeTODate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime convertDateTOLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalDate convertStringToLocalDate(String date) {
        return convertStringToLocalDate(date, "yyyy-MM-dd");
    }

    public static LocalDate convertStringToLocalDate(String date, String opDateFormat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(opDateFormat);
        formatter = formatter.withLocale(Locale.KOREA);
        return LocalDate.parse(date, formatter);
    }

    public static String localDateToString(LocalDate localDate, String dateFormat) {
        return localDate.format(DateTimeFormatter.ofPattern(dateFormat));
    }

    public static String localDateToString(LocalDate localDate) {
        return localDateToString(localDate, YEAR_MONTH_DAY_FORMAT_WITH_UNDERBAR);
    }

    public static String getYYYYMMStyleNowDate() {
        return localDateTimeToString(LocalDateTime.now(), YEAR_MONTH_FORMAT);
    }

    public static String getDDHHStyleNowDate() {
        return localDateTimeToString(LocalDateTime.now(), DAY_HOUR_FORMAT);
    }

    public static String getYYYYMMDDStyleNowDate() {
        return localDateTimeToString(LocalDateTime.now(), YEAR_MONTH_DAY_FORMAT);
    }

    public static LocalDate dateformatWeekInYearToLocalDate(String weekInYear, String dateFormat) {
        return LocalDate.parse(
            weekInYear,
            new DateTimeFormatterBuilder().appendPattern(dateFormat).parseDefaulting(WeekFields.ISO.dayOfWeek(), 1)
                .toFormatter()
        );
    }

    public static LocalDate dateformatWeekInYearToLocalDate(String weekInYear) {
        return dateformatWeekInYearToLocalDate(weekInYear, DEFAULT_WEEK_IN_YEAR_FORMAT);
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        if (localDateTime == null) { return null; }
        return java.util.Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }


}
