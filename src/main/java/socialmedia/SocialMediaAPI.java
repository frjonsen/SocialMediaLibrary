package socialmedia;

import java.net.URL;

public interface SocialMediaAPI<T extends User, E extends Post> {
    T getUser(String id);

    //public abstract List<T> searchUsers(String query);
    /**
     * Gets the URL of the users profile image.
     * @param id site-wide id of the user
     * @return  the URL for the profile image of the user
     */
    URL getProfilePicture(String id);

    //public abstract boolean likePost(String id);

    //public abstract boolean unlikePost(String id);

    /**
     * Gets the post
     * @param id
     * @return
     */
    E getPost(String id);

    //public abstract searchPost();

    //public abstract getPostFeed();

    //public abstract publishPost();

    //public abstract getComments();

    //public abstract publishComment();

    //public abstract getFollowers();

    //public abstract getFollowing();

    //public abstract follow();

    //public abstract unfollow();


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
