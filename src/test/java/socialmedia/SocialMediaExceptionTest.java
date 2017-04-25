package socialmedia;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("regular")
class SocialMediaExceptionTest {
    @Test
    @DisplayName("platform should be set and get unchanged")
    void testGetPlatform() {
        SocialMediaException exception = new SocialMediaException("message", "platform");
        assertEquals("platform", exception.getPlatform());
        assertEquals("message", exception.getMessage());
    }

}