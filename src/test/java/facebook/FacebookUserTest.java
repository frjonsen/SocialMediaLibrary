package facebook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import socialmedia.NotSupportedException;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FacebookUserTest {

    FacebookUser user;

    @BeforeEach
    void init() {
        user = new FacebookUser();
    }

    @Test
    @DisplayName("should set and get gender unchanged")
    void testGender() {
        String gender = "apache";
        user.setGender(gender);
        assertEquals(gender, user.getGender());
    }

    @Test
    @DisplayName("should set and get email unchanged")
    void testEmail() {
        String email = "user@example.com";
        user.setEmail(email);
        assertEquals(email, user.getEmail());
    }

    @Test
    @DisplayName("should set and get city unchanged")
    void testCity() {
        String city = "city";
        user.setCity(city);
        assertEquals(city, user.getCity());
    }

    @Test
    @DisplayName("should set and get language unchanged")
    void testLanguage() {
        ArrayList<String> language = new ArrayList<String>();
        language.add("somelanguage");
        user.setLanguages(language);
        assertEquals(language, user.getLanguages());
    }

    @Test
    @DisplayName("should set and get age unchanged")
    void testAge() {
        int age = 20;
        user.setAge(age);
        assertEquals(age, user.getAge());
    }

    @Test
    @DisplayName("should throw an exception as getFollowingCount is not supported by Facebook")
    void testGetFollowingCount() {
        Throwable exception = assertThrows(NotSupportedException.class, () -> { user.getFollowingCount(); });
        assertEquals("getFollowingCount is not supported for platform \"Facebook\"", exception.toString());
    }

    @Test
    @DisplayName("should throw an exception as setFollowingCount is not supported by Facebook")
    void testSetFollowingCount() {
        Throwable exception = assertThrows(NotSupportedException.class, () -> { user.setFollowingCount(20); });
        assertEquals("setFollowingCount is not supported for platform \"Facebook\"", exception.toString());
    }

    @Test
    @DisplayName("should throw an exception as getUploadCount is not supported by Facebook")
    void testGetUploadCount() {Throwable exception = assertThrows(NotSupportedException.class, () -> { user.getUploadCount(); });
        assertEquals("getUploadCount is not supported for platform \"Facebook\"", exception.toString());

    }

    @Test
    @DisplayName("should throw an exception as setUploadCount is not supported by Facebook")
    void testSetUploadCount() {
        Throwable exception = assertThrows(NotSupportedException.class, () -> { user.setUploadCount(10); });
        assertEquals("setUploadCount is not supported for platform \"Facebook\"", exception.toString());
    }

    @Test
    @DisplayName("should set and get birthday unchanged")
    void testDirectBirthdayAssignment() {
        LocalDate bday = LocalDate.now();
        user.setBirthday(bday);
        assertEquals(bday, user.getBirthday().getDate());
    }

    @Test
    @DisplayName("should correctly parse a birthday as formatted by facebook")
    void testFacebookBirthdayAssignment() {
        user.setBirthday("1991");
        assertEquals(1991, user.getBirthday().getDate().getYear());
        assertEquals(FacebookBirthDateUtil.DateType.YEAR, user.getBirthday().getType());
    }

}