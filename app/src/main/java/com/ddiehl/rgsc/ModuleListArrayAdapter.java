package com.ddiehl.rgsc;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ModuleListArrayAdapter extends ArrayAdapter<CalculatorModule> {
    private static final String TAG = ModuleListArrayAdapter.class.getSimpleName();
    private Context context;
    private int layoutResourceId;
    private ArrayList<CalculatorModule> data;

    public ModuleListArrayAdapter(Context c, int id, ArrayList<CalculatorModule> list_in) {
        super(c, id, list_in);
        context = c;
        layoutResourceId = id;
        data = list_in;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ItemHolder holder;
        CalculatorModule item = data.get(position);

        LayoutInflater inflater = ( (Activity) context ).getLayoutInflater();
        row = inflater.inflate(layoutResourceId, parent, false);

        holder = new ItemHolder();
        holder.itemName = (TextView) row.findViewById(R.id.itemName);
        holder.itemName.setText(item.getModuleName());

        row.setTag(holder);

        Log.d(TAG, "Item #" + position + " : " + item.getModuleName());
        return row;
    }

    private class ItemHolder {
        TextView itemName;
    }
}
