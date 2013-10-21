package com.mark.ExpenseTracker.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.mark.ExpenseTracker.R;
import com.mark.ExpenseTracker.util.DateSpinnerController;
import com.mark.ExpenseTracker.util.Utils;

public class AddExpense extends Activity {

    DateSpinnerController dateController;
    Spinner spinnerCategory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_addexpense);

        dateController = new DateSpinnerController(this);

        spinnerCategory = (Spinner) findViewById(R.id.addexpense_category);
        // create adapter from database table
//        spinnerCategory.setAdapter();
    }

}
