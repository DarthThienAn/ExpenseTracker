package com.mark.ExpenseTracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.mark.ExpenseTracker.item.Expense;
import com.mark.ExpenseTracker.util.Utils;

import java.util.ArrayList;

public class ExpenseDBOperator {
    CategoryDBHelper dbHelper;

    public ExpenseDBOperator(Context context) {
        dbHelper = new CategoryDBHelper(context);
    }

    public void addExpense(Expense expense) {
        if ((expense == null) || !expense.checkExpenseValid(true, true, false, false)) {
            Utils.LogE("cannot add null or invalid expense");
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = ExpenseDBOperator.valuesFromExpense(expense);

        long newRowId = db.insert(
                ExpenseEntry.TABLE_NAME,
                null,
                values);
        if (newRowId == -1){
            Utils.LogE("Failed to add expense: " + expense);
            return;
        }
        Utils.LogD("Expense added: " + expense);
    }

    public void updateExpense(int oldExpenseId, Expense newExpense) {
        if (oldExpenseId < 1) {
            Utils.LogE("expense _ID must be positive");
            return;
        }
        if ((newExpense == null) || !newExpense.checkExpenseValid(true, true, false, false)) {
            Utils.LogE("cannot add null or invalid expense");
            return;
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        ContentValues values = ExpenseDBOperator.valuesFromExpense(newExpense);

        String selection = ExpenseEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(oldExpenseId) };

        int result = db.update(ExpenseEntry.TABLE_NAME, values, selection, selectionArgs);
        if (result == 1) {
            Utils.LogE("Expense update id #" + oldExpenseId + " to " + newExpense);
        } else {
            Utils.LogE("Failed to update expense id #" + oldExpenseId + " to " + newExpense);
        }
    }

    public void deleteExpense(int expenseId) {
        if (expenseId < 1) {
            Utils.LogE("expense _ID must be positive");
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = ExpenseEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(expenseId) };
        int result = db.delete(ExpenseEntry.TABLE_NAME, selection, selectionArgs);
        Utils.LogD("Expense deleted " + result + " rows");
    }

    public Cursor getAllExpenses() {
        return getExpenses(null, null);
    }

    public Cursor getExpenses(String selection, String[] selectionArgs) {
        String sortOrder = ExpenseEntry._ID + " ASC";
        return getExpenses(selection, selectionArgs, sortOrder);
    }

    public Cursor getExpenses(String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                ExpenseEntry._ID,
                ExpenseEntry.COLUMN_DATE,
                ExpenseEntry.COLUMN_COST,
                ExpenseEntry.COLUMN_CATEGORIES,
                ExpenseEntry.COLUMN_COMMENT,
        };

        Cursor cursor = db.query(
                ExpenseEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        cursor.moveToFirst();
        return cursor;
    }

    public static ArrayList<Expense> getExpenseListFromCursor(Cursor cursor) {
        ArrayList<Expense> expenses = new ArrayList<Expense>();
        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
        } else {
            return null;
        }

        while (!cursor.isAfterLast()) {
            expenses.add(getExpenseFromCursor(cursor));
            cursor.moveToNext();
        }
        return expenses;
    }

    public static Expense getExpenseFromCursor(Cursor cursor) {
        String date = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseEntry.COLUMN_DATE));
        float cost = cursor.getFloat(cursor.getColumnIndexOrThrow(ExpenseEntry.COLUMN_COST));
        String categories = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseEntry.COLUMN_CATEGORIES));
        String comment = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseEntry.COLUMN_COMMENT));
        return new Expense(date, cost, categories, comment);
    }

    public static ContentValues valuesFromExpense(Expense expense) {
        if (expense == null) {
            Utils.LogE("Expense should not be null, returning null values");
            return null;
        }

        ContentValues values = new ContentValues();
        values.put(ExpenseEntry.COLUMN_DATE, expense.getDate());
        values.put(ExpenseEntry.COLUMN_COST, expense.getCost());
        values.put(ExpenseEntry.COLUMN_CATEGORIES, expense.getCategoriesString());
        values.put(ExpenseEntry.COLUMN_COMMENT, expense.getComment());
        return values;
    }
}