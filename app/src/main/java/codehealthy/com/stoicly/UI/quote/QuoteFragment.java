package codehealthy.com.stoicly.UI.quote;

import android.app.SearchManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import codehealthy.com.stoicly.R;
import codehealthy.com.stoicly.data.model.QuoteAuthorJoin;

public class QuoteFragment extends Fragment {
    QuoteViewModel        quoteViewModel;
    List<QuoteAuthorJoin> quoteList;
    QuoteAdapter          quoteAdapter;
    RecyclerView          quoteRowView;
    private SwipeRefreshLayout swipeContainer;
    private Handler            handler;
    private Context            context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        quoteViewModel = ViewModelProviders.of(this).get(QuoteViewModel.class);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quote, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setUpToolbar();
        quoteRowView = view.findViewById(R.id.rv_quote);
        getQuotesFromQuoteViewModel();
        setUpSwipeRefreshListener(view);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.context = null;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_toolbar, menu);
        MenuItem searchAction = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) context.getSystemService(Context.SEARCH_SERVICE);
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
                intent.setClass(context, FavouriteQuoteActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    private void setUpToolbar() {
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);

        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        }
    }

    private void getQuotesFromQuoteViewModel() {
        quoteViewModel.getAllQuotesWithAuthorName().observe(getViewLifecycleOwner(), quoteList -> {
            if (quoteList != null && this.quoteList == null) {
                this.quoteList = quoteList;
                setRecyclerViewAndAdapter();
            }

        });
    }

    private void setRecyclerViewAndAdapter() {
        quoteAdapter = new QuoteAdapter(quoteList);
        quoteRowView.setAdapter(quoteAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        quoteRowView.setLayoutManager(linearLayoutManager);
        setUpOnItemClickListener();
    }

    private void setUpSwipeRefreshListener(View view) {
        swipeContainer = view.findViewById(R.id.swipe_refresh_quote);
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
        Collections.shuffle(quoteList);
        quoteAdapter.notifyDataSetChanged();
        swipeContainer.setRefreshing(false);

    }

    private void setUpOnItemClickListener() {
        quoteAdapter.setOnItemClickListener((quote, position, resourceId) -> {
            switch (resourceId) {
                case R.id.btn_quote_favourite:
                    updateFavouriteQuote(quote);
                    notifyAdapterPositionChanged(position);
                    break;
                case R.id.btn_quote_share:
                    shareQuoteByIntent(position);
                    break;
                case R.id.btn_quote_clipboard:
                    copyToClipboard(position);
                    break;
                case R.id.ivAuthorThumbnail:

                    break;

            }
        });
    }

    private void copyToClipboard(int position) {
        ClipboardManager clipboardManager = (ClipboardManager) Objects.requireNonNull(getActivity()).getSystemService(Context.CLIPBOARD_SERVICE);
        QuoteAuthorJoin quote = getQuoteAt(position);
        ClipData clipData = ClipData.newPlainText("quote", quote.getQuote() + "\n" + quote.getAuthorName());
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(context, getString(R.string.description_clipboard_data_copy), Toast.LENGTH_SHORT).show();
    }

    private void shareQuoteByIntent(int position) {
        String authorName = getQuoteAt(position).getAuthorName();
        String quoteText = getQuoteAt(position).getQuote();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_TEXT, quoteText + "\n" + authorName);
        Intent chooser = Intent.createChooser(intent, "Share quote to");
        startActivity(chooser);
    }

    private QuoteAuthorJoin getQuoteAt(int position) {
        return quoteList.get(position);
    }

    private void updateFavouriteQuote(QuoteAuthorJoin quote) {
        int currentQuoteIndex = quoteList.indexOf(quote);
        quote.getQuoteStatus().toggleFavourite();
        quoteList.set(currentQuoteIndex, quote);
        quoteViewModel.updateQuote(quote);
    }

    private void notifyAdapterPositionChanged(int adapterPosition) {
        if (quoteAdapter != null) quoteAdapter.notifyItemChanged(adapterPosition);
    }
}
