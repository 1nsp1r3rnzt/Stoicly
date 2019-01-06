package codehealthy.com.stoicly.data.source.local;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import codehealthy.com.stoicly.data.model.QuoteCategory;

@Dao
public interface QuoteCategoryDao {

    @Query("SELECT * from QuoteCategory WHERE cid = :categoryId")
    LiveData<List<QuoteCategory>> getAllQuoteCategory(int... categoryId);

    @Insert
    void addQuoteCategory(QuoteCategory quoteCategory);

    @Update
    void updateQuoteCategory(QuoteCategory quoteCategory);


}
