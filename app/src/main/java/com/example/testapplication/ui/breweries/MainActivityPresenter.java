package com.example.testapplication.ui.breweries;

import android.annotation.SuppressLint;

import com.example.testapplication.base.BasePresenter;
import com.example.testapplication.data.db.Breweries;
import com.example.testapplication.data.db.Repository;
import com.example.testapplication.utils.eventbus.BreweriesUpdateEvent;
import com.example.testapplication.utils.eventbus.EventBaseWrapper;
import com.example.testapplication.utils.eventbus.InternetConnectionEvent;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

class MainActivityPresenter extends BasePresenter {

    private MainActivityInterface view;
    private BreweriesModel model;
    private Repository repository = new Repository();
    private boolean connection;


    MainActivityPresenter(BreweriesModel model) {
        this.model = model;
    }

    void attachView(MainActivityInterface view) {
        this.view = view;
    }

    private void detachView() {
        view = null;
    }


    @SuppressLint("CheckResult")
    private void getBreweries() {
        model.getBreweries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    List<Breweries> breweriesList = repository.addBreweriesToList(data);
                    repository.addBreweriesToDataBase(breweriesList);
                }, error -> {

                });
    }

    @SuppressLint("CheckResult")
    void getBreweriesByName(String name) {
        if (connection) {
            model.getBreweriesByName(name)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(data -> {
                        List<Breweries> breweriesList = repository.addBreweriesToList(data);
                        view.setList(breweriesList);
                    }, error -> {

                    });
        } else {
            getBreweriesFromDbByName(name);
        }

    }

    private void getBreweriesFromDB() {
        repository.getAllBreweries(breweriesFromDb -> Completable.fromAction(() -> {

        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        view.setList(breweriesFromDb);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                }));
    }

    @SuppressLint("CheckResult")
    private void getBreweriesFromDbByName(String search) {
        repository.getAllBreweriesByName(search)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    view.setList(data);
                });
    }


    @Subscribe
    public void breweriesUpdate(BreweriesUpdateEvent event) {
        if (event.update) {
            getBreweriesFromDB();
        }
    }


    @Subscribe
    public void internetListener(InternetConnectionEvent internet) {
        if (internet.isConnect) {
            connection = true;
            getBreweriesFromDB();
            getBreweries();
        } else {
            connection = false;
            getBreweriesFromDB();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        view.registerInternetReceiver();
        EventBaseWrapper.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        view.unRegisterInternetReceiver();
        EventBaseWrapper.getInstance().unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        detachView();
    }


}
