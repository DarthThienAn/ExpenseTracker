package com.mark.ExpenseTracker.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.mark.ExpenseTracker.R;

public class Menu extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_menu);

        Button btnAddExpense = (Button) findViewById(R.id.menu_btn_addexpense);
        Button btnCategories = (Button) findViewById(R.id.menu_btn_categories);
        Button btnFilter = (Button) findViewById(R.id.menu_btn_filter);

        btnAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, AddExpense.class));
            }
        });

        btnCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, Categories.class));
            }
        });

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, Filter.class));
            }
        });

    }
}
