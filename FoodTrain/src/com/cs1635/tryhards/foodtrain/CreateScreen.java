package com.cs1635.tryhards.foodtrain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class CreateScreen extends FragmentActivity{
	TextView nameField;
	TextView locationField;
	TextView locationList;
	Button dateButton;
	Button contactButton;
	Button timeButton;
	private int returnYear;
	private int returnMonth;
	private int returnDay;
	
	private int returnHour;
	private int returnMinute;
	private ArrayList<String> locations;

	private TextView mDateDisplay;

	static final int DATE_DIALOG_ID = 0;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_layout);
		
		nameField = (TextView) findViewById(R.id.nameEditText);
		//dateField = (EditText) findViewById(R.id.dateEditText);
		locationField = (TextView) findViewById(R.id.locationEditText);
		locationList = (TextView) findViewById(R.id.locationsList);
		dateButton = (Button) findViewById(R.id.myDatePickerButton);
		timeButton = (Button) findViewById(R.id.Button01);
		contactButton = (Button) findViewById(R.id.contactButton);
		//make the listeners for the cancel and click buttons
		findViewById(R.id.cancelbutton).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				CancelClick();
			}
		});
		
		findViewById(R.id.createbutton).setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				CreateClick();
			}
		});
		
		dateButton.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	        	showDatePicker();
	        }
	    });
		
		timeButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showTimePicker();
				
			}
		});
		
		contactButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showContactPicker();
				
			}
		});
		
		findViewById(R.id.addLocationButton).setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	        	addLocationClick();
	        }
	    });

	    // Set to the current date so if the user doesn't choose it, it doesn't error :)
	    final Calendar c = Calendar.getInstance();
	    returnYear = c.get(Calendar.YEAR);
	    returnMonth = c.get(Calendar.MONTH);
	    returnDay = c.get(Calendar.DAY_OF_MONTH);
	    
	    locations = new ArrayList<String>();
	}
		    
		    
	private void showDatePicker() {
		DatePickerFragment datePicker = new DatePickerFragment();
		// Get current day, month, and year
		Calendar calender = Calendar.getInstance();
		Bundle bundle = new Bundle();
		bundle.putInt("year", calender.get(Calendar.YEAR));
		bundle.putInt("month", calender.get(Calendar.MONTH));
		bundle.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
		datePicker.setArguments(bundle);
		// Set callback
		datePicker.setCallBack(dateCallback);
		// Show the fragment
		datePicker.show(getSupportFragmentManager(), "Date Picker");
	}
	
	private void showTimePicker() {
		TimePickerFragment timePicker = new TimePickerFragment();
		// Get current day, month, and year
		Calendar calender = Calendar.getInstance();
		Bundle bundle = new Bundle();
		bundle.putInt("hour", calender.get(Calendar.HOUR_OF_DAY));
		bundle.putInt("minute", calender.get(Calendar.MINUTE));
		timePicker.setArguments(bundle);
		// Set callback
		timePicker.setCallBack(timeCallback);
		// Show the fragment
		timePicker.show(getSupportFragmentManager(), "Time Picker");
	}
	
	OnDateSetListener dateCallback = new OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			returnYear = year;
			returnDay = dayOfMonth;
			returnMonth = monthOfYear + 1;
			
			dateButton.setText(new StringBuilder().append(returnYear)
					.append("-").append(returnMonth)
					.append("-").append(returnDay));
			
			Toast.makeText(CreateScreen.this,String.valueOf(year) + "-" + String.valueOf(monthOfYear + 1)
			+ "-" + String.valueOf(dayOfMonth), Toast.LENGTH_SHORT).show();
		}
	};

	OnTimeSetListener timeCallback = new OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker arg0, int hourOfDay, int minute) {
			returnHour = hourOfDay;
			returnMinute = minute;
			
			int formattedHour = (hourOfDay < 12) ? hourOfDay : hourOfDay - 12;
			formattedHour = (hourOfDay == 0) ? hourOfDay + 12 : hourOfDay;
			String type = (hourOfDay <= 12) ? "am" : "pm";
			
			timeButton.setText(new StringBuilder().append(formattedHour)
					.append(":").append(minute)
					.append(" ").append(type));
		
		}
		
	};
	
	
	public void addLocationClick() {
		String location = locationField.getText().toString();
		
		if ((location == null) || (location.equals(""))) {
			// If there is nothing entered, toast!
			Toast.makeText(CreateScreen.this,"Enter a location!", Toast.LENGTH_SHORT).show();
		}
		else if (locations.contains(location)) {
			// If this location is already in the list, toast!
			Toast.makeText(CreateScreen.this,"Already in the list!", Toast.LENGTH_SHORT).show();
		}
		else {
			// Add the location to the list
			locations.add(location);
			String original = locationList.getText().toString();
			original += ("\n" + location);
			Log.i("FoodTrain", location);
			locationList.setText(original);
			locationField.setText("");
		}
		
	}
	
    private void showContactPicker() {
		List<String> names = new ArrayList<String>();
	    ContentResolver cr = getContentResolver();
	    Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
	            null, null, null);
	    while (cur.moveToNext()) {
	    	 String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
		     names.add(name);
		    }
		    
		    cur.close();
		DemoDialog contactPicker = new DemoDialog();
		// Get current day, month, and year
		Bundle bundle = new Bundle();
		bundle.putStringArrayList("names", (ArrayList<String>) names);
		contactPicker.setArguments(bundle);
		contactPicker.contactObserver = new OnContactSetListener(){

			@Override
			public void onContactSet(List<String> names) {
				if(names.size() > 0){
					String builtName = names.get(0);
					contactButton.setText("Invitees: " + builtName + ", ...");		
				}

			}
			
		};
		// Set callback
		// Show the fragment
		contactPicker.show(getSupportFragmentManager(), "Contact Picker");
	}

   
	
	public void CreateClick()
	{
		
		if (locations.size() < 1) {
			// If this location is already in the list, toast!
			Toast.makeText(CreateScreen.this,"Add at least one location!", Toast.LENGTH_SHORT).show();
		}
		else {
			Intent resultIntent = new Intent();
			
			resultIntent.putExtra("TRAIN-NAME", nameField.getText().toString());
			resultIntent.putExtra("YEAR", returnYear);
			resultIntent.putExtra("MONTH", returnMonth);
			resultIntent.putExtra("DAY", returnDay);
			resultIntent.putExtra("HOUR", returnHour);
			resultIntent.putExtra("MINUTE", returnMinute);
			resultIntent.putStringArrayListExtra("LOCATIONS", locations);
			
			setResult(Activity.RESULT_OK, resultIntent);
			
			finish();
		}
	}
	
	public void CancelClick()
	{
		Intent resultIntent = new Intent();
		// TODO Add extras or a data URI to this intent as appropriate.
		setResult(Activity.RESULT_CANCELED, resultIntent);
		finish();
	}

}
