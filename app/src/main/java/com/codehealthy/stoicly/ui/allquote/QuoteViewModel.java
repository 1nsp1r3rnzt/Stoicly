package com.codehealthy.stoicly.ui.allquote;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.codehealthy.stoicly.data.QuoteRepository;
import com.codehealthy.stoicly.data.model.Quote;
import com.codehealthy.stoicly.data.model.QuoteAuthorJoin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class QuoteViewModel extends AndroidViewModel {
    private List<QuoteAuthorJoin>                  allquotes;
    private QuoteRepository                        repository;
    private LiveData<List<QuoteAuthorJoin>>        allQuotesWithAuthorName;
    private MutableLiveData<List<QuoteAuthorJoin>> randomizedQuotes;
    private int                                    randomNumber;

    public QuoteViewModel(@NonNull Application application) {
        super(application);
        repository = new com.codehealthy.stoicly.data.QuoteRepositoryImpl(application);
        if (randomNumber == 0) {
            randomNumber = getRandomNumber();
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
        Collections.shuffle(quoteAuthorJoin, new Random(randomNumber));
        randomizedQuotes.postValue(quoteAuthorJoin);
        return randomizedQuotes;
    }

    void getRandomQuotes() {
        randomNumber = getRandomNumber();
        List<QuoteAuthorJoin> quotesCopy = new ArrayList<>(allquotes);
        Collections.shuffle(quotesCopy, new Random(randomNumber));
        randomizedQuotes.postValue(quotesCopy);
    }

    LiveData<List<QuoteAuthorJoin>> getAllQuotesWithAuthorName() {
        return allQuotesWithAuthorName;
    }
}
