package facebook;

import facebook.mocks.FacebookLibraryMock;
import facebook4j.FacebookException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import socialmedia.NotSupportedException;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import static org.junit.jupiter.api.Assertions.*;

class FacebookAPIImplTest {
    private FacebookAPI facebook;

    private static boolean verifyFullUser(FacebookUser user) {
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
        return true;
    }

    @BeforeEach
    void init() throws FacebookException, MalformedURLException, ParseException {
        this.facebook = new FacebookAPIImpl(FacebookLibraryMock.getFacebookMock());
    }

    @Test
    @DisplayName("should return a full user")
    void testGetFullValidUser() {
        assertNotNull(this.facebook);
        FacebookUser user = facebook.getUser("56726489657236574");
        assertTrue(verifyFullUser(user));
    }

    @Test
    @DisplayName("should return a full user using 'me' as id")
    void testgetFullMeUser() {
        assertNotNull(this.facebook);
        FacebookUser user = facebook.getUser("me");
        assertTrue(verifyFullUser(user));
    }

    @Test
    @DisplayName("should return null for non-existant post id")
    void testNonExistentGetUser() {
        assertNotNull(this.facebook);
        assertNull(this.facebook.getUser("noexist"));
    }


    @Test
    @DisplayName("should return a full post")
    void testGetFullValidPost() {
        String id = "10202360904079395_10208824524985878";
        assertNotNull(this.facebook);
        FacebookPost post = facebook.getPost(id);

        assertEquals(id, post.getId());
        assertEquals("A regular post #message", post.getText());
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
            assertEquals("message", t);
            tagCount++;
        }
        assertEquals(1, tagCount);
        assertEquals("10", post.getProperties().get(0).getText());
    }

    @Test
    @DisplayName("should return null for non-existant post id")
    void testNonExistentGetPost() {
        assertNotNull(this.facebook);
        assertNull(this.facebook.getPost("noexist"));
    }

    @Test
    @DisplayName("should search for and find three users")
    void testSearchUsers() {
        List<FacebookUser> results = facebook.searchUsers("User");
        assertEquals(3, results.size());
    }

    @Test
    @DisplayName("should get the profile picture of a user")
    void testGetProfilePicture() {
        URL url = facebook.getProfilePicture("56726489657236574");
        assertEquals("https://scontent.xx.fbcdn.net/v/t31.0-1/otheruserpicture", url.toString());
    }

    @Test
    @DisplayName("should get the profile picture of self")
    void testGetOwnProfilePicture() {
        URL url = facebook.getProfilePicture("me");
        assertEquals("https://scontent.xx.fbcdn.net/v/t31.0-1/selfpicture", url.toString());
    }

    @Test
    @DisplayName("should fail to get profile picture of non-existent user")
    void testGetInvalidProfilePicture() {
        assertNull(facebook.getProfilePicture("nonexistent"));
    }

    @Test
    @DisplayName("should successfully like a post")
    void testLikePost() {
        assertTrue(facebook.likePost("10202360904079395_10208824524985878"));
    }

    @Test
    @DisplayName("should fail to like a non-existent post")
    void testLikeInvalidPost() {
        assertFalse(facebook.likePost("nonexistent"));
    }

    @Test
    @DisplayName("should successfully like a post")
    void testUnlikePost() {
        assertTrue(facebook.unlikePost("10202360904079395_10208824524985878"));
    }

    @Test
    @DisplayName("should fail to like a non-existent post")
    void testUnlikeInvalidPost() {
        assertFalse(facebook.unlikePost("nonexistent"));
    }

    @Test
    @DisplayName("should throw an error when trying to search for posts")
    void testSearchPosts() {
        assertThrows(NotSupportedException.class, () -> facebook.searchPost("keyword"));
    }

    @Test
    @DisplayName("should get the feed of self")
    void testGetSelfFeed() {
        List<FacebookPost> feed = facebook.getPostFeed("me");
        assertEquals(3, feed.size());
    }

    @Test
    @DisplayName("should get the feed of another, existing, user")
    void testGetOtherFeed() {
        List<FacebookPost> feed = facebook.getPostFeed("56726489657236574");
        assertEquals(2, feed.size());
    }

    @Test
    @DisplayName("should throw an error when trying to get followers")
    void testGetFollowers() {
        assertThrows(NotSupportedException.class, () -> facebook.getFollowers("someid"));
    }

    @Test
    @DisplayName("should throw an error when trying to get following")
    void testGetFollowing() {
        assertThrows(NotSupportedException.class, () -> facebook.getFollowing("someid"));
    }

    @Test
    @DisplayName("should throw an error when trying to follow")
    void testFollow() {
        assertThrows(NotSupportedException.class, () -> facebook.follow("someid"));
    }

    @Test
    @DisplayName("should throw an error when trying to unfollow")
    void testUnfollow() {
        assertThrows(NotSupportedException.class, () -> facebook.unfollow("someid"));
    }
}