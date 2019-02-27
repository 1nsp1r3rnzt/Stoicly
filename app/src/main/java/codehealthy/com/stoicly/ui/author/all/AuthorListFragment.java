package codehealthy.com.stoicly.ui.author.all;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import codehealthy.com.stoicly.R;
import codehealthy.com.stoicly.data.model.Author;
import codehealthy.com.stoicly.databinding.FragmentListAuthorBinding;
import codehealthy.com.stoicly.ui.main.ToolbarTitle;

public class AuthorListFragment extends Fragment implements AuthorListAdapter.OnInteractionListener {

    private OnFragmentInteractionListener listener;
    private AuthorListAdapter             authorListAdapter;
    private ToolbarTitle                  toolbarListener;

    public static AuthorListFragment newInstance() {
        return new AuthorListFragment();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new IllegalStateException("OnFragmentInteractionListener must be implemented ");
        }

        if (context instanceof ToolbarTitle) {
            toolbarListener = (ToolbarTitle) getActivity();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentListAuthorBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_author, container, false);
        RecyclerView recyclerViewAuthorList = binding.authorListRvContainer.authorListRv;
        authorListAdapter = new AuthorListAdapter(this);
        authorListAdapter.setOnInteractionListener(this);
        recyclerViewAuthorList.setAdapter(authorListAdapter);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        setUpToolbarTitle();
        AuthorListViewModel authorViewModel = ViewModelProviders.of(this).get(AuthorListViewModel.class);
        authorViewModel.getAllAuthors().observe(getViewLifecycleOwner(), this::setUpAdapterData);
    }

    private void setUpToolbarTitle() {
        if (toolbarListener != null) {
            toolbarListener.setToolbarTitle(getString(R.string.description_navigation_author));
        }
    }

    private void setUpAdapterData(List<Author> authors) {
        authorListAdapter.setListItems(authors);
    }


    @Override
    public void onAuthorClick(View view, int authorId) {
        if (listener != null) {

            listener.onAuthorClicked(view, authorId);
        }
    }


    public void setUpFragmentInteractionListener(OnFragmentInteractionListener listener) {
        this.listener = listener;
    }

    public interface OnFragmentInteractionListener {
        void onAuthorClicked(View view, int authorId);
    }
}


