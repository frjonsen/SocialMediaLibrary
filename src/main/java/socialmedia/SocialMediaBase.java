package socialmedia;

import java.io.PrintStream;

public abstract class SocialMediaBase<T extends User, E extends Post> implements SocialMediaAPI<T,E>{
    private PrintStream stream;
    /**
     * Should be used as stand-in for functions which take a user id, where the ID can be inferred for
     * the authenticated user.
     */
    protected static final String SELF_ID = "me";

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

}