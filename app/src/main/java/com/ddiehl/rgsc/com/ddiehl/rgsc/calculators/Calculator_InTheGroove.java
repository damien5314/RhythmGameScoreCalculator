package com.ddiehl.rgsc.com.ddiehl.rgsc.calculators;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ddiehl.rgsc.R;

import java.text.DecimalFormat;

public class Calculator_InTheGroove extends Activity {
	private static final String TAG = Calculator_InTheGroove.class.getSimpleName();

	private static final int FANTASTICS_WEIGHT = 5;
	private static final int EXCELLENTS_WEIGHT = 4;
	private static final int GREATS_WEIGHT = 2;
	private static final int DECENTS_WEIGHT = 0;
	private static final int WAYOFFS_WEIGHT = -6;
	private static final int MISSES_WEIGHT = -12;
	private static final int HOLDS_WEIGHT = 5;
	private static final int MINES_WEIGHT = -6;
	private static final int ROLLS_WEIGHT = 5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calculator_inthegroove);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	public void itgCalculateScore(View v) {
		// Dismiss keyboard
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
		
		int earnedScore = 0;
		int potentialScore = 0;
		int fantastics, excellents, greats, decents, wayoffs, misses, holds, totalHolds, mines, rolls, totalRolls;

		// Read in all of the fields in the ITG score form
		try {
			Editable t = (((EditText) findViewById(R.id.fantastics)).getText());
			fantastics = Integer.valueOf(t.toString());
		} catch (NumberFormatException e) {
			fantastics = 0;
		}
		try {
			Editable t = (((EditText) findViewById(R.id.excellents)).getText());
			excellents = Integer.valueOf(t.toString());
		} catch (NumberFormatException e) {
			excellents = 0;
		}
		try {
			Editable t = (((EditText) findViewById(R.id.greats)).getText());
			greats = Integer.valueOf(t.toString());
		} catch (NumberFormatException e) {
			greats = 0;
		}
		try {
			Editable t = (((EditText) findViewById(R.id.decents)).getText());
			decents = Integer.valueOf(t.toString());
		} catch (NumberFormatException e) {
			decents = 0;
		}
		try {
			Editable t = (((EditText) findViewById(R.id.wayoffs)).getText());
			wayoffs = Integer.valueOf(t.toString());
		} catch (NumberFormatException e) {
			wayoffs = 0;
		}
		try {
			Editable t = (((EditText) findViewById(R.id.misses)).getText());
			misses = Integer.valueOf(t.toString());
		} catch (NumberFormatException e) {
			misses = 0;
		}
		try {
			Editable t = (((EditText) findViewById(R.id.holds)).getText());
			holds = Integer.valueOf(t.toString());
		} catch (NumberFormatException e) {
			holds = 0;
		}
		try {
			Editable t = (((EditText) findViewById(R.id.totalHolds)).getText());
			totalHolds = Integer.valueOf(t.toString());
		} catch (NumberFormatException e) {
			totalHolds = 0;
		}
		try {
			Editable t = (((EditText) findViewById(R.id.mines)).getText());
			mines = Integer.valueOf(t.toString());
		} catch (NumberFormatException e) {
			mines = 0;
		}
		try {
			Editable t = (((EditText) findViewById(R.id.rolls)).getText());
			rolls = Integer.valueOf(t.toString());
		} catch (NumberFormatException e) {
			rolls = 0;
		}
		try {
			Editable t = (((EditText) findViewById(R.id.totalRolls)).getText());
			totalRolls = Integer.valueOf(t.toString());
		} catch (NumberFormatException e) {
			totalRolls = 0;
		}
		
		// Verify input has been submitted
		int[] stepCounts = {fantastics, excellents, greats, decents, wayoffs, misses, holds, totalHolds, rolls, totalRolls};
		int stepTotal = 0;
		for (int i = 0; i < stepCounts.length; i++)
			stepTotal += stepCounts[i];
		
		if (stepTotal != 0) {
			if (holds > totalHolds) {
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(getApplicationContext(), R.string.error_invalid_holds, duration);
				toast.show();
			} else if (rolls > totalRolls) {
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(getApplicationContext(), R.string.error_invalid_rolls, duration);
				toast.show();
			} else {
				// Add number from each field multiplied by its weight to earned score
				earnedScore += fantastics * FANTASTICS_WEIGHT;
				earnedScore += excellents * EXCELLENTS_WEIGHT;
				earnedScore += greats * GREATS_WEIGHT;
				earnedScore += decents * DECENTS_WEIGHT;
				earnedScore += wayoffs * WAYOFFS_WEIGHT;
				earnedScore += misses * MISSES_WEIGHT;
				earnedScore += holds * HOLDS_WEIGHT;
				earnedScore += mines * MINES_WEIGHT;
				earnedScore += rolls * ROLLS_WEIGHT;

				// Calculate potential score
				potentialScore += ((fantastics + excellents + greats + decents
						+ wayoffs + misses) * FANTASTICS_WEIGHT)
						+ (totalHolds * HOLDS_WEIGHT) + (totalRolls * ROLLS_WEIGHT);

				// Calculate score percentage rounded to 2 decimal places
				double scorePercent = ((int) (((double) earnedScore / (double) potentialScore) * 10000) / 100.00);
				DecimalFormat df = new DecimalFormat("0.00");

				TextView vEarnedScoreValue = (TextView) findViewById(R.id.earnedScoreValue);
				vEarnedScoreValue.setText(earnedScore + "");
				TextView vPotentialScoreValue = (TextView) findViewById(R.id.potentialScoreValue);
				vPotentialScoreValue.setText(potentialScore + "");
				TextView vScorePercent = (TextView) findViewById(R.id.scorePercent);
				vScorePercent.setText(df.format(scorePercent) + "%");
				((TextView)this.findViewById(R.id.scoreGrade)).setText(calculateGrade(scorePercent));
			}
		} else {
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(getApplicationContext(), R.string.error_no_steps, duration);
			toast.show();
		}		
	}
	
	private String calculateGrade(double in) {
		double score = in;
		
		if (score == 100.0)
			return "(****)";
		else if (score > 99.0)
			return "(***)";
		else if (score > 98.0)
			return "(**)";
		else if (score > 96.0)
			return "(*)";
		else if (score > 94.0)
			return "(S+)";
		else if (score > 92.0)
			return "(S)";
		else if (score > 89.0)
			return "(S-)";
		else if (score > 86.0)
			return "(A+)";
		else if (score > 83.0)
			return "(A)";
		else if (score > 80.0)
			return "(A-)";
		else if (score > 76.0)
			return "(B+)";
		else if (score > 72.0)
			return "(B)";
		else if (score > 68.0)
			return "(B-)";
		else if (score > 64.0)
			return "(C+)";
		else if (score > 60.0)
			return "(C)";
		else if (score > 55.0)
			return "(C-)";
		else
			return "(D)";
	}
	
	public void clearForm(View v) {
		((EditText) findViewById(R.id.fantastics)).setText("");
		((EditText) findViewById(R.id.excellents)).setText("");
		((EditText) findViewById(R.id.greats)).setText("");
		((EditText) findViewById(R.id.decents)).setText("");
		((EditText) findViewById(R.id.wayoffs)).setText("");
		((EditText) findViewById(R.id.misses)).setText("");
		((EditText) findViewById(R.id.holds)).setText("");
		((EditText) findViewById(R.id.totalHolds)).setText("");
		((EditText) findViewById(R.id.mines)).setText("");
		((EditText) findViewById(R.id.rolls)).setText("");
		((EditText) findViewById(R.id.totalRolls)).setText("");
		
		((TextView) findViewById(R.id.earnedScoreValue)).setText("0");
		((TextView) findViewById(R.id.potentialScoreValue)).setText("0");
		((TextView) findViewById(R.id.scorePercent)).setText(R.string.score_percent_default);
		((TextView)findViewById(R.id.scoreGrade)).setText(R.string.score_grade_default);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
	    switch(item.getItemId()){
	    
	    }
	    return false;
	}

}
