package com.tomktw.network.retrofit;

import android.support.v4.util.SimpleArrayMap;

import com.tomktw.network.NetTask;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class RetrofitTask extends NetTask<RetrofitRequest, RetrofitResponse> {
    public RetrofitTask(Call<ResponseBody> call, String tag) {
        super(new RetrofitRequest(call), new RetrofitResponse(), tag);
    }

    public RetrofitTask(Call<ResponseBody> call, String tag, SimpleArrayMap<String, Object> args) {
        super(new RetrofitRequest(call), new RetrofitResponse(args), tag);
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
}
