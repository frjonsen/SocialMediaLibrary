package tumblr;

import java.net.URL;

public abstract class TumblrAPI {
    abstract TumblrUser getAuthedUser();

    public abstract  TumblrUser getUser(String id);

    public abstract URL getProfilePicture(String id);
}
