package socialmedia;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;

import java.io.PrintStream;


@Tag("regular")
public class SocialMediaBaseTest {

    SocialMediaBase sm;
    PrintStream ps;

    @BeforeEach
    void init() {
        sm = Mockito.mock(SocialMediaBase.class, Mockito.CALLS_REAL_METHODS);
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

    @Test
    @DisplayName("should use toString to print object")
    void testPrintObject() {
        Object o = new Object() {
            String s = "something";

            @Override
            public String toString() {
                return s;
            }
        };
        sm.debug(o);
        Mockito.verify(ps).println("something");
    }

    @Test
    @DisplayName("should do nothing when PrintStream is null")
    void testPrintStreamNull() {
        sm.setDebugStream(null);
        sm.debug("something");
        sm.debugf("something", "else");
        Mockito.verify(ps, Mockito.never()).println("something");
        Mockito.verify(ps, Mockito.never()).printf("something", "else");
    }


}