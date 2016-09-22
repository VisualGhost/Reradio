package com.reradio.errorhandler;

public interface IErrorHandler {
    boolean isHandled(Throwable e);

    boolean isHandled(String stringThrowable);

    int getStringResId();
}
