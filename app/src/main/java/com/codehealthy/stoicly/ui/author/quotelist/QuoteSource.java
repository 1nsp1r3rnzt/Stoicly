package com.codehealthy.stoicly.ui.author.quotelist;

public class QuoteSource {
    private String source;

    public QuoteSource(String selectedQuoteSource) {
        source = selectedQuoteSource;
    }

    public String getSource() {
        return source;
    }

    public boolean isStatusAllQuotes() {
        return source.equals("All Quotes");
    }
}
