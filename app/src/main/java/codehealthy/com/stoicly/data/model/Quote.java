package codehealthy.com.stoicly.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Quote {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "quote")
    private String quote;

    @ColumnInfo(name = "author_id")
    private int authorId;

    @ColumnInfo(name = "category_id")
    private int categoryId;

    @ColumnInfo(name = "is_read")
    private boolean isRead;


    @ColumnInfo(name = "is_favourite")
    private boolean isFavourite;

    public Quote(String quote, int authorId, int categoryId, boolean isRead, boolean isFavourite) {
        this.quote = quote;
        this.authorId = authorId;
        this.categoryId = categoryId;
        this.isFavourite = isFavourite;
        this.isRead = isRead;
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

    public boolean isRead() {
        return isRead;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

}







