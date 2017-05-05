package tumblr;

import socialmedia.NotSupportedException;
import socialmedia.SocialMediaBase;

import java.util.List;

public abstract class TumblrAPI extends SocialMediaBase<TumblrUser, TumblrPost> {
    protected static final String PLATFORM = "Tumblr";

    /**
     * Will get post with id from the active blog and
     * return an TumblrPost object.
     * @see TumblrPost
     * @param id id of the post
     * @return post object
     */
    @Override
    public abstract TumblrPost getPost(String id);

    /**
     * Will get post with id from the blog specified and
     * return an TumblrPost object.
     * @see TumblrPost
     * @param blogName name of blog to fetch from
     * @param id id of the post
     * @return post object
     */
    public abstract TumblrPost getPost(String blogName, String id);

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
     * Publishes a status post on the current active blog
     * with the text body of message.
     * @param message text to post
     * @return id of post created
     */
    @Override
    public abstract String publishStatusPost(String message);

    /**
     * Publishes a status post on the given blog,
     * as long as it is owned by the authenticating user,
     * with the text body of message.
     * @param blogName name of blog to post on
     * @param message text body to post
     * @return id of created post
     */
    public abstract String publishStatusPost(String blogName, String message);

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
