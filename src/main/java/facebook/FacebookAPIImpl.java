package facebook;

import facebook4j.*;
import facebook4j.User;
import facebook4j.conf.ConfigurationBuilder;
import socialmedia.Post.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FacebookAPIImpl extends FacebookAPI {

    private Facebook libraryInstance;

    private static socialmedia.Post.Type convertFacebookType(String type) {
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

    private List<FacebookUser> convertNameIdToSimpleUsers(List<IdNameEntity> users) {
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

    private FacebookPost facebook4jPostConversion(Post post) {
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

    private FacebookUser facebook4jUserConversion(User user) {
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
            user = libraryInstance.getUser(id);
        }
        catch (FacebookException fe) {
            debug(fe);
            throw new FacebookAPIException(fe.getMessage());
        }

        if (user == null) throw new FacebookAPIException("No user with id \"" + id + "\"");
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
        if (post == null) throw new FacebookAPIException("No post with id \"" + id + "\"");
        FacebookPost fPost = facebook4jPostConversion(post);
        debugf("Retrieved Facebook post", fPost);
        return fPost;
    }

    //TODO:: Overloads for pagination
   /* public List<FacebookUser> searchUser(String query) {

    }*/
}
