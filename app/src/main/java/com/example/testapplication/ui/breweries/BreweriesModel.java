package com.example.testapplication.ui.breweries;

import com.example.testapplication.R;
import com.example.testapplication.data.network.RetrofitInterface;
import com.example.testapplication.data.network.response.BreweriesResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BreweriesModel {

    Single<List<BreweriesResponse>> getBreweries() {

        return Single.create(singleSubscriber -> RetrofitInterface.getBreweries().enqueue(new Callback<List<BreweriesResponse>>() {
            @Override
            public void onResponse(Call<List<BreweriesResponse>> call, Response<List<BreweriesResponse>> response) {
                if (response.isSuccessful()) {
                    singleSubscriber.onSuccess(response.body());
                } else {
                    singleSubscriber.onError(new Exception(String.valueOf(R.string.no_data)));
                }
            }

            @Override
            public void onFailure(Call<List<BreweriesResponse>> call, Throwable t) {
                singleSubscriber.onError(t);
            }
        }));
    }


    Single<List<BreweriesResponse>> getBreweriesByName(String name) {

        return Single.create(singleSubscriber -> RetrofitInterface.getBreweriesByName(name).enqueue(new Callback<List<BreweriesResponse>>() {
            @Override
            public void onResponse(Call<List<BreweriesResponse>> call, Response<List<BreweriesResponse>> response) {
                if (response.isSuccessful()) {
                    singleSubscriber.onSuccess(response.body());
                } else {
                    singleSubscriber.onError(new Exception(String.valueOf(R.string.no_data)));
                }
            }

            @Override
            public void onFailure(Call<List<BreweriesResponse>> call, Throwable t) {
                singleSubscriber.onError(t);
            }
        }));
    }


}
