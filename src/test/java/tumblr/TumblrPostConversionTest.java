package tumblr;

import org.junit.jupiter.api.BeforeAll;
import tumblr.mocks.TumblrLibraryMock;

import java.io.IOException;

public class TumblrPostConversionTest {
    private static TumblrAPI tumblr;

    @BeforeAll
    static void init() throws IOException {
        tumblr = new TumblrAPIImpl(TumblrLibraryMock.getTumblrMock());
        tumblr.setActiveBlog("testblog");
    }


}
