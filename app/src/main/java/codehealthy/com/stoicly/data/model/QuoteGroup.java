package codehealthy.com.stoicly.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class QuoteGroup {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "qid")
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String  description;
    @ColumnInfo(name = "excerpt")
    private String  excerpt;
    @ColumnInfo(name = "group_quotes")
    private String  groupQuotes;
    @ColumnInfo(name = "group_category")
    private String  groupCategory;
    @ColumnInfo(name = "is_hidden")
    private boolean isHidden;
    @ColumnInfo(name = "is_seen")
    private boolean isSeen;
    @ColumnInfo(name = "created_at")
    private String  createdAt;
    @ColumnInfo(name = "background")
    private String  backGround;

    public QuoteGroup(String name, String description, String excerpt, String groupQuotes, String groupCategory, String backGround) {
        this.name = name;
        this.description = description;
        this.excerpt = excerpt;
        this.groupQuotes = groupQuotes;
        this.groupCategory = groupCategory;
        this.backGround = backGround;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public String getGroupQuotes() {
        return groupQuotes;
    }

    public String getGroupCategory() {
        return groupCategory;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean isSeen) {
        this.isSeen = isSeen;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getBackGround() {
        return backGround;
    }

    public void setBackGround(String backGround) {
        this.backGround = backGround;
    }

}
