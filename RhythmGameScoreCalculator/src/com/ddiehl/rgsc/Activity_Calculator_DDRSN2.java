package com.ddiehl.rgsc;

import java.text.DecimalFormat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import com.dd.rgsc.R;

public class Activity_Calculator_DDRSN2 extends Activity {

	private static final int MARVELLOUSES_WEIGHT = 3;
	private static final int PERFECTS_WEIGHT = 2;
	private static final int GREATS_WEIGHT = 1;
	private static final int GOODS_WEIGHT = 0;
	private static final int BOOS_WEIGHT = -4;
	private static final int MISSES_WEIGHT = -8;
	private static final int HOLDS_WEIGHT = 6;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculator_ddrsn2);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
	
	public void ddrexCalculateScore(View v) {
		// Dismiss keyboard
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
		
		System.out.println("Calculating DDR SN score...");
		int earnedScore = 0;
		int potentialScore = 0;
		int marvellouses, perfects, greats, goods, boos, misses, holds, totalHolds;
		
		// Read in all of the fields in the DDR score form
		try {
			Editable t = (((EditText)findViewById(R.id.marvellouses)).getText());
			marvellouses = Integer.valueOf(t.toString());	
		} catch (NumberFormatException e) { marvellouses = 0; }
		try {
			Editable t = (((EditText)findViewById(R.id.perfects)).getText());
			perfects = Integer.valueOf(t.toString());	
		} catch (NumberFormatException e) { perfects = 0; }
		try {
			Editable t = (((EditText)findViewById(R.id.greats)).getText());
			greats = Integer.valueOf(t.toString());	
		} catch (NumberFormatException e) { greats = 0; }
		try {
			Editable t = (((EditText)findViewById(R.id.goods)).getText());
			goods = Integer.valueOf(t.toString());	
		} catch (NumberFormatException e) { goods = 0; }
		try {
			Editable t = (((EditText)findViewById(R.id.boos)).getText());
			boos = Integer.valueOf(t.toString());	
		} catch (NumberFormatException e) { boos = 0; }
		try {
			Editable t = (((EditText)findViewById(R.id.misses)).getText());
			misses = Integer.valueOf(t.toString());	
		} catch (NumberFormatException e) { misses = 0; }
		try {
			Editable t = (((EditText)findViewById(R.id.holds)).getText());
			holds = Integer.valueOf(t.toString());	
		} catch (NumberFormatException e) { holds = 0; }
		try {
			Editable t = (((EditText)findViewById(R.id.totalHolds)).getText());
			totalHolds = Integer.valueOf(t.toString());	
		} catch (NumberFormatException e) { totalHolds = 0; }
		
		// Verify input has been submitted
		int[] stepCounts = {marvellouses, perfects, greats, goods, boos, misses, holds, totalHolds};
		int stepTotal = 0;
		for (int i = 0; i < stepCounts.length; i++)
			stepTotal += stepCounts[i];
		
		if (stepTotal != 0) {
			if (holds <= totalHolds) {
				// Add number from each field multiplied by its weight to earned score
				earnedScore += marvellouses * MARVELLOUSES_WEIGHT;
				earnedScore += perfects * PERFECTS_WEIGHT;
				earnedScore += greats * GREATS_WEIGHT;
				earnedScore += goods * GOODS_WEIGHT;
				earnedScore += boos * BOOS_WEIGHT;
				earnedScore += misses * MISSES_WEIGHT;
				earnedScore += holds * HOLDS_WEIGHT;
				
				// Calculate potential score
				int bestWeight = MARVELLOUSES_WEIGHT;
				int[] imperfectSteps = {greats, goods, boos, misses, totalHolds-holds};
				potentialScore += ((marvellouses + perfects + greats + goods + boos + misses) * bestWeight)
						+ (totalHolds * HOLDS_WEIGHT);
				
				// Calculate score percentage rounded to 2 decimal places
				double scorePercent = ((int)(((double)earnedScore / (double)potentialScore) * 10000) / 100.00);
				String grade = calculateGrade(scorePercent, imperfectSteps);
				DecimalFormat df = new DecimalFormat("0.00");
				System.out.println("Earned Score:    " + earnedScore);
				System.out.println("Potential Score: " + potentialScore);
				System.out.println("Score Percent:   " + df.format(scorePercent) + "%");
				System.out.println("Score Grade:     " + grade);

				TextView vEarnedScoreValue = (TextView) findViewById(R.id.earnedScoreValue);
				vEarnedScoreValue.setText(earnedScore + "");
				TextView vPotentialScoreValue = (TextView) findViewById(R.id.potentialScoreValue);
				vPotentialScoreValue.setText(potentialScore + "");
				TextView vScorePercent = (TextView) findViewById(R.id.scorePercent);
				vScorePercent.setText(df.format(scorePercent) + "%");
				TextView vScoreGrade = (TextView) findViewById(R.id.scoreGrade);
				vScoreGrade.setText(grade);
			} else {
				System.out.println("Error: Holds > TotalHolds.");
				String text = "Error: Holds > Total Holds";
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(getApplicationContext(), text, duration);
				toast.show();
			}
		} else {
			System.out.println("Error: User did not input any steps.");
			String text = "No steps entered.";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(getApplicationContext(), text, duration);
			toast.show();
		}
	}
	
	public String calculateGrade(double percentScore, int[] imperfectSteps) {
		// imperfectSteps = {greats, goods, boos, misses, totalHolds-holds}
		boolean anyImperfectSteps = false;
				for (int i = 0; i < imperfectSteps.length; i++)
					if (imperfectSteps[i] != 0)
						anyImperfectSteps = true;
				
		if (!anyImperfectSteps) {
			if (percentScore == 100.00)
				return "(AAAA)";
			else
				return "(AAA)";
		} else {
			if (percentScore >= 93.00)
				return "(AA)";
			else if (percentScore >= 80.00)
				return "(A)";
			else if (percentScore >= 65.00)
				return "(B)";
			else if (percentScore >= 45.00)
				return "(C)";
			else
				return "(D)";
		}
	}
	
	public void clearForm(View v) {
		EditText  e;
		e = (EditText) findViewById(R.id.marvellouses);
		e.setText("");
		e = (EditText) findViewById(R.id.perfects);
		e.setText("");
		e = (EditText) findViewById(R.id.greats);
		e.setText("");
		e = (EditText) findViewById(R.id.goods);
		e.setText("");
		e = (EditText) findViewById(R.id.boos);
		e.setText("");
		e = (EditText) findViewById(R.id.misses);
		e.setText("");
		e = (EditText) findViewById(R.id.holds);
		e.setText("");
		e = (EditText) findViewById(R.id.totalHolds);
		e.setText("");
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
