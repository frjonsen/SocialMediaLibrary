package socialmedia;

import java.time.*;
import java.net.URL;

public interface Post {
    String getText();
    void setText(String text);

    ZonedDateTime getCreationTime();
    void setCreationTime(ZonedDateTime time);

    ZonedDateTime getEditTime();
    void setEditTime(ZonedDateTime time);

    String getId();
    void setId(String id);

    User getAuthor();
    void setAuthor(User author);

    int getSharedCount();
    void setSharedCount(int count);

    String getLanguage();
    void setLanguage(String language);

    Iterable<User> getTo();
    void setTo(Iterable<User> user);

    Iterable<String> getTags();
    void setTags(Iterable<String> tags);

    URL getPermalink();
    void setPermalink(URL permalink);

    String getLocationName();
    void setLocationName();

    // Coordinates getCoordinates();
    // void setCoordinates(Coordinates location);
}