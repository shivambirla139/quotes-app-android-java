package com.newapps.quotesapp;

import java.util.List;

public interface QuoteResponseListener {

    void didFetch(List<Quote> response,String message);
    void didError(String message);
}
