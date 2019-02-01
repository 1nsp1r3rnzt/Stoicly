package codehealthy.com.stoicly.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Quote {
    public static final boolean DEFAULT_READ_STATUS      = false;
    public static final boolean DEFAULT_FAVOURITE_STATUS = false;
    @PrimaryKey(autoGenerate = true)
    private             int     id;

    @ColumnInfo(name = "quote")
    private String quote;

    @ColumnInfo(name = "author_id")
    private int authorId;

    @ColumnInfo(name = "category_id")
    private int categoryId;

    @Embedded
    private QuoteStatus quoteStatus;

    @ColumnInfo(name = "source")
    private String source;

    public Quote(String quote, int authorId, int categoryId, QuoteStatus quoteStatus, String source) {
        this.quote = quote;
        this.authorId = authorId;
        this.categoryId = categoryId;
        this.source = source;
        this.quoteStatus = quoteStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuote() {
        return quote;
    }

    public int getAuthorId() {
        return authorId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public QuoteStatus getQuoteStatus() {
        return quoteStatus;
    }

    public void setQuoteStatus(QuoteStatus quoteStatus) {
        this.quoteStatus = quoteStatus;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
