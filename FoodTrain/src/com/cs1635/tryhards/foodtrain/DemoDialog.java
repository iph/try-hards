package com.cs1635.tryhards.foodtrain;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.DialogFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class DemoDialog extends DialogFragment {

	
	List<String> names;
	List<Boolean> clickedNames;
	public OnContactSetListener contactObserver;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setStyle(DialogFragment.STYLE_NORMAL, 0);

	}
	public void setArguments(Bundle args) {
		super.setArguments(args);
		names = args.getStringArrayList("names");
		clickedNames = new ArrayList<Boolean>();
		for(int i = 0; i < names.size(); i++){
			clickedNames.add(false);
		}
	}
	
	
	@Override
	public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LinearLayout herp = new LinearLayout(getActivity());
		herp.setOrientation(LinearLayout.VERTICAL);
		final ListView v = new ListView(getActivity());		
		final BaseAdapter b = new BaseAdapter() {
			
			@Override
			public View getView(final int position, View convertView, ViewGroup parent) {
				View rowView = null;
				if (convertView == null) {
					rowView = inflater.inflate(R.layout.menu_rowview, parent, false);
				}
				else
					rowView = convertView;
				TextView textView = (TextView) rowView.findViewById(R.id.textView1);
				textView.setText(names.get(position));
				if(clickedNames.get(position)){
					rowView.setBackgroundColor(Color.BLACK);
				}
				else{
					rowView.setBackgroundColor(Color.WHITE);

				}
				return rowView;
			}
			
			@Override public long getItemId(int arg0) { return arg0; }
			@Override public Object getItem(int arg0) { return names.get(arg0); }
			@Override public int getCount() { return 50; }
		};
		v.setAdapter(b);
		v.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				clickedNames.set(arg2, !clickedNames.get(arg2));
				b.notifyDataSetChanged();
			}
			
		});
		ViewGroup.LayoutParams fds = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 500);
		v.setLayoutParams(fds);
		v.setClickable(true);
		herp.addView(v);
		Button butt = new Button(getActivity());
		butt.setText("Done");
		final DemoDialog heeee = this;
		butt.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(contactObserver != null){
					List<String> checkedNames = new ArrayList<String>();
					for(int i = 0; i < clickedNames.size(); i++){
						if(clickedNames.get(i)){
							checkedNames.add(names.get(i));
						}
					}
					contactObserver.onContactSet(checkedNames);
				}
				heeee.dismiss();
			}
			
		});
		butt.setBackgroundColor(Color.WHITE);
		herp.addView(butt);
		return herp;
	}
}
