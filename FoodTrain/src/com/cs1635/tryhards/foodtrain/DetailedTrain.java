package com.cs1635.tryhards.foodtrain;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class DetailedTrain extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailed_train);
		setState();
	}

	public void setState(){
		 Intent sender=getIntent();
		 String trainName = sender.getExtras().getString("name");
		 String trainDate = sender.getExtras().getString("date");
		 String trainTime = sender.getExtras().getString("time");
		 boolean isCreator = sender.getExtras().getBoolean("creator", true);
		 final TextView name = (TextView)findViewById(R.id.train_name);
		 final TextView date = (TextView)findViewById(R.id.dayTrainTextView);
		 final TextView time = (TextView)findViewById(R.id.timeTrainTextView);

		 date.setText("Day: " + trainDate);
		 time.setText("Time: " + trainTime);
		 name.setText(trainName);
		 
		findViewById(R.id.declineButton).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				DeclineClick();
			}
		});

		if(isCreator){
			final Button herp = (Button)findViewById(R.id.declineButton);
			herp.setText("Delete");
		}
		findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				CancelClick();
			}
		});
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_detailed_train, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void DeclineClick()
	{
		Intent resultIntent = new Intent();
		
		resultIntent.putExtra("position", getIntent().getExtras().getInt("position"));
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
