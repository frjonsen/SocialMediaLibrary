package socialmedia;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

public class SocialMediaUtil {
    /**
     * Converts a java.util.Date to a ZonedDateTime.
     * As java.util.Date lacks a timezone, this function
     * assumes the timezone is UTC. For any other timezone,
     * the result will be incorrect
     * @param date Date to convert
     * @return ZonedDateTime with same date and time
     */
    public static ZonedDateTime UTCJavaDateToZonedDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return ZonedDateTime.ofInstant(date.toInstant(), ZoneOffset.UTC);
    }
}
