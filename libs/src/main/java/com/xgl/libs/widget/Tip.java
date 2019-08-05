package com.xgl.libs.widget;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * @author xingguolei
 */
public final class Tip {
    private static Toast tip;

    public static void init(Context context, String message) {
        tip = Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT);
        tip.setGravity(Gravity.CENTER, 0, 0);
    }

    public static void show(Context context, String message) {
        if (message == null) {
            message = "--";
        }

        if (tip == null) {
            init(context, message);
        } else {
            tip.setText(message);
        }
        tip.show();
    }
}
