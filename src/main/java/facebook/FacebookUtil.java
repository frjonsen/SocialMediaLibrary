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
            FacebookUser converted = new FacebookUser();
            converted.setName(user.getName());
            converted.setId(user.getId());
            u.add(converted);
        }
        return u;
    }

    static socialmedia.Post.Type convertFacebookType(String type) {
        Post.Type t;
        if (type == null) {
            t = Post.Type.UNKNOWN;
        }
        else {
            String convertedType = type.toLowerCase();
            switch (convertedType) {
                case "photo":
                    t = Post.Type.IMAGE;
                    break;
                case "link":
                    t = Post.Type.LINK;
                    break;
                case "status":
                    t = Post.Type.TEXT;
                    break;
                case "video":
                    t = Post.Type.VIDEO;
                    break;
                case "offer":
                    t = Post.Type.OFFER;
                    break;
                default:
                    t = Post.Type.UNKNOWN;
            }
        }
        return t;
    }

    static FacebookUser categoryToUser(Category category) {
        FacebookUser user = new FacebookUser();
        user.setId(category.getId());
        user.setName(category.getName());
        return user;
    }
}
