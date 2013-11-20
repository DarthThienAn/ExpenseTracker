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

public class Utils {
    // generic function to create spinners
    public static Spinner createSpinner(Activity activity, int resId, int stringsId) {
        Spinner spinner = (Spinner) activity.findViewById(resId);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                activity, stringsId,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        return spinner;
    }
}
