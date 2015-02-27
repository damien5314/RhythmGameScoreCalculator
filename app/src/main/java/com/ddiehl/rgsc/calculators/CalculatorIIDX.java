package com.ddiehl.rgsc.calculators;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ddiehl.rgsc.R;

import java.text.DecimalFormat;

public class CalculatorIIDX extends Activity {
    private static final String TAG = CalculatorIIDX.class.getSimpleName();

    private static final int JGREATS_WEIGHT = 2;
    private static final int GREATS_WEIGHT = 1;
    private static final int GOODS_WEIGHT = 0;
    private static final int BADS_WEIGHT = 0;
    private static final int POORS_WEIGHT = 0;
    private static final int BEST_WEIGHT = JGREATS_WEIGHT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator_iidx);
    }

    /**
     * Test cases
     * 1569, 337, 94, 0, 0 = 3475 AA
     */
    public void calculateScore(View v) {
        // Dismiss keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

        int earnedScore = 0;
        int potentialScore = 0;
        int jgreats, greats, goods, bads, poors;

        // Read in all of the fields in the form
        try { jgreats = Integer.valueOf((((EditText)findViewById(R.id.jgreats)).getText()).toString());
        } catch (NumberFormatException e) { jgreats = 0; }
        try { greats = Integer.valueOf((((EditText)findViewById(R.id.greats)).getText()).toString());
        } catch (NumberFormatException e) { greats = 0; }
        try { goods = Integer.valueOf((((EditText)findViewById(R.id.goods)).getText()).toString());
        } catch (NumberFormatException e) { goods = 0; }
        try { bads = Integer.valueOf((((EditText)findViewById(R.id.bads)).getText()).toString());
        } catch (NumberFormatException e) { bads = 0; }
        try { poors = Integer.valueOf((((EditText)findViewById(R.id.poors)).getText()).toString());
        } catch (NumberFormatException e) { poors = 0; }

        // Verify input has been submitted
        int[] stepCounts = { jgreats, greats, goods, bads, poors };
        int stepTotal = 0;
        for (int stepCount : stepCounts)
            stepTotal += stepCount;

        if (stepTotal != 0) {
            // Add number from each field multiplied by its weight to earned score
            earnedScore += jgreats * JGREATS_WEIGHT;
            earnedScore += greats * GREATS_WEIGHT;
            earnedScore += goods * GOODS_WEIGHT;
            earnedScore += bads * BADS_WEIGHT;
            earnedScore += poors * POORS_WEIGHT;

            // Calculate potential score
            potentialScore += ((jgreats + greats + goods + bads + poors) * BEST_WEIGHT);

            // Calculate score percentage rounded to 2 decimal places
            double scorePercent = ((int)(((double)earnedScore / (double)potentialScore) * 10000) / 100.00);
            String grade = calculateGrade(scorePercent);
            DecimalFormat df = new DecimalFormat("0.00");

            TextView vEarnedScoreValue = (TextView) findViewById(R.id.earned_score_value);
            vEarnedScoreValue.setText(earnedScore + "");
            TextView vScorePercent = (TextView) findViewById(R.id.score_percent);
            vScorePercent.setText(df.format(scorePercent) + "%");
            TextView vScoreGrade = (TextView) findViewById(R.id.score_grade);
            vScoreGrade.setText(grade);
        } else {
            Toast.makeText(this, R.string.error_no_steps, Toast.LENGTH_SHORT).show();
        }
    }

    public String calculateGrade(double percentScore) {
        if (percentScore >= 8.0/9.0*100)
            return "(AAA)";
        else if (percentScore >= 7.0/9.0*100)
            return "(AA)";
        else if (percentScore >= 6.0/9.0*100)
            return "(A)";
        else if (percentScore >= 5.0/9.0*100)
            return "(B)";
        else if (percentScore >= 4.0/9.0*100)
            return "(C)";
        else
            return "(D)";
    }

    public void clearForm(View v) {
        ((EditText)findViewById(R.id.jgreats)).setText("");
        ((EditText)findViewById(R.id.greats)).setText("");
        ((EditText)findViewById(R.id.goods)).setText("");
        ((EditText)findViewById(R.id.bads)).setText("");
        ((EditText)findViewById(R.id.poors)).setText("");

        ((TextView)findViewById(R.id.earned_score_value)).setText(R.string.score_value_earned_default);
        ((TextView)findViewById(R.id.score_percent)).setText(R.string.score_percent_default);
        ((TextView)findViewById(R.id.score_grade)).setText(R.string.score_grade_default);
    }

}
