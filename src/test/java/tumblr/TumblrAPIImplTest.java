package tumblr;

import com.tumblr.jumblr.types.Blog;
import com.tumblr.jumblr.types.User;
import org.junit.jupiter.api.*;
import tumblr.mocks.TumblrLibraryMock;

import static org.junit.jupiter.api.Assertions.*;

@Tag("regular")
class TumblrAPIImplTest {
    private static TumblrAPI tumblr;

    @BeforeAll
    static void init() {
        tumblr = new TumblrAPIImpl(TumblrLibraryMock.getTumblrMock());
    }

    @Test
    @DisplayName("should convert a jumblr blog to TumblrUser")
    void testConvertBlog() {
        Blog blog = TumblrLibraryMock.getTumblrFullBlogMock();
        TumblrUser convertedBlog = TumblrAPIImpl.jumblrBlogToUserConversion(blog);
        assertEquals(TumblrUser.UserType.BLOG, convertedBlog.getType());
        assertEquals("testblog", convertedBlog.getId());
        assertEquals("testblog", convertedBlog.getUsername());
        assertEquals("Real name of the blog", convertedBlog.getName());
        assertEquals(7, convertedBlog.getUploadCount());
        assertEquals(13, convertedBlog.getFollowersCount().intValue());
        assertEquals("Blog description", convertedBlog.getBiography());
    }

    @Test
    @DisplayName("should convert a jumblr user to a TumblrUser")
    void testConvertUser() {
        User user = TumblrLibraryMock.getTumblrFullUserMock();
        TumblrUser convertedUser = TumblrAPIImpl.jumblrUserToUserConversion(user);
        assertEquals("testuser", convertedUser.getName());
        assertEquals("testuser", convertedUser.getId());
        assertEquals("testuser", convertedUser.getUsername());
        assertEquals(18, convertedUser.getFollowingCount());
        assertEquals(3, convertedUser.getBlogs().size());
    }
}