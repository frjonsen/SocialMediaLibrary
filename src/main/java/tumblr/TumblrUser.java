package tumblr;

import socialmedia.User;
import java.util.List;

public class TumblrUser extends User {

    public enum UserType {
        BLOG,
        USER
    }

    private UserType type;
    private List<TumblrUser> blogs;

    public TumblrUser(UserType userType) {
        this.type = userType;
    }

    public UserType getType() {
        return type;
    }

    public List<TumblrUser> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<TumblrUser> blogs) {
        this.blogs = blogs;
    }
}
