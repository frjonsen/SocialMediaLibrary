package facebook;

import socialmedia.NotSupportedException;
import socialmedia.User;

/**
 * This class is an extension of the User class. The variables added here are the
 * facebook exclusive fields for a user.
 * @see socialmedia.User
 */
public class FacebookUser extends User {
    static final String PLATFORM = "Facebook";

    private int age;
    private String gender;
    private String email;
    private String city;
    private String language;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }


    /**
     * Returns the Age of the User object.
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
     * This variable is not supported by facebook and throws an NotSupportedException
     * @return
     */
    @Override
    public int getFollowingCount() {
        throw new NotSupportedException("getFollowingCount", FacebookUser.PLATFORM);
    }

    /**
     * This variable is not supported by facebook and will throw an
     * NotSupportedException.
     * @param count
     */
    @Override
    public void setFollowingCount(int count) {
        throw new NotSupportedException("setFollowingCount", FacebookUser.PLATFORM);
    }
    /**
     * This variable is not supported by facebook and will throw an
     * NotSupportedException.
     */
    @Override
    public int getUploadCount() {
        throw new NotSupportedException("getUploadCount", FacebookUser.PLATFORM);
    }
    /**
     * This variable is not supported by facebook and will throw an
     * NotSupportedException:
     * @param count
     */
    @Override
    public void setUploadCount(int count) {
        throw new NotSupportedException("setUploadCount", FacebookUser.PLATFORM);
    }

}
