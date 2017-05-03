package tumblr;

import socialmedia.Post;

public class TumblrPost extends Post<TumblrUser>{
    private boolean liked;

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
