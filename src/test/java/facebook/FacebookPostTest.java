package facebook;

import facebook4j.Category;
import facebook4j.FacebookResponse;
import facebook4j.Place;
import facebook4j.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

@Tag("regular")
class FacebookPostTest {
    private FacebookPost post;

    private List<FacebookUser> createFriendsSample(int count) {
        List<FacebookUser> friends = new ArrayList<>();
        for (int i = 0; i < count; ++i) {
            FacebookUser user = new FacebookUser();
            user.setId("friend" + i + "id");
            user.setName("Friend " + i);
            friends.add(user);
        }
        return friends;
    }

    @BeforeEach
    void setUp() {
        post = new FacebookPost();
    }

    @Test
    @DisplayName("should set and get withTags unchanged")
    void testWithTags() {
        List<FacebookUser> with = createFriendsSample(2);
        List<FacebookUser> clone = createFriendsSample(2);
        post.setWithTags(with);

        for (FacebookUser u : post.getWithTags()) {
            Predicate<FacebookUser> predicate = user -> user.getId().equals(u.getId());
            assertTrue(clone.stream().anyMatch(predicate));
            clone.removeIf(predicate);
        }

        assertEquals(0, clone.size());
    }

    @Test
    @DisplayName("should set and get messageTags unchanged")
    void testMessageTags() {

        List<FacebookUser> tags = createFriendsSample(3);
        List<FacebookUser> clone = createFriendsSample(3);
        post.setMessageTags(tags);

        for (FacebookUser u : post.getMessageTags()) {
            Predicate<FacebookUser> predicate = user -> user.getId().equals(u.getId());
            assertTrue(clone.stream().anyMatch(predicate));
            clone.removeIf(predicate);
        }

        assertEquals(0, clone.size());
    }

    @Test
    @DisplayName("should set and get hidden unchanged")
    void testHidden() {
        post.setHidden(true);
        assertTrue(post.isHidden());
        post.setHidden(false);
        assertFalse(post.isHidden());
    }

    @Test
    @DisplayName("should set and get published unchanged")
    void testPublished() {
        post.setPublished(true);
        assertTrue(post.isPublished());
        post.setPublished(false);
        assertFalse(post.isPublished());
    }

    @Test
    @DisplayName("should set and get link unchanged")
    void testLink() throws MalformedURLException {
        post.setLink(new URL("https://example.com"));
        assertEquals("https://example.com", post.getLink().toString());
    }

    @Test
    @DisplayName("should set and get object id unchanged")
    void testObjectId() {
        post.setObjectId("someid");
        assertEquals("someid", post.getObjectId());
    }

    @Test
    @DisplayName("should set and get parent id unchagned")
    void testParentId() {
        post.setParentId("someid");
        assertEquals("someid", post.getParentId());
    }

    @Test
    @DisplayName("should set and get Place of a post unchanged")
    void testPlace() {
        // Actual contents isn't all that relevant for the test
        Place place = new Place() {
            @Override
            public String getId() {
                return null;
            }

            @Override
            public String getName() {
                return null;
            }

            @Override
            public List<Category> getCategories() {
                return null;
            }

            @Override
            public Location getLocation() {
                return null;
            }

            @Override
            public Metadata getMetadata() {
                return null;
            }
        };
        post.setPlace(place);
        assertEquals(place, post.getPlace());
    }

    @Test
    @DisplayName("should set and get source unchanged")
    void testSource() throws MalformedURLException {
        post.setSource(new URL("https://example.com"));
        assertEquals("https://example.com", post.getSource().toString());
    }

    @Test
    @DisplayName("should set and get status type unchanged")
    void testStatusType() {
        post.setStatusType("approved_friend");
        assertEquals("approved_friend", post.getStatusType());
    }
}