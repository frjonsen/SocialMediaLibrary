package facebook;

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

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("apiCall")
public class FacebookPageIntegrationTest {

    static FacebookAPI facebook = null;
    static String t = null;

    @BeforeAll
    static void init() {
        ConfigFilesProvider filesProvider = () -> Arrays.asList(new File("secrets.yaml").toPath().toAbsolutePath());
        ConfigurationSource fileConfig = new FilesConfigurationSource(filesProvider);
        ConfigurationSource source = new EnvironmentVariablesConfigurationSource();
        FallbackConfigurationSource fallbackConfig = new FallbackConfigurationSource(fileConfig, source);
        ConfigurationProvider provider = new ConfigurationProviderBuilder()
                .withConfigurationSource(fallbackConfig)
                .build();
        String appId = provider.getProperty("FACEBOOK.APPID", String.class);
        String appSecret = provider.getProperty("FACEBOOK.APPSECRET", String.class);
        String token = provider.getProperty("FACEBOOK.ACCESSTOKEN", String.class);
        facebook = new FacebookAPIImpl(appId, appSecret, token, "public_profile,user_about_me,user_hometown,email");
        facebook = new FacebookAPIImpl(appId, appSecret, facebook.getPages().get(0).getAccessToken(), "public_profile,user_about_me,user_hometown,email");
    }

    @Test
    void testLikeUnlikePost() {
        assertTrue(facebook.likePost("1391221074275077_1415755851821599"));
        assertTrue(facebook.unlikePost("1391221074275077_1415755851821599"));

        assertThrows(FacebookAPIException.class, () -> facebook.likePost("nonexistantpost"));
        assertThrows(FacebookAPIException.class, () -> facebook.unlikePost("nonexistantpost"));
    }

    @Test
    void testPublishPost() {
        List<FacebookPost> posts = facebook.getPostFeed("me");
        facebook.publishStatusPost("testpost");
        List<FacebookPost> updatedFeed = facebook.getPostFeed("me");
        FacebookPost p = updatedFeed.stream().filter(post -> post.getText().equals("testpost")).findFirst().get();
        assertEquals(posts.size() + 1, updatedFeed.size());
        assertTrue(facebook.destroyStatusPost(p.getId()));
    }

    @Test
    void testGetComments() {
        List<FacebookComment> comments = facebook.getComments("1391221074275077_1415755851821599");
        assertEquals("hello", comments.get(0).getMessage());
    }
}
