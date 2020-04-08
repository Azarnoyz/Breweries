package com.example.testapplication.ui.breweries;

import com.example.testapplication.data.db.Breweries;

import java.util.List;

public interface MainActivityInterface {

    void setList(List<Breweries> breweriesList);

    void registerInternetReceiver();

    void unRegisterInternetReceiver();
}
