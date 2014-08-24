package com.ddiehl.rgsc;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dd.rgsc.R;

public class Activity_Main extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		ArrayList<String> list = new ArrayList<String>();
		list.add("In the Groove");
		list.add("DDR Extreme");
		list.add("DDR Supernova");
		list.add("DDR Supernova 2");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);
		ListView listView = (ListView) findViewById(R.id.game_menu_selector_view);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();
				switch (position) {
				case 0:
					intent = new Intent(view.getContext(),
							Activity_Calculator_InTheGroove.class);
					break;
				case 1:
					intent = new Intent(view.getContext(),
							Activity_Calculator_DDRExtreme.class);
					break;
				case 2:
					intent = new Intent(view.getContext(),
							Activity_Calculator_DDRSN1.class);
					break;
				case 3:
					intent = new Intent(view.getContext(),
							Activity_Calculator_DDRSN2.class);
					break;
				default:
					System.out.println("Error: No class configured for list item.");
				}
				startActivity(intent);
			}
		});
		listView.setAdapter(adapter);
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
