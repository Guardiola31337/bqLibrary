package com.bqlibrary.guardiola.general;

import android.content.Context;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    public static Date stringToDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return date;
    }
}
