package com.ddiehl.rgsc;

import java.text.DecimalFormat;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.rgsc.R;

public class Activity_Calculator_InTheGroove extends Activity {
	private static final String TAG = "Activity_Calculator_InTheGroove";

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
		setContentView(R.layout.activity_calculator_inthegroove);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	public void itgCalculateScore(View v) {
		Log.i(TAG, "Calculating ITG score.");
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
				System.out.println("Error: Holds > Total Holds");
				String text = "Error: Holds > Total Holds";
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(getApplicationContext(), text, duration);
				toast.show();
			} else if (rolls > totalRolls) {
				System.out.println("Error: Rolls > Total Rolls");
				String text = "Error: Rolls > Total Rolls";
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(getApplicationContext(), text, duration);
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
				Log.i(TAG, "Earned Score:    " + earnedScore);
				Log.i(TAG, "Potential Score: " + potentialScore);
				Log.i(TAG, "Score Percent:   " + df.format(scorePercent) + "%");

				TextView vEarnedScoreValue = (TextView) findViewById(R.id.earnedScoreValue);
				vEarnedScoreValue.setText(earnedScore + "");
				TextView vPotentialScoreValue = (TextView) findViewById(R.id.potentialScoreValue);
				vPotentialScoreValue.setText(potentialScore + "");
				TextView vScorePercent = (TextView) findViewById(R.id.scorePercent);
				vScorePercent.setText(df.format(scorePercent) + "%");
			}
		} else {
			System.out.println("Error: User did not input any steps");
			String text = "No steps entered";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(getApplicationContext(), text, duration);
			toast.show();
		}		
	}
	
	public void clearForm(View v) {
		EditText e;
		e = (EditText) findViewById(R.id.fantastics);
		e.setText("");
		e = (EditText) findViewById(R.id.excellents);
		e.setText("");
		e = (EditText) findViewById(R.id.greats);
		e.setText("");
		e = (EditText) findViewById(R.id.decents);
		e.setText("");
		e = (EditText) findViewById(R.id.wayoffs);
		e.setText("");
		e = (EditText) findViewById(R.id.misses);
		e.setText("");
		e = (EditText) findViewById(R.id.holds);
		e.setText("");
		e = (EditText) findViewById(R.id.totalHolds);
		e.setText("");
		e = (EditText) findViewById(R.id.mines);
		e.setText("");
		e = (EditText) findViewById(R.id.rolls);
		e.setText("");
		e = (EditText) findViewById(R.id.totalRolls);
		e.setText("");
		
		TextView tv;
		tv = (TextView) findViewById(R.id.earnedScoreValue);
		tv.setText(getString(R.string.itg_earnedScoreValue_default));
		tv = (TextView) findViewById(R.id.potentialScoreValue);
		tv.setText(getString(R.string.itg_potentialScoreValue_default));
		tv = (TextView) findViewById(R.id.scorePercent);
		tv.setText(getString(R.string.itg_score_percent_default));
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
	    case R.id.action_about_app:
	        Intent intent = new Intent(this, Activity_AboutApp.class);
	        startActivity(intent);
	        return true;
	    }
	    return false;
	}

}
