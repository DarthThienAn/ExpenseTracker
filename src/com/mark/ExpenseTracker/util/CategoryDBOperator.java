package com.mark.ExpenseTracker.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.mark.ExpenseTracker.database.CategoryDBHelper;
import com.mark.ExpenseTracker.database.CategoryEntry;
import java.util.ArrayList;

public class CategoryDBOperator {
    CategoryDBHelper dbHelper;

    public CategoryDBOperator(Context context) {
        dbHelper = new CategoryDBHelper(context);
    }

    public void addCategory(String name) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CategoryEntry.COLUMN_NAME, name);

        long newRowId = db.insert(
                CategoryEntry.TABLE_NAME,
                null,
                values);
        if (newRowId == -1){
            Utils.ETLOG("row insert failed");
        }
        Utils.ETLOG(name + " added");
    }

    public int updateCategory(String oldName, String newName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(CategoryEntry.COLUMN_NAME, newName);

        String selection = CategoryEntry.COLUMN_NAME + " LIKE ?";
        String[] selectionArgs = { oldName };

        return db.update(CategoryEntry.TABLE_NAME, values, selection, selectionArgs);
    }

    public void deleteCategory(String name) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = CategoryEntry.COLUMN_NAME + " LIKE ?";
        String[] selectionArgs = { name };
        db.delete(CategoryEntry.TABLE_NAME, selection, selectionArgs);
    }

    public Cursor getCategories(String selection, String[] selectionArgs) {
        String sortOrder = CategoryEntry._ID + " DESC";
        return getCategories(selection, selectionArgs, sortOrder);
    }

    public Cursor getCategories(String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                CategoryEntry._ID,
                CategoryEntry.COLUMN_NAME,
        };

        Cursor cursor = db.query(
                CategoryEntry.TABLE_NAME,  // The table to query
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

    public static ArrayList<String> getNameListFromCursor(Cursor cursor) {
        ArrayList<String> names = new ArrayList<String>();
        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
        } else {
            return null;
        }
        while (!cursor.isAfterLast()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(CategoryEntry.COLUMN_NAME));
            names.add(name);
            cursor.moveToNext();
        }
        return names;
    }

    public static String getNameFromCursor(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow(CategoryEntry.COLUMN_NAME));
    }
}
