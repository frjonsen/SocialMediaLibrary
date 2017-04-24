package socialmedia;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

public class SocialMediaUtil {

    private SocialMediaUtil() {}

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

    /**
     * Checks if a string is null or contains only whitespace or linebreaks
     * @param str String to check
     * @return True if string is null or contains only whitespace
     */
    public static boolean isNullOrWhitespace(String str) {
        return str == null || str.trim().isEmpty();
    }
}
