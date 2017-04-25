package socialmedia;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("regular")
class NotSupportedExceptionTest {
    @Test
    @DisplayName("exception should have an informative message")
    void testToString() {
        NotSupportedException exception = new NotSupportedException("someFunction", "platform");
        assertEquals("someFunction is not supported for platform \"platform\"" ,exception.toString());
    }

    @Test
    @DisplayName("test if empty constructor for notSupportedException works")
    void testEmptyConstructor() {
        assertThrows(NotSupportedException.class, () -> {
        throw new NotSupportedException();
        });
    }

}