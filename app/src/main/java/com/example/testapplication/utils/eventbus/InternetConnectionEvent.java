package com.example.testapplication.utils.eventbus;

import android.net.NetworkInfo;

public class InternetConnectionEvent {

    public final boolean isConnect;
    public final boolean isWifi;
    public final boolean isMobile;
    public final NetworkInfo.State state;


    public InternetConnectionEvent(boolean isConnect, boolean isWifi, boolean isMobile, NetworkInfo.State state) {
        this.isConnect = isConnect;
        this.isWifi = isWifi;
        this.isMobile = isMobile;
        this.state = state;
    }
}
