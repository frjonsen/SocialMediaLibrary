package facebook.mocks;

import facebook4j.Paging;
import facebook4j.ResponseList;
import facebook4j.Summary;

import java.util.ArrayList;

/**
 * Used to mock facebook4j.internal.json.ResponseListImpl, which is package private
 * @param <T>
 */
class ResponseListMock<T> extends ArrayList<T> implements ResponseList<T> {

    @Override
    public Integer getCount() {
        return this.size();
    }

    @Override
    public Paging<T> getPaging() {
        return null;
    }

    @Override
    public Summary getSummary() {
        return null;
    }
}