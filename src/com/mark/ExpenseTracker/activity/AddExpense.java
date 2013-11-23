package com.mark.ExpenseTracker.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.mark.ExpenseTracker.R;
import com.mark.ExpenseTracker.item.Expense;
import com.mark.ExpenseTracker.spinner.CategoriesSpinner;
import com.mark.ExpenseTracker.util.CategoryDBOperator;
import com.mark.ExpenseTracker.util.DateSpinnerController;
import com.mark.ExpenseTracker.util.Utils;

public class AddExpense extends Activity {

    DateSpinnerController dateController;
    Spinner spinnerCategory;
    CategoriesSpinner categoriesSpinner;

    EditText editCost, editComment;
    Button btnAddAction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_addexpense);

        dateController = new DateSpinnerController(this,
                R.id.addexpense_day, R.id.addexpense_month, R.id.addexpense_year);

        spinnerCategory = (Spinner) findViewById(R.id.addexpense_category);
        CategoryDBOperator categoryDBOperator = new CategoryDBOperator(getApplicationContext());
        categoriesSpinner = new CategoriesSpinner(getApplicationContext(), categoryDBOperator.getAllCategories());
        spinnerCategory.setAdapter(categoriesSpinner);

        editCost = (EditText) findViewById(R.id.addexpense_cost);
        editComment = (EditText) findViewById(R.id.addexpense_comment);
        btnAddAction = (Button) findViewById(R.id.addexpense_btn_add);
        btnAddAction.setOnClickListener(btnAddActionListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshCursor();
    }

    private void refreshCursor() {
        CategoryDBOperator categoryDBOperator = new CategoryDBOperator(getApplicationContext());
        categoriesSpinner.changeCursor(categoryDBOperator.getAllCategories());
    }

    final View.OnClickListener btnAddActionListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String costString = editCost.getText().toString();
            if (Utils.isStringEmpty(costString)) {
                // cost cannot be blank!
                return;
            }

            String commentString = editComment.getText().toString();
            if (Utils.isStringEmpty(commentString)) {
                commentString = "";
            }

            try {
                float cost = Float.parseFloat(costString);
                String dateString = dateController.getDateString();
                Expense newExpense = new Expense(dateString, cost, null, commentString);
            } catch (NumberFormatException e) {
                return;
            }
        }
    };
}
