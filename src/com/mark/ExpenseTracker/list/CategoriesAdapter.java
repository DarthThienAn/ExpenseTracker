package com.mark.ExpenseTracker.list;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.mark.ExpenseTracker.R;
import com.mark.ExpenseTracker.database.CategoryDBOperator;

public class CategoriesAdapter extends CursorAdapter {
    public CategoriesAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TextView tv = (TextView) layoutInflater.inflate(R.layout.i_category, null, false);
        tv.setText(CategoryDBOperator.getNameFromCursor(cursor));
        return tv;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tv;
        if (view instanceof TextView) {
            tv = (TextView) view;
            tv.setText(CategoryDBOperator.getNameFromCursor(cursor));
        } else {
            newView(context, cursor, null);
        }
    }
}
