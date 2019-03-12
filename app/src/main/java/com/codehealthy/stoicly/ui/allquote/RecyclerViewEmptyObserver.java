package com.codehealthy.stoicly.ui.allquote;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class RecyclerViewEmptyObserver extends RecyclerView.AdapterDataObserver {
    private View         emptyView;
    private RecyclerView recyclerView;

    public RecyclerViewEmptyObserver(View emptyView, RecyclerView recyclerView) {
        this.emptyView = emptyView;
        this.recyclerView = recyclerView;
        checkAdapterEmpty();
    }

    private void checkAdapterEmpty() {
        if (emptyView != null && recyclerView.getAdapter() != null) {
            int totalItems = recyclerView.getAdapter().getItemCount();
            emptyView.setVisibility(totalItems == 0 ? View.VISIBLE : View.GONE);
            recyclerView.setVisibility(totalItems == 0 ? RecyclerView.GONE : RecyclerView.VISIBLE);
        }
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount) {
        super.onItemRangeChanged(positionStart, itemCount);
        checkAdapterEmpty();
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
        super.onItemRangeChanged(positionStart, itemCount, payload);
        checkAdapterEmpty();
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
        super.onItemRangeRemoved(positionStart, itemCount);
        checkAdapterEmpty();
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        super.onItemRangeInserted(positionStart, itemCount);
        checkAdapterEmpty();
    }


}