package com.example.sqlitetest.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

    private static Toast toast;

    public static void shortToast(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setText(msg);
        }
        toast.show();
    }

    public static void longToast(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        } else {
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setText(msg);
        }
        toast.show();
    }

}
