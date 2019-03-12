package com.codehealthy.stoicly.data;

import android.arch.lifecycle.LiveData;

import com.codehealthy.stoicly.data.model.QuoteGroup;

import java.util.List;

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
