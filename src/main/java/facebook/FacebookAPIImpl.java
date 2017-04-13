package facebook;

import facebook4j.*;
import facebook4j.User;
import facebook4j.conf.ConfigurationBuilder;
import socialmedia.Post.Type;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FacebookAPIImpl extends FacebookAPI {

    private Facebook libraryInstance;
    private static final String SELF_ID = "me";

    static socialmedia.Post.Type convertFacebookType(String type) {
        if (type == null) return Type.UNKNOWN;
        String convertedType = type.toLowerCase();
        switch (convertedType) {
            case "photo":
                return Type.IMAGE;
            case "link":
                return Type.LINK;
            case "status":
                return Type.TEXT;
            case "video":
                return Type.VIDEO;
            case "offer":
                return Type.OFFER;
            default:
                return Type.UNKNOWN;
        }
    }

    static List<String> getHashTags(String message) {
        if (message == null) return new ArrayList<>();
        String[] words = message.split(" ");
        return Stream.of(words)
                .filter(word -> word.startsWith("#"))
                .map(word -> word.substring(1))
                .filter(word -> word.trim().length() > 0)
                .collect(Collectors.toList());
    }

    static private List<FacebookUser> convertNameIdToSimpleUsers(List<IdNameEntity> users) {
        List<FacebookUser> u = new ArrayList<>();
        for (IdNameEntity user : users) {
            FacebookUser converted = new FacebookUser();
            converted.setName(user.getName());
            converted.setId(user.getId());
            u.add(converted);
        }
        return u;
    }

    public FacebookAPIImpl(String appId, String appSecret, String userAccessToken, String permissions) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setOAuthAppId(appId)
                .setOAuthAppSecret(appSecret)
                .setOAuthAccessToken(userAccessToken)
                .setOAuthPermissions(permissions);
        libraryInstance = new FacebookFactory(cb.build()).getInstance();
    }

    private static FacebookPost facebook4jPostConversion(Post post) {
        FacebookPost fbPost = new FacebookPost();
        fbPost.setId(post.getId());
        fbPost.setType(convertFacebookType(post.getType()));
        fbPost.setCreationTime(post.getCreatedTime());
        fbPost.setEditTime(post.getUpdatedTime());
        fbPost.setText(post.getMessage());
        fbPost.setTo(convertNameIdToSimpleUsers(post.getTo()));
        fbPost.setWithTags(convertNameIdToSimpleUsers(post.getWithTags()));
        List<String> hashTags = getHashTags(post.getMessage());
        fbPost.setTags(hashTags);
        fbPost.setHidden(post.isHidden());
        fbPost.setPublished(post.isPublished());
        fbPost.setLink(post.getLink());
        fbPost.setSource(post.getSource());
        fbPost.setPlace(post.getPlace());
        fbPost.setObjectId(post.getObjectId());
        fbPost.setParentId(post.getParentId());
        fbPost.setStatusType(post.getStatusType());
        fbPost.setProperties(post.getProperties());

        return fbPost;
    }

    static private FacebookUser facebook4jUserConversion(User user) {
        FacebookUser fbUser = new FacebookUser();
        fbUser.setId(user.getId());
        fbUser.setUsername(user.getUsername());
        fbUser.setGender(user.getGender());
        fbUser.setAge(user.getAgeRange());
        fbUser.setLanguages(user.getLanguages().stream().map(IdNameEntity::getName).collect(Collectors.toList()));
        fbUser.setBiography(user.getBio());
        fbUser.setBirthday(user.getBirthday());
        IdNameEntity city = user.getHometown();
        fbUser.setCity(city == null ? null : city.getName());
        fbUser.setEmail(user.getEmail());
        fbUser.setName(user.getName());
        fbUser.setWebsite(user.getWebsite());
        return fbUser;
    }

    FacebookAPIImpl(Facebook facebook) {
        this.libraryInstance = facebook;
    }

    /**
     * Retreives a facebook user, using its id
     * @param id Site-wide id of the user
     * @return A facebook user, filled in with all visible information
     */
    @Override
    public FacebookUser getUser(String id) {
        User user;
        try {
            if (id.equals(SELF_ID)) {
                user = libraryInstance.getMe();
            } else {
                user = libraryInstance.getUser(id);
            }

        }
        catch (FacebookException fe) {
            debug(fe);
            throw new FacebookAPIException(fe.getMessage());
        }
        if (user == null) {
            debug("Attempted to retrieve user with id \"" + id + "\". No user with this id was found.");
            return null;
        }
        FacebookUser fUser = facebook4jUserConversion(user);
        debugf("Retrieved Facebook user", fUser);
        return fUser;
    }

    /**
     * Retreives a facebook post, using its id.
     * Post IDs are generally formatted as "12345678901234567_12345678901234567", where the
     * left side of the _ is the id of the author, and the right side is the actual post identifier.
     * @param id Full id of the post.
     * @return A post filled in will all visible information
     */
    @Override
    public FacebookPost getPost(String id) {
        facebook4j.Post post = null;
        try {
            post = libraryInstance.getPost(id);
        } catch (FacebookException fe) {
            debug(fe);
            throw new FacebookAPIException(fe.getMessage());
        }
        if (post == null) {
            debug("Attempted to retrieve post with id \"" + id + "\". No post with this id was found.");
            return null;
        }
        FacebookPost fPost = facebook4jPostConversion(post);
        debugf("Retrieved Facebook post", fPost);
        return fPost;
    }

    @Override
    public List<FacebookUser> searchUsers(String query) {
        ResponseList<User> results;
        try {
            results = libraryInstance.searchUsers(query);
        } catch(FacebookException fe) {
            debug(fe);
            throw new FacebookAPIException(fe.getMessage());
        }
        if (results == null) throw new FacebookAPIException("Unable to query for \"" + query + "\"");

        List<FacebookUser> users = new ArrayList<>();
        for (User user : results) {
            users.add(facebook4jUserConversion(user));
        }
        return users;
    }

    @Override
    public URL getProfilePicture(String id) {
        try {
            URL url = null;
            if (id.equals(SELF_ID)) {
                url = libraryInstance.getPictureURL(Integer.MAX_VALUE, 0);
            } else {
                url = libraryInstance.getPictureURL(id, Integer.MAX_VALUE, 0);
            }
            if (url == null) {
                throw new FacebookAPIException("No profile picture for id \"" + id + "\". User probably does not exist");
            }
            return url;

        }
        catch (FacebookException fe) {
            debug(fe);
            throw new FacebookAPIException(fe.getMessage());
        }
    }

    @Override
    public boolean likePost(String id) {
        try {
            return libraryInstance.likePost(id);
        } catch (FacebookException fe) {
            debug(fe);
            throw new FacebookAPIException(fe.getMessage());
        }
    }

    @Override
    public boolean unlikePost(String id) {
        try {
            return libraryInstance.unlikePost(id);
        } catch (FacebookException fe) {
            debug(fe);
            throw new FacebookAPIException(fe.getMessage());
        }
    }

    @Override
    public List<FacebookPost> getPostFeed(String id) {
        try {
            List<Post> feed = null;
            if (id.equals(SELF_ID)) {
                feed = libraryInstance.getFeed();
            } else {
                feed = libraryInstance.getFeed(id);
            }

            return feed.stream().map(FacebookAPIImpl::facebook4jPostConversion).collect(Collectors.toList());
        }
        catch (FacebookException fe) {
            debug(fe);
            throw new FacebookAPIException(fe.getMessage());
        }
    }


    //TODO:: Overloads for pagination
   /* public List<FacebookUser> searchUser(String query) {

    }*/
}
