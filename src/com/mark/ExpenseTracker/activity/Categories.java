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
import android.widget.*;
import com.mark.ExpenseTracker.R;
import com.mark.ExpenseTracker.database.CategoryEntry;
import com.mark.ExpenseTracker.list.CategoriesAdapter;
import com.mark.ExpenseTracker.util.CategoryDBOperator;
import com.mark.ExpenseTracker.util.Utils;

public class Categories extends ListActivity implements AdapterView.OnItemClickListener {

    private CategoriesAdapter categoriesAdapter;

    // add dialog
    private Button btnAddCategory;
    private AlertDialog dialogAdd;

    // rename dialog
    private AlertDialog dialogRename;
    private EditText editRename;
    private String oldName;

    // delete dialog
    private AlertDialog dialogDelete;

    private boolean isShowing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_categories);

        btnAddCategory = (Button) findViewById(R.id.categories_btn_add);
        btnAddCategory.setOnClickListener(btnAddCategoryListener);

        CategoryDBOperator categoryDBOperator = new CategoryDBOperator(getApplicationContext());
        categoriesAdapter = new CategoriesAdapter(getApplicationContext(), categoryDBOperator.getAllCategories());
        getListView().setOnItemClickListener(this);
        getListView().setAdapter(categoriesAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialogAdd != null) dialogAdd.dismiss();
        if (dialogRename != null) dialogRename.dismiss();
        if (dialogDelete != null) dialogDelete.dismiss();
    }

    /**
     * Add Category
     */

    final View.OnClickListener btnAddCategoryListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final EditText editAdd = (EditText) getLayoutInflater().inflate(R.layout.d_categories_add, null, false);

            dialogAdd = new AlertDialog.Builder(Categories.this)
                    .setView(editAdd)
                    .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String categoryName = editAdd.getText().toString().trim();
                            if (!Utils.isStringEmpty(categoryName)) {
                                CategoryDBOperator categoryDBOperator = new CategoryDBOperator(getApplicationContext());
                                categoryDBOperator.addCategory(categoryName);
                                refreshCursor(categoryDBOperator);
                            } else {
                                Utils.LogW("Can't add empty category!");
                            }
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .setCancelable(true)
                    .show();
        }
    };

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

        if (!isShowing) {
            isShowing = true;
            dialogRename = new AlertDialog.Builder(Categories.this)
                    .setView(editRename)
                    .setPositiveButton("Save", dialogRenameConfirm)
                    .setNegativeButton("Cancel", dialogRenameClose)
                    .setNeutralButton("Delete", dialogRenameDelete)
                    .setCancelable(true)
                    .setOnCancelListener(dialogRenameCancel)
                    .show();
        }
    }

    private void refreshCursor(CategoryDBOperator categoryDBOperator) {
        categoriesAdapter.changeCursor(categoryDBOperator.getAllCategories());
    }

    /**
     * Rename Dialog
     */

    private final AlertDialog.OnClickListener dialogRenameConfirm = new AlertDialog.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if ((oldName != null) && (editRename != null)) {
                String newName = editRename.getText().toString().trim();
                if (!oldName.equals(newName)) {
                    CategoryDBOperator categoryDBOperator = new CategoryDBOperator(getApplicationContext());
                    categoryDBOperator.updateCategory(oldName, newName);
                    refreshCursor(categoryDBOperator);
                }
            }
            cleanupRename();
        }
    };

    private final AlertDialog.OnClickListener dialogRenameClose = new AlertDialog.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            cleanupRename();
        }
    };

    private final DialogInterface.OnCancelListener dialogRenameCancel = new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialogInterface) {
            cleanupRename();
        }
    };

    private void cleanupRename() {
        dialogRename = null;
        oldName = null;
        editRename = null;
        isShowing = false;
    }

    /**
     * Delete dialog (extension of Rename)
     */

    private final AlertDialog.OnClickListener dialogRenameDelete = new AlertDialog.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialogDelete = new AlertDialog.Builder(Categories.this)
                    .setMessage("Are you sure?")
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (oldName != null) {
                                String categoryName = oldName;
                                if (!Utils.isStringEmpty(categoryName)) {
                                    CategoryDBOperator categoryDBOperator = new CategoryDBOperator(getApplicationContext());
                                    categoryDBOperator.deleteCategory(categoryName);
                                    refreshCursor(categoryDBOperator);
                                } else {
                                    Utils.LogW("Can't delete empty category!");
                                }
                            }
                            cleanupRename();
                        }
                    })
                    .setNegativeButton("Cancel", dialogRenameClose)
                    .setCancelable(true)
                    .setOnCancelListener(dialogRenameCancel)
                    .show();
        }
    };

}
