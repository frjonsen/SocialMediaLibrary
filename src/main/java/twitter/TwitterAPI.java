package twitter;

import socialmedia.SocialMediaBase;
import twitter4j.RateLimitStatus;

import java.util.List;
import java.util.Map;

/**
 * Contains functionality for interacting with Twitter.
 */
public abstract class TwitterAPI extends SocialMediaBase<TwitterUser, TwitterPost> {

    /**
     * Gets a TwitterUser by provided screenName, filling a user object
     * with available information. Calling the function with the string "me"
     * will return the authenticating users info.
     * As users have a large degree of flexibility
     * regarding what's visible, some data may be null, even if the user has
     * set the information.
     * @param screenName site-wide screen name of the user
     * @return a TwitterUser found
     */
    public abstract TwitterUser getUser(String screenName);

    /**
     * Gets a TwitterUser by provided id, filling a user object
     * with available information. As users have a large degree of flexibility
     * regarding what's visible, some data may be null, even if the user has
     * set the information.
     * @param id site-wide id of the user
     * @return a TwitterUser found
     */
    public abstract TwitterUser getUser(long id);

    /**
     * Gets a TwitterPost by provided id, filling a post object
     * with available information. Some fields may be null.
     * @param id
     * @return a TwitterPost
     */
    public abstract TwitterPost getPost(String id);

    /**
     * Gets the post-feed of a user.
     * If id is "me", will return the feed of the authenticating user.
     * @param screenName name of the user
     * @return list of twitter posts
     */
    public abstract List<TwitterPost> getPostFeed(String screenName);

    /**
     * Gets the post-feed of a user with id.
     * @param id id of the user
     * @return list of twitter posts
     */
    public abstract List<TwitterPost> getPostFeed(long id);

    /**
     * Makes authenticating users account follow account
     * of given screen name. If input is an id it will try to
     * redirect the call to the correct function. Returns true if successful
     * @param screenName screen name of user to follow
     * @return if successful
     */
    public abstract boolean follow(String screenName);

    /**
     * Makes authenticating users account follow account
     * of given screen name. If input is an id it will try to
     * redirect the call to the correct function. Returns true if successful
     * @param notifications if notifications should be enabled or disabled from this user
     * @param screenName screen name of user to follow
     * @return if successful
     */
    public abstract boolean follow(String screenName, boolean notifications);

    /**
     * Makes authenticating users account follow account
     * of given id. Returns true if successful
     * @param id id of user to follow
     * @return if successful
     */
    public abstract boolean follow(long id);

    /**
     * Makes authenticating users account follow account
     * of given id. Returns true if successful
     * @param notifications if notifications should be enabled or disabled from this user
     * @param id id of user to follow
     * @return if successful
     */
    public abstract boolean follow(long id, boolean notifications);

    /**
     * Makes authenticating users account unfollow account
     * of screen name. If input is of id type it will redirect it
     * to the correct function. Returns true if successful
     * @param screenName id of user to unfollow
     * @return if successful
     */
    public abstract boolean unfollow(String screenName);

    /**
     * Makes authenticating users account unfollow account
     * of id. Returns true if successful
     * @param id id of user to unfollow
     * @return if successful
     */
    public abstract boolean unfollow(long id);

    /**
     * Gets the rate limit status for the authenticating user
     * @see RateLimitStatus
     * @return  map of rate statuses
     */
    public abstract Map<String, RateLimitStatus> getRateLimitStatus();

    public abstract boolean destroyStatusPost(String id);

    public abstract boolean destroyStatusPost(long id);


}
