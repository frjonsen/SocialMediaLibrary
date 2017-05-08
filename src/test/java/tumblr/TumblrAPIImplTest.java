package tumblr;

import com.tumblr.jumblr.types.Blog;
import com.tumblr.jumblr.types.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import socialmedia.NotSupportedException;
import tumblr.mocks.TumblrLibraryMock;

import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("regular")
class TumblrAPIImplTest {
    private static TumblrAPI tumblr;

    @BeforeAll
    static void init() {
        tumblr = new TumblrAPIImpl(TumblrLibraryMock.getTumblrMock());
        tumblr.setActiveBlog("testblog");
    }

    @Test
    @DisplayName("should create a TumblrAPIImpl through it's constructor")
    void testConstructor() {
        TumblrAPI tumblrTest = new TumblrAPIImpl("consumerKey", "consumerSecret", "accessToken", "acessSecret", "activeBlog");
        assertNotNull(tumblrTest);

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

    @Test
    @DisplayName("should get the blogs authed user is following")
    void testGetFollowing() {
        List<TumblrUser> blogs = tumblr.getFollowing(null, 1);
        assertEquals(3, blogs.size());

        assertEquals(0, tumblr.getFollowers(null, 0).size());
    }

    @Test
    @DisplayName("should get the users following a blog")
    void testGetFollowers() {
        List<TumblrUser> users = tumblr.getFollowers("testblog", 1);
        assertEquals(2, users.size());

        assertEquals(0, tumblr.getFollowers("testblog", 0).size());

        assertThrows(TumblrAPIException.class, () -> tumblr.getFollowers("fails", 1));
    }

    @Test
    @DisplayName("should handle invalid blog when getting followers")
    void testGetInvalidFollowers() {
        assertEquals(0, tumblr.getFollowers("nonexistant", 1).size());
    }

    @Test
    @DisplayName("should throw when using searchUsers")
    void testSearchUsers() {
        assertThrows(NotSupportedException.class, () -> tumblr.searchUsers("user"));
    }

    @Test
    @DisplayName("should retrieve blog avatar")
    void testGetProfilePicture() {
        URL url = tumblr.getProfilePicture("testblog");
        assertEquals("http://urlforavatar.com", url.toString());

        assertThrows(TumblrAPIException.class, () -> tumblr.getProfilePicture("fails"));
    }

    @Test
    @DisplayName("like post")
    void testLikePost() {
        assertTrue(tumblr.likePost("123555"));
        assertThrows(TumblrAPIException.class, () -> tumblr.likePost("1234"));
    }

    @Test
    @DisplayName("unlike post")
    void testUnlikePost() {
        assertTrue(tumblr.unlikePost("123555"));
        assertThrows(TumblrAPIException.class, () -> tumblr.unlikePost("1234"));

    }


}