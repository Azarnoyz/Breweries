package com.example.testapplication.ui.breweries;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapplication.R;
import com.example.testapplication.base.BaseActivity;
import com.example.testapplication.data.adapter.BreweriesAdapter;
import com.example.testapplication.data.db.Breweries;
import com.example.testapplication.utils.ConnectListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

public class MainActivity extends BaseActivity implements MainActivityInterface, BreweriesAdapter.Callback {

    private MainActivityPresenter presenter;

    @BindView(R.id.main_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.search_view)
    SearchView searchView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    private BreweriesAdapter breweriesAdapter;
    private ConnectListener networkChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initToolbar();
        init();
        initAdapter();
        initSearchListener();

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        toolbarTitle.setText(toolbar.getTitle());
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

    }

    @SuppressLint("CheckResult")
    private void initSearchListener() {
        Observable.create((ObservableOnSubscribe<String>) subscriber -> {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    subscriber.onNext(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    subscriber.onNext(newText);
                    return false;
                }
            });
        }).map(text -> text.toLowerCase().trim())
                .debounce(250, TimeUnit.MILLISECONDS)
                .subscribe(text -> presenter.getBreweriesByName(text));
    }


    private void initAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        breweriesAdapter = new BreweriesAdapter(new ArrayList<>());
        breweriesAdapter.setCallback(this);
    }

    public void setList(List<Breweries> breweriesList) {
        breweriesAdapter.addItems(breweriesList);
        recyclerView.setAdapter(breweriesAdapter);
    }

    private void init() {
        BreweriesModel breweriesModel = new BreweriesModel();
        presenter = new MainActivityPresenter(breweriesModel);
        presenter.attachView(this);
        initPresenter(presenter);
        networkChangeReceiver = new ConnectListener();
    }

    @OnClick(R.id.search_view)
    public void searchEnable() {
        searchView.setIconified(false);
    }


    @Override
    public void mapButtonClick(String lat, String lon) {

        String geoUriString = "geo:" + Double.parseDouble(lat) + "," +
                Double.parseDouble(lon) + "?q=" + Double.parseDouble(lat) + "," +
                Double.parseDouble(lon);
        Uri geoUri = Uri.parse(geoUriString);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, geoUri);
        startActivity(mapIntent);
    }

    public void registerInternetReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, intentFilter);

    }

    public void unRegisterInternetReceiver() {
        unregisterReceiver(networkChangeReceiver);
    }

}
