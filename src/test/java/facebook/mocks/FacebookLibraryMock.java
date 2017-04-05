package facebook.mocks;


import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.IdNameEntity;
import facebook4j.User;
import org.mockito.Mockito;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FacebookLibraryMock {

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

    public static Facebook getFacebookMock() throws FacebookException, MalformedURLException {
        Facebook f = Mockito.mock(Facebook.class);
        User user = getFacebookFullUserMock();
        Mockito.when(f.getUser("someid")).thenReturn(user);
        return f;
    }

    public static User getFacebookFullUserMock() throws MalformedURLException {
        User user = Mockito.mock(User.class);
        Mockito.when(user.getGender()).thenReturn("Man");
        Mockito.when(user.getBio()).thenReturn("A very short biography");
        User.AgeRange range = new User.AgeRange() {
            @Override
            public Integer getMin() {
                return 21;
            }

            @Override
            public Integer getMax() {
                return null;
            }
        };
        Mockito.when(user.getAgeRange()).thenReturn(range);
        Mockito.when(user.getBirthday()).thenReturn("10/02/1986");
        Mockito.when(user.getHometown()).thenReturn(new IdNameEntity() {
            @Override
            public String getId() {
                return "uselessid";
            }

            @Override
            public String getName() {
                return "atown";
            }
        });
        Mockito.when(user.getEmail()).thenReturn("email@example.com");
        List<String> languages = new ArrayList<String>();
        languages.add("Swedish");
        languages.add("English");
        Mockito.when(user.getLanguages()).thenReturn(createIdNameList(languages));
        Mockito.when(user.getName()).thenReturn("some name");
        Mockito.when(user.getId()).thenReturn("56726489657236574");
        Mockito.when(user.getUsername()).thenReturn("someusername");
        Mockito.when(user.getWebsite()).thenReturn(new URL("https://example.com/user"));
        return user;
    }
}
