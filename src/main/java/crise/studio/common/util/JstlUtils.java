package crise.studio.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JstlUtils {
    public static String localDateTimeISOFormat(LocalDateTime localDateTime) {
        if (localDateTime == null) { return null; }
        return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(localDateTime);
    }

    public static String localDateTimeFormat(LocalDateTime localDateTime) {
        if (localDateTime == null) { return null; }
        return LocalDateTimeUtil.localDateTimeToString(localDateTime, "yyyy-MM-dd HH:mm:ss");
    }
}
