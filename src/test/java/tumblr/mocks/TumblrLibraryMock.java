package tumblr.mocks;

import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.exceptions.JumblrException;
import com.tumblr.jumblr.types.Blog;
import com.tumblr.jumblr.types.User;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

public class TumblrLibraryMock {
    public static JumblrClient getTumblrMock() {
        JumblrClient client = Mockito.mock(JumblrClient.class);
        Blog fullBlogMock = getTumblrFullBlogMock();
        Mockito.when(client.blogInfo(eq("testblog"))).thenReturn(fullBlogMock);
        List<Blog> followingBlogs = generateSimpleBlogs(3);
        Mockito.when(client.userFollowing(any(Map.class))).thenReturn(followingBlogs);
        List<User> followersUsers = generateSimpleUsers(2);
        Mockito.when(client.blogFollowers(eq("testblog"), any(Map.class))).thenReturn(followersUsers);
        Mockito.doThrow(JumblrException.class).when(client).blogFollowers(eq("fails"), any(Map.class));
        Mockito.when(client.blogAvatar("testblog", 512)).thenReturn("http://urlforavatar.com");
        Mockito.when(client.blogAvatar("incorrecturl")).thenReturn("no%t.u$rl");
        Mockito.when(client.blogAvatar("fails", 512)).thenThrow(JumblrException.class);

        Mockito.doThrow(JumblrException.class).when(client).like(1234L, "rk1");
        Mockito.doNothing().when(client).like(123555L , "rk2");
        Mockito.doThrow(JumblrException.class).when(client).unlike(1234L, "rk1");
        Mockito.doNothing().when(client).unlike(123555L, "rk2");
        Mockito.when(client.blogPost("testblog", 1234L)).thenThrow(JumblrException.class);
        
        return client;
    }

    private static List<Blog> generateSimpleBlogs(int nrOfBlogs) {
        List<Blog> blogs = new ArrayList<>();
        for (int i = 0; i < nrOfBlogs; ++i) {
            Blog blog = Mockito.mock(Blog.class);
            Mockito.when(blog.getName()).thenReturn("blog" + i);
            Mockito.when(blog.getDescription()).thenReturn("Description of blog " + i);
            Mockito.when(blog.getPostCount()).thenReturn(i);
            Mockito.when(blog.getFollowersCount()).thenReturn(i % 2 == 1 ? null : i);
            Mockito.when(blog.getTitle()).thenReturn("Title of blog " + i);
            blogs.add(blog);
        }

        return blogs;
    }

    private static List<User> generateSimpleUsers(int nrOfUsers) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < nrOfUsers; ++i) {
            User user = Mockito.mock(User.class);
            Mockito.when(user.getName()).thenReturn("user" + i);
            Mockito.when(user.getFollowingCount()).thenReturn(i);
            Mockito.when(user.getLikeCount()).thenReturn(12 + i);
            List<Blog> blogs = generateSimpleBlogs(i);
            Mockito.when(user.getBlogs()).thenReturn(blogs);
            users.add(user);
        }
        return users;
    }

    public static Blog getTumblrFullBlogMock() {
        Blog blog = Mockito.mock(Blog.class);
        Mockito.when(blog.getName()).thenReturn("testblog");
        Mockito.when(blog.getDescription()).thenReturn("Blog description");
        Mockito.when(blog.getPostCount()).thenReturn(7);
        Mockito.when(blog.getFollowersCount()).thenReturn(13);
        Mockito.when(blog.getTitle()).thenReturn("Real name of the blog");

        return blog;
    }

    public static User getTumblrFullUserMock() {
        User user = Mockito.mock(User.class);
        Mockito.when(user.getName()).thenReturn("testuser");
        Mockito.when(user.getFollowingCount()).thenReturn(18);
        List<Blog> blogs = generateSimpleBlogs(3);
        Mockito.when(user.getBlogs()).thenReturn(blogs);

        return user;
    }


}
