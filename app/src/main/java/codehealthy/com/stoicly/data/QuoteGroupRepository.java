package codehealthy.com.stoicly.data;

import android.arch.lifecycle.LiveData;

import java.util.List;

import codehealthy.com.stoicly.data.model.QuoteGroup;

public interface QuoteGroupRepository {

    LiveData<List<QuoteGroup>> getAllQuoteGroup();

    List<QuoteGroup> getQuoteGroupByCategoryId(int categoryId);

    void updateQuoteGroup(QuoteGroup quoteGroup);

    void createQuoteGroup(QuoteGroup quoteGroups);

    void deleteQuoteGroup(QuoteGroup quoteGroup);

    interface GetQuoteGroupCallback {

        void onDataLoaded(List<QuoteGroup> quoteGroups);

        void onDataNotAvailable();
    }
}
