package com.example.testapplication.data.db;

import android.annotation.SuppressLint;

import com.example.testapplication.base.App;
import com.example.testapplication.data.network.response.BreweriesResponse;
import com.example.testapplication.utils.eventbus.BreweriesUpdateEvent;
import com.example.testapplication.utils.eventbus.EventBaseWrapper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class Repository {
    private AppDataBase db = App.getInstance().getDatabase();


    public List<Breweries> addBreweriesToList(List<BreweriesResponse> response) {
        List<Breweries> breweriesList = new ArrayList<>();

        for (BreweriesResponse getBreweries : response) {
            breweriesList.add(new Breweries(
                    getBreweries.getId(),
                    getBreweries.getName(),
                    getBreweries.getBreweryType(),
                    getBreweries.getStreet(),
                    getBreweries.getCity(),
                    getBreweries.getState(),
                    getBreweries.getPostalCode(),
                    getBreweries.getCountry(),
                    getBreweries.getLongitude(),
                    getBreweries.getLatitude(),
                    getBreweries.getPhone(),
                    getBreweries.getWebsiteUrl(),
                    getBreweries.getUpdatedAt()));
        }
        return breweriesList;
    }


    public void addBreweriesToDataBase(List<Breweries> breweriesList) {
        Completable.fromAction(() -> insertBreweries(breweriesList)).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onComplete() {
                EventBaseWrapper.getInstance().post(new BreweriesUpdateEvent(true));
            }

            @Override

            public void onError(Throwable e) {
            }
        });
    }

    public void insertBreweries(List<Breweries> breweriesList) {
        Breweries breweries = db.breweriesDao().getAnyBreweries();
        if (breweries == null) {
            db.breweriesDao().insert(breweriesList);
        } else {
            db.breweriesDao().update(breweriesList);
        }
    }


    @SuppressLint("CheckResult")
    public void getAllBreweries(Consumer<List<Breweries>> onSuccess) {
        db.breweriesDao().getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess);
    }


    public Flowable<List<Breweries>> getAllBreweriesByName(String search) {
        return db.breweriesDao().getAllWithName(search);
    }

}
