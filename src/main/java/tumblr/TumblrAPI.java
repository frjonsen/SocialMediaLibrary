package tumblr;

import socialmedia.NotSupportedException;
import socialmedia.SocialMediaBase;

import java.util.List;

public abstract class TumblrAPI extends SocialMediaBase<TumblrUser, TumblrPost> {
    protected static final String PLATFORM = "Tumblr";

    /**
     * Will throw an NotSupportedException because
     * getPost should be called with blogName and id
     * as params.
     */
    @Override
    public TumblrPost getPost(String id) { throw new NotSupportedException("getPost", PLATFORM); }

    /**
     * Tumbler requires both blogName and id to get
     * a post unlike other platforms. Returns a TumblrPost
     * @param blogName name of blog that published post
     * @param id id of post
     * @return post object
     */
    public abstract TumblrPost getPost(String blogName, long id);

    /**
     * Gets the user object of the authenticating user
     * @return user object
     */
    abstract TumblrUser getAuthedUser();

    /**
     * Returns the user object of for the given
     * blogname.
     * @param blogName name of blog
     * @return user object
     */
    public abstract  TumblrUser getUser(String blogName);

    /**
     * dis be broken ma'm
     * @param message text to post
     * @return
     */
    @Override
    public String publishStatusPost(String message) { throw new NotSupportedException("getPost", PLATFORM); }

    /**
     * Gets all the users following a specified blog. Tumblr only allows getting followers for the
     * authed users blogs.
     * @param id id of blog
     * @param maxCalls max number of calls to api
     * @return A list of users following the blog
     */
    @Override
    public abstract List<TumblrUser> getFollowers(String id, int maxCalls);
}
