package com.dd.rgsc.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.TextView;

import com.dd.rgsc.R;
import com.ddiehl.rgsc.Activity_Calculator_InTheGroove;
import com.robotium.solo.Solo;

public class ItgScoreCalculatorTest extends
		ActivityInstrumentationTestCase2<Activity_Calculator_InTheGroove> {

	private Activity_Calculator_InTheGroove mItgScoreCalculator;
	private TextView mTestText;
	private Solo solo;

	public ItgScoreCalculatorTest() {
		super(Activity_Calculator_InTheGroove.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
		mTestText = (TextView) solo.getView(R.id.scorePercent);
	}

	@MediumTest
	public void testItgCalculator() {

	}

	@Override
	protected void tearDown() throws Exception {
		solo.sleep(5000);
		super.tearDown();
	}
}
