package com.cs1635.tryhards.foodtrain;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class GroupActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final List<String> names = new ArrayList<String>();
	    ContentResolver cr = getContentResolver();
	    Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
	            null, null, null);
	    while (cur.moveToNext()) {
	    	 String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
		     names.add(name);
		    }
		    
		    cur.close();
		    
			final ArrayList<Boolean> clickedNames = new ArrayList<Boolean>();
			for(int i = 0; i < names.size(); i++){
				clickedNames.add(false);
			}
		setContentView(R.layout.activity_group);
		ListView lists = (ListView)findViewById(R.id.listView1);
		final LayoutInflater inflater =(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
		lists.setAdapter(b);
		lists.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				clickedNames.set(arg2, !clickedNames.get(arg2));
				b.notifyDataSetChanged();
			}
			
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_group, menu);
		return true;
	}

}
