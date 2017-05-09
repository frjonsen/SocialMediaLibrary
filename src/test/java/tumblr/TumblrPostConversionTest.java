package tumblr;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import socialmedia.Post;
import tumblr.mocks.TumblrLibraryMock;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("regular")
public class TumblrPostConversionTest {
    private static TumblrAPI tumblr;

    @BeforeAll
    static void init() throws IOException {
        tumblr = new TumblrAPIImpl(TumblrLibraryMock.getTumblrMock());
        tumblr.setActiveBlog("testblog");
    }

    @Test
    @DisplayName("textpost")
    void testTextPost() {
        TumblrPost post = tumblr.getPost("11");

        assertEquals(Post.Type.TEXT, post.getType());
        assertEquals("Master of tests\n\nThis is how you test like you mean it", post.getText());
    }

    @Test
    @DisplayName("quotepost")
    void testQuotePost() {
        TumblrPost post = tumblr.getPost("13");

        assertEquals(Post.Type.QUOTE, post.getType());
        assertEquals("I like testing titles\n\nsource: \"https://sml2003.tumblr.com/post/160297775984/testing-title\"", post.getText());
    }

    @Test
    @DisplayName("linkpost")
    void testLinkPost() {
        TumblrPost post = tumblr.getPost("14");

        assertEquals(Post.Type.LINK, post.getType());
        assertEquals("LinkPost\n\nthis is the best description\n\nsource: \"https://sml2003.tumblr.com/post/160297775984/testing-title\"", post.getText());
    }

    @Test
    @DisplayName("chatpost")
    void testChatPost() {
        TumblrPost post = tumblr.getPost("15");

        assertEquals(Post.Type.CHAT, post.getType());
        assertEquals("ChatPost\n\nthis is the best body", post.getText());
    }

    @Test
    @DisplayName("answerpost")
    void testAnswerPost() {
        TumblrPost post = tumblr.getPost("18");

        assertEquals(Post.Type.ANSWER, post.getType());
        assertEquals("Testie\n" +
                "\n" +
                "Can has test?\n" +
                "\n" +
                "Noh", post.getText());
        List<TumblrUser> to = (List<TumblrUser>) post.getTo();
        assertEquals(2, to.size());
    }




}
