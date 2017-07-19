package com.tomktw.network;


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class NetManager implements NetTask.Callback {

    private final List<NetTask> tasks = new ArrayList<>();
    private WeakReference<Callback> callbackRef;

    public NetManager() {
    }

    public void cancelAll() {
        for (NetTask task : tasks) if (task != null) task.cancel();
        tasks.clear();
    }

    public void start(NetTask task) {
        tasks.add(task);
        task.setCallback(this);
        task.execute();
    }

    public void setCallback(Callback callback) {
        if (callbackRef != null) callbackRef.clear();
        callbackRef = new WeakReference<>(callback);
    }

    @Override
    public void onResponse(NetTask task) {
        tasks.remove(task);
        task.setCallback(null);
        Callback callback = callbackRef.get();
        if (callback != null) callback.onResponse(task);
    }

    public interface Callback {
        void onResponse(NetTask task);
    }
}
