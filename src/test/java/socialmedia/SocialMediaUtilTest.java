package socialmedia;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class SocialMediaUtilTest {
    @Test
    @DisplayName("should return null when time is null")
    void testUTCJavaDateToZonedDateTimeNull() {
        assertNull(SocialMediaUtil.UTCJavaDateToZonedDateTime(null));
    }

    @Test
    @DisplayName("should correctly convert java.util.Date to java.time.ZonedDateTime")
    void testUTCJavaDateToZonedDateTimeValid() throws ParseException {
        Date d = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse("2017-03-18T16:59:49+0000");
        ZonedDateTime time = SocialMediaUtil.UTCJavaDateToZonedDateTime(d);
        assertEquals(2017, time.getYear());
        assertEquals(16, time.getHour());
        assertEquals(time.getZone(), ZoneOffset.UTC);
    }

}