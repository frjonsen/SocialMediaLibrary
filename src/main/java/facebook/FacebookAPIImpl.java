package facebook;

import facebook4j.*;
import facebook4j.User;
import facebook4j.conf.ConfigurationBuilder;
import socialmedia.*;
import socialmedia.Post;

import java.util.stream.Collectors;

public class FacebookAPIImpl extends FacebookAPI {

    private Facebook libraryInstance;

    public FacebookAPIImpl(String appId, String appSecret, String userAccessToken, String permissions) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setOAuthAppId(appId)
                .setOAuthAppSecret(appSecret)
                .setOAuthAccessToken(userAccessToken)
                .setOAuthPermissions(permissions);
        libraryInstance = new FacebookFactory(cb.build()).getInstance();
    }

    FacebookAPIImpl(Facebook facebook) {
        this.libraryInstance = facebook;
    }

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
        FacebookUser fbUser = new FacebookUser();
        fbUser.setGender(user.getGender());
        fbUser.setAge(user.getAgeRange());
        fbUser.setLanguages(user.getLanguages().stream().map(IdNameEntity::getName).collect(Collectors.toList()));
        fbUser.setBiography(user.getBio());
        fbUser.setBirthday(user.getBirthday());
        IdNameEntity city = user.getHometown();
        fbUser.setCity(city == null ? null : city.getName());
        fbUser.setEmail(user.getEmail());
        return fbUser;
    }

    @Override
    public FacebookPost getPost(String id) {
        return null;
    }

    //TODO:: Overloads for pagination
   /* public List<FacebookUser> searchUser(String query) {

    }*/
}
