package com.mark.ExpenseTracker.list;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mark.ExpenseTracker.R;
import com.mark.ExpenseTracker.database.ExpenseDBOperator;
import com.mark.ExpenseTracker.item.Expense;

public class ExpenseAdapter extends CursorAdapter {

    private static final String EXPENSE_TAG = "expense";

    public ExpenseAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout expenseView = (LinearLayout) layoutInflater.inflate(R.layout.i_expense, null, false);
        expenseView.setTag(EXPENSE_TAG);
        Expense expense = ExpenseDBOperator.getExpenseFromCursor(cursor);
        setExpenseView(expenseView, expense);
        return expenseView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if ((view.getTag() != null) && (view.getTag().equals(EXPENSE_TAG))) {
            Expense expense = ExpenseDBOperator.getExpenseFromCursor(cursor);
            setExpenseView(view, expense);
        } else {
            newView(context, cursor, null);
        }
    }

    private void setExpenseView(View expenseView, Expense expense) {
        TextView date = (TextView) expenseView.findViewById(R.id.expense_date);
        TextView cost = (TextView) expenseView.findViewById(R.id.expense_cost);
        TextView categories = (TextView) expenseView.findViewById(R.id.expense_categories);
        TextView comment = (TextView) expenseView.findViewById(R.id.expense_comment);

        date.setText(expense.getDate());
        cost.setText(String.format("%.2f", expense.getCost()));
        categories.setText(expense.getCategoriesString());
        comment.setText(expense.getComment());
    }
}
