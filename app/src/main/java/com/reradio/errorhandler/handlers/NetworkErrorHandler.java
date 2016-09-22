package com.reradio.errorhandler.handlers;

import com.reradio.errorhandler.IErrorHandler;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

public class NetworkErrorHandler implements IErrorHandler {

    private int mResId;

    public NetworkErrorHandler(int resId) {
        mResId = resId;
    }

    @Override
    public boolean isHandled(Throwable e) {
        return (e instanceof UnknownHostException)
                || (e instanceof SocketTimeoutException)
                || (e instanceof TimeoutException);
    }

    @Override
    public boolean isHandled(String stringThrowable) {
        return stringThrowable.equals("");
    }

    @Override
    public int getStringResId() {
        return mResId;
    }
}
