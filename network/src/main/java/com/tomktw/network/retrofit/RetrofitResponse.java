package com.tomktw.network.retrofit;

import android.support.v4.util.SimpleArrayMap;

import com.tomktw.network.NetResponse;

public class RetrofitResponse extends NetResponse {

    public RetrofitResponse() {
    }

    public RetrofitResponse(SimpleArrayMap<String, Object> args) {
        super(args);
    }

    public String getBody() {
        return (String) content.get(Key.BODY);
    }

    public boolean isSuccess() {
        return (Boolean) content.get(Key.SUCCESS);
    }

    public int getCode() {
        return (Integer) content.get(Key.CODE);
    }

    public long getLength() {
        return (Long) content.get(Key.LENGTH);
    }

    public String getType() {
        return (String) content.get(Key.TYPE);
    }

    public Throwable getThrowable() {
        return (Throwable) content.get(Key.THROWABLE);
    }

    public void respond(short status) {
        super.respond(status);
    }

    public static final class Key {
        public static final String BODY = "Body";
        public static final String SUCCESS = "Success";
        public static final String CODE = "Code";
        public static final String LENGTH = "Length";
        public static final String TYPE = "Type";
        public static final String THROWABLE = "Throwable";
    }
}
