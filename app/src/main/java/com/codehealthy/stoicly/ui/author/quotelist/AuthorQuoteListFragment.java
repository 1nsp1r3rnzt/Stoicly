package com.codehealthy.stoicly.ui.author.quotelist;

import android.arch.lifecycle.ViewModelProviders;
import android.content.ClipData;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.codehealthy.stoicly.R;
import com.codehealthy.stoicly.data.model.QuoteAuthorJoin;
import com.codehealthy.stoicly.databinding.FragmentQuoteAuthorBinding;
import com.codehealthy.stoicly.ui.allquote.QuoteAdapter;
import com.codehealthy.stoicly.ui.allquote.RecyclerViewEmptyObserver;
import com.codehealthy.stoicly.ui.main.LinearLayoutManagerWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AuthorQuoteListFragment extends Fragment implements QuoteAdapter.OnItemClickListener {
    private static final String KEY_ARGUMENT_AUTHOR_ID = "KEY_AUTHOR_ID";

    private int                                        authorId;
    private AuthorQuoteListViewModel                   authorQuoteListViewModel;
    private QuoteAdapter.OnFragmentInteractionListener interactionListener;
    private QuoteAdapter                               quoteAdapter;
    private FragmentQuoteAuthorBinding                 binding;

    public static AuthorQuoteListFragment newInstance(int authorId) {
        AuthorQuoteListFragment authorQuoteListFragment = new AuthorQuoteListFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_ARGUMENT_AUTHOR_ID, authorId);
        authorQuoteListFragment.setArguments(args);
        return authorQuoteListFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof QuoteAdapter.OnFragmentInteractionListener) {
            interactionListener = (QuoteAdapter.OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        int ID_ZERO = 0;
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        authorId = getArguments().getInt(KEY_ARGUMENT_AUTHOR_ID);
        if (authorId == ID_ZERO) {
            throw new IllegalStateException("Author id must be passed to AuthorQuoteListFragment.");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quote_author, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView authorQuotesRV = binding.itemQuoteRvContainer.rvQuote;
        View emptyView = binding.emptyRvContainer;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManagerWrapper(getContext(), LinearLayoutManager.VERTICAL, false);
        quoteAdapter = new QuoteAdapter(getContext());
        quoteAdapter.registerAdapterDataObserver(new RecyclerViewEmptyObserver(emptyView, authorQuotesRV));
        authorQuotesRV.setAdapter(quoteAdapter);
        authorQuotesRV.setLayoutManager(linearLayoutManager);

        binding.spQuoteFavOrAll.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedQuoteSource = parent.getItemAtPosition(position).toString();
                if (selectedQuoteSource.equals("All Quotes")) {
                    QuoteSource quoteSource = new QuoteSource("All Quotes");
                    quoteAdapter.changeSource(quoteSource);
                } else {
                    QuoteSource quoteSource = new QuoteSource("Favourite Quotes");
                    quoteAdapter.changeSource(quoteSource);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        AuthorViewModelFactory authorViewModelFactory = new AuthorViewModelFactory(Objects.requireNonNull(getActivity()).getApplication(), authorId);
        authorQuoteListViewModel = ViewModelProviders.of(this, authorViewModelFactory).get(AuthorQuoteListViewModel.class);
        getAllQuotesByAuthorFromViewModel();
        getFavouriteQuotesOfAuthor();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();


    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.interactionListener = null;
    }

    @Override
    public void onItemClick(View itemView, QuoteAuthorJoin quote, Bundle args) {
        String quoteName = quote.getAuthorName();
        String quoteText = quote.getQuote();
        int resourceId = args.getInt(QuoteAdapter.BUNDLE_RESOURCE_ID);
        switch (resourceId) {
            case (R.id.btn_quote_clipboard):
                ClipData clipData = ClipData.newPlainText("quote", quoteText + "\n" + quoteName);
                interactionListener.onCopyToClipboardListener(clipData);
                break;
            case (R.id.btn_quote_favourite):
                quote.getQuoteStatus().toggleFavourite();
                updateQuote(quote);
                break;
            case (R.id.btn_quote_share):

                interactionListener.onShareByIntentListener(quoteText + "\n" + quoteName, "Share quote via");
                break;

        }
    }

    //region liveDataObservers
    private void getAllQuotesByAuthorFromViewModel() {
        authorQuoteListViewModel.getAllQuoteByAuthorList().observe(getViewLifecycleOwner(), this::setUpQuoteAdapter);
    }

    private void getFavouriteQuotesOfAuthor() {
        authorQuoteListViewModel.getAllFavouriteQuotes().observe(getViewLifecycleOwner(), this::setUpFavouriteQuoteListItems);
    }
    //endregion liveDataObservers

    private void setUpFavouriteQuoteListItems(List<QuoteAuthorJoin> quoteAuthorJoins) {
        quoteAdapter.setFavouriteQuoteItems(quoteAuthorJoins);
    }

    private void setUpQuoteAdapter(List<QuoteAuthorJoin> allAuthorsList) {


        setUpOnClickListener();

        List<QuoteAuthorJoin> newList = new ArrayList<>(allAuthorsList.size());
        for (QuoteAuthorJoin quote : allAuthorsList) {
            newList.add(QuoteAuthorJoin.newInstance(quote));

        }
        quoteAdapter.setQuoteListItems(newList);

    }


    private void setUpOnClickListener() {
        quoteAdapter.setOnItemClickListener(this);
    }

    private void updateQuote(QuoteAuthorJoin quote) {

        authorQuoteListViewModel.updateQuote(quote);
    }

    private void notifyAdapterPositionChanged(int adapterPosition) {
        if (quoteAdapter != null) quoteAdapter.notifyItemChanged(adapterPosition);
    }

}
