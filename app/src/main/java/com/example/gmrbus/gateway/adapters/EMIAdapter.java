package com.example.gmrbus.gateway.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gmrbus.R;
import com.example.gmrbus.gateway.dto.EMIOptionDTO;

import java.util.ArrayList;

public class EMIAdapter extends ArrayAdapter<EMIOptionDTO> {
	private Activity context;
	ArrayList<EMIOptionDTO> data = null;

	public EMIAdapter(Activity context, int resource,
			ArrayList<EMIOptionDTO> data) {
		super(context, resource, data);
		this.context = context;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) { 
		super.getView(position, convertView, parent);
		return getDropDownView(position, convertView, parent);
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) { 
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			row = inflater.inflate(R.layout.spinner_item, parent, false);
		}
		EMIOptionDTO emiOptionDTO = data.get(position);
		if (emiOptionDTO != null) { // Parse the data from each object and set it.
			TextView gtwName = (TextView) row.findViewById(R.id.item_value);
			gtwName.setText(emiOptionDTO.getGtwName());
		}
		return row;
	}
}