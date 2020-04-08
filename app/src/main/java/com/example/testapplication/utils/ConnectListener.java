package com.example.testapplication.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.testapplication.utils.eventbus.EventBaseWrapper;
import com.example.testapplication.utils.eventbus.InternetConnectionEvent;

import java.util.Objects;


public class ConnectListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = Objects.requireNonNull(cm).getActiveNetworkInfo();

        if (activeNetwork != null) {
            boolean isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
            boolean isMobile = activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE;

            EventBaseWrapper.getInstance().post(new InternetConnectionEvent(true, isWiFi, isMobile, activeNetwork.getState()));
        } else {
            EventBaseWrapper.getInstance().post(new InternetConnectionEvent(false, false, false, null));
        }
    }


}
