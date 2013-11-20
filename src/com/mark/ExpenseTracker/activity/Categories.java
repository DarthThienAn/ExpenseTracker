/*
 *    Copyright 2013 APPNEXUS INC
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.mark.ExpenseTracker.activity;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.mark.ExpenseTracker.R;
import com.mark.ExpenseTracker.database.CategoryEntry;
import com.mark.ExpenseTracker.list.CategoriesAdapter;
import com.mark.ExpenseTracker.util.CategoryDBOperator;

public class Categories extends ListActivity implements AdapterView.OnItemClickListener {

    CategoriesAdapter categoriesAdapter;
    AlertDialog dialogRename;
    EditText editRename;
    String oldName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_categories);
        CategoryDBOperator categoryDBOperator = new CategoryDBOperator(getApplicationContext());
        Cursor categoriesCursor = categoryDBOperator.getCategories(null, null);
        categoriesAdapter = new CategoriesAdapter(getApplicationContext(), categoriesCursor);
        getListView().setOnItemClickListener(this);
        getListView().setAdapter(categoriesAdapter);
    }

    @Override
    public ListView getListView() {
        return super.getListView();
    }

    @Override
    public long getSelectedItemId() {
        return super.getSelectedItemId();
    }

    @Override
    public int getSelectedItemPosition() {
        return super.getSelectedItemPosition();
    }

    @Override
    public void setSelection(int position) {
        super.setSelection(position);
    }

    @Override
    public ListAdapter getListAdapter() {
        return categoriesAdapter;
    }

    @Override
    public void setListAdapter(ListAdapter adapter) {
        categoriesAdapter = (CategoriesAdapter) adapter;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        categoriesAdapter.getCursor().moveToPosition(i);
        oldName = categoriesAdapter.getCursor().getString(categoriesAdapter.getCursor().getColumnIndex(CategoryEntry.COLUMN_NAME));

        editRename = (EditText) getLayoutInflater().inflate(R.layout.d_categories_rename, null, false);
        editRename.setText(oldName);

        dialogRename = new AlertDialog.Builder(Categories.this)
                .setView(editRename)
                .setPositiveButton("Save", dialogRenameConfirm)
                .setNegativeButton("Cancel", dialogRenameClose)
                .setCancelable(true)
                .setOnCancelListener(dialogRenameCancel)
                .show();
    }

    private final AlertDialog.OnClickListener dialogRenameConfirm = new AlertDialog.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if ((oldName != null) && (editRename != null)) {
                String newName = editRename.getText().toString();
                CategoryDBOperator categoryDBOperator = new CategoryDBOperator(getApplicationContext());
                categoryDBOperator.updateCategory(oldName, newName);
            }
            cleanupRename();
            dialogInterface.dismiss();
        }
    };

    private final AlertDialog.OnClickListener dialogRenameClose = new AlertDialog.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.cancel();
        }
    };

    private final DialogInterface.OnCancelListener dialogRenameCancel = new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialogInterface) {
            cleanupRename();
        }
    };

    private void cleanupRename() {
        oldName = null;
        editRename = null;
    }
}
