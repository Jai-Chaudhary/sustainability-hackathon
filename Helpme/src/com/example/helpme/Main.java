package com.example.helpme;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Main extends Activity implements OnClickListener{

	Button help;
	double latitudenew,longitudenew;
	
	protected void onCreate(Bundle savedInstancestate)
	{
	super.onCreate(savedInstancestate);
	setContentView(R.layout.main);
	
	help=(Button)findViewById(R.id.help);
	help.setOnClickListener(this);
	
	
	}
	
	public void onClick(View v)
	{
		if (v==help)
		{
			LocationManager lm = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

		    Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

		if(location != null){ 
		    latitudenew = location.getLatitude();
		    longitudenew = location.getLongitude();
		    Toast.makeText(Main.this, Double.toString(latitudenew)+" "+Double.toString(longitudenew), Toast.LENGTH_LONG).show();    
		}
		}
	}
}