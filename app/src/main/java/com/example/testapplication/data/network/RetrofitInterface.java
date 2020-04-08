package com.example.testapplication.data.network;

import com.example.testapplication.data.network.response.BreweriesResponse;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RetrofitInterface {

    private static final String BASE_URL = "https://api.openbrewerydb.org";

    private static RetrofitInterface.ApiInterface apiInterface;

    interface ApiInterface {

        @GET("/breweries")
        Call<List<BreweriesResponse>> getBreweries();

        @GET("/breweries?")
        Call<List<BreweriesResponse>> getBreweriesByName(@Query("by_name") String searchText);

    }

    public static Call<List<BreweriesResponse>> getBreweries() {
        return apiInterface.getBreweries();
    }

    public static Call<List<BreweriesResponse>> getBreweriesByName(String breweriesName) {
        return apiInterface.getBreweriesByName(breweriesName);
    }


    static {
        initialize();
    }

    private static void initialize() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        apiInterface = retrofit.create(RetrofitInterface.ApiInterface.class);
    }


}
