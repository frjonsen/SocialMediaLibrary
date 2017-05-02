package facebook;

import org.cfg4j.provider.ConfigurationProvider;
import org.cfg4j.provider.ConfigurationProviderBuilder;
import org.cfg4j.source.ConfigurationSource;
import org.cfg4j.source.compose.FallbackConfigurationSource;
import org.cfg4j.source.context.filesprovider.ConfigFilesProvider;
import org.cfg4j.source.files.FilesConfigurationSource;
import org.cfg4j.source.system.EnvironmentVariablesConfigurationSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import socialmedia.Post;
import socialmedia.User;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
        facebook = new FacebookAPIImpl(appId, appSecret, token, "public_profile,user_about_me,user_hometown,email");
    }

    @Test
    void testGetProfile() {
        FacebookUser u = facebook.getUser("10208868385322359");
        assertEquals("Fredrik Jonsén", u.getName());
        assertEquals("10208868385322359", u.getId());
        assertEquals("Linköping, Sweden", u.getCity());
        assertEquals("http://jonsen.se/", u.getWebsite().toString());
        assertEquals("male", u.getGender());
        assertEquals(21, (int)u.getAgeRange().getMin());
    }

    @Test
    void testGetPost() {
        FacebookPost p = facebook.getPost("10208868385322359_10204310978590039");
        assertEquals("Här kan man va", p.getText());
        assertFalse(p.isHidden());
        assertTrue(p.isPublished());
        assertEquals("10204310978590039", p.getObjectId());
        assertEquals(Post.Type.IMAGE, p.getType());
        Iterable<FacebookUser> with = p.getWithTags();
        assertEquals(4, with.spliterator().getExactSizeIfKnown());
    }

    @Test
    void testSearchUsers() {
        List<FacebookUser> results = facebook.searchUsers("Fredrik");
        assertNotNull(results);
        // Can't make more assumptions than this, due to no control over real data
        assertTrue(results.size() > 0);
    }

    @Test
    void testGetProfilePicture() {
        URL picture = facebook.getProfilePicture("10208868385322359");
        // Will return null when there's no picture, so this test is sufficient
        assertNotNull(picture);
    }

    @Test
    void testGetSilhouettePicture() {
        List<FacebookUser> pages = facebook.getPages();
        System.out.println(pages.size());
        Optional<FacebookUser> testing = pages.stream().filter(page -> page.getName().equals("Testing")).findFirst();
        assertTrue(testing.isPresent());
        FacebookUser page = testing.get();
        assertNotNull(facebook.getProfilePicture(page.getId()));
    }

    @Test
    @DisplayName("should get the pages of the authed user")
    void testGetAccounts() {
        List<FacebookUser> pages = facebook.getPages();
        assertTrue(pages.size() > 0);
    }

}
