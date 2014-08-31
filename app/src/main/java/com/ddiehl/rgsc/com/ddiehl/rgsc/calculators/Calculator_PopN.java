package com.ddiehl.rgsc.com.ddiehl.rgsc.calculators;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ddiehl.rgsc.R;

public class Calculator_PopN extends Activity {
    private static final String TAG = Calculator_PopN.class.getSimpleName();
    private static final int COOLS_WEIGHT = 2;
    private static final int GREATS_WEIGHT = 1;
    private static final int GOODS_WEIGHT = 0;
    private static final int BOOS_WEIGHT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator_popn);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void calculateScore(View v) {
        // Dismiss keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

        int earnedScore = 0;
        int potentialScore = 0;
        int cools, greats, goods, boos;

        // Read input from all fields
        try { cools = Integer.valueOf(((EditText) findViewById(R.id.cools)).getText().toString()); }
        catch (NumberFormatException e) { cools = 0; }
        try { greats = Integer.valueOf(((EditText) findViewById(R.id.greats)).getText().toString()); }
        catch (NumberFormatException e) { greats = 0; }
        try { goods = Integer.valueOf(((EditText) findViewById(R.id.goods)).getText().toString()); }
        catch (NumberFormatException e) { goods = 0; }
        try { boos = Integer.valueOf(((EditText) findViewById(R.id.boos)).getText().toString()); }
        catch (NumberFormatException e) { boos = 0; }

        // Verify input has been submitted
        int[] stepCounts = { cools, greats, goods, boos };
        int stepTotal = 0;
        for (int i : stepCounts)
            stepTotal += i;

        if (stepTotal != 0) {
            // Add number from each field multiplied by its weight to earned score
            earnedScore += cools * COOLS_WEIGHT;
            earnedScore += greats * GREATS_WEIGHT;
            earnedScore += goods * GOODS_WEIGHT;
            earnedScore += boos * BOOS_WEIGHT;

            // Calculate potential score
            int bestWeight = COOLS_WEIGHT;
            potentialScore += ((cools + greats + goods + boos) * bestWeight);

            // Calculate score percentage rounded to 2 decimal places
            int weightedScore = ((int)(((double)earnedScore / (double)potentialScore) * 100000));

            Log.d(TAG, "Earned score:    " + earnedScore);
            Log.d(TAG, "Potential score: " + potentialScore);
            Log.d(TAG, "Weighted score:  " + weightedScore);

            ((TextView) findViewById(R.id.earnedScoreValue)).setText(weightedScore + "");
        } else {
            Toast.makeText(this, R.string.error_no_steps, Toast.LENGTH_SHORT).show();
        }
    }

    public void clearForm(View v) {

    }

}