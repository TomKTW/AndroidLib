package com.tomktw.network;

public abstract class NetRequest {

    public abstract void execute(final NetResponse response);

    public abstract void cancel(final NetResponse response);

}
