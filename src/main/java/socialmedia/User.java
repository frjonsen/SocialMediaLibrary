package socialmedia;

/**
 * The User class represents a user on a social media network.
 * Depending on the platform associated with the User object,
 * not all attributes may be supported.
 * If a call is made to an unsupported attribute an exception
 * of type NotSupportedException is thrown.
 * See each individual implementation of a social media network
 * for a detailed description of support.
 */
public interface User {
    /**
     * Returns the name of the User object from the platform.
     * @return      Name of the User.
     */
    String getName(); // throws NotSupportedException;

    /**
     * Updates the name on the local User object. It does not update
     * the information on the platform.
     * @param name - the new Name.
     */
    void setName(String name);

    /**
     * Returns the Gender of the User object from the platform.
     * @return      Gender of the User.
     */
    String getGender();

    /**
     * Updates the gender on the local User object. It does not update
     * the information on the platform.
     * @param gender - the new Gender.
     */
    void setGender(String gender);

    /**
     * Returns the Age of the User object from the platform.
     * @return      Age of the User.
     */
    int getAge();

    /**
     * Updates the age on the local User object. It does not update
     * the information on the platform.
     * @param age - the new Age.
     */
    void setAge(int age);

    /**
     * Returns the Email of the user object from the platform.
     * @return      Email of the User.
     */
    String getEmail();

    /**
     * Updates the email on the local User object. It does not update
     * the information on the platform.
     * @param email - the new Email.
     */
    void setEmail(String email);

    /**
     * Returns the City of a User from the platform.
     * Which is the location representing the account.
     * @return      City of the User.
     */
    String getCity();

    /**
     * Updates the city on the local User object. It does not update
     * the information on the platform.
     * @param city - the new City.
     */
    void setCity(String city);

    /**
     * Returns the ID of the User object from the platform.
     * @return      ID of the User.
     */
    String getId();

    /**
     * Updates the ID on the local User object. It does not update
     * the information on the platform.
     * @param id - the new ID.
     */
    void setId(String id);

    /**
     * Returns the base language of the User from the platform.
     * @return      Language of the User.
     */
    String getLanguage();

    /**
     * Updates the language on the local User object. It does not update
     * the information on the platform.
     * @param language - the new Language.
     */
    void setLanguage(String language);

    /**
     * Returns the Username of the User from the platform.
     * @return      Username of the User.
     */
    String getUsername();

    /**
     * Updates the username on the local User object. It does not update
     * the information on the platform.
     * @param username - the new Username.
     */
    void setUsername(String username);

    /**
     *  Returns the Website associated with the User from the platform.
     * @return      Website of the User.
     */
    String getWebsite();

    /**
     * Updates the website on the local User object. It does not update
     * the information on the platform.
     * @param website - the new Website.
     */
    void setWebsite(String website);

    /**
     * Returns the Biography of the User object  from the platform.
     * @return      Biography of the User.
     */
    String getBiography();

    /**
     * Updates the biography on the local User object. It does not update
     * the information on the platform.
     * @param biography - the new Biography.
     */
    void setBiography(String biography);

    /**
     * Returns the number of times the User has uploaded content on the platform.
     * This does not include chat messages.
     * @return      UploadCount of the User.
     */
    int getUploadCount();

    /**
     *  Updates the upload count on the local User object. It does not update
     *  the information on the platform.
     * @param count - the new UploadCount.
     */
    void setUploadCount(int count);

    /**
     * Returns the number of other users/accounts the given User is following on the platform.
     * @return      FollowingCount of the User.
     */
    int getFollowingCount();

    /**
     * Updates the following count on the local User object. It does not update
     * the information on the platform.
     * @param count - the new FollowingCount.
     */
    void setFollowingCount(int count);

    /**
     * Returns number of users/accounts the User is following on the platform.
     * @return      FollowersCount of the User.
     */
    int getFollowersCount();

    /**
     * Updates the follower count on the local User object. It does not update
     * the information on the platform.
     * @param count - the new FollowerCount.
     */
    void setFollowersCount(int count);
}