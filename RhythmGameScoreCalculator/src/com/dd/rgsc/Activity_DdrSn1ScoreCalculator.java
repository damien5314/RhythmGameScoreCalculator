package com.dd.rgsc;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Activity_DdrSn1ScoreCalculator extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_ddr_sn1_score_calculator);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
