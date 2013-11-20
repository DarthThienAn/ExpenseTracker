package com.mark.ExpenseTracker.database;

import android.provider.BaseColumns;

/* Inner class that defines the table contents */
public abstract class CategoryEntry implements BaseColumns {
    public static final String TABLE_NAME = "Category";
    public static final String COLUMN_NAME = "name";
}

