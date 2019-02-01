package codehealthy.com.stoicly.UI.quote;

import android.content.Context;
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

public class QuoteAdapter extends RecyclerView.Adapter<QuoteAdapter.ViewHolder> implements Filterable {
    private OnItemClickListener   listener;
    private List<QuoteAuthorJoin> quoteAuthorJoinList;
    private List<QuoteAuthorJoin> filteredQuoteAuthorJoinList;

    QuoteAdapter(List<QuoteAuthorJoin> quoteAuthorJoinList) {
        this.quoteAuthorJoinList = quoteAuthorJoinList;
        this.filteredQuoteAuthorJoinList = quoteAuthorJoinList;

    }

    @NonNull
    @Override
    public QuoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View customRowView = layoutInflater.inflate(R.layout.item_quote_row, viewGroup, false);
        return new ViewHolder(customRowView);
    }

    @Override
    public void onBindViewHolder(@NonNull QuoteAdapter.ViewHolder viewHolder, int position) {
        position = viewHolder.getAdapterPosition();
        QuoteAuthorJoin currentQuote = filteredQuoteAuthorJoinList.get(position);
        viewHolder.authorNameTextView.setText(currentQuote.getAuthorName());
        viewHolder.quoteTextView.setText(currentQuote.getQuote());
        viewHolder.quote = currentQuote;
        bindFavouriteIcon(viewHolder, position);
    }

    private void bindFavouriteIcon(ViewHolder viewHolder, int position) {
        QuoteAuthorJoin currentQuote = filteredQuoteAuthorJoinList.get(position);
        if (currentQuote.getQuoteStatus().isFavourite()) {
            viewHolder.isFavouriteButton.setImageResource(R.drawable.ic_favorite_black_24dp);

        } else {
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
                String query = constraint.toString();

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

