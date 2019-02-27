package codehealthy.com.stoicly.ui.main;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Objects;

import codehealthy.com.stoicly.R;
import codehealthy.com.stoicly.databinding.ActivityMainBinding;
import codehealthy.com.stoicly.ui.allquote.QuoteAdapter;
import codehealthy.com.stoicly.ui.allquote.QuoteFragment;
import codehealthy.com.stoicly.ui.author.all.AuthorListFragment;
import codehealthy.com.stoicly.ui.author.quotelist.AuthorBioFragment;
import codehealthy.com.stoicly.ui.author.quotelist.AuthorQuoteListFragment;
import codehealthy.com.stoicly.ui.favourite.FavouriteQuoteFragment;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements QuoteAdapter.OnFragmentInteractionListener, AuthorListFragment.OnFragmentInteractionListener, AuthorBioFragment.OnFragmentInteractionListener {
    private final static String TAG_AUTHOR_LIST_FRAGMENT       = "TAG_AUTHOR_LIST_FRAGMENT";
    private static final String TAG_AUTHOR_QUOTE_LIST_FRAGMENT = "TAG_AUTHOR_QUOTE_LIST_FRAGMENT";
    private final static String TAG_AUTHOR_BIO_FRAGMENT        = "TAG_AUTHOR_BIO_FRAGMENT";
    private final static String TAG_QUOTE_FRAGMENT             = "TAG_QUOTE_FRAGMENT";
    private static final String TAG_FAV_QUOTES_FRAGMENT        = "TAG_FAV_QUOTES_FRAGMENT";
    ActivityMainBinding binding;
    private DrawerLayout            mainDrawerLayout;
    private ActionBarDrawerToggle   actionBarDrawerToggle;
    private CollapsingToolbarLayout mainCollapsingToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mainDrawerLayout = binding.mainDrawerLayout;

        if (savedInstanceState == null) {
            setUpQuoteFragment();
        }

        setUpToolbarAsActionBar();

        if (isAnyFragmentOpened()) {
            setUpBackButtonOnActionBar();
        } else {
            setUpHomeButtonOnActionBar();
        }
        setUpDrawerToggle();
        NavigationView mainNavigationView = binding.mainNavigationView;
        setUpDrawerContent(mainNavigationView);
    }

    private void disableCollapsingToolbarTitle() {
        if (binding != null) {
            mainCollapsingToolbar = binding.appbarContainer.mainCollapsingToolbar;
            mainCollapsingToolbar.setTitleEnabled(false);
        }
    }

    // region Save and Restore Instance
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
    //endregion save and Restore Instance

    private void setUpDrawerContent(NavigationView mainNavigationView) {
        mainNavigationView.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {

                case R.id.action_navigation_all_authors:
                    launchAllAuthorListFragment();
                    mainDrawerLayout.closeDrawers();
                    setUpBackButtonOnActionBar();
                    return true;
                case R.id.action_navigation_favourite:
                    setUpActionBarTitle(getResources().getString(R.string.description_navigation_favourite));
                    mainDrawerLayout.closeDrawers();
                    launchFavouriteQuoteFragment();
                    setUpBackButtonOnActionBar();
                    return true;
            }
            return false;

        });
    }


    private void launchFavouriteQuoteFragment() {
        clearBackStack();
        FavouriteQuoteFragment favouriteQuoteFragment = FavouriteQuoteFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, favouriteQuoteFragment, TAG_FAV_QUOTES_FRAGMENT);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void clearBackStack() {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    private void launchAllAuthorListFragment() {
        clearBackStack();


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        AuthorListFragment authorListFragment = (AuthorListFragment) fragmentManager.findFragmentByTag(TAG_AUTHOR_LIST_FRAGMENT);
        if (authorListFragment == null) {
            authorListFragment = AuthorListFragment.newInstance();

            fragmentTransaction.replace(R.id.fragment_container, authorListFragment, TAG_AUTHOR_LIST_FRAGMENT);
            fragmentTransaction.addToBackStack(null);
        }
        if (isAuthorDetailFragmentVisible(fragmentManager)) {
            fragmentTransaction.hide(Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag(TAG_AUTHOR_BIO_FRAGMENT)));
        }
        fragmentTransaction.commit();

    }

    private boolean isAuthorDetailFragmentVisible(FragmentManager fragmentManager) {
        AuthorBioFragment authorBioFragment = (AuthorBioFragment) fragmentManager.findFragmentByTag(TAG_AUTHOR_BIO_FRAGMENT);
        return authorBioFragment != null && authorBioFragment.isVisible();
    }

    //region setup Toolbar
    private void setUpToolbarAsActionBar() {
        Toolbar toolbar = binding.appbarContainer.mainToolbar;
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

    }

    private void setUpDrawerToggle() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                mainDrawerLayout,
                R.string.activity_main_drawer_open,
                R.string.activity_main_drawer_close);
        setupToggleAsDrawerListener();
    }

    private void setupToggleAsDrawerListener() {
        mainDrawerLayout.addDrawerListener(actionBarDrawerToggle);
    }

    private void setUpHomeButtonOnActionBar() {
        ActionBar actionbar = getSupportActionBar();
        if (actionbar == null) throw new AssertionError();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        setDrawerSwipeGesture();
    }

    private void setDrawerSwipeGesture() {
        mainDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }


    //endregion setup Toolbar
    //region QuoteFragment

    private void setUpQuoteFragment() {
        QuoteFragment quoteFragment = QuoteFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, quoteFragment, TAG_QUOTE_FRAGMENT);
        fragmentTransaction.commit();
    }


    //endregion QuoteFragment
    //region QuoteAdapter.OnFragmentInteractionListener

    @Override
    public void onShareByIntentListener(String text, String chooserTitle) {
        shareByIntent(text, chooserTitle);
    }

    @Override
    public void onCopyToClipboardListener(ClipData clipData) {
        copyToClipboard(clipData);
    }

    @Override
    public void onAuthorSelectedListener(View itemView, int authorId) {
        launchAuthorBioFragment(itemView, authorId);
        setUpBackButtonOnActionBar();
    }

    @Override
    public void setHomeAsNavigationIcon() {

        setUpHomeButtonOnActionBar();
        setUpDrawerToggle();
    }

    private void setUpBackButtonOnActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
            mainDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else {

            Timber.e("Actionbar is null");
        }
    }

    //endregion  QuoteAdapter.OnFragmentInteractionListener
    //region AuthorBioFragment

    private void launchAuthorBioFragment(View itemView, int authorId) {
        AuthorBioFragment authorBioFragment = AuthorBioFragment.newInstance(authorId, itemView);
        AuthorQuoteListFragment authorQuoteListFragment = AuthorQuoteListFragment.newInstance(authorId);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, authorQuoteListFragment, TAG_AUTHOR_QUOTE_LIST_FRAGMENT)
                .setReorderingAllowed(true)
                .addSharedElement(itemView, itemView.getTransitionName())
                .replace(R.id.fragment_author_container, authorBioFragment, TAG_AUTHOR_BIO_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }
    //endregion  AuthorBioFragment
    //region Menu

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case (R.id.action_favourite):
                launchFavouriteQuoteFragment();
                setUpBackButtonOnActionBar();

                return true;
            case android.R.id.home:
                if (isAnyFragmentOpened()) {
                    onBackPressed();
                } else {
                    mainDrawerLayout.openDrawer(GravityCompat.START);
                    actionBarDrawerToggle.syncState();

                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private boolean isAnyFragmentOpened() {
        return getSupportFragmentManager().getBackStackEntryCount() > 0;
    }

    //endregion Menu
    private void copyToClipboard(ClipData clipData) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(this, getString(R.string.description_clipboard_data_copy), Toast.LENGTH_SHORT).show();
    }

    private void shareByIntent(@NonNull String text, @NonNull String chooserTitle) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        Intent chooser = Intent.createChooser(intent, chooserTitle);
        startActivity(chooser);
    }

    @Override
    public void onAuthorClicked(View view, int authorId) {
        onAuthorSelectedListener(view, authorId);
    }

    @Override
    public void setToolbarCollapsedTitle(String title) {
        CollapsingToolbarLayout collapsingToolbarLayout = binding.appbarContainer.mainCollapsingToolbar;
        collapsingToolbarLayout.setTitleEnabled(true);
        AppBarLayout appBarLayout = binding.appbarContainer.appbar;
        collapsingToolbarLayout.setCollapsedTitleTextColor(this.getResources().getColor(R.color.colorWhite));
        appBarLayout.addOnOffsetChangedListener((returnedAppBarLayout, verticalOffset) -> {
            int pendingDistance = returnedAppBarLayout.getTotalScrollRange() + verticalOffset;
            final int SHOW_TITLE_AT_PERCENTAGE_SCROLL = 10;
            if (((pendingDistance) / 100) * returnedAppBarLayout.getTotalScrollRange() < SHOW_TITLE_AT_PERCENTAGE_SCROLL) {
                collapsingToolbarLayout.setTitle(title);
            } else {
                collapsingToolbarLayout.setTitle(" ");
            }
        });
    }

    private void setUpActionBarTitle(String string) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(string);
        }
    }

    @Override
    public void setToolbarTitle(String title) {
        disableCollapsingToolbarTitle();
        setUpActionBarTitle(title);
    }
}
