package codehealthy.com.stoicly.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

@Entity
public class QuoteAuthorJoin extends Quote {
//    Adding AuthorName

    @ColumnInfo(name = "author_name")
    private String authorName;

    public QuoteAuthorJoin(String quote, int authorId, int categoryId, boolean isRead, boolean isFavourite, String authorName) {
        super(quote, authorId, categoryId, isRead, isFavourite);
        this.authorName = authorName;

    }

    public String getAuthorName() {
        return authorName;
    }


}




