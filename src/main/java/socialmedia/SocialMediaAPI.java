package socialmedia;

import java.io.PrintStream;

public abstract class SocialMediaAPI {
    private PrintStream stream;

    public void setDebugStream(PrintStream stream) {
        this.stream = stream;
    }

    protected void debug(String s) {
        if(stream != null)
            stream.println(s);
    }

    protected void debugf(String s, Object... args) {
        if(stream != null)
            stream.printf(s, args);
    }

    public abstract User getUser(String id);

    //public abstract searchUser();

    //public abstract getProfilePicture();

    //public abstract likePost();

    //public abstract unlikePost();

    //public abstract getPost();

    //public abstract searchPost();

    //public abstract getPostFeed();

    //public abstract publishPost();

    //public abstract publishComment();

    //public abstract getFollowers();

    //public abstract getFollowing();

    //public abstract follow();

    //public abstract unFollow();




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