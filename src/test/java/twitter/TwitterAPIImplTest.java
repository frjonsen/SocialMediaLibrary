package twitter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import socialmedia.Post;
import socialmedia.User;
import twitter.mocks.TwitterLibraryMock;
import twitter4j.TwitterException;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class TwitterAPIImplTest {
    private TwitterAPI twitter;

    @BeforeEach
    void init() throws TwitterException {
        this.twitter = new TwitterAPIImpl(TwitterLibraryMock.getTwitterMock());
    }

    @Test
    @DisplayName("should create a TwitterAPIImpl through regular constructor")
    void testConstructor() {
        TwitterAPI twitterTest = new TwitterAPIImpl("consumerKey", "consumerSecret", "accessToken", "accessSecret", true);
        assertNotNull(twitterTest);
    }

    @Test
    @DisplayName("should return a full user with correct fields")
    void testGetFullValidUser() throws MalformedURLException {
        assertNotNull(this.twitter);
        TwitterUser user = twitter.getUser(6253282L);
        assertEquals("6253282", user.getId());
        assertEquals("Testington", user.getName());
        assertEquals("TestyMcTest", user.getUsername());
        URL url = new URL("https:://twitter.testington.org/");
        assertEquals(url, user.getWebsite());
        assertEquals("Lorem ipsum testy mctest ipsunator...", user.getBiography());
        assertEquals(10, user.getUploadCount());
        assertEquals(2, user.getFollowingCount());
        assertEquals(2, user.getFollowersCount());
        assertEquals("testify@test.com", user.getEmail());
        assertEquals("Testing Street", user.getLocation());
        assertEquals("Testarian", user.getLanguage());
    }

    @Test
    @DisplayName("should throw exception for non-existant post id")
    void testNonExistantUserID() {
        assertNotNull(this.twitter);
        assertNull(this.twitter.getUser(123546875L));
    }

    @Test
    @DisplayName("should get a TwitterUser through ScreenName")
    void testNonExistantUserScreenName() {
        assertNotNull(this.twitter);
        TwitterUser user = this.twitter.getUser("TestyMcFaulty");
        assertNull(user);
    }

    @Test
    @DisplayName("should get a TwitterUser through ScreenName")
    void testGetUserWithScreenName() {
        assertNotNull(this.twitter);
        TwitterUser user = this.twitter.getUser("TestyMcTest");
        assertEquals("6253282", user.getId());
    }

    @Test
    @DisplayName("should return a full tweet with correct fields")
    void testGetFullValidTweet() throws MalformedURLException{
        assertNotNull(this.twitter);
        TwitterPost tweet = this.twitter.getPost("114749583439036416");
        assertEquals("114749583439036416", tweet.getId());
        assertEquals("Testing everythin at everytime.", tweet.getText());
        Date date = new Date(1491913808000L);
        ZonedDateTime zDate = ZonedDateTime.ofInstant(date.toInstant(), ZoneOffset.UTC);
        assertEquals(zDate, tweet.getCreationTime());
        assertEquals(zDate, tweet.getEditTime());
        assertEquals(Post.Type.UNKNOWN, tweet.getType());
        assertEquals(123, tweet.getSharedCount());

        ArrayList<TwitterUser> users = new ArrayList<>();
        TwitterUser user1 = new TwitterUser();
        user1.setId("114749583439036416");
        user1.setName("Testington");
        user1.setUsername("TestyMcTest");
        users.add(user1);

        ArrayList<String> tags = new ArrayList<>();
        tags.add("hashtag");

        int withCount = 0;
        for (TwitterUser u : tweet.getTo()) {
            Predicate<TwitterUser> predicate = user -> user.getId().equals(u.getId());
            assertTrue(users.stream().anyMatch(predicate));
            withCount++;
        }
        assertEquals(1, withCount);
        assertTrue(Arrays.asList(tags).containsAll(Arrays.asList(tweet.getTags())));
        assertEquals(1L,  Stream.of(tweet.getTo()).count());
        assertEquals(1L,  Stream.of(tweet.getTags()).count());
        assertEquals("6253282", tweet.getAuthor().getId());
        URL link = new URL("https://twitter.com/" + tweet.getAuthor().getUsername() + "/" + tweet.getId());
        assertEquals(link, tweet.getPermalink());
        assertEquals("Testarian", tweet.getLanguage());
        assertEquals(28.385233, tweet.getCoordinate().getLatitude());
        assertEquals(-81.563874, tweet.getCoordinate().getLongitude());
        assertNotNull(tweet.getPlace());
        assertNotNull(tweet.getMediaEntities());
        assertNotNull(tweet.getSymbolEntities());
        assertNotNull(tweet.getUrlEntities());
        assertEquals(1234, tweet.getFavoriteCount());
        assertEquals("TestFriend", tweet.getReplyToScreenName());
        assertEquals(495834390114736416L, tweet.getReplyToUserId());
        assertEquals(903641611474958343L, tweet.getReplyToStatusId());
        assertEquals(false, tweet.isPossiblySensitive());
        assertNull(tweet.getQuotedStatus());
        assertEquals(903641611474958343L, tweet.getQuotedStatusId());
        assertEquals(false, tweet.isRetweet());
        assertEquals(false, tweet.isRetweeted());
        assertEquals(false, tweet.isRetweetedByMe());
        assertEquals(false, tweet.isTruncated());
        assertEquals(true, tweet.isFavorited());

        int withheldCount = 0;
        for (TwitterUser u : tweet.getTo()) {
            Predicate<TwitterUser> predicate = user -> user.getId().equals(u.getId());
            assertTrue(users.stream().anyMatch(predicate));
            withCount++;
        }
        assertTrue(Arrays.asList(tweet.getWithheldInCountries()).containsAll(Arrays.asList("EN", "GB", "SE")));
        assertEquals("\\u003Ca href=\"http:\\/\\/itunes.apple.com\\/us\\/app\\/twitter\\/id409789998?mt=12\" \\u003ETwitter for Mac\\u003C\\/a\\u003E", tweet.getSource());
        assertEquals(26815871309L, tweet.getCurrentUserRetweetId());
        assertNull(tweet.getRetweetedStatus());
    }

}
