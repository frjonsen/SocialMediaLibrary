package twitter;

import org.cfg4j.provider.ConfigurationProvider;
import org.cfg4j.provider.ConfigurationProviderBuilder;
import org.cfg4j.source.ConfigurationSource;
import org.cfg4j.source.compose.FallbackConfigurationSource;
import org.cfg4j.source.context.filesprovider.ConfigFilesProvider;
import org.cfg4j.source.files.FilesConfigurationSource;
import org.cfg4j.source.system.EnvironmentVariablesConfigurationSource;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.Arrays;

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
    @DisplayName("Testing testing")
    void testGetUser(){
        System.out.println(twitter.getUser("me"));
        assertEquals("hej", "hej");

    }

    @Test
    @DisplayName("Testing testing")
    void testGetPost(){

    }

    @Test
    @DisplayName("Testing testing")
    void testGetPostFeed(){

    }

    @Test
    @DisplayName("Testing testing")
    void testFollow(){

    }

    @Test
    @DisplayName("Testing testing")
    void testUnfollow(){

    }

    @Test
    @DisplayName("Testing testing")
    void testGetRateLimitStatus(){

    }

    @Test
    @DisplayName("Testing testing")
    void testSearchUsers(){

    }

    @Test
    @DisplayName("Testing testing")
    void testGetProfilePicture(){

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

}
