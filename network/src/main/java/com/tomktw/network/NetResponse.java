package com.tomktw.network;

import android.support.v4.util.SimpleArrayMap;

public abstract class NetResponse {

    protected final SimpleArrayMap<String, Object> content = new SimpleArrayMap<>();

    protected Callback callback;

    public NetResponse() {
    }

    public NetResponse(SimpleArrayMap<String, Object> args) {
        putAll(args);
    }

    protected void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void respond(short status) {
        if (callback != null) callback.respond(status);
    }

    public void clear() {
        content.clear();
    }

    public boolean containsKey(Object key) {
        return content.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return content.containsValue(value);
    }

    public Object get(Object key) {
        return content.get(key);
    }

    public boolean isEmpty() {
        return content.isEmpty();
    }

    public void put(String key, Object value) {
        content.put(key, value);
    }

    public void putAll(SimpleArrayMap<? extends String, ?> array) {
        content.putAll(array);
    }

    public void remove(Object key) {
        content.remove(key);
    }

    public String getBody() {
        return (String) content.get(Key.BODY);
    }

    public boolean isSuccess() {
        return (boolean) content.get(Key.SUCCESS);
    }

    public int getCode() {
        return (int) content.get(Key.CODE);
    }

    public long getLength() {
        return (long) content.get(Key.LENGTH);
    }

    public String getType() {
        return (String) content.get(Key.TYPE);
    }

    public Throwable getThrowable() {
        return (Throwable) content.get(Key.THROWABLE);
    }

    protected interface Callback {
        void respond(short status);
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
