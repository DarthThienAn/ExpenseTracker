package com.mark.ExpenseTracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.mark.ExpenseTracker.database.CategoryDBHelper;
import com.mark.ExpenseTracker.database.CategoryEntry;
import com.mark.ExpenseTracker.util.Utils;

import java.util.ArrayList;

public class CategoryDBOperator {
    CategoryDBHelper dbHelper;

    public CategoryDBOperator(Context context) {
        dbHelper = new CategoryDBHelper(context);
    }

    public void addCategory(String name) {
        if (Utils.isStringEmpty(name)) {
            Utils.LogE("name cannot be empty");
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CategoryEntry.COLUMN_NAME, name);

        if (checkIfCategoryExists(name)) {
            return;
        }

        long newRowId = db.insert(
                CategoryEntry.TABLE_NAME,
                null,
                values);
        if (newRowId == -1){
            Utils.LogE("Failed to add category: " + name);
            return;
        }
        Utils.LogD("Category added: " + name);
    }

    private boolean checkIfCategoryExists(String name) {
        if (Utils.isStringEmpty(name)) {
            Utils.LogE("name cannot be empty");
            return false;
        }

        String selection = CategoryEntry.COLUMN_NAME + " LIKE ?";
        String[] selectionArgs = { name };
        Cursor cursor = getCategories(selection, selectionArgs);
        if (cursor.getCount() > 0) {
            Utils.LogW("Category already exists in DB: " + name);
            return true;
        }

        return false;
    }

    public int updateCategory(String oldName, String newName) {
        if (Utils.isStringEmpty(oldName) || Utils.isStringEmpty(newName)) {
            Utils.LogE("name cannot be empty");
            return -1;
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(CategoryEntry.COLUMN_NAME, newName);

        String selection = CategoryEntry.COLUMN_NAME + " LIKE ?";
        String[] selectionArgs = { oldName };

        int result = db.update(CategoryEntry.TABLE_NAME, values, selection, selectionArgs);
        if (result == 1) {
            Utils.LogD("Category update " + oldName + " to " + newName);
        } else {
            Utils.LogE("Failed to update category: " + oldName + " to " + newName);
        }
        return result;
    }

    public void deleteCategory(String name) {
        if (Utils.isStringEmpty(name)) {
            Utils.LogE("name cannot be empty");
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = CategoryEntry.COLUMN_NAME + " LIKE ?";
        String[] selectionArgs = { name };
        int result = db.delete(CategoryEntry.TABLE_NAME, selection, selectionArgs);
        Utils.LogD("Category deleted " + result + " rows");
    }

    public Cursor getAllCategories() {
        return getCategories(null, null);
    }

    public Cursor getCategories(String selection, String[] selectionArgs) {
        String sortOrder = CategoryEntry.COLUMN_NAME + " ASC";
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
            String name = getNameFromCursor(cursor);
            names.add(name);
            cursor.moveToNext();
        }
        return names;
    }

    public static String getNameFromCursor(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow(CategoryEntry.COLUMN_NAME));
    }
}
