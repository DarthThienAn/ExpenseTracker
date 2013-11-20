package com.mark.ExpenseTracker.list;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.mark.ExpenseTracker.R;
import com.mark.ExpenseTracker.util.CategoryDBOperator;
import com.mark.ExpenseTracker.util.Constants;
import com.mark.ExpenseTracker.util.Utils;

public class CategoriesAdapter extends CursorAdapter {
    public CategoriesAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    private TextView createNewView(Context context) {
        LayoutInflater layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return (TextView) layoutInflater.inflate(R.layout.i_category, null, false);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        Utils.ETLOG("newView");
        TextView tv = createNewView(context);
        tv.setText(CategoryDBOperator.getNameFromCursor(cursor));
        return tv;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Utils.ETLOG("bindView");
        TextView tv;
        if (view instanceof TextView) {
            tv = (TextView) view;
        } else {
            tv = createNewView(context);
        }
        tv.setText(CategoryDBOperator.getNameFromCursor(cursor));
    }
}
