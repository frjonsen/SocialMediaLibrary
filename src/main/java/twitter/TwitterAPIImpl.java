package twitter;

import socialmedia.*;
import twitter4j.*;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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

    TwitterAPIImpl(Twitter twitter) { this.libraryInstance = twitter; }

    @Override
    public TwitterUser getUser(String id){
        User user;
        try {
            long lId = Long.parseLong(id);
            return getUser(lId);
        }
        catch (NumberFormatException nfe) {} //NOSONAR

        try {
            user = libraryInstance.showUser(id);
        }
        catch(TwitterException tw) {
            debug(tw);
            throw new TwitterAPIException(tw.getMessage());
        }
        if(user == null) {
            return null;
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
        if(user == null) {
            return null;
        }

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
            return null;
        }

        return createStatus(status);
    }

    @Override
    public URL getProfilePicture(String id) {
        User user;
        long lId = Long.parseLong(id);

        try {
            user = libraryInstance.showUser(lId);
            if(user == null) {
                return null;
            }

            return new URL(user.getBiggerProfileImageURLHttps());
        } catch (TwitterException|MalformedURLException e) {
            debug(e);
            throw new TwitterAPIException(e.getMessage());
        }
    }
    @Override
    public List<TwitterPost> getPostFeed(String screenName){
        try {
            long lId = Long.parseLong(screenName);
            return getPostFeed(lId);
        }
        catch (NumberFormatException nfe) {} //NOSONAR

        ResponseList<Status> timeLine;
        try {
            timeLine = libraryInstance.getUserTimeline(screenName);
        } catch (TwitterException te) {
            debug(te);
            throw new TwitterAPIException(te.getMessage());
        }

        return responseListConverter(timeLine);
    }



    @Override
    public List<TwitterPost> getPostFeed(long id){
        ResponseList<Status> timeLine;
        try {
            timeLine = libraryInstance.getUserTimeline(id);
        } catch (TwitterException te) {
            debug(te);
            throw new TwitterAPIException(te.getMessage());
        }
        return responseListConverter(timeLine);
    }

    @Override
    public Map<String, RateLimitStatus> getRateLimitStatus(){
        try {
            return libraryInstance.getRateLimitStatus();
        } catch (TwitterException te) {
            debug(te);
            return null;
        }
    }

    @Override
    public String publishStatusPost(String message){
        Status status = null;
        try {
            status = libraryInstance.updateStatus(message);
        } catch (TwitterException te){
            debug(te);
            throw new TwitterAPIException(te.getMessage());
        }

        return status == null ? null : String.valueOf(status.getId());
    }

    @Override
    public boolean likePost(String id){
        Status status = null;
        try {
            long lId = Long.parseLong(id);
            status = libraryInstance.createFavorite(lId);
        }
        catch (NumberFormatException nfe){
            debug(nfe);
            throw new TwitterAPIException("invalid id" + "\"" + id + "\"");
        }
        catch (TwitterException te){
            debug(te);
            throw new TwitterAPIException(te.getMessage());
        }

        return status != null;
    }

    @Override
    public boolean unlikePost(String id){
        Status status = null;
        try {
            long lId = Long.parseLong(id);
            status = libraryInstance.destroyFavorite(lId);
        }
        catch (NumberFormatException nfe){
            debug(nfe);
            throw new TwitterAPIException("invalid id" + "\"" + id + "\"");
        }
        catch (TwitterException te){
            debug(te);
            throw new TwitterAPIException(te.getMessage());
        }

        return status != null;
    }

    @Override
    public boolean follow(String screenName){
        try {
            long lId = Long.parseLong(screenName);
            return follow(lId);
        } catch (NumberFormatException nfe) {} //NOSONAR

        User user;
        try {
            user = libraryInstance.createFriendship(screenName);
        } catch (TwitterException te){
            debug(te);
            throw new TwitterAPIException(te.getMessage());
        }

        return user != null;
    }

    @Override
    public boolean follow(String screenName, boolean notifications){
        try {
            long lId = Long.parseLong(screenName);
            return follow(lId);
        } catch (NumberFormatException nfe) {} //NOSONAR

        User user;
        try {
            user = libraryInstance.createFriendship(screenName, notifications);
        } catch (TwitterException te){
            debug(te);
            throw new TwitterAPIException(te.getMessage());
        }

        return user != null;
    }

    @Override
    public boolean follow(long id){
        User user;
        try {
            user = libraryInstance.createFriendship(id);
        } catch (TwitterException te){
            debug(te);
            throw new TwitterAPIException(te.getMessage());
        }

        return user != null;
    }

    @Override
    public boolean follow(long id, boolean notifications){
        User user;
        try {
            user = libraryInstance.createFriendship(id, notifications);
        } catch (TwitterException te){
            debug(te);
            throw new TwitterAPIException(te.getMessage());
        }

        return user != null;

    }

    @Override
    public boolean unfollow(String screenName){
        try {
            long lId = Long.parseLong(screenName);
            return unfollow(lId);
        } catch (NumberFormatException nfe) {} //NOSONAR

        User user;
        try {
            user = libraryInstance.destroyFriendship(screenName);
        } catch (TwitterException te){
            debug(te);
            throw new TwitterAPIException(te.getMessage());
        }

        return user != null;

    }

    @Override
    public boolean unfollow(long id){
        User user;
        try {
            user = libraryInstance.createFriendship(id);
        } catch (TwitterException te){
            debug(te);
            throw new TwitterAPIException(te.getMessage());
        }

        return user != null;
    }

    private IDs getFollowers(Long id, long cursor) throws TwitterException {
        if (id == null) {
            return libraryInstance.getFollowersIDs(cursor);
        }
        return libraryInstance.getFollowersIDs(id, cursor);
    }

    @Override
    public List<TwitterUser> getFollowers(String id, int maxCalls){
        Long lId;
        List<TwitterUser> followers = new ArrayList<>();
        try {
            lId = Long.parseLong(id);
        } catch(NumberFormatException nfe) {
            if (id.equals(SELF_ID)) lId = null;
            else throw new TwitterAPIException("Invalid id");
        }
        try {
            IDs ids = getFollowers(lId, -1);
            if(ids.getIDs().length != 0){
                followers.addAll(longListToUsers(ids.getIDs()));
            }
            int callsMade = 1;
            while( ids.hasNext() && (maxCalls == -1 || callsMade++ < maxCalls)){
                ids = getFollowers(lId, ids.getNextCursor());
                if(ids.getIDs().length != 0) {
                    followers.addAll(longListToUsers(ids.getIDs()));
                }
            }
        }
        catch (TwitterException te) {
            debug(te);
            throw new TwitterAPIException(te.getMessage());
        }

        return followers;
    }

    private IDs getFollowing(Long id, long cursor) throws TwitterException {
        if (id == null) {
            return libraryInstance.getFriendsIDs(cursor);
        }
        return libraryInstance.getFriendsIDs(id, cursor);
    }

    @Override
    public List<TwitterUser> getFollowing(String id, int maxCalls){

        Long lId;
        List<TwitterUser> followers = new ArrayList<>();
        try {
            lId = Long.parseLong(id);
        } catch (NumberFormatException nfe) {
            if (id.equals(SELF_ID)) lId = null;
            else throw new TwitterAPIException("Invalid id");
        }

        try {
            IDs ids = getFollowing(lId, -1);
            if(ids.getIDs().length != 0){
                followers.addAll(longListToUsers(ids.getIDs()));
            }
            int callsMade = 1;
            while( ids.hasNext() && (maxCalls == -1 || callsMade++ < maxCalls)){
                ids = getFollowing(lId, ids.getNextCursor());
                if(ids.getIDs().length != 0) {
                    followers.addAll(longListToUsers(ids.getIDs()));
                }
            }
        }
        catch (TwitterException te) {
            debug(te);
            throw new TwitterAPIException(te.getMessage());
        }

        return followers;
    }

    private List<TwitterUser> longListToUsers(long[] ids){
        List<TwitterUser> followers = new ArrayList<>();
        for(long i : ids) {
            TwitterUser user = new TwitterUser();
            user.setId(String.valueOf(i));
            followers.add(user);
        }
        return followers;
    }

    private List<TwitterPost> responseListConverter(ResponseList<Status> responseList){
        if(responseList == null) {
            return Collections.emptyList();
        }
        List<TwitterPost> postList = new ArrayList<>();
        responseList.forEach(status -> postList.add(createStatus(status)));
        return postList;
    }

    private TwitterPost createStatus(Status status) {
        if( status == null ) {
            return null;
        }

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
        if(status.getUser() != null) {
            try {
                URL url = new URL("https://twitter.com/" + tp.getAuthor().getUsername() + "/" + tp.getId());
                tp.setPermalink(url);
            } catch (MalformedURLException mue) {
                debug(mue);
            }
        }
        tp.setLanguage(status.getLang());
        if(status.getGeoLocation() != null){
            tp.setCoordinate(new Coordinate(status.getGeoLocation().getLatitude(), status.getGeoLocation().getLongitude()));
        }
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
        if(user == null) {
            return null;
        }

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

        if(mentions == null) {
            return usersList;
        }

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
