package com.mark.ExpenseTracker.database;

import android.provider.BaseColumns;

/* Inner class that defines the table contents */
public abstract class ExpenseEntry implements BaseColumns {
    public static final String TABLE_NAME = "Expense";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_COST = "cost";
    public static final String COLUMN_CATEGORIES = "categories";
    public static final String COLUMN_COMMENT = "comment";
}

