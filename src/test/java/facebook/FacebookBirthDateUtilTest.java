package facebook;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@Tag("regular")
class FacebookBirthDateUtilTest {

    @BeforeAll
    static void beforeAll() {
       new FacebookBirthDateUtil();
    }

    @Test
    @DisplayName("should return the type of a full facebook date")
    void testGetFullDateType() {
        String date = "02/21/1991";
        FacebookBirthDateUtil.DateType type = FacebookBirthDateUtil.getDateType(date);
        assertEquals(FacebookBirthDateUtil.DateType.FULL, type);
    }

    @Test
    @DisplayName("should return the type of facebook date with only year")
    void testGetYearDateType() {
        String date = "2007";
        FacebookBirthDateUtil.DateType type = FacebookBirthDateUtil.getDateType(date);
        assertEquals(FacebookBirthDateUtil.DateType.YEAR, type);
    }

    @Test
    @DisplayName("should return the type of a facebook date with no year")
    void testGetNoYearDateType() {
        String date = "08/12";
        FacebookBirthDateUtil.DateType type = FacebookBirthDateUtil.getDateType(date);
        assertEquals(FacebookBirthDateUtil.DateType.MONTHDAY, type);
    }

    @Test
    @DisplayName("should return INVALID when date is null or empty")
    void testGetInvalidDateType() {
        assertEquals(FacebookBirthDateUtil.DateType.INVALID, FacebookBirthDateUtil.getDateType(null));
        assertEquals(FacebookBirthDateUtil.DateType.INVALID, FacebookBirthDateUtil.getDateType(""));
    }

    @Test
    @DisplayName("should return a facebook birthdate of full facebook date")
    void testGetFullFormattedDate() {
        String date = "08/21/1991";
        FacebookBirthDateUtil.FacebookBirthDate formatted = FacebookBirthDateUtil.formatDate(date);
        LocalDate d = formatted.getDate();
        assertEquals(formatted.getType(), FacebookBirthDateUtil.DateType.FULL);
        assertEquals(8, d.getMonthValue());
        assertEquals(21, d.getDayOfMonth());
        assertEquals(1991, d.getYear());
    }

    @Test
    @DisplayName("should return a Facebook birthdate of facebook date with only year")
    void testGetYearFormattedDate() {
        String date = "1967";
        FacebookBirthDateUtil.FacebookBirthDate formatted = FacebookBirthDateUtil.formatDate(date);
        assertEquals(1967, formatted.getDate().getYear());
        assertEquals(FacebookBirthDateUtil.DateType.YEAR, formatted.getType());
    }

    @Test
    @DisplayName("should return a facebook birthdate, lacking year")
    void testGetMonthDayFormattedDate() {
        String date = "10/08";
        FacebookBirthDateUtil.FacebookBirthDate formatted = FacebookBirthDateUtil.formatDate(date);
        assertEquals(10, formatted.getDate().getMonthValue());
        assertEquals(8, formatted.getDate().getDayOfMonth());
        assertEquals(FacebookBirthDateUtil.DateType.MONTHDAY, formatted.getType());
    }

    @Test
    @DisplayName("should handle null gracefully")
    void testInvalidInput() {
        FacebookBirthDateUtil.FacebookBirthDate birthDate = FacebookBirthDateUtil.formatDate(null);
        assertNull(birthDate);
    }
}