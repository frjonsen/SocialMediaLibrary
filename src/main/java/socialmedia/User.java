package socialmedia;

public interface User {
    String getName();
    void setName(String name);

    String getGender();
    void setGender(String gender);

    int getAge();
    void setAge(int age);

    String getEmail();
    void setEmail(String email);

    String getCity();
    void setCity();

    String getId();
    void setId(String id);

    String getLanguage();
    void setLanguage();

    String getUsername();
    void setUsername(String username);

    String getWebsite();
    void setWebsite(String website);

    String getBiography();
    void setBiography(String biography);

    int getUploadCount();
    void setUploadCount(int count);

    int getFollowingCount();
    void setFollowingCount(int count);

    int getFollowersCount();
    void setFollowersCount(int count);

    int getLikesCount();
    void setLikesCount(int count);
}