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
    public TumblrPost getPost(String blogName, long id) {
        try {
            Post post = libraryInstance.blogPost(blogName, id);
            return jumblrPostConversion(post);
        } catch(JumblrException je) {
            debug(je);
            throw new TumblrAPIException(je.getMessage());
        }
    }

    @Override
    public String publishStatusPost(String message) {
        return null;
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

    /**
     * Gets all the users following a specified blog. Tumblr only allows getting followers for the
     * authed users blogs.
     * @param id id of blog
     * @param maxCalls max number of calls to api
     * @return A list of users following the blog
     */
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
        String type = jumblrPost.getType();
        String text;
        switch(type.toUpperCase()) {
            case "TEXT":
                post.setType(TEXT);
                TextPost textPost = (TextPost)jumblrPost;
                text = textPost.getTitle() + "\n\n" + textPost.getBody();;
                post.setText(text);
                break;
            case "PHOTO":
                post.setType(IMAGE);
                PhotoPost photoPost = (PhotoPost)jumblrPost;
                post.setText(formatPhotoPostText(photoPost));
                break;
            case "QUOTE":
                post.setType(QUOTE);
                QuotePost quotePost = (QuotePost)jumblrPost;
                text = quotePost.getText() + "source: \"" + quotePost.getSource() + "\"";
                post.setText(text);
                break;
            case "LINK":
                post.setType(LINK);
                LinkPost linkPost = (LinkPost)jumblrPost;
                text = linkPost.getTitle() +
                        "\n\n" + linkPost.getDescription() +
                        "\n\n" + "source: \"" + linkPost.getLinkUrl() + "\"";
                post.setText(text);
                break;
            case "CHAT":
                post.setType(CHAT);
                ChatPost chatPost = (ChatPost)jumblrPost;
                text = chatPost.getTitle() + "\n\n" + chatPost.getBody();
                post.setText(text);
                break;
            case "AUDIO":
                post.setType(AUDIO);
                AudioPost audioPost = (AudioPost)jumblrPost;
                text = audioPost.getSourceTitle() +
                        "\n\n" + audioPost.getCaption() +
                        "\n\n" + "url: \"" + audioPost.getSourceUrl() + "\"";
                post.setText(text);
                break;
            case "VIDEO":
                post.setType(VIDEO);
                VideoPost videoPost = (VideoPost)jumblrPost;
                text = videoPost.getCaption() + "\n\n" +
                        "url: \"" + videoPost.getThumbnailUrl() + "\"";
                post.setText(text);
                break;
            case "ANSWER":
                post.setType(ANSWER);
                AnswerPost answerPost = (AnswerPost)jumblrPost;
                String askingName = answerPost.getAskingName();

                TumblrUser questionUser = new TumblrUser(BLOG);
                questionUser.setName(askingName);
                if(post.getTo() == null){
                    post.setTo(Arrays.asList(questionUser));
                } else {
                    List<TumblrUser> toList = (List<TumblrUser>)post.getTo();
                    toList.add(questionUser);
                    post.setTo(toList);
                }

                text = askingName + "\n" + answerPost.getQuestion() + "\n\n" + answerPost.getAnswer();

                post.setText(text);

                break;
            default:
                post.setType(UNKNOWN);
                break;
        }
        return post;
    }

    private String formatPhotoPostText(PhotoPost post) {
        String body = post.getCaption();
        String text = body + "\n\n";
        text += post.getPhotos()
                .stream()
                .map(photo -> photo.getOriginalSize().getUrl())
                .reduce("", (a, b) -> a + "\n" + "\"" + b +"\"");

        return text;
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
