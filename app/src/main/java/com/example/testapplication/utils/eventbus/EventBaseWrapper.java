package com.example.testapplication.utils.eventbus;

import org.greenrobot.eventbus.EventBus;

public class EventBaseWrapper {
    private static final class LazyHolder {
        private static final EventBaseWrapper INSTANCE = new EventBaseWrapper(EventBus.getDefault());
    }

    public static EventBaseWrapper getInstance() {
        return LazyHolder.INSTANCE;
    }

    public static EventBaseWrapper create() {
        return new EventBaseWrapper(EventBus.builder().build());
    }

    private EventBus bus;

    private EventBaseWrapper(EventBus bus) {
        this.bus = bus;
    }

    public void register(Object o) {
        bus.register(o);

    }

    public void unregister(Object o) {
        bus.unregister(o);
    }

    public void post(Object o) {
        bus.post(o);
    }
}
