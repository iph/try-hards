package com.cs1635.tryhards.foodtrain;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class TimePickerFragment extends DialogFragment {
	
	OnTimeSetListener ontimeSet;

	public TimePickerFragment() { }

	public void setCallBack(OnTimeSetListener ontime) {
		ontimeSet = ontime;
	}

	private int hour;
	private int minute;
	private boolean daytime;

	@Override
	public void setArguments(Bundle args) {
		super.setArguments(args);
		hour = args.getInt("hour");
		minute = args.getInt("minute");
		daytime = args.getBoolean("daytime");
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return new TimePickerDialog(getActivity(), ontimeSet, hour, minute, daytime);
	}

}
