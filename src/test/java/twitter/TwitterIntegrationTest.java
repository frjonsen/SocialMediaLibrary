package twitter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("apiCall")
public class TwitterIntegrationTest {

    @BeforeEach
    void init() {
        System.out.println("before each");
    }

    @Test
    @DisplayName("Testing testing")
    void testStuff(){
        assertEquals("hej", "hej");
        System.out.println("This is major tom to ground control...");
    }
}
