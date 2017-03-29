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
public class User {
    private String name;
    private String gender;
    private int age;
    private String email;
    private String city;
    private String id;
    private String language;
    private String username;
    private String website;
    private String biography;
    private int uploadCount;
    private int followingCount;
    private int followersCount;

    /**
     * Returns the name of the User object from the platform.
     * @return      Name of the User.
     */
    String getName() { return this.name; } // throws NotSupportedException;

    /**
     * Updates the name on the local User object. It does not update
     * the information on the platform.
     * @param name - the new Name.
     */
    void setName(String name) { this.name = name; }

    /**
     * Returns the Gender of the User object from the platform.
     * @return      Gender of the User.
     */
    String getGender() { return this.gender; }

    /**
     * Updates the gender on the local User object. It does not update
     * the information on the platform.
     * @param gender - the new Gender.
     */
    void setGender(String gender) { this.gender = gender; }

    /**
     * Returns the Age of the User object from the platform.
     * @return      Age of the User.
     */
    int getAge() { return this.age; }

    /**
     * Updates the age on the local User object. It does not update
     * the information on the platform.
     * @param age - the new Age.
     */
    void setAge(int age) { this.age = age; }

    /**
     * Returns the Email of the user object from the platform.
     * @return      Email of the User.
     */
    String getEmail() { return this.email; }

    /**
     * Updates the email on the local User object. It does not update
     * the information on the platform.
     * @param email - the new Email.
     */
    void setEmail(String email) { this.email = email; }

    /**
     * Returns the City of a User from the platform.
     * Which is the location representing the account.
     * @return      City of the User.
     */
    String getCity() { return this.city; }

    /**
     * Updates the city on the local User object. It does not update
     * the information on the platform.
     * @param city - the new City.
     */
    void setCity(String city) { this.city = city; }

    /**
     * Returns the ID of the User object from the platform.
     * @return      ID of the User.
     */
    String getId() { return this.id; }

    /**
     * Updates the ID on the local User object. It does not update
     * the information on the platform.
     * @param id - the new ID.
     */
    void setId(String id) { this.id = id; }

    /**
     * Returns the base language of the User from the platform.
     * @return      Language of the User.
     */
    String getLanguage() { return this.language; }

    /**
     * Updates the language on the local User object. It does not update
     * the information on the platform.
     * @param language - the new Language.
     */
    void setLanguage(String language) { this.language = language; }

    /**
     * Returns the Username of the User from the platform.
     * @return      Username of the User.
     */
    String getUsername() { return this.username; }

    /**
     * Updates the username on the local User object. It does not update
     * the information on the platform.
     * @param username - the new Username.
     */
    void setUsername(String username) { this.username = username; }

    /**
     *  Returns the Website associated with the User from the platform.
     * @return      Website of the User.
     */
    String getWebsite() { return this.website; }

    /**
     * Updates the website on the local User object. It does not update
     * the information on the platform.
     * @param website - the new Website.
     */
    void setWebsite(String website) { this.website = website; }

    /**
     * Returns the Biography of the User object  from the platform.
     * @return      Biography of the User.
     */
    String getBiography() { return this.biography; }

    /**
     * Updates the biography on the local User object. It does not update
     * the information on the platform.
     * @param biography - the new Biography.
     */
    void setBiography(String biography) { this.biography = biography; }

    /**
     * Returns the number of times the User has uploaded content on the platform.
     * This does not include chat messages.
     * @return      UploadCount of the User.
     */
    int getUploadCount() { return this.uploadCount; }

    /**
     *  Updates the upload count on the local User object. It does not update
     *  the information on the platform.
     * @param count - the new UploadCount.
     */
    void setUploadCount(int count) { this.uploadCount = count; }

    /**
     * Returns the number of other users/accounts the given User is following on the platform.
     * @return      FollowingCount of the User.
     */
    int getFollowingCount() { return this.followingCount; }

    /**
     * Updates the following count on the local User object. It does not update
     * the information on the platform.
     * @param count - the new FollowingCount.
     */
    void setFollowingCount(int count) { this.followingCount = count; }

    /**
     * Returns number of users/accounts the User is following on the platform.
     * @return      FollowersCount of the User.
     */
    int getFollowersCount() { return this.followersCount; }

    /**
     * Updates the follower count on the local User object. It does not update
     * the information on the platform.
     * @param count - the new FollowerCount.
     */
    void setFollowersCount(int count) { this.followersCount = count; }
    
}