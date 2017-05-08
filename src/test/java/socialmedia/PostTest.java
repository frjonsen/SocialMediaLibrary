package socialmedia;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static socialmedia.Post.Type.TEXT;

/*
    This whole test class is mostly to add code coverage.
    The only functions with any actual logic is getEditTime
*/

@Tag("regular")
class PostTest {

    private Post<User> post = null;

    @BeforeEach
    void init() {
        this.post = new Post<User>() {};
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
    @DisplayName("should convert a java.util.Date to ZonedDateTime")
    void testSimpleDateConversionCreation() throws ParseException {
        Date d = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse("2017-03-18T16:59:49+0000");
        post.setCreationTime(d);
        assertEquals(2017, post.getCreationTime().getYear());
        assertEquals(16, post.getCreationTime().getHour());
        assertEquals(post.getCreationTime().getZone(), ZoneOffset.UTC);
    }

    @Test
    @DisplayName("should properly handle a null value for creation time")
    void testNullCreatedConversation() {
        Date d = null;
        post.setCreationTime(d);
        assertNull(post.getCreationTime());
    }

    @Test
    @DisplayName("should set and get post edit time unchanged")
    void testEditTime() {
        ZonedDateTime time = ZonedDateTime.now();
        post.setEditTime(time);
        assertEquals(time, post.getEditTime());
    }

    @Test
    @DisplayName("should convert a java.util.Date to ZonedDateTime")
    void testSimpleDateConversionEdit() throws ParseException {
        Date d = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse("2017-03-18T16:59:49+0000");
        post.setCreationTime(d);
        assertEquals(2017, post.getEditTime().getYear());
        assertEquals(16, post.getEditTime().getHour());
        assertEquals(post.getEditTime().getZone(), ZoneOffset.UTC);
    }

    @Test
    @DisplayName("should properly handle a null value for creation time")
    void testNullEditConversation() {
        Date d = null;
        post.setEditTime(d);
        // Creation time must be null too
        assertNull(post.getCreationTime());
        assertNull(post.getEditTime());
    }

    @Test
    @DisplayName("should return creation time when edit is null")
    void testNullZonedEditTime() {
        ZonedDateTime time = ZonedDateTime.now();
        post.setCreationTime(time);
        // Need to set as null separately, as it will otherwise be an ambiguous call
        Date d = null;
        post.setEditTime(d);
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
    @DisplayName("should set and get post type unchanged")
    void testType() {
        Post.Type type = TEXT;
        post.setType(type);
        assertEquals(type, post.getType());
    }
}
