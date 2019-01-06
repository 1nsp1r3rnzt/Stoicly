
package codehealthy.com.stoicly.data.source.local;


import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import codehealthy.com.stoicly.data.model.Quote;
import timber.log.Timber;

//    get path of json file
//    create file object
// TODO: check local version against codehealthy version
public class QuoteData extends Application {
    private Context context;

    public QuoteData(Context context) {
        this.context = context;
    }

    public List<Quote> getQuotesData() {
        List<Quote> quoteList = new ArrayList<>();

        try {
            JSONObject quotesJSObject = new JSONObject(getJSONResource());
            JSONArray jsonArray = quotesJSObject.getJSONArray("quotes");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject quotesObj = jsonArray.getJSONObject(i);
                String quoteStr = quotesObj.getString("quote");
                int authorId = Integer.parseInt(quotesObj.getString("author_id"));
                quoteList.add(new Quote(quoteStr, authorId, 1, false, false));
            }
        } catch (JSONException e) {
            Toast.makeText(context, "Error: can't parse json file" + e.toString(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        return quoteList;
    }

    private String getJSONResource() {
        String allQuotesToJSON = null;

        try {
            InputStream quoteInputStream = context.getAssets().open("quotes.json");
            int sizeAvailable = quoteInputStream.available();
            byte[] bufferQuote = new byte[sizeAvailable];
            quoteInputStream.read(bufferQuote);
            quoteInputStream.close();
            allQuotesToJSON = new String(bufferQuote, "UTF-8");

        } catch (IOException ioException) {
            Timber.d("Unable to read JSON file");
        }
        return allQuotesToJSON;
    }


}
