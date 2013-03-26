package com.cs1635.tryhards.foodtrain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
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
	
	
	EditText nameField;
	EditText dateField;
	TimePicker timeField;
	
	private int returnYear;
	private int returnMonth;
	private int returnDay;

	private TextView mDateDisplay;

	static final int DATE_DIALOG_ID = 0;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_layout);
		
		nameField = (EditText) findViewById(R.id.nameEditText);
		//dateField = (EditText) findViewById(R.id.dateEditText);
		timeField = (TimePicker) findViewById(R.id.timePicker);
		mDateDisplay = (TextView) findViewById(R.id.myDate);
		
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
		
		findViewById(R.id.myDatePickerButton).setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	        	showDatePicker();
	        }
	    });

	    // Set to the current date so if the user doesn't choose it, it doesn't error :)
	    final Calendar c = Calendar.getInstance();
	    returnYear = c.get(Calendar.YEAR);
	    returnMonth = c.get(Calendar.MONTH);
	    returnDay = c.get(Calendar.DAY_OF_MONTH);
	    
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

	OnDateSetListener dateCallback = new OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			returnYear = year;
			returnDay = dayOfMonth;
			returnMonth = monthOfYear + 1;
			
			mDateDisplay.setText(new StringBuilder().append(returnYear)
					.append("-").append(returnMonth)
					.append("-").append(returnDay));
			
			Toast.makeText(CreateScreen.this,String.valueOf(year) + "-" + String.valueOf(monthOfYear + 1)
			+ "-" + String.valueOf(dayOfMonth), Toast.LENGTH_SHORT).show();
		}
	};

	
	public void CreateClick()
	{
		Intent resultIntent = new Intent();
		
		resultIntent.putExtra("TRAIN-NAME", nameField.getText().toString());
		resultIntent.putExtra("YEAR", returnYear);
		resultIntent.putExtra("MONTH", returnMonth);
		resultIntent.putExtra("DAY", returnDay);
		resultIntent.putExtra("HOUR", timeField.getCurrentHour());
		resultIntent.putExtra("MINUTE", timeField.getCurrentMinute());
		
		setResult(Activity.RESULT_OK, resultIntent);

		finish();
	}
	
	public void CancelClick()
	{
		Intent resultIntent = new Intent();
		// TODO Add extras or a data URI to this intent as appropriate.
		setResult(Activity.RESULT_CANCELED, resultIntent);
		finish();
	}

}
