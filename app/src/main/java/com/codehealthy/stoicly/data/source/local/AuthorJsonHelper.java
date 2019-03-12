package com.codehealthy.stoicly.data.source.local;

import android.content.Context;

import com.codehealthy.stoicly.data.model.Author;
import com.codehealthy.stoicly.ui.common.utils.UtilityHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class AuthorJsonHelper implements ImportJson<Author> {
    private static final String       AUTHORS_ARRAY = "authors";
    private final        Context      context;
    private              List<Author> authorsList;

    public AuthorJsonHelper(Context context) {
        this.context = context;
    }

    @Override
    public List<Author> getListFrom(String jsonName) {
        final String AUTHOR_NAME_KEY = "name";
        final String AUTHOR_DATE_DIED_KEY = "dateDied";
        final String AUTHOR_DATE_BORN_KEY = "dateBorn";
        final String AUTHOR_THUMBNAIL_URL_KEY = "thumbnailUrl";
        final String AUTHOR_PROFESSION_KEY = "profession";
        final String AUTHOR_BIO_KEY = "bio";
        authorsList = new ArrayList<>();
        try {
            JSONObject authorObj = new JSONObject(UtilityHelper.readResourceFileToJson(context, jsonName));

            JSONArray allAuthors = authorObj.getJSONArray(AUTHORS_ARRAY);

            for (int i = 0; i < allAuthors.length(); i++) {
                JSONObject currentAuthor = allAuthors.getJSONObject(i);

                String authorName = currentAuthor.getString(AUTHOR_NAME_KEY);
                String dateBorn = currentAuthor.getString(AUTHOR_DATE_BORN_KEY);
                String dateDied = currentAuthor.getString(AUTHOR_DATE_DIED_KEY);

                String thumbnailUrl = currentAuthor.getString(AUTHOR_THUMBNAIL_URL_KEY);
                String profession = currentAuthor.getString(AUTHOR_PROFESSION_KEY);
                String bio = currentAuthor.getString(AUTHOR_BIO_KEY);

                authorsList.add(new Author(authorName, thumbnailUrl, bio, dateBorn, dateDied, profession));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return authorsList;
    }
}
