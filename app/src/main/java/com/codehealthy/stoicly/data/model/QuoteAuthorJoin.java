package com.codehealthy.stoicly.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

@Entity
public class QuoteAuthorJoin extends Quote implements Comparable {
    private static final String DEFAULT_THUMBNAIL_URL = "default_author.jpeg";

    @ColumnInfo(name = "thumbnail_url")
    private final String thumbnailUrl;

    @ColumnInfo(name = "author_name")
    private String authorName;

    public QuoteAuthorJoin(String quote, int authorId, int categoryId, QuoteStatus quoteStatus, String authorName, String source, String thumbnailUrl) {
        super(quote, authorId, categoryId, quoteStatus, source);
        this.authorName = authorName;
        this.thumbnailUrl = thumbnailUrl != null ? thumbnailUrl : DEFAULT_THUMBNAIL_URL;
    }

    public static QuoteAuthorJoin newInstance(QuoteAuthorJoin quote) {

        QuoteAuthorJoin quoteAuthorJoin = new QuoteAuthorJoin(
                quote.getQuote(),
                quote.getAuthorId(),
                quote.getCategoryId(),
                QuoteStatus.newInstance(quote.getQuoteStatus()),
                quote.getAuthorName(),
                quote.getSource(),
                quote.getThumbnailUrl()
        );
        quoteAuthorJoin.setId(quote.getId());
        return quoteAuthorJoin;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getAuthorName() {
        return authorName;
    }

    @Override
    public int compareTo(Object o) {
        QuoteAuthorJoin compare = (QuoteAuthorJoin) o;
        if (compare.getId() == this.getId() &&
                compare.getQuote().equals(this.getQuote()) &&
                compare.getQuoteStatus().isFavourite() == this.getQuoteStatus().isFavourite() &&
                compare.getQuoteStatus().isRead() == this.getQuoteStatus().isRead() &&
                compare.getAuthorName().equals(this.getAuthorName()) &&
                compare.getAuthorId() == this.getAuthorId()) {
            return 0;
        }
        return 1;
    }

}







