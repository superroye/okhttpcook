package com.wolf.lib.okhttpcool.util;

import android.util.Log;

import java.lang.reflect.Method;

/**
 * Created by Roye on 2018/4/3.
 */

public class L {

    private static final String TAG = "DZSDK";
    private static boolean isDebug = true;

    public static void setDebug(boolean isDebug) {
        L.isDebug = isDebug;
    }

    //这种方案能统一做debug开关，控制日志是否打印
    public static int print(String methodName, Object... params) {
        if (!isDebug) {
            return -1;
        }
        Class[] cls = new Class[params.length];
        for (int idx = 0; idx < params.length; idx++) {
            cls[idx] = params[idx].getClass();
            if (Throwable.class.isAssignableFrom(cls[idx])) {
                cls[idx] = Throwable.class;
            }
        }
        try {
            Method method = Log.class.getMethod(methodName, cls);
            return (int) method.invoke(L.class, params);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static String getMethodName() {
        String method = "";
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        for (int i = 2; i < elements.length; i++) {
            if (elements[i].getMethodName().length() < 2) {
                method = elements[i].getMethodName();
                break;
            }
        }
        return method;
    }

    public static int v(String tag, String msg) {
        return print(getMethodName(), tag, msg);
    }

    public static int v(String tag, String msg, Throwable tr) {
        return print(getMethodName(), tag, msg, tr);
    }

    public static int d(String tag, String msg) {
        return print(getMethodName(), tag, msg);
    }

    public static int d(String tag, String msg, Throwable tr) {
        return print(getMethodName(), tag, msg, tr);
    }

    public static int i(String tag, String msg) {
        return print(getMethodName(), tag, msg);
    }

    public static int i(String tag, String msg, Throwable tr) {
        return print(getMethodName(), tag, msg, tr);
    }

    public static int w(String tag, String msg) {
        return print(getMethodName(), tag, msg);
    }

    public static int w(String tag, String msg, Throwable tr) {
        return print(getMethodName(), tag, msg, tr);
    }

    public static int e(String tag, String msg) {
        return print(getMethodName(), tag, msg);
    }

    public static int e(String tag, String msg, Throwable tr) {
        return print(getMethodName(), tag, msg, tr);
    }

    /**
     * default tag
     */
    public static int v(String msg) {
        return v(TAG, msg);
    }

    public static int v(String msg, Throwable tr) {
        return v(TAG, msg, tr);
    }

    public static int d(String msg) {
        return d(TAG, msg);
    }

    public static int d(String msg, Throwable tr) {
        return d(TAG, msg, tr);
    }

    public static int i(String msg) {
        return i(TAG, msg);
    }

    public static int i(String msg, Throwable tr) {
        return i(TAG, msg, tr);
    }

    public static int w(String msg) {
        return w(TAG, msg);
    }

    public static int w(String msg, Throwable tr) {
        return w(TAG, msg, tr);
    }

    public static int e(String msg) {
        return e(TAG, msg);
    }

    public static int e(String msg, Throwable tr) {
        return e(TAG, msg, tr);
    }
}
