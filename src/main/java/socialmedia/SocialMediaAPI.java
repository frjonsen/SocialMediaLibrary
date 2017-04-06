package socialmedia;

import java.io.PrintStream;
import java.net.URL;
import java.util.List;

public abstract class SocialMediaAPI<T extends User>{
    private PrintStream stream;

    public void setDebugStream(PrintStream stream) {
        this.stream = stream;
    }

    protected void debug(Object o) {
        if (stream != null) {
            stream.println(o.toString());
        }
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

    //public abstract List<T> searchUser(String query);

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