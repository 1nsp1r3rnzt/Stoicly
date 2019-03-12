package codehealthy.com.stoicly.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.codehealthy.stoicly.ui.common.utils.UtilityHelper;


@Entity
public class Author {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "thumbnailUrl")
    private String thumbnailUrl;

    @ColumnInfo(name = "bio")
    private String bio;

    @ColumnInfo(name = "date_born")
    private String dateBorn;

    @ColumnInfo(name = "date_died")
    private String dateDied;

    @ColumnInfo(name = "profession")
    private String profession;

    @ColumnInfo(name = "bio_link")
    private String readMoreUrl;


    public Author(String name, String thumbnailUrl, String bio, String dateBorn, String dateDied, String profession) {
        this.name = UtilityHelper.capitalize(name);
        this.thumbnailUrl = thumbnailUrl;
        this.bio = bio;
        this.dateBorn = dateBorn;
        this.dateDied = dateDied;
        this.profession = profession;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getBio() {
        return bio;
    }

    public String getDateBorn() {
        return dateBorn;
    }

    public String getDateDied() {
        return dateDied;
    }

    public String getName() {
        return name;
    }

    public String getProfession() {
        return profession;
    }

    public String getReadMoreUrl() {
        return readMoreUrl;
    }

    public void setReadMoreUrl(String readMoreUrl) {
        this.readMoreUrl = readMoreUrl;
    }
}
