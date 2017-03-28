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
     * Returns the name of the User object.
     * @return      Name of the User.
     */
    String getName(); // throws NotSupportedException;
    void setName(String name);

    /**
     * Returns the Gender of the User object.
     * @return      Gender of the User.
     */
    String getGender();
    void setGender(String gender);

    /**
     *
     * @return
     */
    int getAge();
    void setAge(int age);

    /**
     *
     * @return
     */
    String getEmail();
    void setEmail(String email);

    /**
     *
     * @return
     */
    String getCity();
    void setCity();

    /**
     *
     * @return
     */
    String getId();
    void setId(String id);

    /**
     *
     * @return
     */
    String getLanguage();
    void setLanguage();

    /**
     *
     * @return
     */
    String getUsername();
    void setUsername(String username);

    /**
     *
     * @return
     */
    String getWebsite();
    void setWebsite(String website);

    /**
     *
     * @return
     */
    String getBiography();
    void setBiography(String biography);

    /**
     *
     * @return
     */
    int getUploadCount();
    void setUploadCount(int count);

    /**
     *
     * @return
     */
    int getFollowingCount();
    void setFollowingCount(int count);

    /**
     *
     * @return
     */
    int getFollowersCount();
    void setFollowersCount(int count);

    /**
     *
     * @return
     */
    int getLikesCount();
    void setLikesCount(int count);
}