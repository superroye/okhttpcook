package com.wolf.lib.okhttpcool.util;

import android.os.Looper;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.wolf.lib.okhttpcool.base.BaseApplication;

/**
 * Created by liqi on 2016-2-23.
 */
public class ToastUtils {

    private static long time = 0;
    private static String last = "";

    public static boolean isTooFast() {
        return isTooFast(600);
    }

    public static boolean isTooFast(int delay) {
        long curTime = System.currentTimeMillis();
        long span = curTime - time;
        time = curTime;
        if (span < delay) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isSame(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            if (msg.equals(last))
                return true;
        }
        last = msg;
        return false;
    }

    public static void showToast(final String msg) {
        if (!isTooFast() || !isSame(msg)) {
            new android.os.Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(BaseApplication.app, msg, Toast.LENGTH_SHORT).show();
                }
            }, 100);
        }
    }

    public static void showToast(int resId) {
        String msg = BaseApplication.app.getString(resId);
        showToast(msg);
    }

    public static void showToastAtCenter(String msg) {
        if (isTooFast())
            return;
        if (BaseApplication.app == null)
            return;

        Toast toast = Toast.makeText(BaseApplication.app, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showToastAtCenter(int resId) {
        if (isTooFast())
            return;
        if (BaseApplication.app == null)
            return;

        Toast toast = Toast.makeText(BaseApplication.app, resId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showToastAtTop(String msg) {
        if (isTooFast())
            return;
        if (BaseApplication.app == null)
            return;
        Toast toast = Toast.makeText(BaseApplication.app, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 200);
        toast.show();
    }

    public static void showToastAtTop(int resId) {
        if (isTooFast())
            return;
        if (BaseApplication.app == null)
            return;
        Toast toast = Toast.makeText(BaseApplication.app, resId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 200);
        toast.show();
    }
}
