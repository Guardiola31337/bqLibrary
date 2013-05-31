package com.bqlibrary.guardiola.general;

import android.content.Context;
import android.widget.Toast;

/**
 * Created with IntelliJ IDEA.
 * User: Administrador
 * Date: 31/05/13
 * Time: 14:46
 * To change this template use File | Settings | File Templates.
 */
public class Utils {
    private static final Utils INSTANCE = new Utils();

    private Utils() {}

    public static final Utils getInstance() {
        return INSTANCE;
    }

    public static void showToast(String msg, Context context) {
        Toast error = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        error.show();
    }
}
