package codehealthy.com.stoicly.ui.allquote;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import codehealthy.com.stoicly.data.QuoteRepository;
import codehealthy.com.stoicly.data.QuoteRepositoryImpl;
import codehealthy.com.stoicly.data.model.Quote;
import codehealthy.com.stoicly.data.model.QuoteAuthorJoin;
import timber.log.Timber;

public class QuoteViewModel extends AndroidViewModel {
    List<QuoteAuthorJoin> allquotes;
    private QuoteRepository                        repository;
    private LiveData<List<QuoteAuthorJoin>>        allQuotesWithAuthorName;
    private MutableLiveData<List<QuoteAuthorJoin>> randomizedQuotes;
    private int                                    rotationDistanceForList;

    public QuoteViewModel(@NonNull Application application) {
        super(application);
        repository = new QuoteRepositoryImpl(application);
        if (rotationDistanceForList == 0) {
            rotationDistanceForList = getRandomNumber();
        }

        randomizedQuotes = new MutableLiveData<>();
        allQuotesWithAuthorName = Transformations.switchMap(repository.getAllQuotesWithAuthorName(), this::shuffleQuotes);

    }


    private int getRandomNumber() {
        return ThreadLocalRandom.current().nextInt(1, 800);

    }


    void updateQuote(Quote quote) {
        repository.updateQuote(quote);
    }


    private LiveData<List<QuoteAuthorJoin>> shuffleQuotes(List<QuoteAuthorJoin> quoteAuthorJoin) {
        allquotes = new ArrayList<>(quoteAuthorJoin);
        Collections.shuffle(quoteAuthorJoin, new Random(rotationDistanceForList));
        randomizedQuotes.postValue(quoteAuthorJoin);
        Timber.e("random %s", rotationDistanceForList);
        return randomizedQuotes;
    }


    void getRandomQuotes() {
        rotationDistanceForList = getRandomNumber();
        List<QuoteAuthorJoin> quotesCopy = new ArrayList<>(allquotes);
        Collections.shuffle(quotesCopy, new Random(rotationDistanceForList));
        randomizedQuotes.postValue(quotesCopy);
    }

    LiveData<List<QuoteAuthorJoin>> getAllQuotesWithAuthorName() {
        return allQuotesWithAuthorName;
    }
}
