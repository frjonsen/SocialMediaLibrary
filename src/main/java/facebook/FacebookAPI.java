package facebook;

import facebook4j.*;
import facebook4j.conf.ConfigurationBuilder;
import socialmedia.SocialMediaAPI;

import java.util.List;
import java.util.stream.Collectors;

public class FacebookAPI extends SocialMediaAPI<FacebookUser> {

    private Facebook libraryInstance;

    public FacebookAPI(String appId, String appSecret, String userAccessToken, String permissions) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setOAuthAppId(appId)
                .setOAuthAppSecret(appSecret)
                .setOAuthAccessToken(userAccessToken)
                .setOAuthPermissions(permissions);
        libraryInstance = new FacebookFactory(cb.build()).getInstance();
    }

    public FacebookAPI(String appId, String appSecret, String userAccessToken, String[] permissions) {
        this(appId, appSecret, userAccessToken, String.join(",", permissions));
    }

    public FacebookUser getUser(String id) throws FacebookAPIException {
        User user;
        try {
            user = libraryInstance.getUser(id);
        }
        catch (FacebookException fe) {
            debugf(fe.toString());
            throw new FacebookAPIException(fe.getErrorMessage());
        }
        FacebookUser fbUser = new FacebookUser();
        fbUser.setGender(user.getGender());
        fbUser.setLanguages(user.getLanguages().stream().map(IdNameEntity::getName).collect(Collectors.toList()));
        fbUser.setBiography(user.getBio());
        return fbUser;
    }

    //TODO:: Overloads for pagination
   /* public List<FacebookUser> searchUser(String query) {

    }*/
}
