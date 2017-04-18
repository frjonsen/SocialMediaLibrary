package facebook;

import socialmedia.NotSupportedException;
import socialmedia.SocialMediaBase;

import java.net.URL;
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
     * Searches for all users matching the query.
     * @param query Query to use as search word
     * @return A list of matching FacebookUsers
     */
    public abstract List<FacebookUser> searchUsers(String query);

    /**
     * Retrieves the profile picture of a user. Where several sizes are
     * available, will retrieve the largest available
     * @param id Id of the user
     * @return A URL to the profile picture
     */
    public abstract URL getProfilePicture(String id);

    /**
     * Likes a post. Currently, Facebook only allows for pages
     * liking posts and comments on itself or other pages.
     * User likes can not be managed through the API.
     * @param id Id of the post
     * @return true if successful
     */
    public abstract boolean likePost(String id);


    /**
     * Unlikes a post. Currently, Facebook only allows for pages
     * liking posts and comments on itself or other pages.
     * User likes can not be managed through the API.
     * @param id Id of the post
     * @return true if successful
     */
    public abstract boolean unlikePost(String id);

    /**
     * Not supported by facebook. Always throws an exception.
     * @return Always throws an exception
     */
    public FacebookPost searchPost(String query) {
        throw new NotSupportedException("searchPost", PLATFORM);
    }

    /**
     * Gets the feed of a user or page.
     * If id is "me", will return the feed of the current user or page
     * @param id id of user or page
     * @return A list of posts
     */
    public abstract List<FacebookPost> getPostFeed(String id);

    /**
     * Not supported by facebook. Always throws an exception.
     * @param id -
     * @return Always throws an exception
     */
    public List<FacebookUser> getFollowers(String id) {
        throw new NotSupportedException("getFollowers", PLATFORM);
    }

    /**
     * Not supported by facebook. Always throws an exception.
     * @param id -
     * @return Always throws an exception
     */
    public List<FacebookUser> getFollowing(String id) {
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
}
