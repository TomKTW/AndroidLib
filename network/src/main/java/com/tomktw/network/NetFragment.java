package com.tomktw.network;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class NetFragment extends Fragment implements NetManager.Callback {

    private static final String TAG = "NetFragment";
    private final NetManager netManager = new NetManager();
    private Callback callback;

    public static NetFragment init(AppCompatActivity activity) {
        return init(activity.getSupportFragmentManager());
    }

    public static NetFragment init(Fragment fragment) {
        return init(fragment.getChildFragmentManager());
    }

    private static NetFragment init(FragmentManager fm) {
        Fragment fragment = fm.findFragmentByTag(TAG);
        if (fragment == null || !(fragment instanceof NetFragment)) {
            fragment = new NetFragment();
            fm.beginTransaction().add(fragment, TAG).commit();
        }
        return (NetFragment) fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        final Fragment parent = getParentFragment();
        if (parent != null && parent instanceof Callback) callback = (Callback) parent;
        else if (context instanceof Callback) callback = (Callback) context;
        else callback = null;
        netManager.setCallback(this);
    }

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setRetainInstance(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (callback != null) callback.onNetResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        netManager.cancelAll();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
        netManager.setCallback(null);
    }

    public void start(NetTask task) {
        netManager.start(task);
    }

    @Override
    public void onResponse(NetTask task) {
        if (callback != null) {
            callback.onNetTaskResponse(task);
        }
    }

    public interface Callback {
        void onNetResume();

        void onNetTaskResponse(NetTask task);
    }
}
