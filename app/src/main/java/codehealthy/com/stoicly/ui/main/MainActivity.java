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
import android.widget.Toast;

import codehealthy.com.stoicly.R;
import codehealthy.com.stoicly.databinding.ActivityMainBinding;
import codehealthy.com.stoicly.ui.allquote.QuoteAdapter;
import codehealthy.com.stoicly.ui.allquote.QuoteFragment;
import codehealthy.com.stoicly.ui.author.all.AuthorListFragment;
import codehealthy.com.stoicly.ui.author.quotelist.AuthorBioFragment;
import codehealthy.com.stoicly.ui.author.quotelist.AuthorQuoteListFragment;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements QuoteAdapter.OnQuoteFragmentInteractionListener {
    private final static String TAG_AUTHOR_LIST_FRAGMENT       = "TAG_AUTHOR_LIST_FRAGMENT";
    private static final String TAG_AUTHOR_QUOTE_LIST_FRAGMENT = "TAG_AUTHOR_QUOTE_LIST_FRAGMENT";
    private final static String TAG_AUTHOR_BIO_FRAGMENT        = "TAG_AUTHOR_BIO_FRAGMENT";
    private final static String TAG_QUOTE_FRAGMENT             = "TAG_QUOTE_FRAGMENT";
    ActivityMainBinding binding;
    private DrawerLayout          mainDrawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainDrawerLayout = binding.mainDrawerLayout;

        if (savedInstanceState == null) {
            setUpQuoteFragment();
        }
        setUpToolbarAsActionBar();
        NavigationView mainNavigationView = binding.mainNavigationView;
        setUpDrawerContent(mainNavigationView);
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
                    return true;
                case R.id.action_navigation_favourite:
                    //TODO: Launch favourite QuoteFragment
//                    call item.setChecked(true)
                    Toast.makeText(this, "nav", Toast.LENGTH_SHORT).show();
                    break;
                default:

            }
            return false;

        });
    }

    private void launchAllAuthorListFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        AuthorListFragment authorListFragment = (AuthorListFragment) fragmentManager.findFragmentByTag(TAG_AUTHOR_LIST_FRAGMENT);
        if (authorListFragment == null) {
            authorListFragment = AuthorListFragment.newInstance();
            authorListFragment.setUpFragmentInteractionListener((authorId) -> onAuthorSelectedListener(authorId, ""));
            fragmentTransaction.replace(R.id.fragment_container, authorListFragment, TAG_AUTHOR_LIST_FRAGMENT);
            fragmentTransaction.addToBackStack(null);
        }
        popPreviousFragmentFromBackStack(fragmentManager);
        fragmentTransaction.commit();

    }

    private void popPreviousFragmentFromBackStack(FragmentManager fragmentManager) {
        fragmentManager.popBackStack();
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
            Timber.e("setting up toolbar form acitivty");
        } else {
            Timber.e("No toolbar");
        }
        ActionBar actionbar = getSupportActionBar();
        if (actionbar == null) throw new AssertionError();
        Timber.e("Set Actionbar");
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                mainDrawerLayout,
                toolbar,
                R.string.activity_main_drawer_open,
                R.string.activity_main_drawer_close);
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

    //region QuoteAdapter.OnQuoteFragmentInteractionListener
    @Override
    public void onShareByIntentListener(String text, String chooserTitle) {
        shareByIntent(text, chooserTitle);
    }

    @Override
    public void onCopyToClipboardListener(ClipData clipData) {
        copyToClipboard(clipData);
    }

    @Override
    public void onAuthorSelectedListener(int authorId, String transitionName) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        createAuthorBioFragment(fragmentTransaction, authorId);
    }
    //endregion  QuoteAdapter.OnQuoteFragmentInteractionListener

    //region AuthorBioFragment
    private void createAuthorBioFragment(FragmentTransaction fragmentTransaction, int authorId) {
        AuthorQuoteListFragment authorQuoteListFragment = AuthorQuoteListFragment.newInstance(authorId);

        AuthorBioFragment authorBioFragment = AuthorBioFragment.newInstance(authorId);
        authorBioFragment.setUpFragmentInteractionListener(this::setAuthorNameOnToolbar);

        fragmentTransaction.replace(R.id.fragment_container, authorQuoteListFragment, TAG_AUTHOR_QUOTE_LIST_FRAGMENT)
                .replace(R.id.fragment_author_container, authorBioFragment, TAG_AUTHOR_BIO_FRAGMENT)
                .addToBackStack("load_author")
                .commit();
    }


    private void setAuthorNameOnToolbar(String authorName) {

        CollapsingToolbarLayout collapsingToolbarLayout = binding.appbarContainer.mainCollapsingToolbar;
        AppBarLayout appBarLayout = binding.appbarContainer.appbar;
        appBarLayout.addOnOffsetChangedListener((returnedAppBarLayout, verticalOffset) -> {
            int pendingDistance = returnedAppBarLayout.getTotalScrollRange() + verticalOffset;
            if (((pendingDistance) / 100) * returnedAppBarLayout.getTotalScrollRange() < 10) {
                collapsingToolbarLayout.setTitle(authorName);
            } else {
                collapsingToolbarLayout.setTitle(" ");
            }
        });
    }
    //endregion  AuthorBioFragment

    //region Menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mainDrawerLayout.openDrawer(GravityCompat.START);
                actionBarDrawerToggle.syncState();
                return true;
        }
        return super.onOptionsItemSelected(item);
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

}
