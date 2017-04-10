package twitter;

import socialmedia.Coordinate;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.net.MalformedURLException;
import java.net.URL;

public class TwitterAPIImpl extends TwitterAPI {

    private Twitter libraryInstance;

    public TwitterAPIImpl(String consumerKey, String consumerSecret, String accessToken, String accessSecret, boolean debug) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(debug)
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessSecret);
        TwitterFactory tf = new TwitterFactory(cb.build());
        libraryInstance = tf.getInstance();
    }
    @Override
    public TwitterUser getUser(String id){
        User user;
        try {
            long lId = Long.valueOf(id);
            getUser(lId);
        }
        catch (NumberFormatException nfe) {}

        try {
            user = libraryInstance.showUser(id);
        }
        catch(TwitterException tw) {
            debug(tw);
            throw new TwitterAPIException(tw.getMessage());
        }

        return createUser(user);
    }

    @Override
    public TwitterUser getUser(long id) {
        User user;
        try {
            user = libraryInstance.showUser(id);
        }
        catch (TwitterException tw) {
            debug(tw);
            throw new TwitterAPIException(tw.getMessage());
        }

        return createUser(user);
    }

    @Override
    public TwitterPost getPost(String id) {
        Status status;
        try {
            long lId = Long.valueOf(id);
            status = libraryInstance.showStatus(lId);
        }
        catch (NumberFormatException nfe) {
            debug(nfe);
            throw new TwitterAPIException("Invalid tweet ID: " + "\"" + id + "\"");
        }
        catch (TwitterException te) {
            debug(te);
            throw new TwitterAPIException(te.getMessage());
        }

        TwitterPost tp = new TwitterPost();
        tp.setType(); //fix
        tp.setText(status.getText());
        tp.setCreationTime(status.getCreatedAt());
        tp.setId(String.valueOf(status.getId()));
        tp.setSharedCount(status.getRetweetCount());
        tp.setTo();//fix
        tp.setTags();//fix
        tp.setAuthor();
        try {
            tp.setPermalink(new URL("https://twitter.com/" + tp.getAuthor().getUsername() + "/" + tp.getId()));
        }
        catch (MalformedURLException mue) {
            debug(mue);
        }
        tp.setLanguage(status.getLang());
        tp.setCoordinate(new Coordinate(status.getGeoLocation().getLatitude(), status.getGeoLocation().getLongitude()));
        tp.setPlace(status.getPlace());
        tp.setMediaEntities(status.getMediaEntities());
        tp.setSymbolEntities(status.getSymbolEntities());
        tp.setUrlEntities(status.getURLEntities());
        tp.setFavoriteCount(status.getFavoriteCount());
        
        tp.setReplyToScreenName();
        tp.setReplyToStatusId();
        tp.setReplyToUserId();
        tp.setPossiblySensitive();
        tp.setQuotedStatusId();
        tp.setQuotedStatus();
        tp.setRetweetedStatus();
        tp.setCurrentUserRetweetId();
        tp.setScopes();
        tp.setSource();
        tp.setWithheldInCountries();
        tp.setFavorited();
        tp.setRetweet();
        tp.setRetweeted();
        tp.setRetweetedByMe();
        tp.setTruncated();
    }

    private TwitterUser createUser(User user) {
        TwitterUser twUser = new TwitterUser();
        twUser.setName(user.getName());
        twUser.setId(String.valueOf(user.getId()));
        twUser.setUsername(user.getScreenName());
        try {
            twUser.setWebsite(new URL(user.getURL()));
        }
        catch (MalformedURLException mue) {
            debug(mue);
            twUser.setWebsite(null);
        }
        twUser.setBiography(user.getDescription());
        twUser.setUploadCount(user.getStatusesCount());
        twUser.setFollowingCount(user.getFriendsCount());
        twUser.setFollowersCount(user.getFollowersCount());
        twUser.setEmail(user.getEmail());
        twUser.setLocation(user.getLocation());
        twUser.setLanguage(user.getLang());

        return twUser;
    }
}
