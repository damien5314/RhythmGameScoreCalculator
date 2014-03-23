package com.ddiehl.rgsc;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dd.rgsc.R;

public class GameMenuSelector extends Activity {
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu_selector);
        ArrayList<String> list = new ArrayList<String>();
        list.add("In the Groove");
        list.add("DDR Extreme");
        list.add("DDR Supernova 2");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
                android.R.layout.simple_list_item_1, list);
		ListView listView = (ListView) findViewById(R.id.game_menu_selector_view);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
		            int position, long id) {
				Intent i = new Intent();
				switch (position) {
				case 0:
					i = new Intent (view.getContext(), ItgScoreCalculator.class);
					break;
				case 1:
					i = new Intent(view.getContext(), DdrExScoreCalculator.class);
					break;
				case 2:
					i = new Intent(view.getContext(), DdrSn2ScoreCalculator.class);
					break;
				default:
					System.out.println("Error: No class configured for list item.");
				}
				startActivity(i);
			}
		});
		listView.setAdapter(adapter);
	}
}
