package codehealthy.com.stoicly.ui.allquote;

import android.app.SearchManager;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.List;
import java.util.Objects;

import codehealthy.com.stoicly.R;
import codehealthy.com.stoicly.data.model.QuoteAuthorJoin;
import codehealthy.com.stoicly.ui.main.ToolbarTitle;

public class QuoteFragment extends BaseQuoteFragment {
    private QuoteViewModel quoteViewModel;
    private String         newText;
    private ToolbarTitle   toolbarListener;


    public static QuoteFragment newInstance() {
        return new QuoteFragment();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ToolbarTitle) {
            toolbarListener = (ToolbarTitle) getActivity();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        setUpToolbarTitle();
        quoteViewModel = ViewModelProviders.of(this).get(QuoteViewModel.class);
        subscribeToQuotes(quoteViewModel.getAllQuotesWithAuthorName());
        setUpSwipeRefreshListener();
        getListener().setHomeAsNavigationIcon();
    }

    private void setUpToolbarTitle() {
        if (toolbarListener != null) {
            assert getActivity() != null;
            String appName = getActivity().getApplicationInfo().loadLabel(getActivity().getPackageManager()).toString();
            toolbarListener.setToolbarTitle(appName);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_toolbar, menu);
        MenuItem searchAction = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) Objects.requireNonNull(getContext()).getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) searchAction.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(Objects.requireNonNull(getActivity()).getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getQuoteAdapter().getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                QuoteFragment.this.newText = newText;
                getQuoteAdapter().getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int actionId = item.getItemId();

        switch (actionId) {
            case (R.id.action_favourite):
                return false;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private void subscribeToQuotes(LiveData<List<QuoteAuthorJoin>> liveData) {
        liveData.observe(getViewLifecycleOwner(), this::setUpAdapterData);
    }

    private void setUpAdapterData(List<QuoteAuthorJoin> quoteList) {
        if (quoteList != null && getQuoteAdapter() != null) {
            getQuoteAdapter().setQuoteListItems(quoteList);
        }

    }

    private void setUpSwipeRefreshListener() {

        getSwipeContainer().setOnRefreshListener(() ->
                getHandler().post(this::shuffleQuotesAndNotifyDataSet));
    }

    private void shuffleQuotesAndNotifyDataSet() {
        getQuoteAdapter().clear();
        quoteViewModel.getRandomQuotes();
        getSwipeContainer().setRefreshing(false);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        toolbarListener = null;
    }

    @Override
    protected void updateQuote(QuoteAuthorJoin quote) {
        quoteViewModel.updateQuote(quote);
    }

}

