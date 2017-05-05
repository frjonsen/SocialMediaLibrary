package tumblr;

import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.exceptions.JumblrException;

import com.tumblr.jumblr.types.*;
import socialmedia.SocialMediaUtil;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import java.util.List;

import static socialmedia.Post.Type.*;
import static tumblr.TumblrUser.UserType.BLOG;
import static tumblr.TumblrUser.UserType.USER;

public class TumblrAPIImpl extends TumblrAPI {

    private JumblrClient libraryInstance;

    public TumblrAPIImpl(String consumerKey, String consumerSecret, String accessToken, String accessSecret) {
        libraryInstance = new JumblrClient(consumerKey, consumerSecret, accessToken, accessSecret);
    }

    public TumblrAPIImpl(String consumerKey, String consumerSecret, String accessToken, String accessSecret, String activeBlog) {
        this(consumerKey, consumerSecret, accessToken, accessSecret);
        this.activeBlog = activeBlog;
    }

    @Override
    public String getActiveBlog() {
        return activeBlog;
    }

    @Override
    public void setActiveBlog(String activeBlog) {
        this.activeBlog = activeBlog;
    }

    @Override
    public boolean likePost(String id) {
        return false;
    }

    @Override
    public boolean unlikePost(String id) {
        return false;
    }

    @Override
    public TumblrPost getPost(String id) {
        return getPost(this.activeBlog, id);
    }

    @Override
    public TumblrPost getPost(String blogName, String id) {
        try {
            long lId = Long.parseLong(id);
            Post post = libraryInstance.blogPost(blogName, lId);
            return TumblrPostConversion.jumblrPostConversion(post);
        } catch(JumblrException|NumberFormatException|MalformedURLException e) {
            debug(e);
            throw new TumblrAPIException(e.getMessage());
        }
    }

    @Override
    public String publishStatusPost(String message) {
        return publishStatusPost(this.activeBlog, message);
    }

    @Override
    public String publishStatusPost(String blogName, String message) {
        try {
            Map<String, String> options = new HashMap<>();
            options.put("type", "text");
            options.put("body", message);
            Long id = libraryInstance.postCreate(blogName, options);
            return id != null ? String.valueOf(id) : null;
        } catch(JumblrException|IOException e) {
            debug(e);
            throw new TumblrAPIException(e.getMessage());
        }
    }

    @Override
    public boolean destroyStatusPost(String id) {
        long pId;
        try {
            pId = Long.parseLong(id);
        } catch (NumberFormatException nfe) {
            debug(nfe);
            throw new TumblrAPIException(nfe.getMessage());
        }
        libraryInstance.postDelete(activeBlog, pId);
        try {
            libraryInstance.blogPost(activeBlog, pId);
            return false;
        } catch (JumblrException je) {
            if ("Not Found".equals(je.getMessage())) {
                return true;
            }
            debug(je);
            throw new TumblrAPIException(je.getMessage());
        }
    }

    @Override
    public List<TumblrPost> searchPost(String query, int maxCalls) {
        return null;
    }

    @Override
    public List<TumblrPost> getPostFeed(String id) {
        String blogId = (id == null || id.equals(SELF_ID)) ? activeBlog : id;
        List<Post> posts = libraryInstance.blogPosts(blogId);
        try {
            List<TumblrPost> returnList = new ArrayList<>();
            for(Post post: posts) {
                returnList.add(TumblrPostConversion.jumblrPostConversion(post));
            }
            return returnList;
        } catch(MalformedURLException mue) {
            debug(mue);
            throw new TumblrAPIException(mue.getMessage());
        }

    }

    @Override
    public List<TumblrUser> getFollowers(String id, int maxCalls) {
        int maximumCalls = maxCalls == -1 ? Integer.MAX_VALUE : maxCalls; // To make Sonar happy
        final int limit = 20; // Maximum amount of users tumblr allows per call
        Map<String, Integer> options = new HashMap<>();
        List<TumblrUser> followers = new ArrayList<>();
        for (int i = 0; i < maximumCalls; ++i) {
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

    @Override
    public List<TumblrUser> getFollowing(String id, int maxCalls) {
        int maximumCalls = maxCalls == -1 ? Integer.MAX_VALUE : maxCalls; // To make Sonar happy

        final int limit = 20; // Maximum number of blogs tumblr allows per call
        Map<String, Integer> options = new HashMap<>();
        List<TumblrUser> following = new ArrayList<>();
        for (int i = 0; i < maximumCalls; ++i) {
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
        TumblrUser user = new TumblrUser(USER);
        user.setName(jumblrUser.getName());
        user.setId(jumblrUser.getName());
        user.setUsername(jumblrUser.getName());
        List<Blog> userBlogs = jumblrUser.getBlogs();
        List<TumblrUser> blogs = new ArrayList<>();
        if (userBlogs != null) {
            blogs = userBlogs.stream().map(TumblrAPIImpl::jumblrBlogToUserConversion).collect(Collectors.toList());
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
        String rawUrl;
        try {
            rawUrl = libraryInstance.blogAvatar(id, 512);
        } catch (JumblrException je) {
            debug(je);
            throw new TumblrAPIException(je.getMessage());
        }

        try {
            return new URL(rawUrl);
        } catch (MalformedURLException me) {
            debug(me);
            throw new TumblrAPIException(me.getMessage());
        }
    }

}
