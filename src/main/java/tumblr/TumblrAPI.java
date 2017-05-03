package tumblr;

public abstract class TumblrAPI {
    abstract TumblrUser getAuthedUser();

    public abstract  TumblrUser getUser(String id);
}
