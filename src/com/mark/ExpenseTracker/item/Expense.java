package com.mark.ExpenseTracker.item;

import com.mark.ExpenseTracker.util.Utils;

import java.util.ArrayList;

public class Expense {
    private static final float DEFAULT_COST = Float.NEGATIVE_INFINITY;
    private String date;
    private float cost = DEFAULT_COST;
    private ArrayList<Category> categories;
    private String comment;

    public Expense(String date, float cost, ArrayList<Category> categories, String comment) {
        this.date = date;
        this.cost = cost;
        this.categories = categories;
        this.comment = comment;
    }

    public Expense(String date, float cost, String categories, String comment) {
        this.date = date;
        this.cost = cost;
        this.categories = categoriesFromString(categories);
        this.comment = comment;
    }

    public boolean checkExpenseValid(boolean dateRequired, boolean costRequired, boolean categoriesRequired, boolean commentRequired) {
        if (dateRequired && Utils.isStringEmpty(date)) {
            Utils.LogE("Date required but not found");
            return false;
        }

        if (costRequired && (cost == Float.NEGATIVE_INFINITY)) {
            Utils.LogE("Cost required but not found");
            return false;
        }

        if (categoriesRequired && Utils.isStringEmpty(this.getCategoriesString())) {
            Utils.LogE("Categories required but not found");
            return false;
        }

        if (commentRequired && Utils.isStringEmpty(comment)) {
            Utils.LogE("Cost required but not found");
            return false;
        }

        return true;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public String getCategoriesString() {
        return "default_category";
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public ArrayList<Category> categoriesFromString(String categoriesString) {
        //TODO!
        return categories;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Expense: ");
        sb.append("date='").append(date).append('\'');
        sb.append(", cost=").append(cost);
        sb.append(", categories=").append(categories);
        sb.append(", comment='").append(comment).append('\'');

        return sb.toString();
    }
}
