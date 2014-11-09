package com.ddiehl.rgsc;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AboutApp extends Activity {
    private final static String TAG = AboutApp.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_app);
        refresh();
	}

    public void refresh() {
        // Update version number and last update time
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            ((TextView) findViewById(R.id.versionNumberValue)).setText(info.versionName);
            ((TextView) findViewById(R.id.updateTimeValue)).setText(formatDate(info.lastUpdateTime));
        } catch (PackageManager.NameNotFoundException e) { Log.e(TAG, "Package name not found: " + getPackageName()); }
        
        // Populate data structure with changelog entries (List<String[]>)
        // TODO How can we grab all resources instead of adding them manually by ID?
        List<String[]> changeList = new ArrayList<String[]>();
        changeList.add(getResources().getStringArray(R.array.v1_1));
        changeList.add(getResources().getStringArray(R.array.v1_0));
        
        // Add new TableRows to TableLayout for each entry in changelog data structure
        TableLayout table = (TableLayout) findViewById(R.id.about_changelog);
        for (String[] change : changeList) {
            TableRow newRow = (TableRow) getLayoutInflater().inflate(R.layout.changelog_row, null);
            TextView vVersion = ((TextView) getLayoutInflater().inflate(R.layout.changelog_element_version, null));
            vVersion.setText(change[0]);
            TextView vUpdated = ((TextView) getLayoutInflater().inflate(R.layout.changelog_element_updated, null));
            vUpdated.setText(change[1]);
            TextView vChanges = ((TextView) getLayoutInflater().inflate(R.layout.changelog_element_changes, null));
            vChanges.setText(change[2]);
            newRow.addView(vVersion);
            newRow.addView(vUpdated);
            newRow.addView(vChanges);
            table.addView(newRow);
        }
    }
    
    private String formatDate(long utcTime) {
        SimpleDateFormat formatUTC = new SimpleDateFormat("dd MMMM yyyy");
        return formatUTC.format(new Date(utcTime));
    }
}
