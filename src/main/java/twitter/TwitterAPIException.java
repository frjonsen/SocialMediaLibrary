package twitter;

import socialmedia.SocialMediaException;

/**
 * This is the type of exceptions thrown when something goes wrong
 * in twitter.
 */
public class TwitterAPIException extends SocialMediaException {
    public static final String PLATFORM = "TWITTER";

    public TwitterAPIException(String message) { super(message, PLATFORM); }
}
