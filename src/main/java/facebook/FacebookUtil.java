package facebook;

import facebook4j.Category;
import facebook4j.IdNameEntity;
import socialmedia.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Utility functions for Facebook-specific cases
 */
class FacebookUtil {

    private FacebookUtil() { }

    /**
     * Retrieves all hash tags in a message
     * @param message Message to parse
     * @return A list of all hashtags found
     */
    static List<String> getHashTags(String message) {
        if (message == null) {
            return new ArrayList<>();
        }
        String[] words = message.split(" ");
        return Stream.of(words)
                .filter(word -> word.startsWith("#"))
                .map(word -> word.substring(1))
                .filter(word -> word.trim().length() > 0)
                .collect(Collectors.toList());
    }

    /**
     * Converts a facebook4j IdNameEntity to a simple user
     * @param users A list of IdNameEntities
     * @return A list of basic users, containing only Id and name
     */
    static List<FacebookUser> convertNameIdToSimpleUsers(List<IdNameEntity> users) {
        List<FacebookUser> u = new ArrayList<>();
        if (users == null) {
            return u;
        }
        for (IdNameEntity user : users) {
            FacebookUser converted = new FacebookUser(FacebookUser.UserType.USER);
            converted.setName(user.getName());
            converted.setId(user.getId());
            u.add(converted);
        }
        return u;
    }

    /**
     * Converts facebook post types to the general Post.Type
     * @param type String representation of a type
     * @return Generalized enum representation of the type
     */
    static socialmedia.Post.Type convertFacebookType(String type) {
        if (type == null) {
            return Post.Type.UNKNOWN;
        }
        String convertedType = type.toLowerCase();
        switch (convertedType) {
            case "photo":
                return Post.Type.IMAGE;
            case "link":
                return Post.Type.LINK;
            case "status":
                return Post.Type.TEXT;
            case "video":
                return Post.Type.VIDEO;
            case "offer":
                return Post.Type.OFFER;
            default:
                return Post.Type.UNKNOWN;
        }
    }

    /**
     * Converts a facebook4j Category to a basic Facebook user, containing only Id and Name
     * @param category Category to convert
     * @return Basic Facebook user
     */
    static FacebookUser categoryToUser(Category category) {
        FacebookUser user = new FacebookUser(FacebookUser.UserType.USER);
        user.setId(category.getId());
        user.setName(category.getName());
        return user;
    }
}
