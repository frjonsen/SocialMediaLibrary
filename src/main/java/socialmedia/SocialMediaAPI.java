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
}