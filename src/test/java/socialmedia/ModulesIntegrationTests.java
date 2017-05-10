package socialmedia;

import facebook.FacebookAPI;
import facebook.FacebookAPIImpl;
import org.cfg4j.provider.ConfigurationProvider;
import org.cfg4j.provider.ConfigurationProviderBuilder;
import org.cfg4j.source.ConfigurationSource;
import org.cfg4j.source.compose.FallbackConfigurationSource;
import org.cfg4j.source.context.filesprovider.ConfigFilesProvider;
import org.cfg4j.source.files.FilesConfigurationSource;
import org.cfg4j.source.system.EnvironmentVariablesConfigurationSource;
import org.junit.jupiter.api.BeforeAll;
import tumblr.TumblrAPI;
import tumblr.TumblrAPIImpl;
import twitter.TwitterAPI;
import twitter.TwitterAPIImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModulesIntegrationTests {
    static List<SocialMediaAPI<? extends User, ? extends Post>> apis = new ArrayList<>();

    static ConfigurationProvider getConfig() {
        ConfigFilesProvider filesProvider = () -> Arrays.asList(new File("secrets.yaml").toPath().toAbsolutePath());
        ConfigurationSource fileConfig = new FilesConfigurationSource(filesProvider);
        ConfigurationSource source = new EnvironmentVariablesConfigurationSource();
        FallbackConfigurationSource fallbackConfig = new FallbackConfigurationSource(fileConfig, source);
        return new ConfigurationProviderBuilder()
                .withConfigurationSource(fallbackConfig)
                .build();
    }

    static FacebookAPI getFacebookAPI(ConfigurationProvider config) {
        String appId = config.getProperty("FACEBOOK.APPID", String.class);
        String appSecret = config.getProperty("FACEBOOK.APPSECRET", String.class);
        String token = config.getProperty("FACEBOOK.ACCESSTOKEN", String.class);
        return new FacebookAPIImpl(appId, appSecret, token, "public_profile,user_about_me,user_hometown,email");
    }

    static TwitterAPI getTwitterAPI(ConfigurationProvider config) {
        String accessToken = config.getProperty("TWITTER.ACCESSTOKEN", String.class);
        String tokenSecret = config.getProperty("TWITTER.TOKENSECRET", String.class);
        String consumerKey = config.getProperty("TWITTER.CONSUMERKEY", String.class);
        String consumerSecret = config.getProperty("TWITTER.CONSUMERSECRET", String.class);

        return new TwitterAPIImpl(consumerKey, consumerSecret, accessToken, tokenSecret,true);
    }

    static TumblrAPI getTumblrAPI(ConfigurationProvider config) {
        String accessToken = config.getProperty("TUMBLR.ACCESSTOKEN", String.class);
        String tokenSecret = config.getProperty("TUMBLR.TOKENSECRET", String.class);
        String consumerKey = config.getProperty("TUMBLR.CONSUMERKEY", String.class);
        String consumerSecret = config.getProperty("TUMBLR.CONSUMERSECRET", String.class);

        return new TumblrAPIImpl(consumerKey, consumerSecret, accessToken, tokenSecret, "sml2003");
    }

    @BeforeAll
    static void init() {
        ConfigurationProvider config = getConfig();
        FacebookAPI facebook = getFacebookAPI(config);
        TwitterAPI twitter = getTwitterAPI(config);
        TumblrAPI tumblr = getTumblrAPI(config);
        apis.add(facebook);
        apis.add(twitter);
        apis.add(tumblr);
    }
}
