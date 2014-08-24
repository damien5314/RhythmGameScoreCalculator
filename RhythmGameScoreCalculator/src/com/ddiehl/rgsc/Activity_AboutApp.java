package com.ddiehl.rgsc;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class Activity_AboutApp extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_app);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
}
