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

package com.mark.ExpenseTracker.util;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.mark.ExpenseTracker.R;

import java.util.Calendar;

public class DateSpinnerController {
    private Spinner spinnerYear, spinnerMonth, spinnerDay;
    private Activity activity;

    public DateSpinnerController(Activity activity, int idDay, int idMonth, int idYear) {
        this.activity = activity;
        init(idDay, idMonth, idYear);
    }

    @SuppressWarnings("unchecked")
    private void init(int idDay, int idMonth, int idYear) {
        spinnerYear = Utils.createSpinner(activity, idYear, R.array.years);
        spinnerMonth = Utils.createSpinner(activity, idMonth, R.array.months);
        spinnerDay = Utils.createSpinner(activity, idDay, R.array.days);

        Calendar calendar = Calendar.getInstance();
        spinnerDay.setSelection(calendar.get(Calendar.DAY_OF_MONTH) - 1);
        spinnerMonth.setSelection(calendar.get(Calendar.MONTH)); // months are 0-11 so no need to subtract one
        int yearPosition = ((ArrayAdapter<String>) spinnerYear.getAdapter())
                .getPosition(String.valueOf(calendar.get(Calendar.YEAR)));
        spinnerYear.setSelection(yearPosition);
    }

    public String getDateString() {
        String day = (String) spinnerDay.getSelectedItem();
        String month = (String) spinnerMonth.getSelectedItem();
        String year = (String) spinnerYear.getSelectedItem();

        return String.format("%s/%s/%s", year, month, day);
    }
}
