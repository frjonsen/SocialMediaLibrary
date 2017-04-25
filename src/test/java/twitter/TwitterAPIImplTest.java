package twitter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import socialmedia.Post;
import twitter.mocks.TwitterLibraryMock;
import twitter4j.RateLimitStatus;
import twitter4j.TwitterException;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;
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
        TwitterUser user = this.twitter.getUser(6253282L);
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
    @DisplayName("")
    void testExistantUserIdString() {
        assertNotNull(this.twitter);
        TwitterUser user = this.twitter.getUser("6253282");
        assertEquals("6253282", user.getId());
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

    @Test
    @DisplayName("tries to get a post with a faulty id, should throw an TwitterAPIException")
    void testGetInvalidPostId(){
        assertNotNull(this.twitter);
        assertThrows(TwitterAPIException.class, ()->twitter.getPost("12B"));
    }

    @Test
    @DisplayName("should return the url for the profile image")
    void testGetProfilePicture() {
        assertNotNull(this.twitter);
        URL picture = twitter.getProfilePicture("6253282");
        assertEquals("https://pbs.twimg.com/profile_images/123123123123123123/IPv4Cubt_400x400.jpg", picture.toString());
    }

    @Test
    @DisplayName("should return a valid list of statuses representing a users post feed by long id")
    void testGetPostFeedLong() {
        assertNotNull(this.twitter);
        List<TwitterPost> postfeed = twitter.getPostFeed(6253282L);
        assertEquals(3, postfeed.size());
        assertEquals("123123", postfeed.get(0).getId());
    }

    @Test
    @DisplayName("should return a valid list of statuses representing a users post feed by string id")
    void testGetPostFeedStringId() {
        assertNotNull(this.twitter);
        List<TwitterPost> postfeed = twitter.getPostFeed("6253282");
        assertEquals(3, postfeed.size());
        assertEquals("123123", postfeed.get(0).getId());
    }
    @Test
    @DisplayName("should return a valid list of statuses representing a users post feed by string")
    void testGetPostFeedString() {
        assertNotNull(this.twitter);
        List<TwitterPost> postfeed = twitter.getPostFeed("TestyMcTest");
        assertEquals(3, postfeed.size());
        assertEquals("123123", postfeed.get(0).getId());
    }

    @Test
    @DisplayName("tries to get post-feed from unknown id, should return an empty list")
    void testGetPostFeedUnknownId() {
        assertNotNull(this.twitter);
        List<TwitterPost> postfeed = twitter.getPostFeed("156482");
        assertEquals(0, postfeed.size());
    }
    
    @Test
    @DisplayName("Should get a valid rateStatus")
    void testGetRateLimit() {
        assertNotNull(this.twitter);
        Map<String, RateLimitStatus> status;
        status = twitter.getRateLimitStatus();

        assertEquals(200, status.get("rate1").getLimit());
        assertEquals(50, status.get("rate2").getLimit());
    }

    @Test
    @DisplayName("should return true for liking a tweet")
    void testLikePostValid(){
        assertNotNull(this.twitter);
        boolean success = twitter.likePost("6253282");
        assertTrue(success);
    }

    @Test
    @DisplayName("should return true for unliking a tweet")
    void testUnlikePostValid(){
        assertNotNull(this.twitter);
        boolean success = twitter.unlikePost("6253282");
        assertTrue(success);
    }

    @Test
    @DisplayName("should return false for not being able to like a tweet")
    void testLikePostInvalid(){
        assertNotNull(this.twitter);
        boolean success = twitter.likePost("6251111");
        assertFalse(success);
    }

    @Test
    @DisplayName("should return false for not being able to unlike a tweet")
    void testUnlikePostInvalid(){
        assertNotNull(this.twitter);
        boolean success = twitter.likePost("6253211");
        assertFalse(success);
    }

    @Test
    @DisplayName("should throw exception for a bad id provided")
    void testLikePostBadId(){
        assertNotNull(this.twitter);
        assertThrows(TwitterAPIException.class, ()-> twitter.likePost("6253211bad"));
    }

    @Test
    @DisplayName("should throw exception for a bad id provided")
    void testUnlikePostBadId(){
        assertNotNull(this.twitter);
        assertThrows(TwitterAPIException.class, ()-> twitter.unlikePost("6253211bad"));
    }

    @Test
    @DisplayName("should publish a post and return id of new post")
    void testPublishPost(){
        assertNotNull(this.twitter);
        String id = twitter.publishStatusPost("The best tweet for testing");
        assertEquals("114749583439036416", id);
    }

    @Test
    @DisplayName("should return true for a follow call on valid id")
    void testFollowValid(){
        assertNotNull(this.twitter);
        boolean success = twitter.follow("6253282");
        assertTrue(success);
    }

    @Test
    @DisplayName("should return false for a follow call on invalid id")
    void testFollowInvalid(){
        assertNotNull(this.twitter);
        boolean success = twitter.follow("6253211");
        assertFalse(success);
    }

    @Test
    @DisplayName("should return true for a follow call on valid id with notifications turned on")
    void testFollowValidWithNotifications(){
        assertNotNull(this.twitter);
        boolean success = twitter.follow("6253282", true);
        assertTrue(success);
    }

    @Test
    @DisplayName("should return false for a follow call on invalid id with notifications turned off")
    void testFollowInvalidWithNotifications(){
        assertNotNull(this.twitter);
        boolean success = twitter.follow("6253211", true);
        assertFalse(success);
    }

    @Test
    @DisplayName("should return true for a follow call on valid long id")
    void testFollowValidLong(){
        assertNotNull(this.twitter);
        boolean success = twitter.follow(6253282L);
        assertTrue(success);
    }

    @Test
    @DisplayName("should return false for a follow call on invalid long id")
    void testFollowInvalidLong(){
        assertNotNull(this.twitter);
        boolean success = twitter.follow(6253211L);
        assertFalse(success);

    }

    @Test
    @DisplayName("should return true for a follow call on valid long id with notifications turned on")
    void testFollowValidWithNotificationsLong(){
        assertNotNull(this.twitter);
        boolean success = twitter.follow(6253282L, true);
        assertTrue(success);

    }

    @Test
    @DisplayName("should return false for a follow call on invalid id with notifications turned off")
    void testFollowInvalidWithNotificationsLong(){
        assertNotNull(this.twitter);
        boolean success = twitter.follow(6253211L, true);
        assertFalse(success);
    }

    @Test
    @DisplayName("should return true for a follow call on valid screenName")
    void testFollowValidScreenName(){
        assertNotNull(this.twitter);
        boolean success = twitter.follow("TestyMcTest");
        assertTrue(success);
    }

    @Test
    @DisplayName("should return false for a follow call on invalid screenName")
    void testFollowInvalidScreenName(){
        assertNotNull(this.twitter);
        boolean success = twitter.follow("NotTesty");
        assertFalse(success);
    }

    @Test
    @DisplayName("should return true for a follow call on valid screenName with notifications turned on")
    void testFollowValidWithNotificationsScreenName(){
        assertNotNull(this.twitter);
        boolean success = twitter.follow("TestyMcTest", true);
        assertTrue(success);
    }

    @Test
    @DisplayName("should return false for a follow call on invalid screenName with notifications turned off")
    void testFollowInvalidWithNotificationsScreenName(){
        assertNotNull(this.twitter);
        boolean success = twitter.follow("NotTesty", true);
        assertFalse(success);
    }

    @Test
    @DisplayName("should return true for a unfollow call on valid long id")
    void testUnfollowValidLong(){
        assertNotNull(this.twitter);
        boolean success = twitter.unfollow(6253282L);
        assertTrue(success);

    }

    @Test
    @DisplayName("should return false for a unfollow call on invalid id")
    void testUnfollowInvalidLong(){
        assertNotNull(this.twitter);
        boolean success = twitter.unfollow(6253211L);
        assertFalse(success);
    }

    @Test
    @DisplayName("should return true for a unfollow call on valid id")
    void testUnfollowValid(){
        assertNotNull(this.twitter);
        boolean success = twitter.unfollow("6253282");
        assertTrue(success);

    }

    @Test
    @DisplayName("should return false for a unfollow call on invalid id")
    void testUnfollowInvalid(){
        assertNotNull(this.twitter);
        boolean success = twitter.unfollow("6253211");
        assertFalse(success);
    }

    @Test
    @DisplayName("should return true for a unfollow call on valid screenName")
    void testUnfollowValidScreenName(){
        assertNotNull(this.twitter);
        boolean success = twitter.unfollow("TestyMcTest");
        assertTrue(success);
    }

    @Test
    @DisplayName("should return false for a unfollow call on invalid screenName")
    void testUnfollowInvalidScreenName(){
        assertNotNull(this.twitter);
        boolean success = twitter.unfollow("NotTesty");
        assertFalse(success);
    }

    @Test
    @DisplayName("should return a list of posts whith correct id")
    void testSearchPost(){
        assertNotNull(this.twitter);
        List<TwitterPost> res = twitter.searchPost("Test sweet tweet", 2);
        assertEquals("123123", res.get(0).getId());
        assertEquals(4, res.size());
    }

    @Test
    @DisplayName("should throw a TwitterAPIException when an empty query is used")
    void testEmptySearchPost(){
        assertNotNull(this.twitter);
        assertThrows(TwitterAPIException.class, () -> twitter.searchPost("", 2));
    }

    @Test
    @DisplayName("should get a list of users, with correct length, with query string pattern related usernames")
    void testSearchUsers(){
        assertNotNull(this.twitter);
        List<TwitterUser> res = twitter.searchUsers("Test");
        assertEquals("Testy McTest1", res.get(1).getUsername());
        assertEquals(20, res.size());
    }

    @Test
    @DisplayName("should throw a TwitterAPIException when an empty query is used")
    void testEmptySearchUsers(){
        assertNotNull(this.twitter);
        assertThrows(TwitterAPIException.class, () -> twitter.searchUsers(""));
    }



}
