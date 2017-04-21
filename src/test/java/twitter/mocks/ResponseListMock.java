package twitter.mocks;

import twitter4j.RateLimitStatus;
import twitter4j.ResponseList;
import twitter4j.User;

import java.util.*;

public class ResponseListMock<T> extends ArrayList<T> implements ResponseList<T> {
    @Override
    public RateLimitStatus getRateLimitStatus() {
        return null;
    }

    @Override
    public int getAccessLevel() {
        return 0;
    }
}
