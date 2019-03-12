package com.codehealthy.stoicly.ui.author.quotelist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codehealthy.stoicly.databinding.DialogBioFullAuthorBinding;

public class AuthorFullBioDialog extends DialogFragment {


    private static final String                     BUNDLE_AUTHOR_DESCRIPTION = "BUNDLE_AUTHOR_DESCRIPTION";
    private              DialogBioFullAuthorBinding binding;

    public static AuthorFullBioDialog newInstance(String authorBio) {
        AuthorFullBioDialog fragment = new AuthorFullBioDialog();
        Bundle args = new Bundle();
        args.putString(BUNDLE_AUTHOR_DESCRIPTION, authorBio);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogBioFullAuthorBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assert getArguments() != null;
        binding.tvDialogAuthorBio.setText(getArguments().getString(BUNDLE_AUTHOR_DESCRIPTION));
    }


}
