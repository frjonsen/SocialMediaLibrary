package tumblr;

import socialmedia.SocialMediaException;

public class TumblrAPIException extends SocialMediaException {
    public static final String PLATFORM = "TUMBLR";

    public TumblrAPIException(String message) { super(message, PLATFORM); }
}
