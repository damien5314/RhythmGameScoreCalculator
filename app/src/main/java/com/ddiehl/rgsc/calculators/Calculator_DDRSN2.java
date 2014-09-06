package com.ddiehl.rgsc.calculators;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ddiehl.rgsc.R;

public class Calculator_DDRSN2 extends Activity {
	private static final String TAG = Calculator_DDRSN2.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calculator_ddrsn2);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
	
	public void calculateScore(View v) {
		// Dismiss keyboard
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

        double earnedScore = 0;
		int marvellouses, perfects, greats, goods, boos, misses, holds;
		
		// Read in all of the fields in the DDR score form
		try { marvellouses = Integer.valueOf(((EditText)findViewById(R.id.marvellouses)).getText().toString());
		} catch (NumberFormatException e) { marvellouses = 0; }
		try { perfects = Integer.valueOf(((EditText)findViewById(R.id.perfects)).getText().toString());
		} catch (NumberFormatException e) { perfects = 0; }
		try { greats = Integer.valueOf(((EditText)findViewById(R.id.greats)).getText().toString());
		} catch (NumberFormatException e) { greats = 0; }
		try { goods = Integer.valueOf(((EditText)findViewById(R.id.goods)).getText().toString());
		} catch (NumberFormatException e) { goods = 0; }
		try { boos = Integer.valueOf(((EditText)findViewById(R.id.boos)).getText().toString());
		} catch (NumberFormatException e) { boos = 0; }
		try { misses = Integer.valueOf(((EditText)findViewById(R.id.misses)).getText().toString());
		} catch (NumberFormatException e) { misses = 0; }
		try { holds = Integer.valueOf(((EditText)findViewById(R.id.holds)).getText().toString());
		} catch (NumberFormatException e) { holds = 0; }
//		try { totalHolds = Integer.valueOf(((EditText)findViewById(R.id.totalHolds)).getText().toString());
//		} catch (NumberFormatException e) { totalHolds = 0; }
		
		// Verify input has been submitted
		int[] stepCounts = { marvellouses, perfects, greats, goods, boos, misses, holds };
		int stepTotal = 0;
		for (int steps : stepCounts)
			stepTotal += steps;
		
		if (stepTotal != 0) {
            // Add number from each field multiplied by its weight to earned score
            final double STEP_SCORE = ((1000000 / stepTotal)); // Round down to the nearest 10
            final double MARVELLOUSES_WEIGHT = STEP_SCORE;
            final double PERFECTS_WEIGHT = STEP_SCORE - 10;
            final double GREATS_WEIGHT = (STEP_SCORE / 2.0) - 10;
            final double HOLDS_WEIGHT = STEP_SCORE;
            earnedScore += marvellouses * MARVELLOUSES_WEIGHT;
            earnedScore += perfects * PERFECTS_WEIGHT;
            earnedScore += greats * GREATS_WEIGHT;
            earnedScore += holds * HOLDS_WEIGHT;
            earnedScore = (((int)(earnedScore / 10)) * 10) + 10;

            String grade = calculateGrade((int)earnedScore);

            TextView vEarnedScoreValue = (TextView) findViewById(R.id.earnedScoreValue);
            vEarnedScoreValue.setText((int)earnedScore + "");
            TextView vScoreGrade = (TextView) findViewById(R.id.scoreGrade);
            vScoreGrade.setText(grade);
		} else {
			Toast.makeText(this, R.string.error_no_steps, Toast.LENGTH_SHORT).show();
		}
	}

    /**
     * TODO Display PFC separately from grade
     */
	public String calculateGrade(int earnedScore) {

        if (earnedScore > 990000)
            return "(AAA)";
        else if (earnedScore > 950000)
            return "(AA)";
        else if (earnedScore > 900000)
            return "(A)";
        else if (earnedScore > 800000)
            return "(B)";
        else if (earnedScore > 700000)
            return "(C)";
        else return "(D)";

	}
	
	public void clearForm(View v) {
		((EditText)findViewById(R.id.marvellouses)).setText("");
		((EditText)findViewById(R.id.perfects)).setText("");
		((EditText)findViewById(R.id.greats)).setText("");
		((EditText)findViewById(R.id.goods)).setText("");
		((EditText)findViewById(R.id.boos)).setText("");
		((EditText)findViewById(R.id.misses)).setText("");
		((EditText)findViewById(R.id.holds)).setText("");
//		((EditText)findViewById(R.id.totalHolds)).setText("");
		
		((TextView)findViewById(R.id.earnedScoreValue)).setText(R.string.score_value_earned_default);
//		((TextView)findViewById(R.id.potentialScoreValue)).setText(R.string.score_value_potential_default);
//		((TextView)findViewById(R.id.scorePercent)).setText(R.string.score_percent_default);
		((TextView)findViewById(R.id.scoreGrade)).setText(R.string.score_grade_default);
	}

}
