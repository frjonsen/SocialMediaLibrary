package twitter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("regular")
class TwitterUserTest {
    TwitterUser user;

    @BeforeEach
    void init() { user = new TwitterUser(); }

    @Test
    @DisplayName("should set and get email unchanged")
    void testEmail() {
        String email = "user@example.com";
        user.setEmail(email);
        assertEquals(email, user.getEmail());
    }

    @Test
    @DisplayName("should set and get location unchanged")
    void testLocation() {
        String location = "Exampleland";
        user.setLocation(location);
        assertEquals(location, user.getLocation());
    }

    @Test
    @DisplayName("should set and get language unchanged")
    void testLanguage() {
        String language = "testifian";
        user.setLanguage(language);
        assertEquals(language, user.getLanguage());
    }

}