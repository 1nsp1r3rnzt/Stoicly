package com.codehealthy.stoicly.ui.common.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import timber.log.Timber;

public class UtilityHelper {
    private UtilityHelper() {
    }

    public static String readResourceFileToJson(@NonNull Context context, @NonNull String fileName) {
        String allQuotesToJSON = null;

        try {
            InputStream quoteInputStream = context.getAssets().open(fileName);
            int sizeAvailable = quoteInputStream.available();
            byte[] bufferQuote = new byte[sizeAvailable];
            quoteInputStream.read(bufferQuote);
            quoteInputStream.close();
            allQuotesToJSON = new String(bufferQuote, StandardCharsets.UTF_8);

        } catch (IOException ioException) {

            Toast.makeText(context, "Unable to read Json from Resource file", Toast.LENGTH_SHORT).show();
            Timber.e(ioException);
        }
        return allQuotesToJSON;
    }

    public static String capitalize(String inputStr) {
        StringBuilder stringBuilder = new StringBuilder(inputStr);
        stringBuilder.setCharAt(0, Character.toUpperCase(stringBuilder.charAt(0)));
        return stringBuilder.toString();
    }
}
