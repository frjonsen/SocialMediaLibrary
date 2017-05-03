package tumblr.mocks;

import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.types.Blog;
import com.tumblr.jumblr.types.User;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;

public class TumblrLibraryMock {
    public static JumblrClient getTumblrMock() {
        JumblrClient client = Mockito.mock(JumblrClient.class);
        Blog fullBlogMock = getTumblrFullBlogMock();
        Mockito.when(client.blogInfo(eq("testblog"))).thenReturn(fullBlogMock);

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
