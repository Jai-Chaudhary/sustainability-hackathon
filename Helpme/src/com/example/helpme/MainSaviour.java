package com.example.helpme;


import com.parse.Parse;
import com.parse.ParseUser;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainSaviour extends Activity {

	String userid,type;
	private AlarmManager alarmMgr;
	private PendingIntent alarmIntent;
	
	protected void onCreate(Bundle savedInstancestate)
	{
	super.onCreate(savedInstancestate);
	setContentView(R.layout.mainsaviour);

	Parse.initialize(this, "DaeYRB80R0WhaY3ICgIrFpxSCZRNF99HFTLRn6sA", "YfIOGj1lCdBcc6QPDuPVeK4VHhZKiiSKJU6YVTEs");
	
	userid=ParseUser.getCurrentUser().getObjectId();
	type=ParseUser.getCurrentUser().get("Type").toString();
	
	Toast.makeText(MainSaviour.this, userid+" "+type, Toast.LENGTH_LONG).show();    
		
	 alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
	 Intent intent = new Intent(this, AlarmReceiver.class);
	 alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

	 // setRepeating() lets you specify a precise custom interval--in this case,
	 // 20 minutes.
	 alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
	         1000*60, alarmIntent);
	
	}

}