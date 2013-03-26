package com.cs1635.tryhards.foodtrain;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CreateScreen extends Activity{
	
	String name;
	String date;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_layout);
		
		EditText mEdit1 = (EditText)findViewById(R.id.namefield);	 
		name = (mEdit1.getText()).toString();
		
		EditText mEdit2 = (EditText)findViewById(R.id.datefield);	 
		date = (mEdit2.getText()).toString();
		
		//make the listeners for the cancel and click buttons
		findViewById(R.id.cancelbutton).setOnClickListener(new View.OnClickListener(){
				public void onClick(View v)
				{
					CancelClick();
				}
				});
		
		findViewById(R.id.createbutton).setOnClickListener(new View.OnClickListener(){
			public void onClick(View v)
			{
				CreateClick();
			}
			});
		
	}
	
	public void CreateClick()
	{
		Intent resultIntent = new Intent();
		// TODO Add extras or a data URI to this intent as appropriate.
		resultIntent.putExtra("name", name);
		//resultItnent.putExtra("choices", choices);
		
		resultIntent.putExtra("date", date);
		//resultIntent.putExtra("time", time);*/
		
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
