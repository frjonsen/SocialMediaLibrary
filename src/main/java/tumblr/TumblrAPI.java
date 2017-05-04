package tumblr;

import socialmedia.NotSupportedException;
import socialmedia.SocialMediaBase;
import java.net.URL;

public abstract class TumblrAPI extends SocialMediaBase<TumblrUser, TumblrPost> {
    private static final String PLATFORM = "Tumblr";

    /**
     * Will throw an NotSupportedException because
     * getPost should be called with blogName and id
     * as params.
     */
    @Override
    public TumblrPost getPost(String id) { throw new NotSupportedException("getPost", PLATFORM); }

    public abstract TumblrPost getPost(String blogName, long id);

    abstract TumblrUser getAuthedUser();

    public abstract  TumblrUser getUser(String id);

    public abstract URL getProfilePicture(String id);
}
