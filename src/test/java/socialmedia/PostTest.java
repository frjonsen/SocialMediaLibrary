package socialmedia;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
    This whole test class is mostly to add code coverage.
    The only functions with any actual logic is getEditTime
*/
class PostTest {

    Post post = null;

    @BeforeEach
    void init() {
        this.post = new Post() {};
    }

    @Test
    @DisplayName("should set and get post body text unchanged")
    void testText() {
        String text = "Some body text";
        post.setText(text);
        assertEquals(text, post.getText());
    }

    @Test
    @DisplayName("should set and get post creation time unchanged")
    void testCreationTime() {
        ZonedDateTime time = ZonedDateTime.now();
        post.setCreationTime(time);
        assertEquals(time, post.getCreationTime());
    }

    @Test
    @DisplayName("should set and get post edit time unchanged")
    void testEditTime() {
        ZonedDateTime time = ZonedDateTime.now();
        post.setEditTime(time);
        assertEquals(time, post.getEditTime());
    }

    @Test
    @DisplayName("should return creation time when edit is null")
    void testNullEditTime() {
        ZonedDateTime time = ZonedDateTime.now();
        post.setCreationTime(time);
        post.setEditTime(null);
        assertEquals(time, post.getEditTime());
    }

    @Test
    @DisplayName("should set and get id unchanged")
    void testId() {
        String id = "someid";
        post.setId(id);
        assertEquals(id, post.getId());
    }

    @Test
    @DisplayName("should set and get author unchanged")
    void testAuthor() {
        User user = new User() {};
        String name = "somename";
        user.setName(name);
        post.setAuthor(user);
        assertEquals(name, post.getAuthor().getName());
    };

    @Test
    @DisplayName("should set and get share count unchanged")
    void testShareCount() {
        int count = 10;
        post.setSharedCount(count);
        assertEquals(count, post.getSharedCount());
    }

    @Test
    @DisplayName("should set and get post language unchanged")
    void testLanguage() {
        String language = "Swedish";
        post.setLanguage(language);
        assertEquals(language, post.getLanguage());
    }

    @Test
    @DisplayName("should set and get receiving users unchanged")
    void testToUsers() {
        User first = new User() {};
        first.setId("someid");
        User second = new User() {};
        second.setName("somename");
        List<User> expected = new ArrayList<>();
        expected.add(first);
        expected.add(second);
        post.setTo(expected);
        List<User> actual = new ArrayList<User>();
        post.getTo().forEach(actual::add);
        assert(actual.containsAll(expected));
        assert(expected.containsAll(actual));
    }

    @Test
    @DisplayName("should set and get post tags unchanged")
    void testTags() {
        List<String> expected = new ArrayList<>();
        expected.add("first");
        expected.add("second");
        expected.add("third");
        post.setTags(expected);
        List<String> actual = new ArrayList<>();
        post.getTags().forEach(actual::add);
        assert(actual.containsAll(expected));
        assert(expected.containsAll(actual));
    }

    @Test
    @DisplayName("should set and get post permalink unchanged")
    void testPermalink() throws MalformedURLException {
        URL url = new URL("https://example.com/endpoint#resource?param=value");
        post.setPermalink(url);
        assertEquals(url, post.getPermalink());
    }

    @Test
    @DisplayName("should set and get location name unchanged")
    void testLocationName() {
        String location = "Stockholm";
        post.setLocationName(location);
        assertEquals(location, post.getLocationName());
    }
}
