package facebook;

import socialmedia.NotSupportedException;
import socialmedia.SocialMediaBase;

import java.util.List;

/**
 * Handles API calls for Facebook. Where functions take an id of a specific user,
 * an id of 'me' will translate to the current user. getUser("me") will as such
 * get all information about the current user.
 */
public abstract class FacebookAPI extends SocialMediaBase<FacebookUser, FacebookPost> {

    protected static final String PLATFORM = "Facebook";

    /**
     * Gets a Facebook user, filling a user object with the available information.
     * As users have a large degree of flexibility regarding what's visible,
     * some data may be null, even if the user has set the information.
     * If id is 'me', will retrieve the current user
     * @param id Site-wide id of the user
     * @return A FacebookUser
     */
    public abstract FacebookUser getUser(String id);

    /**
     * Gets a Facebook post, filling a post object with all available information.
     * @param id id of the post
     * @return A FacebookPost
     */
    public abstract FacebookPost getPost(String id);

    /**
     * Not supported by facebook. Always throws an exception.
     * @return Always throws an exception
     */
    public List<FacebookPost> searchPost(String query, int maxCalls) {
        throw new NotSupportedException("searchPost", PLATFORM);
    }

    /**
     * Gets the comments from a Facebook post
     * @param postId Id of post
     * @return A list of comments
     */
    public abstract List<FacebookComment> getComments(String postId);

    /**
     * Publishes a comment on a post
     * @param postId Id of the post
     * @param commentMessage Comment contents
     * @return Id of the new comment
     */
    public abstract String publishComment(String postId, String commentMessage);

    /**
     * Not supported by facebook. Always throws an exception.
     * @param id -
     * @return Always throws an exception
     */
    public List<FacebookUser> getFollowers(String id, int maxCalls) {
        throw new NotSupportedException("getFollowers", PLATFORM);
    }

    /**
     * Not supported by facebook. Always throws an exception.
     * @param id -
     * @return Always throws an exception
     */
    public List<FacebookUser> getFollowing(String id, int maxCalls) {
        throw new NotSupportedException("getFollowing", PLATFORM);
    }

    /**
     * Not supported by facebook. Always throws an exception.
     * @param id -
     * @return Always throws an exception
     */
    public boolean follow(String id) {
        throw new NotSupportedException("follow", PLATFORM);
    }

    /**
     * Not supported by facebook. Always throws an exception.
     * @param id -
     * @return Always throws an exception
     */
    public boolean unfollow(String id) {
        throw new NotSupportedException("unfollow", PLATFORM);
    }

    public boolean destroyStatusPost(String id) {return false; }

}
