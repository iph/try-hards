package com.cs1635.tryhards.foodtrain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TrainScreen extends ListActivity {
	
	private ProgressDialog m_ProgressDialog = null; 
    private ArrayList<Train> m_trains = null;
    private TrainAdapter m_adapter;
    private Runnable viewTrains;
    
    final int CREATE_ACTIVITY = 0; //a way to identify activities
    //for switch statements
    final int GROUP_ACTIVITY = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_train_screen);
		
		// Create and set the TrainAdapter to deal with formatting a Train to the list
		m_trains = new ArrayList<Train>();
        this.m_adapter = new TrainAdapter(this, R.layout.row, m_trains);
        setListAdapter(this.m_adapter);
        
        // Create a runnable to populate the trains list
        viewTrains = new Runnable(){
        	@Override
        	public void run() {
            	getTrains();
            }
        };
        
        // Run the runnable :)
        Thread thread =  new Thread(null, viewTrains, "MagentoBackground");
        thread.start();
        
        // Progress dialog while stuff is loading
        m_ProgressDialog = ProgressDialog.show(TrainScreen.this, "Please wait...", "Retrieving data ...", true);
	}
	
	/**
	 * From the Android site:
	 * his method will be called when an item in the list is selected.
	 * Subclasses should override. Subclasses can call getListView().getItemAtPosition(position) if they need to access the data associated with the selected item.
	 * @param l	The ListView where the click happened
	 * @param v	The view that was clicked within the ListView
	 * @param position	The position of the view in the list
	 * @param id	The row id of the item that was clicked
	 */
	public void onListItemClick (ListView l, View v, int position, long id) {
		// TODO: Make it so this opens a new activity for dealing with Trains
		
		Log.w("TrainScreen", "List clicked!");
		Toast.makeText(getApplicationContext(),
				"Click Train Number " + position, Toast.LENGTH_SHORT)
				.show();
	}
	
	/**
	 * This handles when the Create Train button is clicked.
	 * Probably need to use it to call a new Activity!
	 * @param view View from which it was called?
	 */
	public void createAction(View view) {
		// TODO: Call some other activity or something :)
		// Need to create a train
		// Intent resultIntent;
		//Log.i("TrainScreen", "Create button clicked!");
		Intent createintent = new Intent(this, CreateScreen.class);
		//i'm using startActivityForResult so we can get data back, such as name,
		//choices, date, etc.
		startActivityForResult(createintent, CREATE_ACTIVITY);
		
		//Toast.makeText(TrainScreen.this, "You clicked the Create Train button!",Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * This handles when the Groups button is clicked.
	 * Probably need to use it to call a new Activity!
	 * @param view View from which it was called?
	 */
	public void groupsAction(View view) {
		// TODO: Call some other activity or something :)
		// Need to pull up the groups activity
		// Intent resultIntent;
		//Log.i("TrainScreen", "Groups button clicked!");
		Intent groupintent = new Intent(this, GroupScreen.class);
		startActivity(groupintent);
		
		//Toast.makeText(TrainScreen.this, "You clicked the Groups button!",Toast.LENGTH_SHORT).show();
	}
	
	//this is where you handle the results
	//from the create activity
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		  super.onActivityResult(requestCode, resultCode, data);
		  switch(requestCode) {
		    case (CREATE_ACTIVITY): {//for create button
		    	//clicked Create to make a new train
		      if (resultCode == Activity.RESULT_OK) {
		        // TODO Extract the data returned from the child Activity.
		    	  //add a new train
		    	  
		    	  Calendar cal = Calendar.getInstance();
		    	  
					cal.set(Calendar.YEAR, data.getIntExtra("YEAR", 0));
					cal.set(Calendar.MONTH, data.getIntExtra("MONTH", 0));
					cal.set(Calendar.DATE, data.getIntExtra("DAY", 0));
					cal.set(Calendar.HOUR_OF_DAY, data.getIntExtra("HOUR", 0));
					cal.set(Calendar.MINUTE, data.getIntExtra("MINUTE", 0));
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MILLISECOND, 0);
		    	  
		    	  Log.w("FoodTrain", "Calendar main screen: " + cal);
		    	  
		    	  Train t = new Train(data.getStringExtra("TRAIN-NAME"), cal);
		    	  //i.getStringExtra("date")
		    	  m_trains.add(t); //put it on the list of trains
		    	  runOnUiThread(returnRes); //refresh list of trains
		      }
		      //or they clicked Cancel to not make the train
		      else if (resultCode == Activity.RESULT_CANCELED)
		      {
		    	  
		      }
		      break;
		    } 
		  }
		}
	
	/**
	 * This is just a temporary method to load some fake trains so we can see what they look like :)
	 * It just creates fake trains, then waits for a second, then dismisses the ProgressDialog.
	 */
	private void getTrains() {
        try{
        	m_trains = new ArrayList<Train>();
        	
        	//we know it can display trains,
        	//so I temporarily disabled this
        	/*for (int i = 0;i < 5;i++) { //decided to change it from 20 to 5
        		Train o1 = new Train();
        		  o1.setTrainName("Train number " + i);
                  o1.setTrainStatus("Date" + i);
                  m_trains.add(o1);
        	}*/
          
            //Train o2 = new Train("Test Train", Calendar.getInstance());
            //m_trains.add(o2);
               Thread.sleep(1000);
            //Log.i("ARRAY", ""+ m_trains.size());
          } catch (Exception e) { 
            Log.e("BACKGROUND_PROC", e.getMessage());
          }
          runOnUiThread(returnRes);
      }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_train_list, menu);
		return true;
	}
	
	/**
	 * This will need to be changed.
	 * Right now it is only meant to run once.
	 * We need to create a Runnable that handles letting the TrainAdapter know 
	 * that a train has been added, update, or removed.
	 * 
	 */
	private Runnable returnRes = new Runnable() {

        @Override
        public void run() {
            if(m_trains != null && m_trains.size() > 0){
            	m_adapter.clear();
                m_adapter.notifyDataSetChanged();
                for(int i=0;i<m_trains.size();i++)
                m_adapter.add(m_trains.get(i));
            }
            m_ProgressDialog.dismiss();
            m_adapter.notifyDataSetChanged();
        }
      };
	
	/**
	 * Adapts the list of Trains to the list view.
	 * For more info on ArrayAdapters, see the Android site.
	 * @author Matt
	 *
	 */
	private class TrainAdapter extends ArrayAdapter<Train> {

		private ArrayList<Train> trains;
		
		public TrainAdapter(Context context, int textViewResourceId, ArrayList<Train> trains) {
	        super(context, textViewResourceId, trains);
	        this.trains = trains;
		}
		
		@Override
	    public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.row, null);
			}
			
			Train o = trains.get(position);
			if (o != null) {
				TextView trainname = (TextView) v.findViewById(R.id.trainname);
				TextView trainday = (TextView) v.findViewById(R.id.trainday);
				TextView traintime = (TextView) v.findViewById(R.id.traintime);
				
				if (trainname != null) {
					trainname.setText(o.getTrainName());
			     }
				if(trainday != null){
					trainday.setText(o.getTrainDay());
				}
				if (traintime != null) {
					traintime.setText(o.getTrainTime());
				}
			}
			
			return v;
		}
	}

}
