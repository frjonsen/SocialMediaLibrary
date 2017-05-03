package tumblr;

import com.tumblr.jumblr.JumblrClient;

import java.net.URL;
import java.util.List;

public class TumblrAPIImpl extends TumblrAPI {

    private JumblrClient libraryInstance;

    public TumblrAPIImpl(String consumerKey, String consumerSecret, String accessToken, String accessSecret) {
        libraryInstance = new JumblrClient(consumerKey, consumerSecret, accessToken, accessSecret);
    }

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
    }

}
