/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package codehealthy.com.stoicly.ui.favourite;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Toolbar;

import java.util.List;

import codehealthy.com.stoicly.R;
import codehealthy.com.stoicly.data.model.QuoteAuthorJoin;
import codehealthy.com.stoicly.ui.author.quotelist.AuthorQuoteListViewModel;


public class FavouriteQuoteActivity extends AppCompatActivity {
    private RecyclerView          recyclerView;
    private Toolbar               toolbar;
    private List<QuoteAuthorJoin> favouriteQuotesList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_favourite);
        AuthorQuoteListViewModel authorQuoteListViewModel = ViewModelProviders.of(this).get(AuthorQuoteListViewModel.class);
//        we pass current activity as lifecycle owner in observe method
        authorQuoteListViewModel.getAllQuoteByAuthorList().observe(this, quotes -> {

        });
    }
}
