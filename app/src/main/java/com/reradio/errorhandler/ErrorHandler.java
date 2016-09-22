package com.reradio.errorhandler;

import com.reradio.R;
import com.reradio.errorhandler.handlers.NetworkErrorHandler;

public class ErrorHandler {

    private ErrorHandler() {
        // empty, private
    }

    private static class NetworkErrorHandlerHolder {
        private static NetworkErrorHandler sNetworkErrorHandler = new NetworkErrorHandler(R.string.unknown_host_exception);
    }

    public static int getErrorMessageResId(Throwable e) {
        if (NetworkErrorHandlerHolder.sNetworkErrorHandler.isHandled(e)) {
            return NetworkErrorHandlerHolder.sNetworkErrorHandler.getStringResId();
        }
        return R.string.unknown_error;
    }

    public static int getErrorMessageResId(String stringThrowable) {
        if (NetworkErrorHandlerHolder.sNetworkErrorHandler.isHandled(stringThrowable)) {
            return NetworkErrorHandlerHolder.sNetworkErrorHandler.getStringResId();
        }
        return 0;
    }

}
