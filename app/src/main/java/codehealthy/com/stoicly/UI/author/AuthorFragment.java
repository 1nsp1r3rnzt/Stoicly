package codehealthy.com.stoicly.UI.author;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import codehealthy.com.stoicly.R;
import codehealthy.com.stoicly.data.model.QuoteAuthorJoin;

public class AuthorFragment extends Fragment {
    private static final String                ARGUMENT_AUTHOR_ID_KEY = "AUTHOR_ID_KEY";
    private              List<QuoteAuthorJoin> authorList;
    private              AuthorViewModel       authorViewModel;
    private              int                   authorId;

    private static AuthorFragment newInstance() {
        return new AuthorFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            authorId = bundle.getInt(ARGUMENT_AUTHOR_ID_KEY);
        } else {
            throw new IllegalStateException("Author id not found in fragment.");
        }
        return inflater.inflate(R.layout.fragment_author, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AuthorViewModelFactory authorViewModelFactory = new AuthorViewModelFactory(getActivity().getApplication(), authorId);
        authorViewModel = ViewModelProviders.of(this, authorViewModelFactory).get(AuthorViewModel.class);
        getAllQuotesByAuthor();
    }

    private void getAllQuotesByAuthor() {
        authorViewModel.getAllQuoteByAuthorList().observe(this, allAuthorsList -> {
            this.authorList = allAuthorsList;
        });
    }
}
