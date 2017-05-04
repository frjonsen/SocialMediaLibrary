package tumblr;

import com.tumblr.jumblr.*;
import com.tumblr.jumblr.types.User;
import org.cfg4j.provider.ConfigurationProvider;
import org.cfg4j.provider.ConfigurationProviderBuilder;
import org.cfg4j.source.ConfigurationSource;
import org.cfg4j.source.compose.FallbackConfigurationSource;
import org.cfg4j.source.context.filesprovider.ConfigFilesProvider;
import org.cfg4j.source.files.FilesConfigurationSource;
import org.cfg4j.source.system.EnvironmentVariablesConfigurationSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import twitter.TwitterAPIImpl;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("apiCall")
public class TumblrIntegrationTest {
    private static TumblrAPI tumblr;

    @BeforeAll
    static void init() {
        ConfigFilesProvider filesProvider = () -> Arrays.asList(new File("secrets.yaml").toPath().toAbsolutePath());
        ConfigurationSource fileConfig = new FilesConfigurationSource(filesProvider);
        ConfigurationSource source = new EnvironmentVariablesConfigurationSource();
        FallbackConfigurationSource fallbackConfig = new FallbackConfigurationSource(fileConfig, source);
        ConfigurationProvider provider = new ConfigurationProviderBuilder()
                .withConfigurationSource(fallbackConfig)
                .build();
        String accessToken = provider.getProperty("TUMBLR.ACCESSTOKEN", String.class);
        String tokenSecret = provider.getProperty("TUMBLR.TOKENSECRET", String.class);
        String consumerKey = provider.getProperty("TUMBLR.CONSUMERKEY", String.class);
        String consumerSecret = provider.getProperty("TUMBLR.CONSUMERSECRET", String.class);

        tumblr = new TumblrAPIImpl(consumerKey, consumerSecret, accessToken, tokenSecret);
        /*
        JumblrClient client = new JumblrClient("tIRHYJjPgpIn11RhuRooOAjZNNHQhYcA1nxWjIWp9Jk1jTIgOj", "IqL7ufsJqP9KD7HyQZ64xu117BFqLIIK77irOWJO3AS4x1nE78");
        //client.setToken("oauth_token", "oauth_token_secret");
        client.

        Write the user's name
        User user = client.user();
        System.out.println(user.getName());
        */
    }

    @Test
    void testGetAuthedUser() {
        TumblrUser user = tumblr.getAuthedUser();
        assertEquals(TumblrUser.UserType.USER, user.getType());
        assertEquals("sml2003", user.getName());
        assertEquals(1, user.getFollowingCount());
        assertEquals(1, user.getBlogs().size());
        assertEquals(null, user.getBiography());

        assertEquals("Untitled", user.getBlogs().get(0).getUsername());
        assertNull(user.getBlogs().get(0).getName());
    }

    @Test
    void testGetBlog() {
        TumblrUser blog = tumblr.getUser("sml2003");
        assertEquals("Untitled", blog.getName());
        assertEquals("sml2003", blog.getId());
        assertEquals(1, blog.getUploadCount());
        assertEquals("herro", blog.getBiography());
        assertEquals(0, blog.getFollowersCount().intValue());
    }

    @Test
    void testGetProfilePicture() {
        URL picture = tumblr.getProfilePicture("sml2003");
        assertEquals("https://assets.tumblr.com/images/default_avatar/cube_open_128.png", picture.toString());
    }

    @Test
    void testGetFollowing() {
        List<TumblrUser> following = tumblr.getFollowing(null, 1);
        assertEquals("staff", following.get(0).getId());
    }

    @Test
    void testGetFollowers() {
        List<TumblrUser> followers = tumblr.getFollowers("staff", 2);
        assertEquals(40, followers.size());
    }

    @Test
    void testFollowUnfollow() {
        String blog = "madewithcode";
        assertTrue(tumblr.follow(blog));
        assertTrue(tumblr.getFollowing(null, 1).stream().anyMatch(b -> b.getId().equals(blog)));

        assertTrue(tumblr.unfollow(blog));
        assertFalse(tumblr.getFollowing(null, 1).stream().anyMatch(b -> b.getId().equals(blog)));
    }

    @Test
    void stuff(){
        assertTrue(true);
    }
}
