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

package com.mark.ExpenseTracker.activity;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import com.mark.ExpenseTracker.R;
import com.mark.ExpenseTracker.database.ExpenseDBOperator;
import com.mark.ExpenseTracker.database.ExpenseEntry;
import com.mark.ExpenseTracker.list.ExpenseAdapter;
import com.mark.ExpenseTracker.util.DateSpinnerController;

public class Filter extends ListActivity implements AdapterView.OnItemClickListener {

    DateSpinnerController startDateController, endDateController;
    ExpenseAdapter expenseAdapter;
    Button btnFilter;
    TextView txtTotalCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_filter);

        startDateController = new DateSpinnerController(Filter.this,
                R.id.filter_start_date_day, R.id.filter_start_date_month, R.id.filter_start_date_year);
        endDateController = new DateSpinnerController(Filter.this,
                R.id.filter_end_date_day, R.id.filter_end_date_month, R.id.filter_end_date_year);

        btnFilter = (Button) findViewById(R.id.filter_btn_filter);
        btnFilter.setOnClickListener(btnFilterClick);

        txtTotalCost = (TextView) findViewById(R.id.filter_txt_total_cost);

        getListView().setOnItemClickListener(this);
    }

    private final View.OnClickListener btnFilterClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ExpenseDBOperator expenseDBOperator = new ExpenseDBOperator(getApplicationContext());
            String selection = ExpenseEntry.COLUMN_DATE + " BETWEEN ? AND ?";
            String[] selectionArgs = {startDateController.getDateString(), endDateController.getDateString()};
            Cursor expenseFilterCursor = expenseDBOperator.getExpenses(selection, selectionArgs);
            expenseAdapter = new ExpenseAdapter(getApplicationContext(), expenseFilterCursor);

            if (expenseFilterCursor != null) {
                expenseFilterCursor.moveToFirst();
                double total = 0.0;

                while (!expenseFilterCursor.isAfterLast()) {
                    total += expenseFilterCursor.getFloat(expenseFilterCursor.getColumnIndexOrThrow(ExpenseEntry.COLUMN_COST));
                    expenseFilterCursor.moveToNext();
                }

                txtTotalCost.setText("Total: " + String.valueOf(total));
            }

            getListView().setAdapter(expenseAdapter);
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        return;
    }
}
