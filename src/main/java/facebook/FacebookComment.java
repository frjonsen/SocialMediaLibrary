package facebook;

import java.time.ZonedDateTime;

public class FacebookComment {
    private String message;
    private String id;
    private FacebookUser from;
    private ZonedDateTime created;
    private int likeCount;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FacebookUser getFrom() {
        return from;
    }

    public void setFrom(FacebookUser from) {
        this.from = from;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
}
