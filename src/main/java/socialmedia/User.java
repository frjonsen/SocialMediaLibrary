package socialmedia;

import com.google.gson.JsonObject;

import java.net.URL;


/**
 * The User class represents a user on a social media network.
 * Depending on the platform associated with the User object,
 * not all attributes may be supported.
 * If a call is made to an unsupported attribute an exception
 * of type NotSupportedException is thrown.
 * See each individual implementation of a social media network
 * for a detailed description of support.
 */
public abstract class User {
    private String name;
    private String id;
    private String username;
    private URL website;
    private String biography;
    private int uploadCount;
    private int followingCount;
    private int followersCount;

    /**
     * Returns a stringified version of this instance
     * @return String with all members of this class
     */
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", website=" + website +
                ", biography='" + biography + '\'' +
                ", uploadCount=" + uploadCount +
                ", followingCount=" + followingCount +
                ", followersCount=" + followersCount +
                '}';
    }

    /**
     * This function is simply meant to be of assistance when
     * overriding toString in a derived class
     * @return String with all members of this class
     */
    protected String baseToString() {
        return "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", website=" + website +
                ", biography='" + biography + '\'' +
                ", uploadCount=" + uploadCount +
                ", followingCount=" + followingCount +
                ", followersCount=" + followersCount;
    }

    /**
     * Returns the name of the User object from the platform.
     * @return      Name of the User.
     */
    public String getName() { return this.name; }

    /**
     * Updates the name on the local User object. It does not update
     * the information on the platform.
     * @param name - the new Name.
     */
    public void setName(String name) { this.name = name; }

    /**
     * Returns the ID of the User object from the platform.
     * @return  ID of the User.
     */
    public String getId() { return this.id; }

    /**
     * Updates the ID on the local User object. It does not update
     * the information on the platform.
     * @param id    the new ID.
     */
    public void setId(String id) { this.id = id; }

    /**
     * Returns the Username of the User from the platform.
     * @return  Username of the User.
     */
    public String getUsername() { return this.username; }

    /**
     * Updates the username on the local User object. It does not update
     * the information on the platform.
     * @param username  the new Username.
     */
    public void setUsername(String username) { this.username = username; }

    /**
     *  Returns the Website associated with the User from the platform.
     * @return  Website of the User.
     */
    public URL getWebsite() { return this.website; }

    /**
     * Updates the website on the local User object. It does not update
     * the information on the platform.
     * @param website   the new Website.
     */
    public void setWebsite(URL website) { this.website = website; }

    /**
     * Returns the Biography of the User object  from the platform.
     * @return  Biography of the User.
     */
    public String getBiography() { return this.biography; }

    /**
     * Updates the biography on the local User object. It does not update
     * the information on the platform.
     * @param biography the new Biography.
     */
    public void setBiography(String biography) { this.biography = biography; }

    /**
     * Returns the number of times the User has uploaded content on the platform.
     * This does not include chat messages.
     * @return  UploadCount of the User.
     */
    public int getUploadCount() { return this.uploadCount; }

    /**
     *  Updates the upload count on the local User object. It does not update
     *  the information on the platform.
     * @param count the new UploadCount.
     */
    public void setUploadCount(int count) { this.uploadCount = count; }

    /**
     * Returns the number of other users/accounts the given User is following on the platform.
     * @return  FollowingCount of the User.
     */
    public int getFollowingCount() { return this.followingCount; }

    /**
     * Updates the following count on the local User object. It does not update
     * the information on the platform.
     * @param count the new FollowingCount.
     */
    public void setFollowingCount(int count) throws NotSupportedException {
        this.followingCount = count;
    }

    /**
     * Returns number of users/accounts the User is following on the platform.
     * @return  FollowersCount of the User.
     */
    public int getFollowersCount() { return this.followersCount; }

    /**
     * Updates the follower count on the local User object. It does not update
     * the information on the platform.
     * @param count the new FollowerCount.
     */
    public void setFollowersCount(int count) { this.followersCount = count; }

}