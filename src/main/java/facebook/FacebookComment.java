package facebook;

import socialmedia.SocialMediaUtil;

import java.time.ZonedDateTime;
import java.util.Date;

public class FacebookComment {
    private String message;
    private String id;
    private FacebookUser from;
    private ZonedDateTime created;
    private int likeCount;

    /**
     * Sets text of the comment
     * @return Message text
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set text of the comment
     * @param message Message text
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets id of the comment
     * @return Comment id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets if of the comment
     * @param id Comment id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the user who authored the comment
     * @return Author user
     */
    public FacebookUser getFrom() {
        return from;
    }

    /**
     * Sets the user who authored the comment
     * @param from Author user
     */
    public void setFrom(FacebookUser from) {
        this.from = from;
    }

    /**
     * Gets the date and time the comment was posted
     * @return Creation time
     */
    public ZonedDateTime getCreated() {
        return created;
    }

    /**
     * Sets the date and time the comment was posted
     * @param created Creation time
     */
    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    /**
     * Sets the date and time the comment was posted
     * This will internally be converted to a ZonedDateTime
     * As java.util.Date lacks a timezone, this function
     * assumes the timezone is UTC. For any other time zone,
     * the result will be incorrect
     * @param time Creation time
     */
    public void setCreated(Date time) {
        this.created = SocialMediaUtil.UTCJavaDateToZonedDateTime(time);
    }

    /**
     * Gets amount of likes the comment has received
     * @return Like count
     */
    public int getLikeCount() {
        return likeCount;
    }

    /**
     * Sets amount of likes the comment has received
     * @param likeCount Like count
     */
    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
}
