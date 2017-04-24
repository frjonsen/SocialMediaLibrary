package twitter;

import socialmedia.SocialMediaBase;
import twitter4j.RateLimitStatus;

import java.util.List;
import java.util.Map;

public abstract class TwitterAPI extends SocialMediaBase<TwitterUser, TwitterPost> {

    protected static final String PLATFORM = "Twitter";

    /**
     * Gets a TwitterUser by provided screenName, filling a user object
     * with available information. As users have a large degree of flexibility
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

    //public abstract List<TwitterUser> searchUsers(String query);

    /**
     * Gets a TwitterPost by provided id, filling a post object
     * with available information. Some fields may be null.
     * @param id
     * @return
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

    public abstract boolean follow(String screenName);
    public abstract boolean follow(String screenName, boolean notifications);
    public abstract boolean follow(long id);
    public abstract boolean follow(long id, boolean notifications);

    public abstract boolean unfollow(String id);
    public abstract boolean unfollow(long id);

    /**
     * Gets the rate limit status for the authenticating user
     * @see RateLimitStatus
     * @return  map of rate statuses
     */
    public abstract Map<String, RateLimitStatus> getRateLimitStatus();


}
