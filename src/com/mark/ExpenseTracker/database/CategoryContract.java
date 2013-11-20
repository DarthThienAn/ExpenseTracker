package com.mark.ExpenseTracker.database;

public class CategoryContract {

    private static final String TEXT_TYPE = " TEXT NOT NULL";
    private static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + CategoryEntry.TABLE_NAME + " (" +
                    CategoryEntry._ID + " INTEGER PRIMARY KEY," +
                    CategoryEntry.COLUMN_NAME + TEXT_TYPE +
                    " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + CategoryEntry.TABLE_NAME;

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public CategoryContract() {}
}
