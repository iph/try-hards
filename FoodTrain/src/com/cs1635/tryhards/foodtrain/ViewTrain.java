package com.cs1635.tryhards.foodtrain;

import android.app.Activity;
import android.os.Bundle;

//this is where a user can see a train they're invited to,
//and decide whether to go or not
public class ViewTrain extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_layout);
	}
}
