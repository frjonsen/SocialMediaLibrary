package facebook;

import socialmedia.SocialMediaAPI;

import java.util.List;

public abstract class FacebookAPI extends SocialMediaAPI<FacebookUser> {

    /**
     * Gets a Facebook user, filling a user object with the available information.
     * As users have a large degree of flexibility regarding what's visible,
     * some data may be null, even if the user has set the information.
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
}
