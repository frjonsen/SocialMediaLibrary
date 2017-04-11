package facebook;

import socialmedia.SocialMediaAPI;

public abstract class FacebookAPI extends SocialMediaAPI<FacebookUser> {

    /**
     * Gets a Facebook user, filling a user object with the available information.
     * As users have a large degree of flexibility regarding what's visible,
     * some data may be null, even if the user has set the information.
     * @param id Site-wide id of the user
     * @return A FacebookUser
     */
    public abstract FacebookUser getUser(String id);

    public abstract FacebookPost getPost(String id);
}
