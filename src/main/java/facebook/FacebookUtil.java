package facebook;

import facebook4j.Category;
import facebook4j.IdNameEntity;
import socialmedia.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class FacebookUtil {

    private FacebookUtil() { }

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

    static FacebookUser categoryToUser(Category category) {
        FacebookUser user = new FacebookUser(FacebookUser.UserType.USER);
        user.setId(category.getId());
        user.setName(category.getName());
        return user;
    }
}
