package twitter.mocks;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import twitter4j.IDs;

public class IDsResponseMock implements Answer<IDs> {
    final static int MAX_PAGES = 3;
    final static int IDS_PER_PAGE = 3;
    @Override
    public IDs answer(InvocationOnMock invocationOnMock) throws Throwable {
        Object[] args = invocationOnMock.getArguments();
        long page = args.length == 1 ? (long)args[0] : (long)args[1];
        long[] ids = new long[IDS_PER_PAGE];
        if (page < MAX_PAGES) {
            for (long i = 0; i < IDS_PER_PAGE; ++i) {
                ids[(int)i] = page + i + 1;
            }
        }
        IDs mockedIDs = Mockito.mock(IDs.class);
        Mockito.when(mockedIDs.hasNext()).thenReturn(page < MAX_PAGES);
        Mockito.when(mockedIDs.getIDs()).thenReturn(ids);
        Mockito.when(mockedIDs.getNextCursor()).thenReturn(ids[IDS_PER_PAGE - 1]);
        return mockedIDs;
    }
}
