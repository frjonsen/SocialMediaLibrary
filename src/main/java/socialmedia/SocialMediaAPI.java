package socialmedia;

import java.net.URL;
import java.util.List;

public interface SocialMediaAPI<T extends User, E extends Post> {
    T getUser(String id);

    //public abstract List<T> searchUsers(String query);
    /**
     * Gets the URL of the users profile image.
     * @param id site-wide id of the user
     * @return  the URL for the profile image of the user
     */
    URL getProfilePicture(String id);

    /**
     * Likes a post. Currently, Facebook only allows for pages
     * liking posts and comments on itself or other pages.
     * User likes can not be managed through the API.
     * @param id Id of the post
     * @return true if successful
     */
    boolean likePost(String id);

    /**
     * Unlikes a post. Currently, Facebook only allows for pages
     * liking posts and comments on itself or other pages.
     * User likes can not be managed through the API.
     * @param id Id of the post
     * @return true if successful
     */
    boolean unlikePost(String id);

    /**
     * Gets the post with the id. Differs what type of post
     * returned depending on what platform it is called from.
     * @param id id of the post
     * @return post of type associated with platform
     */
    E getPost(String id);

    String publishStatusPost(String message);

    //public abstract searchPost(); finns inte fb

    /**
     * Gets the users most recent posts published. Might differ
     * on different platforms where it can be screen name as well.
     * @param id id of the user
     * @return a list of posts
     */
    List<E> getPostFeed(String id);

    //public abstract publishStatusPost(String message);

    //public abstract publishComment();

    /**
     * Gets followers for user with given id and returns a list
     * of users for the platform. Only id field is always present.
     * Call with maxCalls -1 to get all available users in list, else
     * specify how many calls should be made to the api.If id parameter
     * is set to "me", will fetch authenticating users following.
     * @param id id of user
     * @param maxCalls max number of calls to api
     * @return list of users
     */
    List<T> getFollowers(String id, int maxCalls);

    /**
     * Gets following for user with given id and returns a list
     * of users for the platform. Only id field is always present.
     * Call with maxCalls -1 to get all available users in list, else
     * specify how many calls should be made to the api. If id parameter
     * is set to "me", will fetch authenticating users following.
     * @param id id of user
     * @param maxCalls max number of calls to api
     * @return list of users
     */
    List<T> getFollowing(String id, int maxCalls);

    boolean follow(String id);

    boolean unfollow(String id);


    /* Implemented individually
    comments
    likes post

    get RSVP's
    create event
    user check ins??
    friend request ---> NOH!
    get friends
    DM features
     */
}
