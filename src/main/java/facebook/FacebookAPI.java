package facebook;

import socialmedia.SocialMediaAPI;

public abstract class FacebookAPI extends SocialMediaAPI<FacebookUser> {

    public abstract FacebookUser getUser(String id);
}
