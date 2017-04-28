package twitter;

import org.cfg4j.provider.ConfigurationProvider;
import org.cfg4j.provider.ConfigurationProviderBuilder;
import org.cfg4j.source.ConfigurationSource;
import org.cfg4j.source.compose.FallbackConfigurationSource;
import org.cfg4j.source.context.filesprovider.ConfigFilesProvider;
import org.cfg4j.source.files.FilesConfigurationSource;
import org.cfg4j.source.system.EnvironmentVariablesConfigurationSource;
import org.junit.jupiter.api.*;
import socialmedia.Post;
import twitter4j.RateLimitStatus;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

@Tag("apiCall")
public class TwitterIntegrationTest {
    private static TwitterAPI twitter;

    @BeforeAll
    static void init() {
        ConfigFilesProvider filesProvider = () -> Arrays.asList(new File("secrets.yaml").toPath().toAbsolutePath());
        ConfigurationSource fileConfig = new FilesConfigurationSource(filesProvider);
        ConfigurationSource source = new EnvironmentVariablesConfigurationSource();
        FallbackConfigurationSource fallbackConfig = new FallbackConfigurationSource(fileConfig, source);
        ConfigurationProvider provider = new ConfigurationProviderBuilder()
                .withConfigurationSource(fallbackConfig)
                .build();
        String accessToken = provider.getProperty("TWITTER.ACCESSTOKEN", String.class);
        String tokenSecret = provider.getProperty("TWITTER.TOKENSECRET", String.class);
        String consumerKey = provider.getProperty("TWITTER.CONSUMERKEY", String.class);
        String consumerSecret = provider.getProperty("TWITTER.CONSUMERSECRET", String.class);

        twitter = new TwitterAPIImpl(consumerKey, consumerSecret, accessToken, tokenSecret,true);
    }

    @Test
    @DisplayName("calling getUser with its different overloads and checking that the user object is valid")
    void testGetUser(){
        TwitterUser user;
        user = twitter.getUser("me");
        assertEquals("829638593786347520", user.getId());
        verifyUserFields(user);

        user = twitter.getUser("Twitter");
        assertEquals("783214", user.getId());

        user = twitter.getUser(6253282L);
        assertEquals("twitterapi", user.getUsername());
    }

    private void verifyUserFields(TwitterUser user) {
        assertEquals("Full Name", user.getName());
        assertEquals("829638593786347520", user.getId());
        assertEquals("liustuds", user.getUsername());
        assertEquals("https://t.co/AizXLwr5eB", user.getWebsite().toString());
        assertEquals("Doing a little test, making a little mock.", user.getBiography());
        assertEquals(0, user.getFollowersCount());
        assertEquals("Testingville", user.getLocation());
        assertEquals("en-gb", user.getLanguage());
        assertEquals(4, user.getUploadCount());
    }

    @Test
    @DisplayName("calling getPost and checking that the post returned is of valid expected format")
    void testGetPost(){
        TwitterPost tweet;
        tweet = twitter.getPost("857568197498851330");

        assertEquals("liustuds", tweet.getAuthor().getUsername());
        assertEquals("I am #Testing stuff https://t.co/tkYUjZp8m9 mystuff @liustuds", tweet.getText());
        assertEquals(Post.Type.UNKNOWN, tweet.getType());
        assertEquals("2017-04-27T12:12:45Z", tweet.getCreationTime().toString());
        assertEquals("2017-04-27T12:12:45Z", tweet.getEditTime().toString());
        assertEquals(0, tweet.getSharedCount());
        assertEquals("liustuds", tweet.getTo().iterator().next().getUsername());
        assertEquals("Testing", tweet.getTags().iterator().next());
        assertEquals("https://twitter.com/liustuds/status/857568197498851330", tweet.getPermalink().toString());
        assertEquals("829638593786347520", tweet.getAuthor().getId());
        assertEquals("en", tweet.getLanguage());
        assertEquals("La Teste-de-Buch", tweet.getPlace().getName());
        assertEquals("https://twitter.com/liustuds/followers?lang=sv", tweet.getUrlEntities()[0].getExpandedURL());
        assertEquals(0, tweet.getFavoriteCount());
    }

    @Test
    @DisplayName("calls getPostFeed for the 3 different overloads and checks that the posts in each feed is the same")
    void testGetPostFeed(){
        List<TwitterPost> feed1 = twitter.getPostFeed(829638593786347520L);
        List<TwitterPost> feed2 = twitter.getPostFeed("829638593786347520");
        List<TwitterPost> feed3 = twitter.getPostFeed("liustuds");

        for(int i = 0; i < feed1.size(); i++) {
            String language = feed1.get(i).getLanguage();
            String id = feed1.get(i).getId();
            String text = feed1.get(i).getText();
            String creationTime = feed1.get(i).getCreationTime().toString();
            String authorUsername = feed1.get(i).getAuthor().getUsername();

            assertTrue(language.equals(feed2.get(i).getLanguage()) && language.equals(feed3.get(i).getLanguage()));
            assertTrue(id.equals(feed2.get(i).getId()) && id.equals(feed3.get(i).getId()));
            assertTrue(text.equals(feed2.get(i).getText()) && text.equals(feed3.get(i).getText()));
            assertTrue(creationTime.equals(feed2.get(i).getCreationTime().toString()) && creationTime.equals(feed3.get(i).getCreationTime().toString()));
            assertTrue(authorUsername.equals(feed2.get(i).getAuthor().getUsername()) && authorUsername.equals(feed3.get(i).getAuthor().getUsername()));
        }

    }

    @Test
    @DisplayName("should follow and unfollow a user for the authenticator")
    
    void testFollowUnfollow() throws InterruptedException{
        List<TwitterUser> following = twitter.getFollowing("me", -1); // following list
        if(following.stream().anyMatch((follower) -> follower.getId().equals("130649891"))) { // check if list has id
            assertTrue(twitter.unfollow("130649891")); //unfollow if there 130649891
            following = twitter.getFollowing("me", -1); // following list
        }

        assertFalse(following.stream().anyMatch((follower) -> follower.getId().equals("130649891"))); //check if id

        assertTrue(twitter.follow(130649891L)); //follow
        following = twitter.getFollowing("me", -1); //get followers
        assertTrue(following.stream().anyMatch((follower) -> follower.getId().equals("130649891"))); //check following

        assertTrue(twitter.unfollow(130649891L)); //unfollow
        following = twitter.getFollowing("me", -1); //check unfollowed
        assertFalse(following.stream().anyMatch((follower) -> follower.getId().equals("130649891"))); //check unfollowed
    }

    @Test
    @DisplayName("searchers for twitter and should find twitters main account in list")
    void testSearchUsers(){
        List<TwitterUser> result = twitter.searchUsers("twitter");
        assertTrue(result.stream().anyMatch((user) -> user.getId().equals("783214")));
    }

    @Test
    @DisplayName("Testing testing")
    void testGetProfilePicture(){
       String url = twitter.getProfilePicture("783214").toString();
       assertEquals("https://pbs.twimg.com/profile_images/842992378960986112/Yd1Z53jW_bigger.jpg", url);
    }

    @Test
    @DisplayName("Testing testing")
    void testLikePost(){

    }

    @Test
    @DisplayName("Testing testing")
    void testUnlikePost(){

    }

    @Test
    @DisplayName("Testing testing")
    void testPublishStatusPost(){

    }

    @Test
    @DisplayName("Testing testing")
    void testSearchPost(){

    }

    @Test
    @DisplayName("Testing testing")
    void testGetFollowers(){

    }

    @Test
    @DisplayName("Testing testing")
    void testGetFollowing(){

    }

    @Test
    @DisplayName("Testing testing")
    void testDestroyStatusPost() {

    }

}
