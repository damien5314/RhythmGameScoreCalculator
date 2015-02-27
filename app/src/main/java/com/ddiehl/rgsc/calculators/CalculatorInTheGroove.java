package com.ddiehl.rgsc.calculators;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ddiehl.rgsc.R;

import java.text.DecimalFormat;

public class CalculatorInTheGroove extends Fragment {
	private static final String TAG = CalculatorInTheGroove.class.getSimpleName();

	private static final int FANTASTICS_WEIGHT = 5;
	private static final int EXCELLENTS_WEIGHT = 4;
	private static final int GREATS_WEIGHT = 2;
	private static final int DECENTS_WEIGHT = 0;
	private static final int WAYOFFS_WEIGHT = -6;
	private static final int MISSES_WEIGHT = -12;
	private static final int HOLDS_WEIGHT = 5;
	private static final int MINES_WEIGHT = -6;
	private static final int ROLLS_WEIGHT = 5;
    private static final int BEST_WEIGHT = FANTASTICS_WEIGHT;
    
    private EditText mFantastics, mExcellents, mGreats, mDecents, mWayoffs, mMisses,
            mHolds, mTotalHolds, mMines, mRolls, mTotalRolls;
    private TextView mEarnedScoreValue, mPotentialScoreValue, mScorePercent, mScoreGrade;
    private ImageButton mClearButton;
    
    private TextWatcher mInputChangedListener = new TextWatcher() {
        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().equals("") || s.toString().equals("0"))
                return;

            calculateScore();
        }
    };

    public CalculatorInTheGroove() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.calculator_inthegroove, parent, false);

        mFantastics = (EditText) v.findViewById(R.id.fantastics);
        mExcellents = (EditText) v.findViewById(R.id.excellents);
        mGreats = (EditText) v.findViewById(R.id.greats);
        mDecents = (EditText) v.findViewById(R.id.decents);
        mWayoffs = (EditText) v.findViewById(R.id.wayoffs);
        mMisses = (EditText) v.findViewById(R.id.misses);
        mHolds = (EditText) v.findViewById(R.id.holds);
        mTotalHolds = (EditText) v.findViewById(R.id.total_holds);
        mMines = (EditText) v.findViewById(R.id.mines);
        mRolls = (EditText) v.findViewById(R.id.rolls);
        mTotalRolls = (EditText) v.findViewById(R.id.total_rolls);
        mEarnedScoreValue = (TextView) v.findViewById(R.id.earned_score_value);
        mPotentialScoreValue = (TextView) v.findViewById(R.id.potential_score_value);
        mScorePercent = (TextView) v.findViewById(R.id.score_percent);
        mScoreGrade = (TextView) v.findViewById(R.id.score_grade);
        mClearButton = (ImageButton) v.findViewById(R.id.clear_form);

        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearForm();
            }
        });

        // Set listener on each field to recalculate score
        mFantastics.addTextChangedListener(mInputChangedListener);
        mExcellents.addTextChangedListener(mInputChangedListener);
        mGreats.addTextChangedListener(mInputChangedListener);
        mDecents.addTextChangedListener(mInputChangedListener);
        mWayoffs.addTextChangedListener(mInputChangedListener);
        mMisses.addTextChangedListener(mInputChangedListener);
        mHolds.addTextChangedListener(mInputChangedListener);
        mTotalHolds.addTextChangedListener(mInputChangedListener);
        mMines.addTextChangedListener(mInputChangedListener);
        mRolls.addTextChangedListener(mInputChangedListener);
        mTotalRolls.addTextChangedListener(mInputChangedListener);
        
        return v;
    }

	public void calculateScore() {
		int earnedScore = 0;
		int potentialScore = 0;
		int fantastics = 0, excellents = 0, greats = 0, decents = 0, wayoffs = 0, misses = 0, 
                holds = 0, totalHolds = 0, mines = 0, rolls = 0, totalRolls = 0;

		// Read in all of the fields in the form
		try {
            fantastics = Integer.valueOf(mFantastics.getText().toString());
            excellents = Integer.valueOf(mExcellents.getText().toString());
            greats = Integer.valueOf(mGreats.getText().toString());
            decents = Integer.valueOf(mDecents.getText().toString());
            wayoffs = Integer.valueOf(mWayoffs.getText().toString());
            misses = Integer.valueOf(mMisses.getText().toString());
            holds = Integer.valueOf(mHolds.getText().toString());
            totalHolds = Integer.valueOf(mTotalHolds.getText().toString());
            mines = Integer.valueOf(mMines.getText().toString());
            rolls = Integer.valueOf(mRolls.getText().toString());
            totalRolls = Integer.valueOf(mTotalRolls.getText().toString());
		} catch (NumberFormatException e) {
            Log.e(TAG, "Invalid input: ", e);
        }
		
		// Verify input has been submitted
		int stepTotal = fantastics + excellents + greats + decents + wayoffs + misses + holds + totalHolds + rolls + totalRolls;
		
		if (stepTotal != 0) {
			if (holds > totalHolds) {
                mHolds.setError(getString(R.string.error_invalid_holds));
                setErrors();
                return;
			} else if (rolls > totalRolls) {
                mRolls.setError(getString(R.string.error_invalid_holds));
                setErrors();
                return;
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
				potentialScore += ((fantastics + excellents + greats + decents + wayoffs + misses) * BEST_WEIGHT)
						+ (totalHolds * HOLDS_WEIGHT) + (totalRolls * ROLLS_WEIGHT);

				// Calculate score percentage rounded to 2 decimal places
				double scorePercent = ((int) (((double) earnedScore / (double) potentialScore) * 10000) / 100.00);
				DecimalFormat df = new DecimalFormat("0.00");

				mEarnedScoreValue.setText(earnedScore + "");
				mPotentialScoreValue.setText(potentialScore + "");
				mScorePercent.setText(df.format(scorePercent) + "%");
				mScoreGrade.setText(calculateGrade(scorePercent));
			}
		} else {
			Toast.makeText(getActivity(), R.string.error_no_steps, Toast.LENGTH_SHORT).show();
		}		
	}
	
	private String calculateGrade(double score) {
		if (score == 100.0) return "(****)";
		else if (score > 99.0) return "(***)";
		else if (score > 98.0) return "(**)";
		else if (score > 96.0) return "(*)";
		else if (score > 94.0) return "(S+)";
		else if (score > 92.0) return "(S)";
		else if (score > 89.0) return "(S-)";
		else if (score > 86.0) return "(A+)";
		else if (score > 83.0) return "(A)";
		else if (score > 80.0) return "(A-)";
		else if (score > 76.0) return "(B+)";
		else if (score > 72.0) return "(B)";
		else if (score > 68.0) return "(B-)";
		else if (score > 64.0) return "(C+)";
		else if (score > 60.0) return "(C)";
		else if (score > 55.0) return "(C-)";
		else return "(D)";
	}
	
	public void clearForm() {
		mFantastics.setText("");
		mExcellents.setText("");
		mGreats.setText("");
		mDecents.setText("");
		mWayoffs.setText("");
		mMisses.setText("");
		mHolds.setText("");
		mTotalHolds.setText("");
		mMines.setText("");
		mRolls.setText("");
		mTotalRolls.setText("");
		
		mEarnedScoreValue.setText(R.string.score_value_earned_default);
		mPotentialScoreValue.setText(R.string.score_value_potential_default);
		mScorePercent.setText(R.string.score_percent_default);
		mScoreGrade.setText(R.string.score_grade_default);
	}

    private void setErrors() {
        mEarnedScoreValue.setText(getString(R.string.earned_score_error));
        mPotentialScoreValue.setText(getString(R.string.potential_score_error));
        mScorePercent.setText("");
        mScoreGrade.setText("");
    }

}
