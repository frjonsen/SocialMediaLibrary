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
import org.junit.jupiter.api.Test;
import twitter.TwitterAPIImpl;

import java.io.File;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void stuff(){
        assertTrue(true);
    }
}
