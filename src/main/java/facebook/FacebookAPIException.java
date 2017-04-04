package facebook;

import socialmedia.SocialMediaException;

public class FacebookAPIException extends SocialMediaException {
    public static final String PLATFORM = "FACEBOOK";

    public FacebookAPIException(String message) {
        super(message, PLATFORM);
    }
}
