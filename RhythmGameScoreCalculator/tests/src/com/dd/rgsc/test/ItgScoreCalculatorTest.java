package com.dd.rgsc.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.TextView;

import com.dd.rgsc.R;
import com.ddiehl.rgsc.ItgScoreCalculator;
import com.robotium.solo.Solo;

public class ItgScoreCalculatorTest extends
		ActivityInstrumentationTestCase2<ItgScoreCalculator> {

	private ItgScoreCalculator mItgScoreCalculator;
	private TextView mTestText;
	private Solo solo;

	public ItgScoreCalculatorTest() {
		super(ItgScoreCalculator.class);
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
