package twitter;

import socialmedia.User;

/**
 * This is an extension of the User class. The variables added here are the
 * Twitter exclusive fields for a user.
 * @see socialmedia.User
 */
public class TwitterUser extends User{
    private static final String PLATFORM = "Twitter";

    private String email; //TODO::might wanna extend with more variables from 4j...
    private String location;
    private String language;

    /**
     * An application needs to be whitelisted by twitter to be able to
     * fetch the email of a user.
     * @return  Email of User object
     */
    public String getEmail() {
        return email;
    }

    /**
     * An application needs to be whitelisted by twitter to be able to
     * fetch the email of a user.
     * Only updates the local object.
     * @param email The new email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns location of the user. This is set by user on
     * twitter and does not always represent an actual location.
     * @return  location of user object.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets location of the user locally only. This is set by user on
     * twitter and does not always represent an actual location.
     * @param location  new location.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * BCP 47 code for the language used in the UI of the user. Self-declared.
     * Does not mean tweets published by user will be of this language.
     * @return  language.
     */
    public String getLanguage() {
        return language;
    }

    /**
     * BCP 47 code for the language used in the UI of the user. Self-declared.
     * Does not mean tweets published by user will be of this language.
     * Only updates the language locally.
     * @param language  new language.
     */
    public void setLanguage(String language) {
        this.language = language;
    }

}
