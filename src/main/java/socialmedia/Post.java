package socialmedia;

import java.net.URL;
import java.time.ZonedDateTime;

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
        IMAGE, TEXT, VIDEO, LINK, OFFER, CHAT, AUDIO, ANSWER, QUOTE, UNKNOWN
    }

    private Type type;
    private String text;
    private ZonedDateTime creationTime;
    private ZonedDateTime editTime;
    private String id;
    private int shareCount;
    private Iterable<T> toUsers;
    private Iterable<String> tags;
    private URL permalink;
    private T author;

    @Override
    public String toString() {
        return "Post{" +
                "type=" + type +
                ", text='" + text + '\'' +
                ", creationTime=" + creationTime +
                ", editTime=" + editTime +
                ", id='" + id + '\'' +
                ", shareCount=" + shareCount +
                ", toUsers=" + toUsers +
                ", tags=" + tags +
                ", permalink=" + permalink +
                ", author=" + author +
                '}';
    }

    protected String baseToString() {
        return "type=" + type +
                ", text='" + text + '\'' +
                ", creationTime=" + creationTime +
                ", editTime=" + editTime +
                ", id='" + id + '\'' +
                ", shareCount=" + shareCount +
                ", toUsers=" + toUsers +
                ", tags=" + tags +
                ", permalink=" + permalink +
                ", author=" + author;
    }



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
    public String getText() {
        return text;
    }

    /**
     * Set the body text of the post.
     * time must be in UTC, or the result will be incorrect
     * @param text Body text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Sets the creation time using a java.util.Date
     * This will internally be converted to a ZonedDateTime
     * As java.util.Date lacks a timezone, this function
     * assumes the timezone is UTC.
     * @param time Creation time of the post
     */
    public void setCreationTime(java.util.Date time) {
        this.creationTime = SocialMediaUtil.dateToZonedDateTime(time);
    }

    /**
     * Gets a copy of the time when the post was first created.
     * @return Creation time
     */
    public  ZonedDateTime getCreationTime() {
        return creationTime;
    }

    /**
     * Sets the time when the post was first created.
     * The passed time is copied before storing
     * @param time Creation time
     */
    public  void setCreationTime(ZonedDateTime time) {
        this.creationTime = time;
    }

    /**
     * Gets the time when the post was last changed.
     * If the post has never been changed, will return
     * the creation time.
     * @return Edit time
     */
    public  ZonedDateTime getEditTime() {
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
    public void setEditTime(ZonedDateTime time) {
        this.editTime = time;
    }

    /**
     * Sets the creation time using a java.util.Date
     * This will internally be converted to a ZonedDateTime
     * As java.util.Date lacks a timezone, this function
     * assumes the timezone is UTC.
     * @param time Creation time of the post
     */
    public void setEditTime(java.util.Date time) {
        this.editTime = SocialMediaUtil.dateToZonedDateTime(time);
    }

    /**
     * Gets the id of the post
     * @return Id of the post
     */
    public String getId() {
        return this.id;
    }

    /**
     * Sets the id of the post
     * @param id Id of the post
     */
    public  void setId(String id) {
        this.id = id;
    }

    /**
     * Gets information about the user who created the post.
     * @return Author user
     */
    public  T getAuthor() {
        return this.author;
    }

    /**
     * Sets the information about the user who created the post
     * @param author Author user
     */
    public void setAuthor(T author) {
        this.author = author;
    }

    /**
     * Gets the amount of times the post has been shared. Shares is
     * counted in some way that makes sense to individual networks.
     * @return Shares count
     */
    public int getSharedCount() {
        return this.shareCount;
    }

    /**
     * Sets the amount of times the post has been shared.
     * @param count Shares count
     */
    public void setSharedCount(int count) {
        this.shareCount = count;
    }

    /**
     * Sets the list of users the post was directed to.
     * What is considered a "to" depends on the network.
     * @return Iterable of users
     */
    public Iterable<T> getTo() {
        return this.toUsers;
    }

    /**
     * Sets the list of users the post was directed to.
     * @param users Iterable of users
     */
    public void setTo(Iterable<T> users) {
        this.toUsers = users;
    }

    /**
     * Gets the list of tags on the post. What is considered
     * a tag depends on the network.
     * @return Iterable of tags as strings
     */
    public Iterable<String> getTags() {
        return this.tags;
    }

    /**
     * Sets the tags for the network
     * @param tags Iterable of tags as strings
     */
    public void setTags(Iterable<String> tags) {
        this.tags = tags;
    }

    /**
     * A URL leading directly to the post
     * @return Permalink URL
     */
    public URL getPermalink() {
        return this.permalink;
    }

    /**
     * Sets the URL leading directly to the post
     * @param permalink Permalink URL
     */
    public void setPermalink(URL permalink) {
        this.permalink = permalink;
    }

}