package twitter.mocks;

import org.mockito.Mockito;
import twitter4j.*;

import java.util.*;

import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;

public class TwitterLibraryMock {

    public static Twitter getTwitterMock() throws TwitterException {
        Twitter tw = Mockito.mock(Twitter.class);
        User user = getTwitterFullUserMock();
        Status tweet = getTwitterFullStatusMock();
        Map<String, RateLimitStatus> rateStatus = getStatusMock();
        ResponseList<Status> timeLine = getTimeLineMock();

        Mockito.when(tw.showUser("TestyMcTest")).thenReturn(user);
        Mockito.when(tw.showUser(6253282L)).thenReturn(user);

        Mockito.when(tw.showStatus(114749583439036416L)).thenReturn(tweet);
        Mockito.when(tw.getRateLimitStatus()).thenReturn(rateStatus);

        Mockito.when(tw.getUserTimeline("TestyMcTest")).thenReturn(timeLine);
        Mockito.when(tw.getUserTimeline(6253282L)).thenReturn(timeLine);

        Mockito.when(tw.createFavorite(6253282L)).thenReturn(tweet);
        Mockito.when(tw.destroyFavorite(6253282L)).thenReturn(tweet);

        Mockito.when(tw.updateStatus("The best tweet for testing")).thenReturn(tweet);

        Mockito.when(tw.createFriendship("TestyMcTest", true)).thenReturn(user);
        Mockito.when(tw.createFriendship("TestyMcTest")).thenReturn(user);
        Mockito.when(tw.createFriendship(6253282L, true)).thenReturn(user);
        Mockito.when(tw.createFriendship(6253282L)).thenReturn(user);
        Mockito.when(tw.destroyFriendship("TestyMcTest")).thenReturn(user);
        Mockito.when(tw.destroyFriendship(6253282L)).thenReturn(user);

        Query query = new Query("Test sweet tweet");
        QueryResult querryRes = getQueryResultMock(query);
        Mockito.when(tw.search(any(Query.class))).thenReturn(querryRes);

        IDsResponseMock response = new IDsResponseMock();
        Mockito.when(tw.getFollowersIDs(not(eq(-2L)))).thenAnswer(response);
        Mockito.when(tw.getFollowersIDs(not(eq(-2L)), anyLong())).thenAnswer(response);
        Mockito.when(tw.getFriendsIDs(not(eq(-2L)))).thenAnswer(response);
        Mockito.when(tw.getFriendsIDs(not(eq(-2L)), anyLong())).thenAnswer(response);
        Mockito.when(tw.search(Mockito.any(Query.class))).thenReturn(querryRes);

        ResponseList<User> searchUserList = getResponselistMock();
        Mockito.when(tw.searchUsers("Test", 1)).thenReturn(searchUserList);
        addFailureCases(tw);
        return tw;
    }

    private static void addFailureCases(Twitter tw) throws TwitterException {
        Mockito.when(tw.showUser("fails")).thenThrow(TwitterException.class);
        Mockito.when(tw.showStatus(-1)).thenThrow(TwitterException.class);
        Mockito.when(tw.getUserTimeline("fails")).thenThrow(TwitterException.class);
        Mockito.when(tw.createFavorite(-1)).thenThrow(TwitterException.class);
        Mockito.when(tw.destroyFavorite(-1)).thenThrow(TwitterException.class);
        Mockito.when(tw.updateStatus("fails")).thenThrow(TwitterException.class);
        Mockito.when(tw.createFriendship("fails")).thenThrow(TwitterException.class);
        Mockito.when(tw.destroyFriendship("fails")).thenThrow(TwitterException.class);
        Mockito.when(tw.getFollowersIDs(-2L)).thenThrow(TwitterException.class);
        Mockito.when(tw.getFriendsIDs(-2L)).thenThrow(TwitterException.class);
        Mockito.when(tw.searchUsers("fails", 1)).thenThrow(TwitterException.class);
    }

    private static Map<String, RateLimitStatus> getStatusMock() {
        Map<String, RateLimitStatus> status = new HashMap<>();
        RateLimitStatus status1 = Mockito.mock(RateLimitStatus.class);
        RateLimitStatus status2 = Mockito.mock(RateLimitStatus.class);
        Mockito.when(status1.getLimit()).thenReturn(200);
        Mockito.when(status2.getLimit()).thenReturn(50);

        status.put("rate1", status1);
        status.put("rate2", status2);

        return status;
    }

    public static ResponseList<Status> getTimeLineMock(){
        ResponseListMock<Status> timeLine = new ResponseListMock<>();
        List<Status> l = generateStatuses(3);
        timeLine.addAll(l);

        return timeLine;
    }

    private static ResponseList<User> getResponselistMock(){
        ResponseListMock<User> users = new ResponseListMock<>();
        List<User> u = generateUsers(20);
        users.addAll(u);

        return users;
    }

    private static List<User> generateUsers(int n){
        List<User> users = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            User user = Mockito.mock(User.class);
            Mockito.when(user.getId()).thenReturn(1L + i);
            Mockito.when(user.getScreenName()).thenReturn("Testy McTest" + i);

            users.add(user);
        }

        return users;
    }

    private static QueryResult getQueryResultMock(Query query){
        query.setCount(100);
        QueryResult queryResult = Mockito.mock(QueryResult.class);
        List<Status> statuses = generateStatuses(2);
        Mockito.when(queryResult.getTweets()).thenReturn(statuses);
        Mockito.when(queryResult.hasNext()).thenReturn(true);
        Mockito.when(queryResult.nextQuery()).thenReturn(query);

        return queryResult;
    }

    private static List<Status> generateStatuses(int n) {
        List<Status> statuses = new ArrayList<>();
        User user = getTwitterFullUserMock();
        for (int i = 0; i < n; ++i) {
            Status status = Mockito.mock(Status.class);
            Mockito.when(status.getId()).thenReturn(123123L + i);
            Mockito.when(status.getText()).thenReturn("Basic small text");
            Mockito.when(status.getUser()).thenReturn(user);

            statuses.add(status);
        }

        return statuses;
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
        Mockito.when(user.getBiggerProfileImageURLHttps()).thenReturn("https://pbs.twimg.com/profile_images/123123123123123123/IPv4Cubt_400x400.jpg");

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
