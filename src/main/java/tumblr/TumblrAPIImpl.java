package tumblr;

import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.exceptions.JumblrException;

import com.tumblr.jumblr.types.*;
import socialmedia.NotSupportedException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import java.util.List;

import static socialmedia.Post.Type.*;
import static tumblr.TumblrUser.UserType.BLOG;
import static tumblr.TumblrUser.UserType.USER;

public class TumblrAPIImpl extends TumblrAPI {

    private JumblrClient libraryInstance;

    public TumblrAPIImpl(String consumerKey, String consumerSecret, String accessToken, String accessSecret) {
        libraryInstance = new JumblrClient(consumerKey, consumerSecret, accessToken, accessSecret);
    }

    @Override
    public List<TumblrUser> searchUsers(String query) {
        throw new NotSupportedException("searchUsers", PLATFORM);
    }

    @Override
    public boolean likePost(String id) {
        return false;
    }

    @Override
    public boolean unlikePost(String id) {
        return false;
    }

    @Override
    public TumblrPost getPost(String id) {
        try {
            long lId = Long.parseLong(id);
            Post post = libraryInstance.blogPost(this.activeBlog, lId);
            return jumblrPostConversion(post);
        } catch(JumblrException|MalformedURLException e) {
            debug(e);
            throw new TumblrAPIException(e.getMessage());
        }
    }

    @Override
    public String publishStatusPost(String message) {
        try {
            Map<String, String> options = new HashMap<>();
            options.put("type", "text");
            options.put("body")
           libraryInstance.postCreate(this.activeBlog, options);
        } catch(JumblrException je) {
            debug(je);
            throw new TumblrAPIException(je.getMessage());
        }

    }

    @Override
    public boolean destroyStatusPost(String id) {
        return false;
    }

    @Override
    public List<TumblrPost> searchPost(String query, int maxCalls) {
        return null;
    }

    @Override
    public List<TumblrPost> getPostFeed(String id) {
        return null;
    }

    @Override
    public List<TumblrUser> getFollowers(String id, int maxCalls) {
        int maximumCalls = maxCalls == -1 ? Integer.MAX_VALUE : maxCalls; // To make Sonar happy
        final int limit = 20; // Maximum amount of users tumblr allows per call
        Map<String, Integer> options = new HashMap<>();
        List<TumblrUser> followers = new ArrayList<>();
        for (int i = 0; i < maximumCalls; ++i) {
            int offset = i*limit;
            options.put("offset", offset);
            options.put("limit", limit);
            List<User> followersChunk;
            try {
                followersChunk = libraryInstance.blogFollowers(id, options);
            } catch(JumblrException je) {
                debug(je);
                throw new TumblrAPIException(je.getMessage());
            }

            if (followersChunk.isEmpty())
                break;
            followers.addAll(followersChunk.stream().map(TumblrAPIImpl::jumblrUserToUserConversion).collect(Collectors.toList()));
        }
        return followers;
    }

    /**
     * Gets the blogs the authed user is following. Id is not used.
     * @param id id of user
     * @param maxCalls max number of calls to api
     * @return List of blogs user is following
     */
    @Override
    public List<TumblrUser> getFollowing(String id, int maxCalls) {
        int maximumCalls = maxCalls == -1 ? Integer.MAX_VALUE : maxCalls; // To make Sonar happy

        final int limit = 20; // Maximum number of blogs tumblr allows per call
        Map<String, Integer> options = new HashMap<>();
        List<TumblrUser> following = new ArrayList<>();
        for (int i = 0; i < maximumCalls; ++i) {
            int offset = i * limit;
            options.put("offset", offset);
            options.put("limit", limit);
            List<Blog> followingChunk;
            try {
                followingChunk = libraryInstance.userFollowing(options);
            } catch (JumblrException je) {
                debug(je);
                throw new TumblrAPIException(je.getMessage());
            }

            if (followingChunk.isEmpty())
                break;
            following.addAll(followingChunk.stream().map(TumblrAPIImpl::jumblrBlogToUserConversion).collect(Collectors.toList()));
        }

        return following;
    }

    @Override
    public boolean follow(String id) {
        Blog blog = libraryInstance.blogInfo(id);
        if (blog == null)
            throw new TumblrAPIException("No blog with id \"" + id + "\"");
        libraryInstance.follow(id);
        return true; // jumblr doesn't return whether it worked. Assume it worked as long as blog exists.
    }

    @Override
    public boolean unfollow(String id) {
        Blog blog = libraryInstance.blogInfo(id);
        if (blog == null)
            throw new TumblrAPIException("No blog with id \"" + id + "\"");
        libraryInstance.unfollow(id);
        return true; // jumblr doesn't return whether it worked. Assume it worked as long as blog exists.
    }

    private TumblrPost jumblrPostConversion(Post jumblrPost) {
        if(jumblrPost == null) {
            return null;
        }
        TumblrPost post = new TumblrPost();
        post.setLiked(jumblrPost.isLiked());

        if(jumblrPost.getAuthorId() != null) {
            TumblrUser user = new TumblrUser(USER);
            user.setId(jumblrPost.getAuthorId());
            post.setAuthor(user);
        }
        Date date = new Date();
        date.setTime(jumblrPost.getTimestamp() * 1000);
        post.setCreationTime(date);
        post.setId(String.valueOf(jumblrPost.getId()));
        try {
            post.setPermalink(new URL(jumblrPost.getPostUrl()));
        } catch (MalformedURLException me) {
            debug(me);
            throw new TumblrAPIException(me.getMessage());
        }

        long noteCount = jumblrPost.getNoteCount();
        if(jumblrPost.getNotes() != null) {
            long reblogCount = jumblrPost.getNotes()
                    .stream()
                    .filter(note -> "reblog".equals(note.getType()))
                    .count();
            post.setSharedCount((int) reblogCount);
            post.setLikeCount((int) (noteCount - reblogCount));
        }
        post.setTags(jumblrPost.getTags());
        if (jumblrPost.getRebloggedFromName() != null) {
            TumblrUser rebloggedFrom = new TumblrUser(BLOG);
            rebloggedFrom.setName(jumblrPost.getRebloggedFromName());
            post.setTo(Arrays.asList(rebloggedFrom));
        }
        post.setReblogKey(jumblrPost.getReblogKey());

        return postTypeSpecificConversion(post, jumblrPost);
    }

    private TumblrPost postTypeSpecificConversion(TumblrPost post, Post jumblrPost) {
        switch(jumblrPost.getType().toUpperCase()) {
            case "TEXT":
                return textPostConverter(post, jumblrPost);
            case "PHOTO":
                return photoPostConverter(post, jumblrPost);
            case "QUOTE":
                return quotePostConverter(post, jumblrPost);
            case "LINK":
                return linkPostConverter(post, jumblrPost);
            case "CHAT":
                return chatPostConverter(post, jumblrPost);
            case "AUDIO":
                return audioPostConverter(post, jumblrPost);
            case "VIDEO":
                return videoPostConverter(post, jumblrPost);
            case "ANSWER":
                return answerPostConverter(post, jumblrPost);
            default:
                post.setType(UNKNOWN);
                return post;
        }
    }

    private TumblrPost photoPostConverter(TumblrPost tumblrPost, Post jumblrPost) {
        PhotoPost post = (PhotoPost)jumblrPost;
        tumblrPost.setType(IMAGE);

        String body = post.getCaption();
        String text = body + "\n\n";
        text += post.getPhotos()
                .stream()
                .map(photo -> photo.getOriginalSize().getUrl())
                .reduce("", (a, b) -> a + "\n" + "\"" + b +"\"");

        tumblrPost.setText(text);
        return tumblrPost;
    }

    private TumblrPost quotePostConverter(TumblrPost tumblrPost, Post jumblrPost) {
        tumblrPost.setType(QUOTE);
        QuotePost quotePost = (QuotePost)jumblrPost;
        String text = quotePost.getText() + "source: \"" + quotePost.getSource() + "\"";
        tumblrPost.setText(text);

        return tumblrPost;
    }

    private TumblrPost textPostConverter(TumblrPost tumblrPost, Post jumblrPost) {
        tumblrPost.setType(TEXT);
        TextPost textPost = (TextPost)jumblrPost;
        String text = textPost.getTitle() + "\n\n" + textPost.getBody();;
        tumblrPost.setText(text);

        return tumblrPost;
    }

    private TumblrPost linkPostConverter(TumblrPost tumblrPost, Post jumblrPost) {
        tumblrPost.setType(LINK);
        LinkPost linkPost = (LinkPost)jumblrPost;
        String text = linkPost.getTitle() +
                "\n\n" + linkPost.getDescription() +
                "\n\n" + "source: \"" + linkPost.getLinkUrl() + "\"";
        tumblrPost.setText(text);

        return tumblrPost;
    }

    private TumblrPost chatPostConverter(TumblrPost tumblrPost, Post jumblrPost) {
        tumblrPost.setType(CHAT);
        ChatPost chatPost = (ChatPost)jumblrPost;
        String text = chatPost.getTitle() + "\n\n" + chatPost.getBody();
        tumblrPost.setText(text);

        return tumblrPost;
    }

    private TumblrPost audioPostConverter(TumblrPost tumblrPost, Post jumblrPost) {
        tumblrPost.setType(AUDIO);
        AudioPost audioPost = (AudioPost)jumblrPost;
        String text = audioPost.getSourceTitle() +
                "\n\n" + audioPost.getCaption() +
                "\n\n" + "url: \"" + audioPost.getSourceUrl() + "\"";
        tumblrPost.setText(text);

        return tumblrPost;
    }

    private TumblrPost videoPostConverter(TumblrPost tumblrPost, Post jumblrPost) {
        tumblrPost.setType(VIDEO);
        VideoPost videoPost = (VideoPost)jumblrPost;
        String text = videoPost.getCaption() + "\n\n" +
                "url: \"" + videoPost.getThumbnailUrl() + "\"";
        tumblrPost.setText(text);

        return tumblrPost;
    }

    private TumblrPost answerPostConverter(TumblrPost tumblrPost, Post jumblrPost) {
        tumblrPost.setType(ANSWER);
        AnswerPost answerPost = (AnswerPost)jumblrPost;
        String askingName = answerPost.getAskingName();

        TumblrUser questionUser = new TumblrUser(BLOG);
        questionUser.setName(askingName);
        if(tumblrPost.getTo() == null){
            tumblrPost.setTo(Arrays.asList(questionUser));
        } else {
            List<TumblrUser> toList = (List<TumblrUser>)tumblrPost.getTo();
            toList.add(questionUser);
            tumblrPost.setTo(toList);
        }

        String text = askingName + "\n" + answerPost.getQuestion() + "\n\n" + answerPost.getAnswer();
        tumblrPost.setText(text);

        return tumblrPost;
    }

    TumblrAPIImpl(JumblrClient client) {
        libraryInstance = client;
    }

    static TumblrUser jumblrBlogToUserConversion(Blog blog) {
        if (blog == null) {
            return null;
        }
        TumblrUser user = new TumblrUser(TumblrUser.UserType.BLOG);
        user.setId(blog.getName());
        user.setUsername(blog.getName());
        user.setName(blog.getTitle());
        user.setBiography(blog.getDescription());
        user.setUploadCount(blog.getPostCount());
        user.setFollowersCount(blog.getFollowersCount());

        return user;
    }

    static TumblrUser jumblrUserToUserConversion(User jumblrUser) {
        if (jumblrUser == null) {
            return null;
        }
        TumblrUser user = new TumblrUser(USER);
        user.setName(jumblrUser.getName());
        user.setId(jumblrUser.getName());
        user.setUsername(jumblrUser.getName());
        List<Blog> userBlogs = jumblrUser.getBlogs();
        List<TumblrUser> blogs = new ArrayList<>();
        if (userBlogs != null) {
            blogs = userBlogs.stream().map(TumblrAPIImpl::jumblrBlogToUserConversion).collect(Collectors.toList());
        }
        user.setBlogs(blogs);
        user.setFollowingCount(jumblrUser.getFollowingCount());

        return user;
    }

    TumblrUser getAuthedUser() {
        return jumblrUserToUserConversion(libraryInstance.user());
    }

    public TumblrUser getUser(String id) {
        return jumblrBlogToUserConversion(libraryInstance.blogInfo(id));
    }

    @Override
    public URL getProfilePicture(String id) {
        String rawUrl;
        try {
            rawUrl = libraryInstance.blogAvatar(id, 512);
        } catch (JumblrException je) {
            debug(je);
            throw new TumblrAPIException(je.getMessage());
        }

        try {
            return new URL(rawUrl);
        } catch (MalformedURLException me) {
            debug(me);
            throw new TumblrAPIException(me.getMessage());
        }
    }

}
