package com.mark.ExpenseTracker.util;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class Utils {
    // generic function to create spinners
    public static Spinner createSpinner(Activity activity, int resId, int stringsId) {
        Spinner spinner = (Spinner) activity.findViewById(resId);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                activity, stringsId,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        return spinner;
    }

    public static boolean isStringEmpty(String s) {
        return (s == null) || s.isEmpty();
    }

    public static final boolean LOG = true;

    public static void LogD(String message) {
        if (LOG) Log.d(Constants.LOG_TAG, message);
    }

    public static void LogW(String message) {
        if (LOG) Log.w(Constants.LOG_TAG, message);
    }

    public static void LogE(String message) {
        if (LOG) Log.e(Constants.LOG_TAG, message);
    }

    public static void toastW(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        LogW(message);
    }
}
