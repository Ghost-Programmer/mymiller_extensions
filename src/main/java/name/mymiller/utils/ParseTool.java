package name.mymiller.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ParseTool {
    public static Long parseLong(Object obj,Long value) {
        return obj != null? Long.parseLong(obj.toString()):value;
    }

    public static String parseString(Object obj,String value) {
        return obj != null? obj.toString():value;
    }

    public static Integer parseInteger(Object obj,Integer value) {
        return obj != null? Integer.parseInt(obj.toString()):value;
    }

    public static Double parseDouble(Object obj,Double value) {
        return obj != null? Double.parseDouble(obj.toString()):value;
    }

    public static ZonedDateTime parseZonedDateTime(Object obj, ZonedDateTime value) {
        if(obj == null) {
            return value;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss");
        return ZonedDateTime.parse(obj.toString(), dateTimeFormatter.withZone(ZoneId.of("UTC")));
    }

    public static LocalDate parseLocalDate(Object obj, LocalDate value) {
        if(obj == null) {
            return value;
        }
        return LocalDateTime.parse(obj.toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss.SSS")).toLocalDate();
    }
}
