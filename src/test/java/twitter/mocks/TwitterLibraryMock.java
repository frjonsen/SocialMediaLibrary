package twitter.mocks;

import org.mockito.Mockito;
import twitter.TwitterUser;
import twitter4j.*;

import java.util.Date;

public class TwitterLibraryMock {

    public static Twitter getTwitterMock() throws TwitterException {
        Twitter tw = Mockito.mock(Twitter.class);
        User user = getTwitterFullUserMock();
        Status tweet = getTwitterFullStatusMock();
        Mockito.when(tw.showUser("TestyMcTest")).thenReturn(user);
        Mockito.when(tw.showUser(6253282L)).thenReturn(user);
        Mockito.when(tw.showStatus(114749583439036416L)).thenReturn(tweet);
        return tw;
    }

    public static User getTwitterFullUserMock() {
        User user = Mockito.mock(User.class);

        Mockito.when(user.getName()).thenReturn("Testington");
        Mockito.when(user.getId()).thenReturn(6253282L);
        Mockito.when(user.getScreenName()).thenReturn("TestyMcTest");
        Mockito.when(user.getURL()).thenReturn("https:://twitter.testington.org/");
        Mockito.when(user.getDescription()).thenReturn("Lorem ipsum testy mctest ipsunator...");
        Mockito.when(user.getStatusesCount()).thenReturn(10);
        Mockito.when(user.getFriendsCount()).thenReturn(2);
        Mockito.when(user.getFollowersCount()).thenReturn(2);
        Mockito.when(user.getEmail()).thenReturn("testify@test.com");
        Mockito.when(user.getLocation()).thenReturn("Testing Street");
        Mockito.when(user.getLang()).thenReturn("Testarian");

        return user;
    }

    public static Status getTwitterFullStatusMock() {
        UserMentionEntity mention = Mockito.mock(UserMentionEntity.class);
        Mockito.when(mention.getId()).thenReturn(114749583439036416L);
        Mockito.when(mention.getName()).thenReturn("Testington");
        Mockito.when(mention.getScreenName()).thenReturn("TestyMcTest");

        HashtagEntity tag = Mockito.mock(HashtagEntity.class);
        Mockito.when(tag.getText()).thenReturn("hashtag");

        Status tweet = Mockito.mock(Status.class);
        Mockito.when(tweet.getText()).thenReturn("Testing everythin at everytime.");
        Date date = new Date(1491913808000L);
        Mockito.when(tweet.getCreatedAt()).thenReturn(date);
        Mockito.when(tweet.getId()).thenReturn(114749583439036416L);
        Mockito.when(tweet.getRetweetCount()).thenReturn(123);
        Mockito.when(tweet.getUserMentionEntities()).thenReturn(new UserMentionEntity[]{mention});
        Mockito.when(tweet.getHashtagEntities()).thenReturn(new HashtagEntity[]{tag});
        Mockito.when(tweet.getMediaEntities()).thenReturn(new MediaEntity[]{});
        Mockito.when(tweet.getSymbolEntities()).thenReturn(new SymbolEntity[]{});
        Mockito.when(tweet.getURLEntities()).thenReturn(new URLEntity[]{});
        User user = getTwitterFullUserMock();
        Mockito.when(tweet.getUser()).thenReturn(user);
        Mockito.when(tweet.getLang()).thenReturn("Testarian");
        GeoLocation geo = new GeoLocation(28.385233,-81.563874);
        Mockito.when(tweet.getGeoLocation()).thenReturn(geo);
        Mockito.when(tweet.getFavoriteCount()).thenReturn(1234);
        Mockito.when(tweet.getInReplyToScreenName()).thenReturn("TestFriend");
        Mockito.when(tweet.getInReplyToStatusId()).thenReturn(903641611474958343L);
        Mockito.when(tweet.isPossiblySensitive()).thenReturn(false);
        Mockito.when(tweet.getQuotedStatus()).thenReturn(null);
        Mockito.when(tweet.getQuotedStatusId()).thenReturn(903641611474958343L);
       // Mockito.when(tweet.getRetweetedStatus()).thenReturn(Mockito.mock(Status.class));
        Mockito.when(tweet.getCurrentUserRetweetId()).thenReturn(26815871309L);
        Mockito.when(tweet.getSource()).thenReturn("\\u003Ca href=\"http:\\/\\/itunes.apple.com\\/us\\/app\\/twitter\\/id409789998?mt=12\" \\u003ETwitter for Mac\\u003C\\/a\\u003E");
        Mockito.when(tweet.getWithheldInCountries()).thenReturn(new String[]{"EN", "GB", "SE"});
        Mockito.when(tweet.isFavorited()).thenReturn(true);
        Mockito.when(tweet.isRetweet()).thenReturn(false);
        Mockito.when(tweet.isRetweeted()).thenReturn(false);
        Mockito.when(tweet.isRetweetedByMe()).thenReturn(false);
        Mockito.when(tweet.isTruncated()).thenReturn(false);
        Mockito.when(tweet.getInReplyToUserId()).thenReturn(495834390114736416L);
        Place place = Mockito.mock(Place.class);
        Mockito.when(tweet.getPlace()).thenReturn(place);

        return tweet;
    }
}
