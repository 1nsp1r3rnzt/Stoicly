package codehealthy.com.stoicly.ui.allquote;

import android.app.SearchManager;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import codehealthy.com.stoicly.R;
import codehealthy.com.stoicly.data.model.QuoteAuthorJoin;
import codehealthy.com.stoicly.ui.favourite.FavouriteQuoteActivity;
import codehealthy.com.stoicly.ui.main.LinearLayoutManagerWrapper;

public class QuoteFragment extends Fragment {
    private static final String BUNDLE_SEARCH_QUERY_KEY = "BUNDLE_SEARCH_QUERY_KEY ";
    QuoteAdapter quoteAdapter;
    @BindView(R.id.rv_quote)
    RecyclerView       quoteRecyclerView;
    @BindView(R.id.swipe_refresh_quote)
    SwipeRefreshLayout swipeContainer;
    private Handler                                         handler;
    private QuoteAdapter.OnQuoteFragmentInteractionListener listener;
    private Unbinder                                        unbinder;
    private QuoteViewModel                                  quoteViewModel;
    private String                                          newText;


    public static QuoteFragment newInstance() {
        return new QuoteFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof QuoteAdapter.OnQuoteFragmentInteractionListener) {
            listener = (QuoteAdapter.OnQuoteFragmentInteractionListener) context;
        } else {
            throw new ClassCastException("Activity must implement OnQuoteFragmentInteractionListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quote, container, false);
        unbinder = ButterKnife.bind(this, view);

        quoteAdapter = new QuoteAdapter(getContext());
        setUpOnItemClickListener(quoteAdapter);
        quoteRecyclerView.setAdapter(quoteAdapter);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManagerWrapper(getContext(), LinearLayoutManager.VERTICAL, false);
        quoteRecyclerView.setLayoutManager(linearLayoutManager);
        setUpSwipeRefreshListener();


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        quoteViewModel = ViewModelProviders.of(this).get(QuoteViewModel.class);
        subscribeToQuotes(quoteViewModel.getAllQuotesWithAuthorName());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
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
                quoteAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                QuoteFragment.this.newText = newText;
                quoteAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int actionId = item.getItemId();

        switch (actionId) {
            case (R.id.action_favourite):
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setClass(Objects.requireNonNull(getContext()), FavouriteQuoteActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private void subscribeToQuotes(LiveData<List<QuoteAuthorJoin>> liveData) {
        liveData.observe(getViewLifecycleOwner(), this::setRecyclerViewAndAdapter);
    }

    private void setRecyclerViewAndAdapter(List<QuoteAuthorJoin> quoteList) {
        quoteAdapter.setQuoteListItems(quoteList);

    }

    private void setUpSwipeRefreshListener() {

        swipeContainer.setOnRefreshListener(() ->
                getHandler().post(this::RefreshQuotesAndNotifyDataSet));
    }

    private Handler getHandler() {
        if (handler == null) {
            handler = new Handler();
        }
        return handler;
    }

    private void RefreshQuotesAndNotifyDataSet() {
        quoteAdapter.clear();
        quoteViewModel.getRandomQuotes();
        swipeContainer.setRefreshing(false);
    }

    private void setUpOnItemClickListener(QuoteAdapter quoteAdapter) {
        quoteAdapter.setOnItemClickListener((quote, bundle) -> {
            int position = bundle.getInt(QuoteAdapter.BUNDLE_QUOTE_POSITION);
            int resourceId = bundle.getInt(QuoteAdapter.BUNDLE_RESOURCE_ID);
            String transitionName = bundle.getString(QuoteAdapter.BUNDLE_SHARED_ELEMENT_TRANSITION);
            switch (resourceId) {
                case R.id.btn_quote_favourite:
                    quote.getQuoteStatus().toggleFavourite();
                    updateQuote(quote);

                    break;
                case R.id.btn_quote_share:
                    String authorName = quote.getAuthorName();
                    String quoteText = quote.getQuote();
                    listener.onShareByIntentListener(quoteText + "\n" + authorName, "Share Quote via");
                    break;
                case R.id.btn_quote_clipboard:
                    ClipData clipData = ClipData.newPlainText("quote", quote.getQuote() + "\n" + quote.getAuthorName());
                    listener.onCopyToClipboardListener(clipData);
                    break;
                case R.id.ivAuthorThumbnail:
                    listener.onAuthorSelectedListener(quote.getAuthorId(), transitionName);
                    break;
            }
        });
    }

    private void updateQuote(QuoteAuthorJoin quote) {
        quoteViewModel.updateQuote(quote);
    }

}

