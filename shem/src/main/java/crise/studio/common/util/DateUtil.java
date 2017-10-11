package crise.studio.common.util;

import java.time.LocalDateTime;
import java.util.Objects;

public class DateUtil {

    private static DateUtil dateUtil = null;

    private DateUtil() {
    }

    public static DateUtil getInstance() {
        if (Objects.isNull(dateUtil)) {
            return new DateUtil();
        }
        else return dateUtil;
    }
    
    public String localDateTimeToSimpleString(LocalDateTime localDateTime){
        String dateStr = localDateTime.toString();
        return dateStr.substring(0, 10);
    }

}
