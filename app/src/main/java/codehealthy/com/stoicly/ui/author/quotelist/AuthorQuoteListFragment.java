package codehealthy.com.stoicly.ui.author.quotelist;

import android.arch.lifecycle.ViewModelProviders;
import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import codehealthy.com.stoicly.R;
import codehealthy.com.stoicly.data.model.QuoteAuthorJoin;
import codehealthy.com.stoicly.ui.allquote.QuoteAdapter;
import codehealthy.com.stoicly.ui.main.LinearLayoutManagerWrapper;
import timber.log.Timber;

public class AuthorQuoteListFragment extends Fragment implements QuoteAdapter.OnItemClickListener {
    private static final String KEY_ARGUMENT_AUTHOR_ID = "KEY_AUTHOR_ID";
    @BindView(R.id.tv_total_quotes)
    TextView totalQuotes;
    @BindView(R.id.sp_quote_fav_or_all)
    Spinner  quoteSpinnerFavouriteOrAll;
    private int                                             authorId;
    private AuthorQuoteListViewModel                        authorQuoteListViewModel;
    private RecyclerView                                    authorQuotesRV;
    private QuoteAdapter.OnQuoteFragmentInteractionListener interactionListener;
    private QuoteAdapter                                    quoteAdapter;
    private Unbinder                                        unbinder;

    public static AuthorQuoteListFragment newInstance(int authorId) {
        AuthorQuoteListFragment authorQuoteListFragment = new AuthorQuoteListFragment();
        Bundle args = new Bundle();
        Timber.d("%s", authorId);
        args.putInt(KEY_ARGUMENT_AUTHOR_ID, authorId);
        authorQuoteListFragment.setArguments(args);
        return authorQuoteListFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof QuoteAdapter.OnQuoteFragmentInteractionListener) {
            interactionListener = (QuoteAdapter.OnQuoteFragmentInteractionListener) context;
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
        View view = inflater.inflate(R.layout.fragment_author, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        authorQuotesRV = view.findViewById(R.id.rv_quote);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManagerWrapper(getContext(), LinearLayoutManager.VERTICAL, false);
        quoteAdapter = new QuoteAdapter(getContext());
        authorQuotesRV.setAdapter(quoteAdapter);
        authorQuotesRV.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        Timber.d("%s the author id submitted", authorId);
        AuthorViewModelFactory authorViewModelFactory = new AuthorViewModelFactory(Objects.requireNonNull(getActivity()).getApplication(), authorId);
        authorQuoteListViewModel = ViewModelProviders.of(this, authorViewModelFactory).get(AuthorQuoteListViewModel.class);
        getAllQuotesByAuthorFromViewModel();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.interactionListener = null;
    }

    @Override
    public void onItemClick(QuoteAuthorJoin quote, Bundle args) {
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

    private void getAllQuotesByAuthorFromViewModel() {
        authorQuoteListViewModel.getAllQuoteByAuthorList().observe(getViewLifecycleOwner(), this::setUpQuoteAdapter);
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
