package twitter.mocks;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import twitter4j.IDs;
import twitter4j.TwitterException;

public class IDsResponseMock implements Answer<IDs> {
    private final static int MAX_PAGES = 3;
    private final static int IDS_PER_PAGE = 3;
    @Override
    public IDs answer(InvocationOnMock invocationOnMock) throws Throwable {
        Object[] args = invocationOnMock.getArguments();
        long offset = 0;
        long page = args.length == 1 ? (long)args[0] : (long)args[1];
        if (args.length == 2) {
            offset = 1;
            if ((long)args[0] == 1234L) throw new TwitterException("Twitter down");
        }
        long[] ids = new long[IDS_PER_PAGE];
        if (page < MAX_PAGES*IDS_PER_PAGE - 1) {
            for (long i = 0; i < IDS_PER_PAGE; ++i) {
                ids[(int)i] = page + i + 1 + offset;
            }
        }
        IDs mockedIDs = Mockito.mock(IDs.class);
        Mockito.when(mockedIDs.hasNext()).thenReturn(page < (MAX_PAGES-2)*IDS_PER_PAGE);
        Mockito.when(mockedIDs.getIDs()).thenReturn(ids);
        Mockito.when(mockedIDs.getNextCursor()).thenReturn(ids[IDS_PER_PAGE - 1]);
        return mockedIDs;
    }
}
