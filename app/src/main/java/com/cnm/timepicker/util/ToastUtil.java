package com.cnm.timepicker.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Date:2018/5/30
 * TIME:18:22
 * author:Jia
 */
public class ToastUtil {
    private static Toast toast;

    public static void showToast(Context context,
                                 String content) {
        if (toast == null) {
            toast = Toast.makeText(context,
                    content,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }
    
}
