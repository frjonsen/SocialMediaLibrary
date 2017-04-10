package facebook;

import facebook.mocks.FacebookLibraryMock;
import facebook4j.FacebookException;
import facebook4j.IdNameEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FacebookAPIImplTest {
    private FacebookAPI facebook;

    @BeforeEach
    void init() throws FacebookException, MalformedURLException, ParseException {
        this.facebook = new FacebookAPIImpl(FacebookLibraryMock.getFacebookMock());
    }

    @Test
    void testFindExistingHashTags() {
        List<String> tags = new ArrayList<>();
        tags.add("message");
        tags.add("hashtags");
        String msg = "A #message with #hashtags";
        List<String> deducedTags = FacebookAPIImpl.getHashTags(msg);
        assertTrue(tags.containsAll(deducedTags));
        assertEquals(tags.size(), deducedTags.size());
    }

    @Test
    void testFindEmptyHashTags() {
        String msg = "A message with weird hashtag # hello";
        List<String> tags = FacebookAPIImpl.getHashTags(msg);
        assertEquals(0, tags.size());
    }

    @Test
    void testGetFullValidUser() {
        assertNotNull(this.facebook);
        FacebookUser user = facebook.getUser("56726489657236574");
        assertEquals("56726489657236574", user.getId());
        assertEquals("Man", user.getGender());
        assertEquals("some name", user.getName());
        assertEquals("someusername", user.getUsername());
        assertNotNull(user.getAgeRange());
        assertNull(user.getAgeRange().getMax());
        Integer age = user.getAgeRange().getMin();
        assertEquals(21, age.intValue());
        assertEquals("A very short biography", user.getBiography());
        assertEquals(1986, user.getBirthday().getDate().getYear());
        assertEquals(2, user.getBirthday().getDate().getDayOfMonth());
        assertEquals(FacebookBirthDateUtil.DateType.FULL, user.getBirthday().getType());
        assertEquals("atown", user.getCity());
        assertEquals("email@example.com", user.getEmail());
        assertEquals("https://example.com/user", user.getWebsite().toString());
        List<String> languages = new ArrayList<>();
        languages.add("English");
        languages.add("Swedish");
        assertEquals(languages.size(), user.getLanguages().size());
        assertTrue(user.getLanguages().containsAll(languages));
    }

    @Test
    void testGetFullValidPost() {
        String id = "10202360904079395_10208824524985878";
        assertNotNull(this.facebook);
        FacebookPost post = facebook.getPost(id);

        assertEquals(id, post.getId());
        assertEquals("A regular post message", post.getText());
        ZonedDateTime created = ZonedDateTime.parse("2017-03-18T16:59:49Z");
        assertEquals(created, post.getCreationTime());
        ZonedDateTime updated = ZonedDateTime.parse("2017-03-18T17:00:49Z");
        assertEquals(updated, post.getEditTime());
        assertFalse(post.isHidden());
        assertTrue(post.isPublished());
        assertEquals("https://example.com/post", post.getLink().toString());
        assertEquals("https://example.com/postsource", post.getSource().toString());
        assertNotNull(post.getPlace());
        assertEquals("theobjectid", post.getObjectId());
        assertEquals("theparentid", post.getParentId());
        assertEquals("approved_friend", post.getStatusType());

        FacebookUser first = Mockito.mock(FacebookUser.class);
        Mockito.when(first.getId()).thenReturn("1");
        Mockito.when(first.getName()).thenReturn("friend1");
        FacebookUser second = Mockito.mock(FacebookUser.class);
        Mockito.when(second.getId()).thenReturn("2");
        Mockito.when(second.getName()).thenReturn("friend2");
        List<FacebookUser> with = new ArrayList<>();
        with.add(first);
        with.add(second);
        int withCount = 0;
        for (FacebookUser u : post.getWithTags()) {
            Predicate<FacebookUser> predicate = user -> user.getId().equals(u.getId());
            assertTrue(with.stream().anyMatch(predicate));
            withCount++;
        }
        assertEquals(2, withCount);
        int tagCount = 0;
        for (String t : post.getTags()) {
            assertEquals("sometag", t);
            tagCount++;
        }
        assertEquals(1, tagCount);
        assertEquals("10", post.getProperties().get(0).getText());
    }
}