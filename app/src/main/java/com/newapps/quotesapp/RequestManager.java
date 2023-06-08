package com.newapps.quotesapp;

import android.content.Context;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class RequestManager {
    Context context;
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://type.fit/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context){
        this.context = context;
    }

    public void getAllQuotes(QuoteResponseListener listener){
        CallQuotes callQuotes = retrofit.create(CallQuotes.class);
        Call<List<Quote>> call = callQuotes.callQuotes();
        call.enqueue(new Callback<List<Quote>>() {
            @Override
            public void onResponse(Call<List<Quote>> call, Response<List<Quote>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(context, "Request not successfull", Toast.LENGTH_SHORT).show();
                    return;
                }
                listener.didFetch(response.body(),response.message());
            }

            @Override
            public void onFailure(Call<List<Quote>> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public interface CallQuotes{
        @GET("/api/quotes")
        Call<List<Quote>> callQuotes();
    }
}
