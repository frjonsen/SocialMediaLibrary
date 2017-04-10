package twitter;

import socialmedia.Coordinate;
import socialmedia.Post;
import twitter4j.*;


public class TwitterPost extends Post<TwitterUser>{
    private String language;
    private Coordinate coordinate;
    private Place place;
    private MediaEntity[] mediaEntities;
    private SymbolEntity[] symbolEntities;
    private URLEntity[] urlEntities;
    private int favoriteCount;
    private String replyToScreenName;
    private long replyToStatusId;
    private long replyToUserId;

    private boolean possiblySensitive;
    private long quotedStatusId;
    private TwitterPost quotedStatus;
    private TwitterPost retweetedStatus;
    private long currentUserRetweetId;
    private Scopes scopes;
    private String source;
    private String[] withheldInCountries;
    private boolean favorited;
    private boolean retweet;
    private boolean retweeted;
    private boolean retweetedByMe;
    private boolean truncated;

    /**
     * Returns location of this tweet as specified by the user or application
     * posting it.
     * @return  Coordinates as longitude and latitude
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }

    /**
     * Sets the location of the tweet locally.
     * @param coordinate    new coordinates
     */
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    /**
     * Returns the language of the tweet as a BCP 47 language
     * identifier detected by twitter. Nullable.
     * @return  language of tweet
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets language locally for the object.
     * @param language the new language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Returns the place object associated with the tweet, but not necessarily
     * originating from.
     * @see Place
     * @return place
     */
    public Place getPlace() {
        return place;
    }

    /**
     * Sets place locally for the object.
     * @see Place
     * @param place the new place
     */
    public void setPlace(Place place) {
        this.place = place;
    }

    /**
     * A list of media entities uploaded with the tweet.
     * @see MediaEntity
     * @return mediaEntities
     */
    public MediaEntity[] getMediaEntities() {
        return mediaEntities;
    }

    /**
     * Sets the list of media entities uploaded with the tweet, locally.
     * @param mediaEntities new mediaEntities
     */
    public void setMediaEntities(MediaEntity[] mediaEntities) {
        this.mediaEntities = mediaEntities;
    }

    /**
     * An array of financial symbols taken from the tweet text.
     * @see SymbolEntity
     * @return symbolEntities
     */
    public SymbolEntity[] getSymbolEntities() {
        return symbolEntities;
    }

    /**
     * Sets the financial symbols list taken from the tweet text, locally.
     * @see SymbolEntity
     * @param symbolEntities new symbolEntities
     */
    public void setSymbolEntities(SymbolEntity[] symbolEntities) {
        this.symbolEntities = symbolEntities;
    }

    /**
     * An array of URLs taken from the tweet.
     * @see URLEntity
     * @return urlEntities
     */
    public URLEntity[] getUrlEntities() {
        return urlEntities;
    }

    /**
     * Sets the URLs array locally.
     * @see URLEntity
     * @param urlEntities new urlEntities
     */
    public void setUrlEntities(URLEntity[] urlEntities) {
        this.urlEntities = urlEntities;
    }

    /**
     * Returns a count of how many times the tweet has been "favorited".
     * @return favorite count
     */
    public int getFavoriteCount() {
        return favoriteCount;
    }

    /**
     * Sets the favorite count locally.
     * @param favoriteCount new favorite count
     */
    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    /**
     * If the tweet is a reply this will be the screen name of the
     * user this is a reply to.
     * @return screen name of user replied to
     */
    public String getReplyToScreenName() {
        return replyToScreenName;
    }

    /**
     * Sets the reply to screen name locally.
     * @param replyToScreenName the new screen name replied too
     */
    public void setReplyToScreenName(String replyToScreenName) {
        this.replyToScreenName = replyToScreenName;
    }

    /**
     * If the tweet is a reply, this will represent the id of the tweet
     * replied too's id
     * @return id of the tweet replied too
     */
    public long getReplyToStatusId() {
        return replyToStatusId;
    }

    /**
     * Sets the reply to tweet id locally.
     * @param replyToStatusId the new reply to tweet id
     */
    public void setReplyToStatusId(long replyToStatusId) {
        this.replyToStatusId = replyToStatusId;
    }

    /**
     * If the tweet is a reply, this will be the id of the user it
     * replies to.
     * @return id of user replied to
     */
    public long getReplyToUserId() {
        return replyToUserId;
    }

    /**
     * Sets the id of the user replied to locally.
     * @param replyToUserId the new id
     */
    public void setReplyToUserId(long replyToUserId) {
        this.replyToUserId = replyToUserId;
    }

    /**
     * Returns true if the tweet contains an URL that is possibly
     * sensitive.
     * @return boolean of sensitive status
     */
    public boolean isPossiblySensitive() {
        return possiblySensitive;
    }

    /**
     * Sets the possiblySensitive status for the tweet locally.
     * @param possiblySensitive the new sensitive status
     */
    public void setPossiblySensitive(boolean possiblySensitive) {
        this.possiblySensitive = possiblySensitive;
    }

    /**
     * Returns tweet id of the quoted tweet.
     * @return tweet id
     */
    public long getQuotedStatusId() {
        return quotedStatusId;
    }

    /**
     * Sets tweet id of the quoted tweet
     * @param quotedStatusId new id
     */
    public void setQuotedStatusId(long quotedStatusId) {
        this.quotedStatusId = quotedStatusId;
    }

    /**
     * Returns the TwitterPost object of the tweet quoted.
     * @return the tweet quoted
     */
    public TwitterPost getQuotedStatus() {
        return quotedStatus;
    }

    /**
     * Sets the quoted tweet locally.
     * @param quotedStatus the new TwitterPost object
     */
    public void setQuotedStatus(TwitterPost quotedStatus) {
        this.quotedStatus = quotedStatus;
    }

    /**
     * Returns the TwitterPost object of the retweeted tweet.
     * @return retweeted tweet
     */
    public TwitterPost getRetweetedStatus() {
        return retweetedStatus;
    }

    /**
     * Sets the retweeted tweet locally.
     * @param retweetedStatus the new TwitterPost object
     */
    public void setRetweetedStatus(TwitterPost retweetedStatus) {
        this.retweetedStatus = retweetedStatus;
    }

    /**
     * Returns authenticating user's retweet's id of tweet.
     * @return id
     */
    public long getCurrentUserRetweetId() {
        return currentUserRetweetId;
    }

    /**
     * Sets the currentUserRetweetId locally.
     * @param currentUserRetweetId the new retweet id
     */
    public void setCurrentUserRetweetId(long currentUserRetweetId) {
        this.currentUserRetweetId = currentUserRetweetId;
    }

    /**
     * Returns the targeting scopes applied to a tweet.
     * @return scopes
     */
    public Scopes getScopes() {
        return scopes;
    }

    /**
     * Sets the scopes locally.
     * @param scopes new scopes
     */
    public void setScopes(Scopes scopes) {
        this.scopes = scopes;
    }

    /**
     * Returns the source. Utillity used to post tweets as an html
     * formatted string.
     * @return
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets the source locally.
     * @param source the new source
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * Returns the list of countries where the tweet is withheld.
     * @return
     */
    public String[] getWithheldInCountries() {
        return withheldInCountries;
    }

    /**
     * Sets the list of countries where the tweet is withheld, locally.
     * @param withheldInCountries sets the list of countries
     */
    public void setWithheldInCountries(String[] withheldInCountries) {
        this.withheldInCountries = withheldInCountries;
    }

    /**
     * Returns true if the tweet has been favorited by the
     * authenticating user.
     * @return if favorited
     */
    public boolean isFavorited() {
        return favorited;
    }

    /**
     * Sets favorited locally.
     * @param favorited the new favorited boolean
     */
    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    /**
     * Returns true if this tweet is a retweet.
     * @return retweet status
     */
    public boolean isRetweet() {
        return retweet;
    }

    /**
     * Sets the retweet status locally.
     * @param retweet new retweet status
     */
    public void setRetweet(boolean retweet) {
        this.retweet = retweet;
    }

    /**
     * Tests if the status is retweeted.
     * @return if retweeted
     */
    public boolean isRetweeted() {
        return retweeted;
    }

    /**
     * Sets the retweeted status locally
     * @param retweeted
     */
    public void setRetweeted(boolean retweeted) {
        this.retweeted = retweeted;
    }
    /**
     * Returns true if this tweet has been retweeted by
     * authenticating user.
     * @return if retweeted
     */
    public boolean isRetweetedByMe() {
        return retweetedByMe;
    }

    /**
     * Sets the retweetedByMe status locally.
     * @param retweetedByMe the new retweetedByMe status
     */
    public void setRetweetedByMe(boolean retweetedByMe) {
        this.retweetedByMe = retweetedByMe;
    }

    /**
     * Returns true if the tweet is truncated.
     * @return if truncated
     */
    public boolean isTruncated() {
        return truncated;
    }

    /**
     * Sets if tweet is truncated locally.
     * @param truncated new truncated status
     */
    public void setTruncated(boolean truncated) {
        this.truncated = truncated;
    }

}
