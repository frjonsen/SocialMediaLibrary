package facebook.mocks;

import facebook4j.*;
import org.mockito.Mockito;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FacebookLibraryMock {

    private static User.AgeRange createAgeRange(Integer min, Integer max) {
        return new User.AgeRange() {
            @Override
            public Integer getMin() {
                return min;
            }

            @Override
            public Integer getMax() {
                return max;
            }
        };
    }

    private static IdNameEntity createHometown(String town) {
        return new IdNameEntity() {
            @Override
            public String getId() {
                return "uselessid";
            }

            @Override
            public String getName() {
                return town;
            }
        };
    }

    private static List<IdNameEntity> createIdNameList(List<String> items) {
        List<IdNameEntity> listed = new ArrayList<IdNameEntity>();
        for (int i = 0; i < items.size(); ++i) {
            final int id = i;
            IdNameEntity item = new IdNameEntity() {
                @Override
                public String getId() {
                    return Integer.toString(id + 1);
                }

                @Override
                public String getName() {
                    return items.get(id);
                }
            };
            listed.add(item);
        }

        return listed;
    }

    private static <T> ResponseList<T> intoResponseList(List<T> l) {
        ResponseListMock<T> list = new ResponseListMock<>();
        list.addAll(l);
        return list;
    }

    public static Facebook getFacebookMock() throws FacebookException, MalformedURLException, ParseException {
        Facebook f = Mockito.mock(Facebook.class);
        User user = getFacebookFullUserMock();
        Post post = getFullFacebookPostMock();
        ResponseList<User> searchResults = intoResponseList(generateBasicFacebookUsers(3));
        Mockito.when(f.getUser("56726489657236574")).thenReturn(user);
        Mockito.when(f.getMe()).thenReturn(user);
        Mockito.when(f.getPost("10202360904079395_10208824524985878")).thenReturn(post);
        Mockito.when(f.searchUsers("User")).thenReturn(searchResults);
        Mockito.when(f.getPictureURL(Integer.MAX_VALUE, 0)).thenReturn(new URL("https://scontent.xx.fbcdn.net/v/t31.0-1/selfpicture"));
        Mockito.when(f.getPictureURL("56726489657236574", Integer.MAX_VALUE, 0))
                .thenReturn(new URL("https://scontent.xx.fbcdn.net/v/t31.0-1/otheruserpicture"));
        Mockito.when(f.likePost("10202360904079395_10208824524985878")).thenReturn(true);
        Mockito.when(f.likePost("nonexistent")).thenReturn(false);
        Mockito.when(f.unlikePost("10202360904079395_10208824524985878")).thenReturn(true);
        Mockito.when(f.unlikePost("nonexistent")).thenReturn(false);
        ResponseList<Post> selfFeed = intoResponseList(generateBasicFacebookPosts(3));
        Mockito.when(f.getFeed()).thenReturn(selfFeed);
        ResponseList<Post> otherFeed = intoResponseList(generateBasicFacebookPosts(2));
        Mockito.when(f.getFeed("56726489657236574")).thenReturn(otherFeed);
        ResponseList<Comment> comments = generateBasicFacebookPostComments(3);
        Mockito.when(f.getPostComments("somepostid")).thenReturn(comments);

        return f;
    }

    public static List<User> generateBasicFacebookUsers(int numberOfUsers) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < numberOfUsers; ++i) {
            User user = Mockito.mock(User.class);
            String gender = i % 2 == 0 ? "Man" : "Woman";
            Mockito.when(user.getGender()).thenReturn(gender);
            Mockito.when(user.getUsername()).thenReturn("User" + i);
            Mockito.when(user.getId()).thenReturn(Integer.toString(i + 1));
            Mockito.when(user.getName()).thenReturn("Firstname LastName" + i);
            Mockito.when(user.getEmail()).thenReturn("email" + i + "@example.com");

            users.add(user);
        }

        return users;
    }

    private static Category generateCategory() throws ParseException {
        Category c = Mockito.mock(Category.class);
        Mockito.when(c.getCategory()).thenReturn("User");
        Mockito.when(c.getName()).thenReturn("someuser");
        Mockito.when(c.getId()).thenReturn("someuserid");
        final Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse("2017-03-18T16:59:49+0000");
        Mockito.when(c.getCreatedTime()).thenReturn(date);
        return c;
    }

    private static List<Post> generateBasicFacebookPosts(int numberOfPosts) throws ParseException {
        List<Post> posts = new ArrayList<>();
        final Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse("2017-03-18T16:59:49+0000");
        Category c = generateCategory();
        for (int i = 0; i < numberOfPosts; ++i) {
            Post post = Mockito.mock(Post.class);
            Mockito.when(post.getId()).thenReturn("PostId"  + i + 1);
            Mockito.when(post.getMessage()).thenReturn("Message of post " + i + 1);
            Mockito.when(post.getStatusType()).thenReturn("status");
            Date d = new Date(date.getTime() + i);
            Mockito.when(post.getCreatedTime()).thenReturn(d);
            Mockito.when(post.getFrom()).thenReturn(c);
            posts.add(post);
        }
        return posts;
    }

    private static ResponseList<Comment> generateBasicFacebookPostComments(int nrOfComments) throws ParseException {
        List<Comment> comments = new ArrayList<>();
        final Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse("2017-03-18T16:59:49+0000");
        for (int i = 0; i < nrOfComments; ++i) {
            Comment comment = Mockito.mock(Comment.class);
            Date d = new Date(date.getTime() + i);
            Mockito.when(comment.getCreatedTime()).thenReturn(d);
            Mockito.when(comment.getMessage()).thenReturn("Comment message " + i);
            Category c = generateCategory();
            Mockito.when(comment.getFrom()).thenReturn(c);
            Mockito.when(comment.getLikeCount()).thenReturn(i);
            Mockito.when(comment.getId()).thenReturn("commentid" + i);
            comments.add(comment);
        }

        return intoResponseList(comments);
    }

    private static User getFacebookFullUserMock() throws MalformedURLException {
        User user = Mockito.mock(User.class);

        List<String> languages = new ArrayList<>();
        languages.add("Swedish");
        languages.add("English");

        Mockito.when(user.getGender()).thenReturn("Man");
        Mockito.when(user.getBio()).thenReturn("A very short biography");
        Mockito.when(user.getAgeRange()).thenReturn(createAgeRange(21, null));
        Mockito.when(user.getBirthday()).thenReturn("10/02/1986");
        Mockito.when(user.getHometown()).thenReturn(createHometown("atown"));
        Mockito.when(user.getEmail()).thenReturn("email@example.com");
        Mockito.when(user.getLanguages()).thenReturn(createIdNameList(languages));
        Mockito.when(user.getName()).thenReturn("some name");
        Mockito.when(user.getId()).thenReturn("56726489657236574");
        Mockito.when(user.getUsername()).thenReturn("someusername");
        Mockito.when(user.getWebsite()).thenReturn(new URL("https://example.com/user"));
        return user;
    }

    private static Post getFullFacebookPostMock() throws MalformedURLException, ParseException {
        Post post = Mockito.mock(Post.class);

        Mockito.when(post.getId()).thenReturn("10202360904079395_10208824524985878");
        Mockito.when(post.getMessage()).thenReturn("A regular post #message");
        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse("2017-03-18T16:59:49+0000");
        Mockito.when(post.getCreatedTime()).thenReturn(date);
        Date updated = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse("2017-03-18T17:00:49+0000");
        Mockito.when(post.getUpdatedTime()).thenReturn(updated);
        Mockito.when(post.isHidden()).thenReturn(false);
        Mockito.when(post.isPublished()).thenReturn(true);
        Mockito.when(post.getLink()).thenReturn(new URL("https://example.com/post"));
        Mockito.when(post.getSource()).thenReturn(new URL("https://example.com/postsource"));
        Mockito.when(post.getPlace()).thenReturn(Mockito.mock(Place.class));
        Mockito.when(post.getObjectId()).thenReturn("theobjectid");
        Mockito.when(post.getParentId()).thenReturn("theparentid");
        Mockito.when(post.getStatusType()).thenReturn("approved_friend");
        List<String> with = new ArrayList<>();
        with.add("friend1");
        with.add("friend2");
        Mockito.when(post.getWithTags()).thenReturn(createIdNameList(with));
        List<Post.Property> properties = new ArrayList<>();
        Mockito.when(post.getProperties()).thenReturn(properties);

        return post;
    }
}
