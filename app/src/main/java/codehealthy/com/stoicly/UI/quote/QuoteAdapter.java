package codehealthy.com.stoicly.UI.quote;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.Group;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import codehealthy.com.stoicly.R;
import codehealthy.com.stoicly.data.model.QuoteAuthorJoin;
import timber.log.Timber;

// 1. Create a class which extends RecyclerView<T>
// ViewHolder gives access to custom view
public class QuoteAdapter extends RecyclerView.Adapter<QuoteAdapter.ViewHolder> implements Filterable {
    public static final String                KEY_LIST_POSITION    = "KEY_LIST_POSITION";
    public static final String                KEY_ADAPTER_POSITION = "KEY_ADAPTER_POSITION";
    public static final String                KEY_RESOURCE_ID      = "KEY_RESOURCE_ID";
    private             OnItemClickListener   listener;
    private             List<QuoteAuthorJoin> quoteAuthorJoinList;
    private             List<QuoteAuthorJoin> filteredQuoteAuthorJoinList;

    QuoteAdapter(List<QuoteAuthorJoin> quoteAuthorJoinList) {
        this.quoteAuthorJoinList = quoteAuthorJoinList;
        this.filteredQuoteAuthorJoinList = quoteAuthorJoinList;
    }

    @NonNull
    @Override
    public QuoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        oncreateviewholder inflates the itemlayout and creates the holder
//        we need context to access the correct resources
//        we need to inflate the xml code here
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View customRowView = layoutInflater.inflate(R.layout.item_quote_row, viewGroup, false);
        return new ViewHolder(customRowView);
    }

    @Override
    public void onBindViewHolder(@NonNull QuoteAdapter.ViewHolder viewHolder, int position) {
//        set the viwewAttribute based on data
        position = viewHolder.getAdapterPosition();
        QuoteAuthorJoin currentQuote = filteredQuoteAuthorJoinList.get(position);
        viewHolder.authorNameTextView.setText(currentQuote.getAuthorName());
//        image Uri
//        viewHolder.authorPictureImageView.setImageURI(());
        viewHolder.quoteTextView.setText(currentQuote.getQuote());
        viewHolder.quote = currentQuote;
        Timber.d("%s", filteredQuoteAuthorJoinList.indexOf(currentQuote));
        bindFavouriteIcon(viewHolder, position);
    }

    private void bindFavouriteIcon(ViewHolder viewHolder, int position) {
        QuoteAuthorJoin currentQuote = filteredQuoteAuthorJoinList.get(position);
        Timber.d("id:%s Fav:%s position:%s", currentQuote.getId(), currentQuote.isFavourite(), position);
        if (currentQuote.isFavourite()) {
            Timber.d("setting is favourite icon true for%s", currentQuote.getQuote());
            viewHolder.isFavouriteButton.setImageResource(R.drawable.ic_favorite_black_24dp);

        } else {
            Timber.d("setting is favourite icon False for%s", currentQuote.getQuote());
            viewHolder.isFavouriteButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);

        }
    }


    @Override
    public int getItemCount() {
        return filteredQuoteAuthorJoinList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                // if the filter result is empty then orignal list should be returned
                String query = constraint.toString();
                // search through the list

                List<QuoteAuthorJoin> filteredList = new ArrayList<QuoteAuthorJoin>();
                if (query.isEmpty()) {
                    filteredList = quoteAuthorJoinList;
                } else {

                    for (QuoteAuthorJoin quote : quoteAuthorJoinList) {

                        if ((quote.getQuote().toLowerCase().contains(query.toLowerCase()) || (quote.getAuthorName().toLowerCase().contains(query.toLowerCase())
                        ))) {
                            filteredList.add(quote);
                        }


                    }
                }
                FilterResults results = new FilterResults();
                results.count = filteredList.size();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                // cast the results.value back to original type
                try {
                    filteredQuoteAuthorJoinList = (ArrayList<QuoteAuthorJoin>) results.values;
                    notifyDataSetChanged();
                } catch (ClassCastException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.listener = onItemClickListener;
    }


    public interface OnItemClickListener {
        void onItemClick(QuoteAuthorJoin quote, int position, int resourceId);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView        quoteTextView;
        TextView        authorNameTextView;
        ImageView       authorPictureImageView;
        ImageButton     isFavouriteButton;
        QuoteAuthorJoin quote;

        ViewHolder(@NonNull View itemView) {
            //the itemView is of individual Row
            //find elements by using the findviewbyId in it to create java object.
            super(itemView);
            quoteTextView = itemView.findViewById(R.id.tvQuote);
            authorPictureImageView = itemView.findViewById(R.id.ivAuthorThumbnail);
            authorNameTextView = itemView.findViewById(R.id.tvAuthorName);
            authorNameTextView = itemView.findViewById(R.id.tvAuthorName);
            isFavouriteButton = itemView.findViewById(R.id.btn_quote_favourite);
            Group group = itemView.findViewById(R.id.group_buttons);

            int ids[] = group.getReferencedIds();

            Bundle bundle = new Bundle();
            for (int resourceId : ids) {

                //lambda function overwrite the OnClickListener
                View currentView = itemView.findViewById(resourceId);

                currentView.setOnClickListener(v -> {
                    //trigger click up to the activity
                    // check for listener if null
                    if (listener != null) {
                        // check for the position
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(quote, position, resourceId);

                        }
                    }
                });
            }
        }
    }


}

