package com.tomktw.network;

import java.lang.ref.WeakReference;

public abstract class NetTask<RQ extends NetRequest, RS extends NetResponse> implements NetResponse.Callback {

    private final RQ request;
    private final RS response;
    private final String tag;
    private short status;
    private WeakReference<Callback> callbackRef;

    public NetTask(RQ request, RS response, String tag) {
        this.request = request;
        this.response = response;
        this.tag = tag;
        this.status = Status.INITIAL;
        this.callbackRef = new WeakReference<>(null);
        response.setCallback(this);
    }

    public void execute() {
        status = Status.RUNNING;
        request.execute(response);
    }

    public void cancel() {
        status = Status.ABORTED;
        request.cancel(response);
    }

    @Override
    public void respond(short status) {
        this.status = status;
        response.setCallback(null);
        Callback callback = callbackRef.get();
        if (callback != null) callback.onResponse(this);
    }

    public RQ getRequest() {
        return request;
    }

    public RS getResponse() {
        return response;
    }

    public short getStatus() {
        return status;
    }

    public String getTag() {
        return tag;
    }

    public String getBody() {
        return getResponse().getBody();
    }

    public boolean isSuccess() {
        return getResponse().isSuccess();
    }

    public int getCode() {
        return getResponse().getCode();
    }

    public long getLength() {
        return getResponse().getLength();
    }

    public String getType() {
        return getResponse().getType();
    }

    public Throwable getThrowable() {
        return getResponse().getThrowable();
    }

    public void setCallback(Callback callback) {
        this.callbackRef = new WeakReference<>(callback);
    }

    public interface Callback {
        void onResponse(NetTask task);
    }

    public static final class Status {
        public static final short INITIAL = 1;
        public static final short RUNNING = 2;
        public static final short SUCCESS = 3;
        public static final short FAILURE = 4;
        public static final short ABORTED = 5;
    }
}
