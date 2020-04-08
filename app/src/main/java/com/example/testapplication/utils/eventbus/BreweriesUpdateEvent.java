package com.example.testapplication.utils.eventbus;

public class BreweriesUpdateEvent {

    public final boolean update;

    public BreweriesUpdateEvent(boolean update) {
        this.update = update;
    }
}
