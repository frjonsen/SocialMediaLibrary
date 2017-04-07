package socialmedia;

import java.time.*;
import java.net.URL;

/**
 * The base class of posts. These fields are what the different
 * platforms have in common. It gets created with what type of user
 * the post is associated with. For instance FacebookUser.
 * @param <T>   The type of user associated with the post
 */
public abstract class Post <T extends User> {
    /**
     * The post types that a post may be
     * <li>{@link #IMAGE}</li>
     * <li>{@link #TEXT}</li>
     * <li>{@link #VIDEO}</li>
     * <li>{@link #LINK}</li>
     * <li>{@link #OFFER}</li>
     */
    public enum Type{
        IMAGE, TEXT, VIDEO, LINK, OFFER
    }

    private Type type;
    private String text;
    private ZonedDateTime creationTime;
    private ZonedDateTime editTime;
    private String id;
    private int shareCount; // retweet count
    private Iterable<User> toUsers;
    private Iterable<String> tags;
    private URL permalink;
    private T author;

    /**
     * Returns the type of the post. If the post is a text,
     * video, image, link post etc.
     * @return  type of post
     */
    public Type getType() {
        return type;
    }

    /**
     * Sets the type of the post locally.
     * @param type  new type of post
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * Gets the body text of the post.
     * @return Body text
     */
    String getText() {
        return text;
    }

    /**
     * Set the body text of the post.
     * @param text Body text
     */
    void setText(String text) {
        this.text = text;
    }

    /**
     * Gets a copy of the time when the post was first created.
     * @return Creation time
     */
    ZonedDateTime getCreationTime() {
        return creationTime;
    }

    /**
     * Sets the time when the post was first created.
     * The passed time is copied before storing
     * @param time Creation time
     */
    void setCreationTime(ZonedDateTime time) {
        this.creationTime = time;
    }

    /**
     * Gets the time when the post was last changed.
     * If the post has never been changed, will return
     * the creation time.
     * @return Edit time
     */
    ZonedDateTime getEditTime() {
        if (editTime == null) {
            return this.getCreationTime();
        }
        return editTime;
    }

    /**
     * Sets the time when the post was last changed.
     * If the post has never been changed, this should be
     * set to the creation time.
     * @param time Edit time
     */
    void setEditTime(ZonedDateTime time) {
        this.editTime = time;
    }

    String getId() {
        return this.id;
    }

    void setId(String id) {
        this.id = id;
    }

    /**
     * Gets information about the user who created the post.
     * @return Author user
     */
    T getAuthor() {
        return this.author;
    }

    /**
     * Sets the information about the user who created the post
     * @param author Author user
     */
    void setAuthor(T author) {
        this.author = author;
    }

    /**
     * Gets the amount of times the post has been shared. Shares is
     * counted in some way that makes sense to individual networks.
     * @return Shares count
     */
    int getSharedCount() {
        return this.shareCount;
    }

    /**
     * Sets the amount of times the post has been shared.
     * @param count Shares count
     */
    void setSharedCount(int count) {
        this.shareCount = count;
    }

    /**
     * Sets the list of users the post was directed to.
     * What is considered a "to" depends on the network.
     * @return Iterable of users
     */
    Iterable<User> getTo() {
        return this.toUsers;
    }

    /**
     * Sets the list of users the post was directed to.
     * @param users Iterable of users
     */
    void setTo(Iterable<User> users) {
        this.toUsers = users;
    }

    /**
     * Gets the list of tags on the post. What is considered
     * a tag depends on the network.
     * @return Iterable of tags as strings
     */
    Iterable<String> getTags() {
        return this.tags;
    }

    /**
     * Sets the tags for the network
     * @param tags Iterable of tags as strings
     */
    void setTags(Iterable<String> tags) {
        this.tags = tags;
    }

    /**
     * A URL leading directly to the post
     * @return Permalink URL
     */
    URL getPermalink() {
        return this.permalink;
    }

    /**
     * Sets the URL leading directly to the post
     * @param permalink Permalink URL
     */
    void setPermalink(URL permalink) {
        this.permalink = permalink;
    }

}