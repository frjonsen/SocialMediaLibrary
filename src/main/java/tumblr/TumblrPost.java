package tumblr;

import socialmedia.Post;

public class TumblrPost extends Post<TumblrUser>{
    private boolean liked;
    private String reblogKey;
    private int likeCount;

    @Override
    public String toString() {
        return "TumblrPost{" +
                super.baseToString() +
                ", liked=" + liked +
                ", reblogKey='" + reblogKey + '\'' +
                ", likeCount=" + likeCount +
                '}';
    }

    /**
     * Gets the number of likes this post has received
     * @return Number of likes
     */
    public int getLikeCount() {
        return likeCount;
    }

    /**
     * Sets the number of likes this post has received
     * @param likeCount Number of likes
     */
    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    /**
     * Gets the reblog key for this post. Used when liking or sharing a post
     * @return Reblog key
     */
    public String getReblogKey() {
        return reblogKey;
    }

    /**
     * Sets the reblog key for this post. used when liking or sharing a post
     * @param reblogKey Reblog key
     */
    public void setReblogKey(String reblogKey) {
        this.reblogKey = reblogKey;
    }

    /**
     * Gets whether this post has been liked by the authed user
     * @return True if authed user has liked the post
     */
    public boolean isLiked() {
        return liked;
    }

    /**
     * Sets whether this post has been liked by the authed user
     * @param liked Boolean whether post is liked
     */
    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
