package facebook;

import facebook4j.Facebook;
import socialmedia.NotSupportedException;
import socialmedia.User;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * This class is an extension of the User class. The variables added here are the
 * facebook exclusive fields for a user.
 * @see socialmedia.User
 */
public class FacebookUser extends User {
    private static final String PLATFORM = "Facebook";

    private int age;
    private String gender;
    private String email;
    private String city;
    private List<String> languages;
    private FacebookBirthDateUtil.FacebookBirthDate birthday;

    public FacebookBirthDateUtil.FacebookBirthDate getBirthday() {
        return birthday;
    }

    public void setBirthday(FacebookBirthDateUtil.FacebookBirthDate birthday) {
        this.birthday = birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = new FacebookBirthDateUtil.FacebookBirthDate(birthday, FacebookBirthDateUtil.DateType.FULL);
    }

    public void setBirthday(String birthday) {
        /* Three possible formats, according to facebook API docs:
        *  Full date as MM/DD/YYYY
        *  Only YEAR as YYYY
        *  Only month and MONTHDAY as MM/DD
        */
        this.birthday = FacebookBirthDateUtil.formatDate(birthday);
    }

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

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
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
