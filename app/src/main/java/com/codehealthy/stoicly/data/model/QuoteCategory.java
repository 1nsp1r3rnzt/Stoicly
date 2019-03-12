package com.codehealthy.stoicly.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class QuoteCategory {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cid")
    private String id;

    @ColumnInfo(name = "category_name")
    private String categoryName;

    @ColumnInfo(name = "category_description")
    private String categoryDescription;

    @ColumnInfo(name = "category_slug")
    private String categorySlug;

    @ColumnInfo(name = "is_hidden")
    private boolean isHidden;

    public QuoteCategory(String categoryName,
                         String categoryDescription,
                         String categorySlug,
                         boolean isHidden) {

        this.isHidden = isHidden;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.categorySlug = categorySlug;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public String getCategorySlug() {
        return categorySlug;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isHidden() {
        return isHidden;
    }
}
