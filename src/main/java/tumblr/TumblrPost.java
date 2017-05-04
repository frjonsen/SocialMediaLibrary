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

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getReblogKey() {
        return reblogKey;
    }

    public void setReblogKey(String reblogKey) {
        this.reblogKey = reblogKey;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
