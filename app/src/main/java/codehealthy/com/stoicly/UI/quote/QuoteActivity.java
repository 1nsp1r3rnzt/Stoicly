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
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import codehealthy.com.stoicly.R;
import codehealthy.com.stoicly.data.model.QuoteAuthorJoin;

public class QuoteActivity extends AppCompatActivity {
    QuoteViewModel        quoteViewModel;
    List<QuoteAuthorJoin> quoteList;
    QuoteAdapter          quoteAdapter;
    RecyclerView          quoteRowView;
    private SwipeRefreshLayout swipeContainer;
    private Handler            handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);
        setToolbar();
        setUpSwipeRefreshListener();
        quoteViewModel = ViewModelProviders.of(this).get(QuoteViewModel.class);
        quoteRowView = findViewById(R.id.rv_quote);
        getQuotesFromQuoteViewModel();

    }

    private void setUpSwipeRefreshListener() {
        swipeContainer = findViewById(R.id.swipe_refresh_quote);
        swipeContainer.setOnRefreshListener(() ->
                getHandler().post(this::refreshAllQuotes));
    }

    private Handler getHandler() {
        if (handler == null) {
            handler = new Handler();
        }
        return handler;
    }

    private void refreshAllQuotes() {
        Collections.shuffle(quoteList);
        quoteAdapter.notifyDataSetChanged();
        swipeContainer.setRefreshing(false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
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
        return super.onCreateOptionsMenu(menu);
//        inflate the menu
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int actionId = item.getItemId();

        switch (actionId) {
            case (R.id.action_favourite):
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setClass(this, FavouriteQuoteActivity.class);
                startActivity(intent);
                break;
        }

        return true;
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    private void getQuotesFromQuoteViewModel() {

        quoteViewModel.getAllQuotesWithAuthorName().observe(this, quoteList -> {
            if (quoteList != null && this.quoteList == null) {
                this.quoteList = quoteList;

                loadDataInQuoteRView(quoteList);

            }
        });
    }


    private void loadDataInQuoteRView(@NonNull List<QuoteAuthorJoin> quoteList) {

        quoteAdapter = new QuoteAdapter(quoteList);
        quoteRowView.setAdapter(quoteAdapter);
        quoteRowView.setLayoutManager(new LinearLayoutManager(this));

        quoteAdapter.setOnItemClickListener((quote, position, resourceId) -> {

            switch (resourceId) {
                case R.id.btn_quote_favourite:
                    updateFavouriteQuote(quote);
                    notifyAdapterPositionChanged(position);
                    break;
                case R.id.btn_quote_share:
                    shareQuote(position);
                    break;
                case R.id.btn_quote_clipboard:
                    copyToClipboard(position);
                    break;
            }
        });
    }

    private void copyToClipboard(int position) {
        ClipboardManager clipboardManager = (ClipboardManager) this.getSystemService(CLIPBOARD_SERVICE);
        QuoteAuthorJoin quote = getQuoteAt(position);
        ClipData clipData = ClipData.newPlainText("quote", quote.getQuote() + "\n" + quote.getAuthorName());
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(this, "Copied to clipboard", Toast.LENGTH_SHORT).show();

    }

    private void shareQuote(int position) {
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
        if (quote.isFavourite()) quote.setFavourite(false);
        else quote.setFavourite(true);

        quoteList.set(currentQuoteIndex, quote);
        quoteViewModel.updateQuote(quote);
    }

    private void notifyAdapterPositionChanged(int adapterPosition) {
        if (quoteAdapter != null) {
            quoteAdapter.notifyItemChanged(adapterPosition);
        }
    }


}

