package facebook;

import facebook4j.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import socialmedia.NotSupportedException;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("regular")
class FacebookUserTest {

    private FacebookUser user;

    @BeforeEach
    void init() {
        user = new FacebookUser(FacebookUser.UserType.USER);
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
        User.AgeRange age = new User.AgeRange() {
            @Override
            public Integer getMin() {
                return 18;
            }

            @Override
            public Integer getMax() {
                return 21;
            }
        };
        user.setAge(age);
        assertEquals(18, user.getAgeRange().getMin().intValue());
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

    @Test
    @DisplayName("should set and get a simple birthdate")
    void testSimpleBirthDateAssignment() {
        LocalDate date = LocalDate.now();
        user.setBirthday(date);
        assertEquals(date, user.getBirthday().getDate());
        assertEquals(FacebookBirthDateUtil.DateType.FULL, user.getBirthday().getType());
    }

    @Test
    @DisplayName("should throw when getting or setting username")
    void testUsername() {
        assertThrows(NotSupportedException.class, () -> user.setUsername("name"));
        assertThrows(NotSupportedException.class, () -> user.getUsername());

    }
}