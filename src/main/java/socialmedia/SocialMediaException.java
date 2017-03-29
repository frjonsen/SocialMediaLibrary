package socialmedia;

public class SocialMediaException extends RuntimeException {
    private String platform;

    public SocialMediaException() {

    }

    public SocialMediaException(String message, String platform) {
        super(message);
        this.platform = platform;
    }

    public String getPlatform() {
        return platform;
    }
}
