package twitter;

import socialmedia.SocialMediaException;

public class TwitterAPIException extends SocialMediaException {
    public static final String PLATFORM = "TWITTER";

    public TwitterAPIException(String message) { super(message, PLATFORM); }
}
