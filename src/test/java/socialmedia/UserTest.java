package socialmedia;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private User user;

    @BeforeEach
    void init() {
        user = new User() {};
    }

    @Test
    @DisplayName("should set and get name unchanged")
    void testName() {
        String name = "somename";
        user.setName(name);
        assertEquals(name, user.getName());
    }

    @Test
    @DisplayName("should set and get id unchanged")
    void testId() {
        String id = "someid";
        user.setId(id);
        assertEquals(id, user.getId());
    }

    @Test
    @DisplayName("should set and get username unchanged")
    void testUsername() {
        String username = "someusername";
        user.setUsername(username);
        assertEquals(username, user.getUsername());
    }

    @Test
    @DisplayName("should set and get website unchanged")
    void testWebsite() throws MalformedURLException {
        URL website = new URL("https://example.com/someuser");
        user.setWebsite(website);
        assertEquals(website, user.getWebsite());
    }

    @Test
    @DisplayName("should set and get biography unchanged")
    void testBiography() {
        String bio = "A very short biography";
        user.setBiography(bio);
        assertEquals(bio, user.getBiography());
    }

    @Test
    @DisplayName("should set and get upload count unchanged")
    void testUploadCount() {
        int count = 10;
        user.setUploadCount(count);
        assertEquals(count, user.getUploadCount());
    }

    @Test
    @DisplayName("should set and get following count unchanged")
    void testFollowingCount() {
        int count = 10;
        user.setFollowingCount(count);
        assertEquals(count, user.getFollowingCount());
    }

    @Test
    @DisplayName("should set and get followers count unchanged")
    void testFollowersCount() {
        int count = 10;
        user.setFollowersCount(count);
        assertEquals(count, user.getFollowersCount());
    }

}