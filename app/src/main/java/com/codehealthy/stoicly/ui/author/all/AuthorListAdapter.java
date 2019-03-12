package com.codehealthy.stoicly.ui.author.all;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codehealthy.stoicly.R;
import com.codehealthy.stoicly.data.model.Author;
import com.codehealthy.stoicly.databinding.RowAuthorListBinding;

import java.util.List;


public class AuthorListAdapter extends RecyclerView.Adapter<AuthorListAdapter.ViewHolder> {
    private List<Author>          authorList;
    private OnInteractionListener onInteractionListener;


    AuthorListAdapter(OnInteractionListener onInteractionListener) {
        this.onInteractionListener = onInteractionListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        RowAuthorListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.row_author_list, viewGroup, false);
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.getBinding().setAuthor(authorList.get(position));
        viewHolder.getBinding().setItemClickListener(onInteractionListener);
        viewHolder.getBinding().executePendingBindings();


    }

    void setOnInteractionListener(OnInteractionListener onInteractionListener) {
        this.onInteractionListener = onInteractionListener;
    }


    @Override
    public int getItemCount() {
        return authorList == null ? 0 : authorList.size();
    }

    void setListItems(List<Author> authors) {

        if (authors != null) {
            authorList = authors;
        }
        notifyDataSetChanged();
    }

    public interface OnInteractionListener {
        void onAuthorClick(View view, int authorId);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private RowAuthorListBinding binding;

        ViewHolder(RowAuthorListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        private RowAuthorListBinding getBinding() {
            return binding;
        }
    }

}
