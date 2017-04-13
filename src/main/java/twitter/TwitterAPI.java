package twitter;

import socialmedia.SocialMediaBase;

public abstract class TwitterAPI extends SocialMediaBase<TwitterUser> {

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

    /**
     *
     * @param id
     * @return
     */
    public abstract TwitterPost getPost(String id);
}
