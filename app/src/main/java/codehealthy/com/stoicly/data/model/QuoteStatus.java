package codehealthy.com.stoicly.data.model;

import android.arch.persistence.room.ColumnInfo;

public class QuoteStatus {
    @ColumnInfo(name = "is_read")
    private boolean isRead;

    @ColumnInfo(name = "is_favourite")
    private boolean isFavourite;

    public QuoteStatus(boolean isRead, boolean isFavourite) {
        this.isRead = isRead;
        this.isFavourite = isFavourite;
    }


    public boolean isRead() {
        return isRead;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite() {
        this.isFavourite = true;
    }

    public void removeFavourite() {
        this.isFavourite = false;
    }

    public void toggleFavourite() {
        if (this.isFavourite()) this.removeFavourite();
        else this.setFavourite();
    }
}