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
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.mark.ExpenseTracker.R;
import com.mark.ExpenseTracker.item.Category;

public class Categories extends ListActivity implements AdapterView.OnItemClickListener {

    ListAdapter listAdapter;
    AlertDialog dialogRename;
    Category selectedItem;
    EditText editRename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_categories);
//        listAdapter = new
        getListView().setOnItemClickListener(this);
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
        return listAdapter;
    }

    @Override
    public void setListAdapter(ListAdapter adapter) {
        listAdapter = adapter;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        selectedItem = (Category) listAdapter.getItem(i);

        editRename = (EditText) getLayoutInflater().inflate(R.layout.d_categories_rename, null, false);
        editRename.setText(selectedItem.getName());

        dialogRename = new AlertDialog.Builder(getBaseContext())
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
            if ((selectedItem != null) && (editRename != null)) {
                String newName = editRename.getText().toString();
                selectedItem.setName(newName);
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
        selectedItem = null;
        editRename = null;
    }
}
