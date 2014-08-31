package com.ddiehl.rgsc.com.ddiehl.rgsc.calculators;

import java.text.DecimalFormat;

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

public class Calculator_DDRSN2 extends Activity {
	private static final String TAG = Calculator_DDRSN2.class.getSimpleName();

	// TODO Check these weights
	private static final int MARVELLOUSES_WEIGHT = 3;
	private static final int PERFECTS_WEIGHT = 2;
	private static final int GREATS_WEIGHT = 1;
	private static final int GOODS_WEIGHT = 0;
	private static final int BOOS_WEIGHT = 0;
	private static final int MISSES_WEIGHT = 0;
	private static final int HOLDS_WEIGHT = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calculator_ddrsn2);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
	
	public void ddrexCalculateScore(View v) {
		// Dismiss keyboard
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
		
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
			if (holds > totalHolds) {
				Toast.makeText(getApplicationContext(), R.string.error_invalid_holds, Toast.LENGTH_SHORT).show();
			} else {
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

				TextView vEarnedScoreValue = (TextView) findViewById(R.id.earnedScoreValue);
				vEarnedScoreValue.setText(earnedScore + "");
				TextView vPotentialScoreValue = (TextView) findViewById(R.id.potentialScoreValue);
				vPotentialScoreValue.setText(potentialScore + "");
				TextView vScorePercent = (TextView) findViewById(R.id.scorePercent);
				vScorePercent.setText(df.format(scorePercent) + "%");
				TextView vScoreGrade = (TextView) findViewById(R.id.scoreGrade);
				vScoreGrade.setText(grade);
			}
		} else {
			Toast.makeText(getApplicationContext(), R.string.error_no_steps, Toast.LENGTH_SHORT).show();
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
		((EditText)findViewById(R.id.marvellouses)).setText("");
		((EditText)findViewById(R.id.perfects)).setText("");
		((EditText)findViewById(R.id.greats)).setText("");
		((EditText)findViewById(R.id.goods)).setText("");
		((EditText)findViewById(R.id.boos)).setText("");
		((EditText)findViewById(R.id.misses)).setText("");
		((EditText)findViewById(R.id.holds)).setText("");
		((EditText)findViewById(R.id.totalHolds)).setText("");
		
		((TextView)findViewById(R.id.earnedScoreValue)).setText("0");
		((TextView)findViewById(R.id.potentialScoreValue)).setText("0");
		((TextView)findViewById(R.id.scorePercent)).setText(R.string.score_percent_default);
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
