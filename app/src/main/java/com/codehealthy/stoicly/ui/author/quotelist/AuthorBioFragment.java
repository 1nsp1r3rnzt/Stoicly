package com.codehealthy.stoicly.ui.author.quotelist;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.Transition;
import android.support.transition.TransitionInflater;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codehealthy.stoicly.R;
import com.codehealthy.stoicly.data.model.Author;
import com.codehealthy.stoicly.databinding.PartialAuthorDescriptionBinding;
import com.codehealthy.stoicly.ui.main.ToolbarTitle;

import java.util.Objects;


public class AuthorBioFragment extends Fragment {

    private static final String                          TAG_AUTHOR_BIO_FRAGMENT = "TAG_AUTHOR_BIO_FRAGMENT";
    private static final String                          BUNDLE_TRANSITION_NAME  = "BUNDLE_TRANSITION_NAME";
    private static final String                          ARG_AUTHOR_ID           = "authorId";
    private              AuthorQuoteListViewModel        authorQuoteListViewModel;
    private              OnFragmentInteractionListener   interactionFragmentListener;
    private              PartialAuthorDescriptionBinding binding;

    public static AuthorBioFragment newInstance(int authorId, View itemView) {
        AuthorBioFragment fragment = new AuthorBioFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_AUTHOR_ID, authorId);
        args.putString(BUNDLE_TRANSITION_NAME, itemView.getTransitionName());
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        int mAuthorId = getArguments().getInt(ARG_AUTHOR_ID);

        AuthorViewModelFactory authorViewModelFactory = new AuthorViewModelFactory(Objects.requireNonNull(getActivity()).getApplication(), mAuthorId);
        authorQuoteListViewModel = ViewModelProviders.of(this, authorViewModelFactory).get(AuthorQuoteListViewModel.class);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Transition transition = TransitionInflater.from(getContext()).inflateTransition(R.transition.image_open_author_transform);
            setSharedElementEnterTransition(transition);

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            interactionFragmentListener = (OnFragmentInteractionListener) context;

        } else {
            throw new IllegalStateException("Activity must implement OnFragmentInteractionListener");
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.partial_author_description, container, false);
        setHasOptionsMenu(true);
        assert getArguments() != null;
        binding.ivAuthorThumbnail.setTransitionName(getArguments().getString(BUNDLE_TRANSITION_NAME));
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        interactionFragmentListener = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        postponeEnterTransition();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        authorQuoteListViewModel.getAuthor().observe(getViewLifecycleOwner(), this::setUpAuthorView);
        startPostponedEnterTransition();
    }

    private void setUpAuthorView(Author author) {

        final int DEFAULT_IMAGE_ID = R.drawable.default_author;
        int imageId = 0;
        binding.tvAuthorName.setText(author.getName());
        sendAuthorNameToListener(author.getName());
        if (author.getBio() != null) {
            final int TRUNCATE_LENGTH = 280;
            int lengthToTruncate = Math.min((author.getBio()).length(), TRUNCATE_LENGTH);
            binding.tvAuthorBio.setText(String.format("%s ...", author.getBio().substring(0, lengthToTruncate)));
        }
        if (author.getThumbnailUrl() != null) {
            imageId = Objects.requireNonNull(getContext()).getResources().getIdentifier(author.getThumbnailUrl(), "drawable", Objects.requireNonNull(getActivity()).getPackageName());
        }
        if (imageId == 0) {
            imageId = DEFAULT_IMAGE_ID;
        }
        binding.ivAuthorThumbnail.setImageResource(imageId);

        binding.tvAuthorDate.setText(String.format("%s - %s", author.getDateBorn(), author.getDateDied()));
        binding.tvAuthorReadMoreText.setOnClickListener(view -> {
            launchAuthorDialog(author.getBio());
        });
    }

    private void launchAuthorDialog(String bio) {
        AuthorFullBioDialog authorFullBioDialog = AuthorFullBioDialog.newInstance(bio);
        assert getFragmentManager() != null;
        authorFullBioDialog.show(getFragmentManager(), TAG_AUTHOR_BIO_FRAGMENT);
    }

    private void sendAuthorNameToListener(String name) {
        if (interactionFragmentListener != null) {
            interactionFragmentListener.setToolbarCollapsedTitle(name);
        }
    }

    public interface OnFragmentInteractionListener extends ToolbarTitle {
    }

}

