package tumblr;

import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.exceptions.JumblrException;
import com.tumblr.jumblr.types.Blog;
import com.tumblr.jumblr.types.User;
import socialmedia.NotSupportedException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import java.util.List;

public class TumblrAPIImpl extends TumblrAPI {

    private JumblrClient libraryInstance;

    public TumblrAPIImpl(String consumerKey, String consumerSecret, String accessToken, String accessSecret) {
        libraryInstance = new JumblrClient(consumerKey, consumerSecret, accessToken, accessSecret);
    }

    @Override
    public List<TumblrUser> searchUsers(String query) {
        throw new NotSupportedException("searchUsers", PLATFORM);
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

    /**
     * Gets all the users following a specified blog. Tumblr only allows getting followers for the
     * authed users blogs.
     * @param id id of blog
     * @param maxCalls max number of calls to api
     * @return A list of users following the blog
     */
    @Override
    public List<TumblrUser> getFollowers(String id, int maxCalls) {
        final int limit = 20; // Maximum amount of users tumblr allows per call
        Map<String, Integer> options = new HashMap<>();
        List<TumblrUser> followers = new ArrayList<>();
        for (int i = 0; i < maxCalls; ++i) {
            int offset = i*limit;
            options.put("offset", offset);
            options.put("limit", limit);
            List<User> followersChunk;
            try {
                followersChunk = libraryInstance.blogFollowers(id, options);
            } catch(JumblrException je) {
                debug(je);
                throw new TumblrAPIException(je.getMessage());
            }

            if (followersChunk.isEmpty())
                break;
            followers.addAll(followersChunk.stream().map(TumblrAPIImpl::jumblrUserToUserConversion).collect(Collectors.toList()));
        }
        return followers;
    }

    /**
     * Gets the blogs the authed user is following. Id is not used.
     * @param id id of user
     * @param maxCalls max number of calls to api
     * @return List of blogs user is following
     */
    @Override
    public List<TumblrUser> getFollowing(String id, int maxCalls) {
        final int limit = 20; // Maximum number of blogs tumblr allows per call
        Map<String, Integer> options = new HashMap<>();
        List<TumblrUser> following = new ArrayList<>();
        for (int i = 0; i < maxCalls; ++i) {
            int offset = i * limit;
            options.put("offset", offset);
            options.put("limit", limit);
            List<Blog> followingChunk;
            try {
                followingChunk = libraryInstance.userFollowing(options);
            } catch (JumblrException je) {
                debug(je);
                throw new TumblrAPIException(je.getMessage());
            }

            if (followingChunk.isEmpty())
                break;
            following.addAll(followingChunk.stream().map(TumblrAPIImpl::jumblrBlogToUserConversion).collect(Collectors.toList()));
        }

        return following;
    }

    @Override
    public boolean follow(String id) {
        Blog blog = libraryInstance.blogInfo(id);
        if (blog == null)
            throw new TumblrAPIException("No blog with id \"" + id + "\"");
        libraryInstance.follow(id);
        return true; // jumblr doesn't return whether it worked. Assume it worked as long as blog exists.
    }

    @Override
    public boolean unfollow(String id) {
        Blog blog = libraryInstance.blogInfo(id);
        if (blog == null)
            throw new TumblrAPIException("No blog with id \"" + id + "\"");
        libraryInstance.unfollow(id);
        return true; // jumblr doesn't return whether it worked. Assume it worked as long as blog exists.
    }

    private TumblrPost jumblrPostConversion() {
        return null;
    }

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
            debug(me);
            throw new TumblrAPIException(me.getMessage());
        }
    }

}
