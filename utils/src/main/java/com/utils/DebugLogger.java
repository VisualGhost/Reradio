package com.utils;

import android.util.Log;

import java.util.List;

public final class DebugLogger {

    private static final String TAG = DebugLogger.class.getSimpleName();

    private static boolean sIsDebug;

    // private
    private DebugLogger() {
        // empty
    }

    // private
    private DebugLogger(boolean isDebug) {
        sIsDebug = isDebug;
    }

    public static class DebugLoggerHolder {
        private static DebugLogger sDebugLogger = new DebugLogger(true);
    }

    /**
     * Enable logging
     */
    public static void enableLogging() {
        DebugLogger debugLogger = DebugLoggerHolder.sDebugLogger;
        DebugLogger.d(TAG, debugLogger + " is enabled for logging");
    }

    private static final String NULL = "NULL";

    public static void d(String tag, String message) {
        if (sIsDebug) {
            Log.d(tag, message != null ? message : NULL);
        }
    }

    public static void d(String tag, List list) {
        if (list != null && list.size() != 0) {
            for (Object o : list) {
                d(tag, o != null ? o.toString() : NULL);
            }
        }
    }

}
