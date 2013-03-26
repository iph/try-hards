package com.cs1635.tryhards.foodtrain;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import android.util.Log;


public class Train {
	
	private String trainName;
	private String trainStatus;
	private Calendar trainDate;
	
	public Train (String name, Calendar date) {
		trainName = name;
		Log.w("FoodTrain", "Calendar: " + date);
		trainDate = date;
	}
	
	public Calendar getTrainDate() {
		return trainDate;
	}
	
	public void setTrainDate(Calendar date) {
		trainDate = date;
	}
	
	public String getTrainDay() {
		return new StringBuilder().append(trainDate.get(Calendar.YEAR))
		.append("-").append(Calendar.MONTH)
		.append("-").append(Calendar.DAY_OF_MONTH).toString();
	}
	
	public String getTrainTime() {
		return DateFormat.getTimeInstance().format(trainDate.getTime());
	}
	
	public String getTrainName() {
        return trainName;
    }
    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }
    public String getTrainStatus() {
        return trainStatus;
    }
    public void setTrainStatus(String trainStatus) {
        this.trainStatus = trainStatus;
    }
    
}
