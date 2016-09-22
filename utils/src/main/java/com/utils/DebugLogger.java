package com.utils;

import android.util.Log;

import java.util.List;

public final class DebugLogger {

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
        DebugLogger.d(DebugLogger.class.getSimpleName(), debugLogger + " is enabled for logging");
    }

    private static final String NULL = "NULL";

    public static void d(String tag, String message) {
        if (sIsDebug) {
            Log.d(getTag(tag), message != null ? message : NULL);
        }
    }

    public static void d(String tag, Object o) {
        if (sIsDebug) {
            Log.d(getTag(tag), o != null ? o.toString() : NULL);
        }
    }

    public static void e(String tag, List list) {
        if (list != null && list.size() != 0) {
            for (Object o : list) {
                e(getTag(tag), o != null ? o.toString() : NULL);
            }
        } else {
            e(getTag(tag), "List is " + NULL);
        }
    }

    public static void e(String tag, String message) {
        if (sIsDebug) {
            Log.e(getTag(tag), message != null ? message : NULL);
        }
    }

    public static void e(String tag, Object o) {
        if (sIsDebug) {
            Log.e(getTag(tag), o != null ? o.toString() : NULL);
        }
    }

    private static String getTag(String tag){
        return String.format("%s : < "+tag+" >", DebugLogger.class.getSimpleName());
    }

}
