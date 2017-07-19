package com.tomktw.network.retrofit;

import android.support.annotation.NonNull;

import com.tomktw.network.NetRequest;
import com.tomktw.network.NetResponse;
import com.tomktw.network.NetTask;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitRequest extends NetRequest {

    private Call<ResponseBody> call;

    public RetrofitRequest(Call<ResponseBody> call) {
        this.call = call;
    }

    public void execute(final NetResponse response) {
        final RetrofitResponse netResponse = (RetrofitResponse) response;
        if (call != null) {
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> resp) {
                    boolean success = resp.isSuccessful();
                    ResponseBody body = null;
                    try {
                        body = success ? resp.body() : resp.errorBody();
                        MediaType type = body != null ? body.contentType() : null;
                        netResponse.put("Success", success);
                        netResponse.put("Code", resp.code());
                        netResponse.put("Length", body != null ? body.contentLength() : -1);
                        netResponse.put("Type", type != null ? type.toString() : "");
                        netResponse.put("Body", body != null ? body.string() : "");
                    } catch (Exception e) {
                        netResponse.put("Throwable", e);
                        e.printStackTrace();
                    } finally {
                        netResponse.respond(NetTask.Status.SUCCESS);
                        if (body != null) {
                            body.close();
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    netResponse.put("Success", false);
                    netResponse.put("Code", -1);
                    netResponse.put("Length", 0);
                    netResponse.put("Type", "");
                    netResponse.put("Body", "");
                    netResponse.put("Throwable", t);
                    netResponse.respond(NetTask.Status.FAILURE);
                    t.printStackTrace();
                }
            });
        }

    }

    public void cancel(NetResponse response) {
        RetrofitResponse netResponse = (RetrofitResponse) response;
        if (this.call != null && !this.call.isCanceled()) {
            if (!this.call.isExecuted()) {
                netResponse.put("Success", false);
                netResponse.put("Code", -1);
                netResponse.put("Length", 0);
                netResponse.put("Type", "");
                netResponse.put("Body", "");
                netResponse.respond(NetTask.Status.ABORTED);
            }
            this.call.cancel();
        }

    }
}