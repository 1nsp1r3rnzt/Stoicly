package codehealthy.com.stoicly.ui.allquote;

import android.support.v7.util.DiffUtil;

import java.util.List;

import codehealthy.com.stoicly.data.model.QuoteAuthorJoin;

public class QuoteDiffCallback extends DiffUtil.Callback {

    private List<QuoteAuthorJoin> oldQuoteAuthorJoinList;
    private List<QuoteAuthorJoin> newQuoteAuthorJoinList;

    QuoteDiffCallback(List<QuoteAuthorJoin> oldQuoteAuthorJoinList, List<QuoteAuthorJoin> newQuoteAuthorJoinList) {
        this.oldQuoteAuthorJoinList = oldQuoteAuthorJoinList;
        this.newQuoteAuthorJoinList = newQuoteAuthorJoinList;
    }

    @Override
    public int getOldListSize() {
        return oldQuoteAuthorJoinList == null ? 0 : oldQuoteAuthorJoinList.size();
    }

    @Override
    public int getNewListSize() {
        return newQuoteAuthorJoinList == null ? 0 : newQuoteAuthorJoinList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldQuoteAuthorJoinList.get(oldItemPosition).getId() == newQuoteAuthorJoinList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        QuoteAuthorJoin oldQuote = oldQuoteAuthorJoinList.get(oldItemPosition);
        QuoteAuthorJoin newQuote = newQuoteAuthorJoinList.get(newItemPosition);
        return oldQuote.getQuoteStatus().isFavourite() == newQuote.getQuoteStatus().isFavourite();

    }
}
