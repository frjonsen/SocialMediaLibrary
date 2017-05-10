package facebook;

import socialmedia.NotSupportedException;
import socialmedia.User;

import java.time.LocalDate;
import java.util.List;

/**
 * This class is an extension of the User class. The variables added here are the
 * facebook exclusive fields for a user. Can represent a Facebook User, Page or Group.
 * @see socialmedia.User
 */
public class FacebookUser extends User {
    private static final String PLATFORM = "Facebook";
    public enum UserType {
        USER,
        PAGE,
        GROUP
    }

    private facebook4j.User.AgeRange age;
    private String gender;
    private String email;
    private String city;
    private List<String> languages;
    private FacebookBirthDateUtil.FacebookBirthDate birthday;
    private UserType type;
    private String accessToken;

    /**
     * An access token which can be used to make API calls on behalf of this user.
     * @return String with access token
     */
    public String getAccessToken() {
        return accessToken;
    }


    /**
     * Set the access token which can be used to make API calls on behalf of this user.
     * @param accessToken String with access token
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }


    /**
     * Sets the type of user the instance represents
     * @param type Type of user
     */
    public FacebookUser(UserType type) {
        this.type = type;
    }

    /**
     * Gets the type of user the instance represents
     * @return Type of user
     */
    public UserType getType() {
        return this.type;
    }

    @Override
    public String getUsername() {
        throw new NotSupportedException("username", PLATFORM);
    }

    @Override
    public void setUsername(String username) {
        throw new NotSupportedException("username", PLATFORM);
    }

    public FacebookBirthDateUtil.FacebookBirthDate getBirthday() {
        return birthday;
    }

    /**
     * Sets the birthday of the user
     * @param birthday Birthday of the user
     */
    public void setBirthday(FacebookBirthDateUtil.FacebookBirthDate birthday) {
        this.birthday = birthday;
    }

    /**
     * Sets the birthday of the user. DateType is assumed to be FULL.
     * @param birthday Birthday of the user
     */
    public void setBirthday(LocalDate birthday) {
        this.birthday = new FacebookBirthDateUtil.FacebookBirthDate(birthday, FacebookBirthDateUtil.DateType.FULL);
    }

    /**
     * The birthday of a user
     * @param birthday Birthday formatted as returned by the Facebook API
     * @see facebook.FacebookBirthDateUtil.FacebookBirthDate
     */
    public void setBirthday(String birthday) {
        /* Three possible formats, according to facebook API docs:
        *  Full date as MM/DD/YYYY
        *  Only YEAR as YYYY
        *  Only month and MONTHDAY as MM/DD
        */
        this.birthday = FacebookBirthDateUtil.formatDate(birthday);
    }

    /**
     * Facebook allows for users to set gender and pronoun. As such, this is a string,
     * as no assumptions can be made
     * @return Gender of the user
     */
    public String getGender() {
        return gender;
    }

    /**
     * Facebook allows for users to set gender and pronoun.
     * @param gender Gender of the user
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Email of the user.
     * @return User's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Email of the user
     * @param email User's email. No checks are made towards it's validity
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Location where the user lives
     * @return Location where the user lives
     */
    public String getCity() {
        return city;
    }

    /**
     * Location where the user lives
     * @param city Location where the user lives
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Languages the user speaks
     * @return Languages the user speaks
     */
    public List<String> getLanguages() {
        return languages;
    }

    /**
     * Languages the user speaks
     * @param languages Languages the user speaks
     */
    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }


    /**
     * Returns the Age Range of the user.
     * The age range is never a specific age. Possible ranges are 13 - 17, 18 - 21, 21 or above.
     * It's intended use is mostly for marketing purposes, see Birthday for an exact age
     * @return      Age of the User.
     */
    facebook4j.User.AgeRange getAgeRange() { return this.age; }

    /**
     * Updates the age range of the user. Ranges should be 13 - 17, 18 - 21, 21 or above,
     * but no checks are made for it's validity
     * @param age - the new Age.
     */
    void setAge(facebook4j.User.AgeRange ageRange) { this.age = ageRange; }

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

    @Override
    public String toString() {
        String bday = birthday == null ? null : birthday.toString();
        return "FacebookUser{" +
                super.baseToString() +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", languages=" + languages +
                ", birthday=" + bday +
                ", type=" + type +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }
}
