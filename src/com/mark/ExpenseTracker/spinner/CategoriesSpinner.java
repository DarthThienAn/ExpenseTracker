/*
 *    Copyright 2013 APPNEXUS INC
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.mark.ExpenseTracker.spinner;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.mark.ExpenseTracker.R;
import com.mark.ExpenseTracker.database.CategoryDBOperator;

public class CategoriesSpinner extends CursorAdapter implements SpinnerAdapter {
    public CategoriesSpinner(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TextView tv = (TextView) layoutInflater.inflate(R.layout.s_category, null, false);
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

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return super.getDropDownView(position, convertView, parent);
    }
}
