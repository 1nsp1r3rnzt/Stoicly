package codehealthy.com.stoicly.ui.author.all;

import android.arch.lifecycle.ViewModelProviders;
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

public class AuthorListFragment extends Fragment implements AuthorListAdapter.OnInteractionListener {

    private OnInteractionFragmentListener listener;
    private AuthorListViewModel           authorViewModel;
    private FragmentListAuthorBinding     binding;
    private RecyclerView                  recyclerViewAuthorList;
    private AuthorListAdapter             authorListAdapter;

    public static AuthorListFragment newInstance() {
        return new AuthorListFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_author, container, false);
        recyclerViewAuthorList = binding.authorListRvContainer.authorListRv;
        authorListAdapter = new AuthorListAdapter(this);
        authorListAdapter.setOnInteractionListener(this);
        recyclerViewAuthorList.setAdapter(authorListAdapter);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        authorViewModel = ViewModelProviders.of(this).get(AuthorListViewModel.class);
        authorViewModel.getAllAuthors().observe(getViewLifecycleOwner(), this::setUpAdapterData);
    }

    private void setUpAdapterData(List<Author> authors) {
        authorListAdapter.setListItems(authors);
    }

    @Override
    public void onAuthorClick(int authorId) {
        if (listener != null) {

            listener.onAuthorClicked(authorId);
        }
    }

    public void setUpFragmentInteractionListener(OnInteractionFragmentListener listener) {
        this.listener = listener;
    }

    public interface OnInteractionFragmentListener {
        void onAuthorClicked(int authorId);
    }
}


