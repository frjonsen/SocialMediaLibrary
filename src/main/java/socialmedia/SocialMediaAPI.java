package socialmedia;

public interface SocialMediaAPI<T extends User> {
    public abstract User getUser(String id);

    //public abstract List<T> searchUsers(String query);

    //public abstract URL getProfilePicture(String id, int size);

    //public abstract boolean likePost(String id);

    //public abstract boolean unlikePost(String id);

    public abstract Post getPost(String id);

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
