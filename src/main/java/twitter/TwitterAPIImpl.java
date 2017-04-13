package twitter;

import socialmedia.*;
import twitter4j.*;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    TwitterAPIImpl (Twitter twitter) { this.libraryInstance = twitter; }

    @Override
    public TwitterUser getUser(String id){
        User user;
        try {
            long lId = Long.parseLong(id);
            return getUser(lId);
        }
        catch (NumberFormatException nfe) {}

        try {
            user = libraryInstance.showUser(id);
        }
        catch(TwitterException tw) {
            debug(tw);
            throw new TwitterAPIException(tw.getMessage());
        }
        if(user == null) {
            throw new TwitterAPIException("No user with id " + "\"" + id + "\"");
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
        if(user == null) { throw new TwitterAPIException("No user with id " + "\"" + id + "\""); }

        return createUser(user);
    }

    @Override
    public TwitterPost getPost(String id) {
        Status status;
        try {
            long lId = Long.parseLong(id);

            status = libraryInstance.showStatus(lId);
        }
        catch (NumberFormatException nfe) {
            debug(nfe);
            throw new TwitterAPIException("Invalid tweet id: " + "\"" + id + "\"");
        }
        catch (TwitterException te) {
            debug(te);
            throw new TwitterAPIException(te.getMessage());
        }
        if(status == null) {
            throw new TwitterAPIException("No tweet with id " + "\"" + id + "\"");
        }

        return createStatus(status);
    }

    private TwitterPost createStatus(Status status) {
        if( status == null ) { return null; }

        TwitterPost tp = new TwitterPost();
        tp.setType(Post.Type.UNKNOWN);
        tp.setText(status.getText());
        tp.setCreationTime(status.getCreatedAt());
        tp.setId(String.valueOf(status.getId()));
        tp.setSharedCount(status.getRetweetCount());
        tp.setTo(createToList(status.getUserMentionEntities()));
        if(status.getHashtagEntities() != null) {
            tp.setTags(Stream.of(status.getHashtagEntities())
                    .map(HashtagEntity::getText)
                    .collect(Collectors.toList()));
        }
        tp.setAuthor(createUser(status.getUser()));
        try {
            URL url = new URL("https://twitter.com/" + tp.getAuthor().getUsername() + "/" + tp.getId());
            tp.setPermalink(url);
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
        tp.setReplyToScreenName(status.getInReplyToScreenName());
        tp.setReplyToStatusId(status.getInReplyToStatusId());
        tp.setReplyToUserId(status.getInReplyToUserId());
        tp.setPossiblySensitive(status.isPossiblySensitive());
        tp.setQuotedStatusId(status.getQuotedStatusId());
        tp.setQuotedStatus(createStatus(status.getQuotedStatus()));
        tp.setRetweetedStatus(createStatus(status.getRetweetedStatus()));
        tp.setCurrentUserRetweetId(status.getCurrentUserRetweetId());
        tp.setSource(status.getSource());
        tp.setWithheldInCountries(status.getWithheldInCountries());
        tp.setFavorited(status.isFavorited());
        tp.setRetweet(status.isRetweet());
        tp.setRetweeted(status.isRetweeted());
        tp.setRetweetedByMe(status.isRetweetedByMe());
        tp.setTruncated(status.isTruncated());

        return tp;
    }

    private TwitterUser createUser(User user) {
        if(user == null) { return null; }

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

    private Iterable<TwitterUser> createToList(UserMentionEntity[] mentions) {
        ArrayList<TwitterUser> usersList = new ArrayList<>();

        if(mentions == null) { return usersList; }

        TwitterUser user;
        for(UserMentionEntity entity : mentions) {
            user = new TwitterUser();
            user.setId(String.valueOf(entity.getId()));
            user.setName(entity.getName());
            user.setUsername(entity.getScreenName());

            usersList.add(user);
        }
        return usersList;
    }

}
