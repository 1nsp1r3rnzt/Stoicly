package com.codehealthy.stoicly.ui.favourite;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.codehealthy.stoicly.data.model.QuoteAuthorJoin;
import com.codehealthy.stoicly.ui.allquote.BaseQuoteFragment;

import java.util.List;

public class FavouriteQuoteFragment extends BaseQuoteFragment {

    private FavouriteQuoteViewModel favouriteQuoteViewModel;

    public static FavouriteQuoteFragment newInstance() {
        return new FavouriteQuoteFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        favouriteQuoteViewModel = ViewModelProviders.of(this).get(FavouriteQuoteViewModel.class);
        getSwipeContainer().setEnabled(false);
        observeForFavouriteQuoteUpdates(favouriteQuoteViewModel.getFavouriteQuotes());
    }

    private void observeForFavouriteQuoteUpdates(LiveData<List<QuoteAuthorJoin>> favouriteQuotes) {
        favouriteQuotes.observe(getViewLifecycleOwner(), this::setFavouriteListItems);
    }

    private void setFavouriteListItems(List<QuoteAuthorJoin> quoteAuthorJoins) {
        getQuoteAdapter().clear();
        getQuoteAdapter().setQuoteListItems(quoteAuthorJoins);
    }

    @Override
    public void updateQuote(QuoteAuthorJoin quote) {
        favouriteQuoteViewModel.updateQuote(quote);
    }


}


