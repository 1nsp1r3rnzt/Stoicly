package codehealthy.com.stoicly.UI.quote;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import codehealthy.com.stoicly.R;

public class MainActivity extends AppCompatActivity implements QuoteFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            setUpFragment();
        }
    }

    private void setUpFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        QuoteFragment quoteFragment = new QuoteFragment();
        fragmentTransaction.replace(R.id.fragment_container, quoteFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onShareByIntentListener(String text, String chooserTitle) {
        shareByIntent(text,chooserTitle);
    }

    @Override
    public void onCopyToClipboardListener(ClipData clipData) {
        handleCopyToClipBoard(clipData);
    }

    private void handleCopyToClipBoard(ClipData clipData) {
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
