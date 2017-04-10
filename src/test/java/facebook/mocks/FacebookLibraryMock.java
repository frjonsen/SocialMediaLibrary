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
    };

    private static List<IdNameEntity> createIdNameList(List<String> items) {
        List<IdNameEntity> listed = new ArrayList<IdNameEntity>();
        for (int i = 0; i < items.size(); ++i) {
            final int id = i;
            IdNameEntity item = new IdNameEntity() {
                @Override
                public String getId() {
                    return Integer.toString(id);
                }

                @Override
                public String getName() {
                    return items.get(id);
                }
            };
            listed.add(item);
        }

        return listed;
    };

    public static Facebook getFacebookMock() throws FacebookException, MalformedURLException, ParseException {
        Facebook f = Mockito.mock(Facebook.class);
        User user = getFacebookFullUserMock();
        Post post = getFullFacebookPostMock();
        Mockito.when(f.getUser("56726489657236574")).thenReturn(user);
        Mockito.when(f.getPost("10202360904079395_10208824524985878")).thenReturn(post);
        return f;
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
        Mockito.when(post.getMessage()).thenReturn("A regular post message");
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
        List<Tag> tags = new ArrayList<>();
        Tag tag = Mockito.mock(Tag.class);
        Mockito.when(tag.getName()).thenReturn("sometag");
        tags.add(tag);
        Mockito.when(post.getMessageTags()).thenReturn(tags);
        List<Post.Property> properties = new ArrayList<>();
        Post.Property property = Mockito.mock(Post.Property.class);
        Mockito.when(property.getName()).thenReturn("length");
        Mockito.when(property.getText()).thenReturn("10");
        properties.add(property);
        Mockito.when(post.getProperties()).thenReturn(properties);

        return post;
    }
}
