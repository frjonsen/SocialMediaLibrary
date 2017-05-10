package tumblr;

import socialmedia.User;

import java.util.List;

/**
 * Extends socialmedia.User. Fields here are
 * Tumblr specific.
 * @see socialmedia.User
 */
public class TumblrUser extends User {

    @Override
    public String toString() {
        return "TumblrUser{" +
                super.baseToString() +
                "type=" + type +
                ", blogs=" + blogs +
                '}';
    }

    /**
     * Specifies what kind of TumblrUser the object is, a
     * blog or a user.
     */
    public enum UserType {
        BLOG,
        USER
    }

    private UserType type;
    private List<TumblrUser> blogs;

    /**
     * Constructor for TumblrUser. Takes type of user as an
     * argument, as a user might be of blog or user type.
     * @param userType
     */
    public TumblrUser(UserType userType) {
        this.type = userType;
    }

    /**
     * Gets the type of the user object
     * @see UserType
     * @return UserType
     */
    public UserType getType() {
        return type;
    }

    /**
     * Returns blogs the user administrates. Only affects TumblrUser
     * if it is of User type.
     * @return list of blogs
     */
    public List<TumblrUser> getBlogs() {
        return blogs;
    }

    /**
     * Sets blogs user administrates, locally.
     * @param blogs list of blogs user administrates
     */
    public void setBlogs(List<TumblrUser> blogs) {
        this.blogs = blogs;
    }
}
