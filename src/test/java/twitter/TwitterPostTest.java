package twitter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import socialmedia.Coordinate;
import twitter4j.*;

import static org.junit.jupiter.api.Assertions.*;

class TwitterPostTest {
    TwitterPost post = null;

    @BeforeEach
    void init() { this.post = new TwitterPost(); }

    @Test
    @DisplayName("should set and get language unchanged")
    void testLanguage(){
        String language = "Kryptonian";
        post.setLanguage(language);
        assertEquals(language, post.getLanguage());
    }

    @Test
    @DisplayName("should set and get coordinate unchanged")
    void testCoordinate(){
        Coordinate coordinate = new Coordinate(28.385233, -81.563874);
        post.setCoordinate(coordinate);
        assertEquals(28.385233, post.getCoordinate().getLatitude());
        assertEquals(-81.563874, post.getCoordinate().getLongitude());
    }

    @Test
    @DisplayName("should set and get place unchanged")
    void testPlace(){
        Place place = Mockito.mock(Place.class);
        post.setPlace(place);
        assertEquals(place, post.getPlace());
    }

    @Test
    @DisplayName("should set and get mediaEntities unchanged")
    void testMediaEntities(){
        MediaEntity[] media = {Mockito.mock(MediaEntity.class),
                Mockito.mock(MediaEntity.class),
                Mockito.mock(MediaEntity.class)};
        post.setMediaEntities(media);
        assertEquals(media, post.getMediaEntities());
    }

    @Test
    @DisplayName("should set and get symbolEntities unchanged")
    void testSymbolEntities(){
        SymbolEntity[] symbols = {Mockito.mock(SymbolEntity.class),
                Mockito.mock(SymbolEntity.class),
                Mockito.mock(SymbolEntity.class)};
        post.setSymbolEntities(symbols);
        assertEquals(symbols, post.getSymbolEntities());
    }

    @Test
    @DisplayName("should set and get urlEntities unchanged")
    void testUrlEntities(){
        URLEntity[] urls = {Mockito.mock(URLEntity.class),
                Mockito.mock(URLEntity.class),
                Mockito.mock(URLEntity.class)};
        post.setUrlEntities(urls);
        assertEquals(urls, post.getUrlEntities());
    }

    @Test
    @DisplayName("should set and get favoriteCount unchanged")
    void testFavoriteCount(){
        int favoriteCount = 720;
        post.setFavoriteCount(favoriteCount);
        assertEquals(favoriteCount, post.getFavoriteCount());
    }

    @Test
    @DisplayName("should set and get replyToScreenName unchanged")
    void testReplyToScreenName(){
        String name = "TestyMcTest";
        post.setReplyToScreenName(name);
        assertEquals(name, post.getReplyToScreenName());
    }

    @Test
    @DisplayName("should set and get replyToStatusId unchanged")
    void testReplyToStatusId(){
        long id = 1234567991;
        post.setReplyToStatusId(id);
        assertEquals(id, post.getReplyToStatusId());
    }

    @Test
    @DisplayName("should set and get replyToUserId unchanged")
    void testReplyToUserId(){
        long id = 1215264835;
        post.setReplyToUserId(id);
        assertEquals(id, post.getReplyToUserId());
    }

    @Test
    @DisplayName("should set and get possiblySensitive unchanged")
    void testPossiblySensitive(){
        boolean sensitive = true;
        post.setPossiblySensitive(sensitive);
        assertEquals(sensitive, post.isPossiblySensitive());
    }

    @Test
    @DisplayName("should set and get quotedStatusId unchanged")
    void testQuotedStatusId(){
        long id = 1234567894;
        post.setQuotedStatusId(id);
        assertEquals(id, post.getQuotedStatusId());
    }

    @Test
    @DisplayName("should set and get quotedStatus unchanged")
    void testQuotedStatus(){
        TwitterPost status = Mockito.mock(TwitterPost.class);
        post.setQuotedStatus(status);
        assertEquals(status, post.getQuotedStatus());
    }

    @Test
    @DisplayName("should set and get retweetedStatus unchanged")
    void testRetweetedStatus(){
        TwitterPost status = Mockito.mock(TwitterPost.class);
        post.setRetweetedStatus(status);
        assertEquals(status, post.getRetweetedStatus());
    }

    @Test
    @DisplayName("should set and get currentUserRetweetId unchanged")
    void testCurrentUserRetweetId(){
        long id = 789456123;
        post.setCurrentUserRetweetId(id);
        assertEquals(id, post.getCurrentUserRetweetId());
    }

    @Test
    @DisplayName("should set and get scopes unchanged")
    void testScopes(){
        Scopes scopes = Mockito.mock(Scopes.class);
        post.setScopes(scopes);
        assertEquals(scopes, post.getScopes());
    }

    @Test
    @DisplayName("should set and get source unchanged")
    void testSource(){
        String source = "\\u003Ca href=\"http:\\/\\/itunes.apple.com\\/us\\/app\\/twitter\\/id409789998?mt=12\" \\u003ETwitter for Mac\\u003C\\/a\\u003E";
        post.setSource(source);
        assertEquals(source, post.getSource());
    }

    @Test
    @DisplayName("should set and get withheldInCountries unchanged")
    void testWithheldInCountries(){
        String[] countries = {"EN", "SE", "GB", "DK"};
        post.setWithheldInCountries(countries);
        assertEquals(countries, post.getWithheldInCountries());
    }

    @Test
    @DisplayName("should set and get favorited unchanged")
    void testFavorited(){
        boolean favorited = true;
        post.setFavorited(favorited);
        assertEquals(favorited, post.isFavorited());
    }

    @Test
    @DisplayName("should set and get retweet unchanged")
    void testRetweet(){
        boolean retweet = false;
        post.setRetweet(retweet);
        assertEquals(retweet, post.isRetweet());
    }

    @Test
    @DisplayName("should set and get retweeted unchanged")
    void testRetweeted(){
        boolean retweeted = true;
        post.setRetweeted(retweeted);
        assertEquals(retweeted, post.isRetweeted());
    }

    @Test
    @DisplayName("should set and get retweetedByMe unchanged")
    void testRetweetedByMe(){
        boolean retweetedByMe = false;
        post.setRetweetedByMe(retweetedByMe);
        assertEquals(retweetedByMe, post.isRetweetedByMe());
    }

    @Test
    @DisplayName("should set and get truncated unchanged")
    void testTruncated(){
        boolean truncated = true;
        post.setTruncated(truncated);
        assertEquals(truncated, post.isTruncated());
    }

}