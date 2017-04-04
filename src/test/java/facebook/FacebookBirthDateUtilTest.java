package facebook;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FacebookBirthDateUtilTest {

    @Test
    @DisplayName("should return the type of a full facebook date")
    void getFullDateType() {
        String date = "02/21/1991";
        FacebookBirthDateUtil.DateType type = FacebookBirthDateUtil.getDateType(date);
        assertEquals(FacebookBirthDateUtil.DateType.FULL, type);
    }

    @Test
    @DisplayName("should return the type of facebook date with only year")
    void getYearDateType() {
        String date = "2007";
        FacebookBirthDateUtil.DateType type = FacebookBirthDateUtil.getDateType(date);
        assertEquals(FacebookBirthDateUtil.DateType.YEAR, type);
    }

    @Test
    @DisplayName("should return the type of a facebook date with no year")
    void getNoYearDateType() {
        String date = "08/12";
        FacebookBirthDateUtil.DateType type = FacebookBirthDateUtil.getDateType(date);
        assertEquals(FacebookBirthDateUtil.DateType.MONTHDAY, type);
    }

    @Test
    @DisplayName("should return INVALID when date is null or empty")
    void getInvalidDateType() {
        assertEquals(FacebookBirthDateUtil.DateType.INVALID, FacebookBirthDateUtil.getDateType(null));
        assertEquals(FacebookBirthDateUtil.DateType.INVALID, FacebookBirthDateUtil.getDateType(""));
    }

    @Test
    @DisplayName("should return a facebook birthdate of full facebook date")
    void getFullFormattedDate() {
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
    void getYearFormattedDate() {
        String date = "1967";
        FacebookBirthDateUtil.FacebookBirthDate formatted = FacebookBirthDateUtil.formatDate(date);
        assertEquals(1967, formatted.getDate().getYear());
        assertEquals(FacebookBirthDateUtil.DateType.YEAR, formatted.getType());
    }

    @Test
    @DisplayName("should return a facebook birthdate, lacking year")
    void getMonthDayFormattedDate() {
        String date = "10/08";
        FacebookBirthDateUtil.FacebookBirthDate formatted = FacebookBirthDateUtil.formatDate(date);
        assertEquals(10, formatted.getDate().getMonthValue());
        assertEquals(8, formatted.getDate().getDayOfMonth());
        assertEquals(FacebookBirthDateUtil.DateType.MONTHDAY, formatted.getType());
    }
}