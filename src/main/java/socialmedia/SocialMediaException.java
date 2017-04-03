package socialmedia;

public class SocialMediaException extends RuntimeException {
    private final String platform;

    public SocialMediaException() {
        platform = "UNKNOWN";
    }

    public SocialMediaException(String message, String platform) {
        super(message);
        this.platform = platform;
    }

    public String getPlatform() {
        return platform;
    }
}
