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

    static QuoteStatus newInstance(QuoteStatus quoteStatus) {
        return new QuoteStatus(quoteStatus.isRead(), quoteStatus.isFavourite());
    }
    public boolean isRead() {
        return isRead;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    private void setFavourite() {
        this.isFavourite = true;
    }

    private void removeFavourite() {
        this.isFavourite = false;
    }

    public void toggleFavourite() {
        if (this.isFavourite()) this.removeFavourite();
        else this.setFavourite();
    }
}