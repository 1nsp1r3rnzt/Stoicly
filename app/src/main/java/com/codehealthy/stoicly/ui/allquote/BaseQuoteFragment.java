package com.codehealthy.stoicly.ui.allquote;

import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codehealthy.stoicly.R;
import com.codehealthy.stoicly.data.model.QuoteAuthorJoin;
import com.codehealthy.stoicly.databinding.FragmentQuoteBinding;
import com.codehealthy.stoicly.ui.main.LinearLayoutManagerWrapper;

public abstract class BaseQuoteFragment extends Fragment {
    private static final String                                     BUNDLE_SEARCH_QUERY_KEY = "BUNDLE_SEARCH_QUERY_KEY ";
    private              QuoteAdapter                               quoteAdapter;
    private              RecyclerView                               quoteRecyclerView;
    private              SwipeRefreshLayout                         swipeContainer;
    private              Handler                                    handler;
    private              QuoteAdapter.OnFragmentInteractionListener listener;
    private              String                                     newText;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof QuoteAdapter.OnFragmentInteractionListener) {
            listener = (QuoteAdapter.OnFragmentInteractionListener) context;
        } else {
            throw new ClassCastException("Activity must implement QuoteAdapter.OnFragmentInteractionListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentQuoteBinding binding = FragmentQuoteBinding.inflate(inflater, container, false);
        View emptyView = binding.emptyRvContainer;
        quoteRecyclerView = binding.itemQuoteRvContainer.rvQuote;
        quoteAdapter = new QuoteAdapter(getContext());
        swipeContainer = binding.swipeRefreshQuote;
        quoteAdapter.registerAdapterDataObserver(new RecyclerViewEmptyObserver(emptyView, quoteRecyclerView));
        quoteRecyclerView.setAdapter(quoteAdapter);
        setUpOnItemClickListener(quoteAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManagerWrapper(getContext(), LinearLayoutManager.VERTICAL, false);
        quoteRecyclerView.setLayoutManager(linearLayoutManager);
        return binding.getRoot();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

    private void setUpOnItemClickListener(QuoteAdapter quoteAdapter) {
        quoteAdapter.setOnItemClickListener((itemView, quote, bundle) -> {
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
                case R.id.iv_quote_author_thumbnail:
                    listener.onAuthorSelectedListener(itemView.findViewById(R.id.iv_quote_author_thumbnail), quote.getAuthorId());
                    break;
            }
        });
    }

    protected abstract void updateQuote(QuoteAuthorJoin quote);

    public QuoteAdapter getQuoteAdapter() {
        return quoteAdapter;
    }

    public SwipeRefreshLayout getSwipeContainer() {
        return swipeContainer;
    }

    protected Handler getHandler() {
        if (handler == null) {
            handler = new Handler();
        }
        return handler;
    }

    public QuoteAdapter.OnFragmentInteractionListener getListener() {
        return listener;
    }
}

