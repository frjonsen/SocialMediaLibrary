package twitter;

import org.junit.jupiter.api.BeforeEach;
import twitter.mocks.TwitterLibraryMock;

public class TwitterAPIImplTest {
    private TwitterAPI twitter;

    @BeforeEach
    void init() {
        this.twitter = new TwitterAPIImpl(TwitterLibraryMock.getTwitterMock());
    }
}
