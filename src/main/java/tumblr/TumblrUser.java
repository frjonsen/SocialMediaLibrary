package tumblr;

import socialmedia.User;
import java.util.List;

public class TumblrUser extends User {

    @Override
    public String toString() {
        return "TumblrUser{" +
                super.baseToString() +
                "type=" + type +
                ", blogs=" + blogs +
                '}';
    }

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
