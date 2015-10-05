package com.ddiehl.rgsc.ddrextreme;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.ddiehl.rgsc.R;

import java.text.DecimalFormat;

public class CalculatorDDRExtreme extends Activity {
	private static final String TAG = CalculatorDDRExtreme.class.getSimpleName();

	private static final double MARVELLOUSES_WEIGHT = 3;
	private static final double PERFECTS_WEIGHT = 2;
	private static final double GREATS_WEIGHT = 1;
	private static final double GOODS_WEIGHT = 0;
	private static final double BOOS_WEIGHT = -4;
	private static final double MISSES_WEIGHT = -8;
	private static final double HOLDS_WEIGHT = 6;
	private boolean isCourseModeOn = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calculator_ddrextreme);
		ToggleButton tb = (ToggleButton) findViewById(R.id.courseModeToggle);
		EditText et = (EditText) findViewById(R.id.marvellouses);
		tb.setChecked(isCourseModeOn);
		et.setFocusable(isCourseModeOn);
		et.setFocusableInTouchMode(isCourseModeOn);
		et.setEnabled(isCourseModeOn);
	}
	
	public void calculateScore(View v) {
		// Dismiss keyboard
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

		int marvellouses, perfects, greats, goods, boos, misses, holds, totalHolds;
		
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
		try { totalHolds = Integer.valueOf(((EditText)findViewById(R.id.total_holds)).getText().toString());
		} catch (NumberFormatException e) { totalHolds = 0; }
		
		// Verify input has been submitted
		int[] stepCounts = { marvellouses, perfects, greats, goods, boos, misses, holds, totalHolds };
		int stepTotal = 0;
		for (int steps : stepCounts)
			stepTotal += steps;
		
		if (stepTotal != 0) {
			if (holds > totalHolds) {
				Toast.makeText(getApplicationContext(), R.string.error_invalid_holds, Toast.LENGTH_SHORT).show();
			} else {
                int[] judgements = { marvellouses, perfects, greats, goods, boos, misses, holds, totalHolds };
                double[] earnedScore = applyGradeWeights(isCourseModeOn, judgements);
				
				// Calculate potential score
				final double BEST_WEIGHT = (isCourseModeOn) ? MARVELLOUSES_WEIGHT : PERFECTS_WEIGHT;
				int[] imperfectSteps = { greats, goods, boos, misses, totalHolds-holds };
                double potentialScore = ((marvellouses + perfects + greats + goods + boos + misses) * BEST_WEIGHT)
                        + (totalHolds * HOLDS_WEIGHT);
                double potentialScoreForGrade = ((marvellouses + perfects + greats + goods + boos + misses) * PERFECTS_WEIGHT)
                        + (totalHolds * HOLDS_WEIGHT);

                double scorePercent = ((int)((sum(earnedScore) / potentialScore) * 10000) / 100.00);
                DecimalFormat df = new DecimalFormat("0.00");

				TextView vEarnedScoreValue = (TextView) findViewById(R.id.earned_score_value);
				vEarnedScoreValue.setText(sum(earnedScore) + "");
				TextView vPotentialScoreValue = (TextView) findViewById(R.id.potential_score_value);
				vPotentialScoreValue.setText(potentialScore + "");
				TextView vScorePercent = (TextView) findViewById(R.id.score_percent);
				vScorePercent.setText(df.format(scorePercent) + "%");
				TextView vScoreGrade = (TextView) findViewById(R.id.score_grade);
				vScoreGrade.setText(calculateGrade(isCourseModeOn, judgements, potentialScoreForGrade, imperfectSteps));
			}
		} else {
			Toast.makeText(getApplicationContext(), R.string.error_no_steps, Toast.LENGTH_SHORT).show();
		}
	}
	
	public String calculateGrade(boolean isCourseModeOn, int[] judgements, double potentialScore, int[] imperfectSteps) {
        judgements[1] += judgements[0];
        judgements[0] = 0;

        // Apply step weights
        double[] earnedScore = applyGradeWeights(isCourseModeOn, judgements);

        double percentScore = ((int)((sum(earnedScore) / potentialScore) * 10000) / 100.00);

		// imperfectSteps = {greats, goods, boos, misses, totalHolds-holds}
		boolean anyImperfectSteps = false;
				for (int step : imperfectSteps)
					if (step != 0)
						anyImperfectSteps = true;
				
		if (!anyImperfectSteps) {
			if (isCourseModeOn)
				if (percentScore == 100.0)
					return "(AAAA)";
				else
					return "(AAA)";
			else
				return "(AAA)";
		} else {
			if (percentScore >= 93.0)
				return "(AA)";
			else if (percentScore >= 80.0)
				return "(A)";
			else if (percentScore >= 65.0)
				return "(B)";
			else if (percentScore >= 45.0)
				return "(C)";
			else
				return "(D)";
		}
	}

    private double[] applyGradeWeights(boolean isCourseModeOn, int[] grades) {
        double[] earnedScore = new double[grades.length];
        if (isCourseModeOn)
            earnedScore[0] = isCourseModeOn ? grades[0] * MARVELLOUSES_WEIGHT : 0;
        earnedScore[1] = grades[1] * PERFECTS_WEIGHT;
        earnedScore[2] = grades[2] * GREATS_WEIGHT;
        earnedScore[3] = grades[3] * GOODS_WEIGHT;
        earnedScore[4] = grades[4] * BOOS_WEIGHT;
        earnedScore[5] = grades[5] * MISSES_WEIGHT;
        earnedScore[6] = grades[6] * HOLDS_WEIGHT;
        return earnedScore;
    }
	
	public void toggleCourseMode(View v) {
		isCourseModeOn = !isCourseModeOn;
		EditText marv = (EditText) findViewById(R.id.marvellouses);
		marv.setFocusable(isCourseModeOn);
		marv.setFocusableInTouchMode(isCourseModeOn);
		marv.setEnabled(isCourseModeOn);
		if (!isCourseModeOn) {
			marv.setText("");
		}
	}
	
	public void clearForm(View v) {
		((EditText) findViewById(R.id.marvellouses)).setText("");
		((EditText) findViewById(R.id.perfects)).setText("");
		((EditText) findViewById(R.id.greats)).setText("");
		((EditText) findViewById(R.id.goods)).setText("");
		((EditText) findViewById(R.id.boos)).setText("");
		((EditText) findViewById(R.id.misses)).setText("");
		((EditText) findViewById(R.id.holds)).setText("");
		((EditText) findViewById(R.id.total_holds)).setText("");

		((TextView)findViewById(R.id.earned_score_value)).setText(R.string.score_value_earned_default);
		((TextView)findViewById(R.id.potential_score_value)).setText(R.string.score_value_potential_default);
		((TextView)findViewById(R.id.score_percent)).setText(R.string.score_percent_default);
		((TextView)findViewById(R.id.score_grade)).setText(R.string.score_grade_default);
	}

    private static double sum(double[] ints) {
        int res = 0;
        for  (double i : ints)
            res += i;
        return res;
    }
	
}
