package twitter;

import socialmedia.SocialMediaAPI;

import java.util.List;

public abstract class TwitterAPI extends SocialMediaAPI<TwitterUser>{
    public abstract TwitterUser getUser(String id);
}
