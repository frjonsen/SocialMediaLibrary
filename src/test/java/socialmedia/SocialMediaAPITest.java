package socialmedia;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class SocialMediaAPITest {

    SocialMediaAPI sm;
    PrintStream ps;

    @BeforeEach
    void init() {
        sm = Mockito.mock(SocialMediaAPI.class, Mockito.CALLS_REAL_METHODS);
        ps = Mockito.mock(PrintStream.class);
        sm.setDebugStream(ps);
    }

    @Test
    @DisplayName("debug should print the given message")
    void testDebug() {
        sm.debug("something");
        Mockito.verify(ps).println("something");
    }

    @Test
    @DisplayName("debugf should forward the given objects")
    void testDebugf() {
        sm.debugf("something", "one", "two");
        Mockito.verify(ps).printf("something", "one", "two");
    }
}