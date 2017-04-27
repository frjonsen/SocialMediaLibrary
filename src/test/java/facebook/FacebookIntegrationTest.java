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
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


@Tag("apiCall")
public class FacebookIntegrationTest {

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
        facebook = new FacebookAPIImpl(appId, appSecret, token, "public_profile,email,user_about_me,user_hometown");
    }

    @Test
    void testGetProfile() {
        FacebookUser u = facebook.getUser("me");
        System.out.println(u);
    }

}
