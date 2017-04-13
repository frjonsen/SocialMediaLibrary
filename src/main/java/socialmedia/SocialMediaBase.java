package socialmedia;

import java.io.PrintStream;

public abstract class SocialMediaBase<T extends User> implements SocialMediaAPI<T>{
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

}