package tumblr;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import socialmedia.Post;
import tumblr.mocks.TumblrLibraryMock;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    }

    @Test
    @DisplayName("should convert a photo post")
    void testPhotoPost() {
        TumblrPost post = tumblr.getPost("12");
        assertEquals(Post.Type.IMAGE, post.getType());
        assertEquals("Photo caption\n\n\"https://tumblr.com/photo0\"\n\"https://tumblr.com/photo1\"\n\"https://tumblr.com/photo2\"", post.getText());
    }

    @Test
    @DisplayName("should convert an audio post")
    void testAudioPost() {
        TumblrPost post = tumblr.getPost("16");
        assertEquals(Post.Type.AUDIO, post.getType());
        assertEquals("Source title\n\nAudio Caption\n\nhttp://tumblr.com/audiourl", post.getText());
    }

    @Test
    @DisplayName("should convert a video post")
    void testVideoPost() {
        TumblrPost post = tumblr.getPost("17");
        assertEquals(Post.Type.VIDEO, post.getType());
        assertEquals("Video Caption\n\n\"https://tumblr.com/videourl\"", post.getText());
    }

}
