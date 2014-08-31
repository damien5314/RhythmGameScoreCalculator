package com.ddiehl.rgsc;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ddiehl.rgsc.com.ddiehl.rgsc.calculators.Calculator_DDRExtreme;
import com.ddiehl.rgsc.com.ddiehl.rgsc.calculators.Calculator_DDRSN2;
import com.ddiehl.rgsc.com.ddiehl.rgsc.calculators.Calculator_IIDX;
import com.ddiehl.rgsc.com.ddiehl.rgsc.calculators.Calculator_InTheGroove;
import com.ddiehl.rgsc.com.ddiehl.rgsc.calculators.Calculator_PopN;

import java.util.ArrayList;

/**
 * TODO Extreme with marvellouses turned on calculates grading strangely.
 *      Grading should be BEST_WEIGHT = PERFECTS while % calculation can be done on the raw integers
 */
public class ModuleList extends Activity {
	private static final String TAG = ModuleList.class.getSimpleName();
    private ArrayList<CalculatorModule> mModuleList;
    private ModuleListArrayAdapter mListAdapter = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.module_list);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mModuleList = new ArrayList<CalculatorModule>();
        mModuleList.add(new CalculatorModule(getString(R.string.title_itg), Calculator_InTheGroove.class));
        mModuleList.add(new CalculatorModule(getString(R.string.title_ddrex), Calculator_DDRExtreme.class));
//        mModuleList.add(new CalculatorModule(getString(R.string.title_activity_ddr_sn1_score_calculator), Calculator_DDRSN1.class));
        mModuleList.add(new CalculatorModule(getString(R.string.title_ddrsn2), Calculator_DDRSN2.class));
        mModuleList.add(new CalculatorModule(getString(R.string.title_iidx), Calculator_IIDX.class));
        mModuleList.add(new CalculatorModule(getString(R.string.title_popn), Calculator_PopN.class));
        refresh();
	}

    private void refresh() {
        if (mListAdapter == null) {
            mListAdapter = new ModuleListArrayAdapter(this, R.layout.module_list_item, mModuleList);
            ListView listView = (ListView) findViewById(R.id.module_list);
            listView.setAdapter(mListAdapter);

            listView.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    CalculatorModule m = mModuleList.get(position);
                    startActivity(new Intent(view.getContext(), m.getModuleClass()));
                }
            });
        } else {
            mListAdapter.notifyDataSetChanged();
        }
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
	    switch(item.getItemId()) {
            case R.id.action_about:
                startActivity(new Intent(this, AboutApp.class));
                return true;
	    }
	    return false;
	}
}
