package socialmedia;

/**
 * Created by emperor on 2017-03-29.
 */
public class NotSupportedException extends SocialMediaException {

    public NotSupportedException(){
        super();
    }

    public NotSupportedException(String message, String platform) {
        super(message, platform);
    }


    @Override
    public String toString() {
        return this.getMessage() + " is not supported for platform \"" + this.getPlatform() + "\"";
    }
}
