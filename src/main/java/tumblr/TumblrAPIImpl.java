package tumblr;

import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.types.Blog;
import com.tumblr.jumblr.types.User;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import java.net.URL;
import java.util.List;

public class TumblrAPIImpl extends TumblrAPI {

    private JumblrClient libraryInstance;

    public TumblrAPIImpl(String consumerKey, String consumerSecret, String accessToken, String accessSecret) {
        libraryInstance = new JumblrClient(consumerKey, consumerSecret, accessToken, accessSecret);
    }

<<<<<<< HEAD
    @Override
    public TumblrUser getUser(String id) {
        return null;
    }

    @Override
    public List<TumblrUser> searchUsers(String query) {
        return null;
    }

    @Override
    public URL getProfilePicture(String id) {
        return null;
    }

    @Override
    public boolean likePost(String id) {
        return false;
    }

    @Override
    public boolean unlikePost(String id) {
        return false;
    }

    public TumblrPost getPost(String blogName, long id) {
        //libraryInstance.blogP
        return null;
    }

    @Override
    public String publishStatusPost(String message) {
        return null;
    }

    @Override
    public boolean destroyStatusPost(String id) {
        return false;
    }

    @Override
    public List<TumblrPost> searchPost(String query, int maxCalls) {
        return null;
    }

    @Override
    public List<TumblrPost> getPostFeed(String id) {
        return null;
    }

    @Override
    public List<TumblrUser> getFollowers(String id, int maxCalls) {
        return null;
    }

    @Override
    public List<TumblrUser> getFollowing(String id, int maxCalls) {
        return null;
    }

    @Override
    public boolean follow(String id) {
        return false;
    }

    @Override
    public boolean unfollow(String id) {
        return false;
    }

    private TumblrPost jumblrPostConversion(){
        return null;
=======
    TumblrAPIImpl(JumblrClient client) {
        libraryInstance = client;
    }

    static TumblrUser jumblrBlogToUserConversion(Blog blog) {
        if (blog == null) {
            return null;
        }
        TumblrUser user = new TumblrUser(TumblrUser.UserType.BLOG);
        user.setId(blog.getName());
        user.setUsername(blog.getName());
        user.setName(blog.getTitle());
        user.setBiography(blog.getDescription());
        user.setUploadCount(blog.getPostCount());
        user.setFollowersCount(blog.getFollowersCount());

        return user;
    }

    static TumblrUser jumblrUserToUserConversion(User jumblrUser) {
        if (jumblrUser == null) {
            return null;
        }
        TumblrUser user = new TumblrUser(TumblrUser.UserType.USER);
        user.setName(jumblrUser.getName());
        user.setId(jumblrUser.getName());
        user.setUsername(jumblrUser.getName());
        List<Blog> userBlogs = jumblrUser.getBlogs();
        List<TumblrUser> blogs = new ArrayList<>();
        if (userBlogs != null) {
            blogs = jumblrUser.getBlogs().stream().map(TumblrAPIImpl::jumblrBlogToUserConversion).collect(Collectors.toList());
        }
        user.setBlogs(blogs);
        user.setFollowingCount(jumblrUser.getFollowingCount());

        return user;
    }

    TumblrUser getAuthedUser() {
        return jumblrUserToUserConversion(libraryInstance.user());
    }

    public TumblrUser getUser(String id) {
        return jumblrBlogToUserConversion(libraryInstance.blogInfo(id));
    }

    @Override
    public URL getProfilePicture(String id) {
        String rawUrl = libraryInstance.blogAvatar(id, 512);
        try {
            return new URL(rawUrl);
        } catch (MalformedURLException me) {
            throw new TumblrAPIException(me.getMessage());
        }
>>>>>>> 152e9f387852d82f16b20affbd6c5efc989bee22
    }

}
