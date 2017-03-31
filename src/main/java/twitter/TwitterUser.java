package twitter;

import socialmedia.User;

public class TwitterUser extends User{
    private static final String PLATFORM = "Twitter";

    private String email;
    private String location;
    private String language;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}
