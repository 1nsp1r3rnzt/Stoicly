package codehealthy.com.stoicly.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

@Entity
public class QuoteAuthorJoin extends Quote {
    private static final String DEFAULT_THUMBNAIL_URL = "default_author.jpeg";

    @ColumnInfo(name="thumbnail_url")
    private final String thumbnailUrl ;

    @ColumnInfo(name = "author_name")
    private String authorName;

    public QuoteAuthorJoin(String quote, int authorId, int categoryId, QuoteStatus quoteStatus, String authorName , String source, String thumbnailUrl) {
        super(quote, authorId, categoryId, quoteStatus, source);
        this.authorName = authorName;
        this.thumbnailUrl = thumbnailUrl!=null?thumbnailUrl: DEFAULT_THUMBNAIL_URL;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getAuthorName() {
        return authorName;
    }


}




