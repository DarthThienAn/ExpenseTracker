package com.mark.ExpenseTracker.database;

public class ExpenseContract {

    private static final String TEXT_TYPE = " TEXT NOT NULL";
    private static final String TEXT_TYPE_NULLABLE = " TEXT ";
    private static final String FLOAT_TYPE = " REAL NOT NULL";

    private static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ExpenseEntry.TABLE_NAME + " (" +
                    ExpenseEntry._ID + " INTEGER PRIMARY KEY," +
                    ExpenseEntry.COLUMN_DATE + TEXT_TYPE + COMMA_SEP +
                    ExpenseEntry.COLUMN_COST + FLOAT_TYPE + COMMA_SEP +
                    ExpenseEntry.COLUMN_CATEGORIES + TEXT_TYPE_NULLABLE + COMMA_SEP +
                    ExpenseEntry.COLUMN_COMMENT + TEXT_TYPE_NULLABLE +
                    " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ExpenseEntry.TABLE_NAME;

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public ExpenseContract() {}
}
