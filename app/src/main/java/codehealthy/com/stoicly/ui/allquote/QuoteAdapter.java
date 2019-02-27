package codehealthy.com.stoicly.ui.allquote;

import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.Group;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import codehealthy.com.stoicly.R;
import codehealthy.com.stoicly.data.model.QuoteAuthorJoin;
import codehealthy.com.stoicly.ui.author.quotelist.QuoteSource;
import de.hdodenhof.circleimageview.CircleImageView;
import timber.log.Timber;

public class QuoteAdapter extends RecyclerView.Adapter<QuoteAdapter.ViewHolder> implements Filterable {
    public static final String                BUNDLE_RESOURCE_ID               = "BUNDLE_RESOURCE_ID";
    static final        String                BUNDLE_QUOTE_POSITION            = "BUNDLE_QUOTE_POSITION";
    static final        String                BUNDLE_SHARED_ELEMENT_TRANSITION = "BUNDLE_SHARED_ELEMENT_TRANSITION";
    private final       Context               context;
    private             OnItemClickListener   listener;
    private             List<QuoteAuthorJoin> quoteAuthorJoinList;
    private             List<QuoteAuthorJoin> quoteAuthorJoinListFull;
    private             List<QuoteAuthorJoin> favouriteQuotesList;

    public QuoteAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public QuoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View customRowView = layoutInflater.inflate(R.layout.row_quote, viewGroup, false);
        return new ViewHolder(customRowView);
    }

    //region bindViewHolder and binds
    @Override
    public void onBindViewHolder(@NonNull QuoteAdapter.ViewHolder viewHolder, int position) {
        position = viewHolder.getAdapterPosition();
        QuoteAuthorJoin currentQuote = quoteAuthorJoinList.get(position);
        viewHolder.authorNameTextView.setText(currentQuote.getAuthorName());
        viewHolder.quoteTextView.setText(currentQuote.getQuote());
        viewHolder.quote = currentQuote;

        bindQuoteSource(viewHolder, currentQuote);
        bindAuthorThumbnail(viewHolder, currentQuote.getThumbnailUrl());
        setAuthorTransitionForGreaterThanLollipop(viewHolder, position);
        bindFavouriteIcon(viewHolder, position);
    }

    private void setAuthorTransitionForGreaterThanLollipop(ViewHolder viewHolder, int position) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            viewHolder.authorThumbnailImageView.setTransitionName("transition" + position);
        }
    }

    private void bindQuoteSource(ViewHolder viewHolder, QuoteAuthorJoin currentQuote) {
        if (currentQuote.getSource().isEmpty()) {
            viewHolder.quoteSourceTextView.setText("");
        } else {
            viewHolder.quoteSourceTextView.setText(String.format("%s %s %s", "\"", currentQuote.getSource(), "\""));

        }
    }

    private void bindAuthorThumbnail(ViewHolder viewHolder, String thumbnailUrl) {
        int imageId = context.getResources().getIdentifier(thumbnailUrl, "drawable", context.getPackageName());
        viewHolder.authorThumbnailImageView.setImageResource(imageId);
    }

    private void bindFavouriteIcon(ViewHolder viewHolder, int position) {
        QuoteAuthorJoin currentQuote = quoteAuthorJoinList.get(position);
        if (currentQuote.getQuoteStatus().isFavourite()) {
            viewHolder.favouriteButton.setChecked(true);
        } else {
            viewHolder.favouriteButton.setChecked(false);

        }
    }

    //endregion bindViewHolder and binds
    @Override
    public int getItemCount() {
        return quoteAuthorJoinList == null ? 0 : quoteAuthorJoinList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String query = constraint.toString().trim();

                List<QuoteAuthorJoin> filteredList = new ArrayList<>();
                if (query.isEmpty()) {
                    filteredList = quoteAuthorJoinListFull;
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
                    quoteAuthorJoinList.clear();
                    quoteAuthorJoinList.addAll((List<QuoteAuthorJoin>) results.values);
                    notifyDataSetChanged();
                } catch (ClassCastException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public void setQuoteListItems(final List<QuoteAuthorJoin> newList) {
        if (newList != null) {
            if (quoteAuthorJoinList == null) {
                quoteAuthorJoinList = new ArrayList<>(newList);
                quoteAuthorJoinListFull = new ArrayList<>(quoteAuthorJoinList);
                notifyItemRangeInserted(0, newList.size());

            } else {

                final QuoteDiffCallback quoteDiffCallback = new QuoteDiffCallback(quoteAuthorJoinListFull, newList);
                final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(quoteDiffCallback);
                quoteAuthorJoinListFull = newList;
                diffResult.dispatchUpdatesTo(this);
            }
        }
    }


    public void setFavouriteQuoteItems(final List<QuoteAuthorJoin> favQuotes) {
        if (favQuotes != null) {
            if (favQuotes.size() != 0) {
                favouriteQuotesList = favQuotes;
                notifyDataSetChanged();
            } else {
                Timber.d("empty fav list");

            }

        }

    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.listener = onItemClickListener;
    }

    public void clear() {
        quoteAuthorJoinList = null;
        notifyDataSetChanged();
    }


    public void changeSource(QuoteSource quoteSource) {
        if (quoteSource.isStatusAllQuotes()) {

            clear();
            quoteAuthorJoinList = quoteAuthorJoinListFull;
        } else {
            clear();
            quoteAuthorJoinList = favouriteQuotesList;

        }
    }


    public interface OnItemClickListener {
        void onItemClick(View itemView, QuoteAuthorJoin quote, Bundle args);
    }

    public interface OnFragmentInteractionListener {
        void onShareByIntentListener(String text, String chooserTitle);

        void onCopyToClipboardListener(ClipData clipData);

        void onAuthorSelectedListener(View itemView, int authorId);

        void setHomeAsNavigationIcon();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvQuote)
        TextView        quoteTextView;
        @BindView(R.id.iv_quote_author_thumbnail)
        CircleImageView authorThumbnailImageView;
        @BindView(R.id.tvAuthorName)
        TextView        authorNameTextView;

        @BindView(R.id.btn_quote_favourite)
        ToggleButton favouriteButton;
        @BindView(R.id.tvQuoteSource)
        TextView     quoteSourceTextView;
        @BindView(R.id.group_buttons)
        Group        group;

        QuoteAuthorJoin quote;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setUpScaleAnimationOnCLick(favouriteButton);
            setUpClickListenerForViewsInGroup(group);
        }

        private void setUpClickListenerForViewsInGroup(Group group) {
            int groupIds[] = group.getReferencedIds();
            for (int resourceId : groupIds) {
                View currentView = itemView.findViewById(resourceId);
                currentView.setOnClickListener(v -> {

                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            Bundle bundle = new Bundle();
                            bundle.putInt(BUNDLE_QUOTE_POSITION, position);
                            bundle.putInt(BUNDLE_RESOURCE_ID, resourceId);
                            bundle.putString(BUNDLE_SHARED_ELEMENT_TRANSITION, authorThumbnailImageView.getTransitionName());
                            listener.onItemClick(itemView, quote, bundle);

                        }
                    }
                });
            }
        }

        private void setUpScaleAnimationOnCLick(ToggleButton favouriteButton) {
            ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
            scaleAnimation.setDuration(500);
            BounceInterpolator bounceInterpolator = new BounceInterpolator();
            scaleAnimation.setInterpolator(bounceInterpolator);
            favouriteButton.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> favouriteButton.startAnimation(scaleAnimation));
        }
    }

}

