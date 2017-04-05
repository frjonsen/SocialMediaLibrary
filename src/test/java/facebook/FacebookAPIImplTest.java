package facebook;

import facebook.mocks.FacebookLibraryMock;
import facebook4j.FacebookException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FacebookAPIImplTest {
    private FacebookAPI facebook;

    @BeforeEach
    void init() throws FacebookException, MalformedURLException {
        this.facebook = new FacebookAPIImpl(FacebookLibraryMock.getFacebookMock());
    }

    @Test
    void testGetFullValidUser() {
        FacebookUser user = facebook.getUser("someid");
        assertEquals("Man", user.getGender());
        assertNotNull(user.getAgeRange());
        assertNull(user.getAgeRange().getMax());
        Integer age = user.getAgeRange().getMin();
        assertEquals(21, age.intValue());
        assertEquals("A very short biography", user.getBiography());
        assertEquals(1986, user.getBirthday().getDate().getYear());
        assertEquals(FacebookBirthDateUtil.DateType.FULL, user.getBirthday().getType());
        assertEquals("atown", user.getCity());
        assertEquals("email@example.com", user.getEmail());
        List<String> languages = new ArrayList<>();
        languages.add("English");
        languages.add("Swedish");
        assertEquals(languages.size(), user.getLanguages().size());
        assertTrue(user.getLanguages().containsAll(languages));
    }
}