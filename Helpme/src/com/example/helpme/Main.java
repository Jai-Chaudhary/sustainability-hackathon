package com.example.helpme;

import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity implements OnClickListener{

	Button help;
	double latitudenew,longitudenew;
	double lattable,longtable;
	ArrayList<Double> distancec,distancep;
	String closestuseridc,closestuseridp,myuserid,myusername;
	private AlarmManager alarmMgr;
	private PendingIntent alarmIntent;
	TextView policeman,community,family;
	
	protected void onCreate(Bundle savedInstancestate)
	{
	super.onCreate(savedInstancestate);
	setContentView(R.layout.main);
	
	help=(Button)findViewById(R.id.help);
	help.setOnClickListener(this);
	distancec=new ArrayList<Double> ();
	distancep=new ArrayList<Double> ();
	policeman=(TextView)findViewById(R.id.Policeman);
	community=(TextView)findViewById(R.id.Community);
	family=(TextView)findViewById(R.id.Family);
	myuserid=ParseUser.getCurrentUser().getObjectId();
	myusername=ParseUser.getCurrentUser().getUsername();
	}
	
	
	
	public double Haversine(double lat,double lon,double lattable,double longtable)
	{
		 final int R = 6371; // Radius of the earth
	        Double lat1 = lat;
	        Double lon1 = lon;
	        Double lat2 = lattable;
	        Double lon2 = longtable;
	        Double latDistance = toRad(lat2-lat1);
	        Double lonDistance = toRad(lon2-lon1);
	        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + 
	                   Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) * 
	                   Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	        Double distance = R * c;
	       
	        return distance;
	        
	}
	
	private static Double toRad(Double value) {
        return value * Math.PI / 180;
    }
	
	private void sendSMS(String phoneNumber, String message)
    {        
		System.out.println("Phone number is"+phoneNumber);
		System.out.println("Message is"+message);
		SmsManager smsManager = SmsManager.getDefault();
		smsManager.sendTextMessage(phoneNumber, null, message, null, null);
    }    
	
	public void onClick(View v)
	{
		if (v==help)
		{
			  Toast.makeText(Main.this, "Help has been Called for!", Toast.LENGTH_LONG).show();    
				
			  ParseQuery<ParseObject> query2 = ParseQuery.getQuery("_User");
  	    	 
       	    // Retrieve the object by id
       	      query2.getInBackground(myuserid, new GetCallback<ParseObject>() {
       	      public void done(ParseObject communityo, ParseException e) {
       	        if (e == null) {
       	     
       	        String ename=communityo.get("Ename").toString();
       	        String enumber=communityo.get("Enumber").toString();
       	       
       	       
       	       String Message="FR: EMERGENCY! Please check the link below to see the location of " +myusername+ " out NOW and reach him/her to HELP. http://54.68.2.96/hackathon/bsr?id="+myuserid;
       	       sendSMS(enumber,Message);
       	      family.setText("Family Contact Informed: "+ename +"- "+enumber);
       	        }
       	        
       	        else
       	        {
       	        	System.out.println("error here"+e.toString());
       	        }
       	      }
       	    });
			  
			  
			 
			alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
			 Intent intent = new Intent(this, AlarmReceiver.class);
			 alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

			 // setRepeating() lets you specify a precise custom interval--in this case,
			 // 20 minutes.
			 alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
			         1000*60, alarmIntent);
			 
			 
		
		// To query all the Community men
		ParseQuery<ParseObject> query=ParseQuery.getQuery("Community");
	 	
	 	query.findInBackground(new FindCallback<ParseObject>() {
	 	    public void done(List<ParseObject> messages, ParseException e) {
	 	    	
	 	    	int size=messages.size();
	 	    	
	 	    	if(size==0)
	 	    	{Toast.makeText(Main.this, "No People in Community table found", Toast.LENGTH_LONG).show();
	 	    	
	 	    	}
	 	    	
	 	    	else
	  	        if (e == null) {
	 	        	
	  	        	for(int i=0;i<size;i++)
	  	        	{
	  	        		
	 	        	lattable=messages.get(i).getDouble("lat");
	 	        	longtable=messages.get(i).getDouble("long");
	 	        
	 	        	System.out.println("lat from table"+ lattable);
	 	        	System.out.println("long from table"+ longtable);
	 	        	
	 	        	double distancenew=Haversine(latitudenew,longitudenew,lattable,longtable);
	 	        	distancec.add(distancenew);
	  	        	} 
	  	        	
	  	        	double smallest=distancec.get(0);
	  	            int index=0;
	  	            
	  	        	for(int i=1;i<distancec.size();i++)
	  	        	{
	  	        		if(distancec.get(i)<smallest)
	  	        		{
	  	        			smallest=distancec.get(i);
	  	        			index=i;
	  	        		}
	  	        		
	  	        		
	  	        	}
	  	        	
	  	        	System.out.println(distancec);
	  	        	System.out.println(index);
	  	        	
	  	        	//User id of the closest community watcher-
	  	        	closestuseridc=messages.get(index).getString("userid");
	  	        	
	  	        	System.out.println("Closest community worker user id"+closestuseridc);
	  	        	

         	    	ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
         	    	 
         	    // Retrieve the object by id
         	      query.getInBackground(closestuseridc, new GetCallback<ParseObject>() {
         	      public void done(ParseObject communityo, ParseException e) {
         	        if (e == null) {
         	     
         	        String username=communityo.get("username").toString();
         	        String phonenumber=communityo.get("Pnumber").toString();
         	       System.out.println(phonenumber);
         	       
         	       String Message="CW: EMERGENCY! Please check the link below to see the location of " +myusername+ " out NOW and reach him/her to HELP. http://54.68.2.96/hackathon/bsr?id="+myuserid;
         	       sendSMS(phonenumber,Message);
         	      community.setText("Community Worker Informed: "+username +"- "+phonenumber);
         	        }
         	        
         	        else
         	        {
         	        	System.out.println("error here"+e.toString());
         	        }
         	      }
         	    });
         	     
	  	        	
	  	        	
	 	        } else {
	 	            Log.d("score", "Error: " + e.getMessage());
	 	        }
	 	    }
	 	}); // end of closest community watch code
	 	
	 	//start of finding closest police man code-
ParseQuery<ParseObject> query1=ParseQuery.getQuery("police");
	 	
	 	query1.findInBackground(new FindCallback<ParseObject>() {
	 	    public void done(List<ParseObject> messages1, ParseException e) {
	 	    	
	 	    	int size=messages1.size();
	 	    	
	 	    	if(size==0)
	 	    	{Toast.makeText(Main.this, "No People in Police table found", Toast.LENGTH_LONG).show();
	 	    	
	 	    	}
	 	    	
	 	    	else
	  	        if (e == null) {
	 	        	
	  	        	for(int i=0;i<size;i++)
	  	        	{
	  	        		
	 	        	lattable=messages1.get(i).getDouble("lat");
	 	        	longtable=messages1.get(i).getDouble("long");
	 	        
	 	        	
	 	        	System.out.println("lat from table police"+ lattable);
	 	        	System.out.println("long from table police"+ longtable);
	 	        	
	 	        	double distancenew=Haversine(latitudenew,longitudenew,lattable,longtable);
	 	        	distancep.add(distancenew);
	  	        	} 
	  	        	
	  	        	double smallest=distancep.get(0);
	  	            int index=0;
	  	            
	  	        	for(int i=1;i<distancep.size();i++)
	  	        	{
	  	        		if(distancep.get(i)<smallest)
	  	        		{
	  	        			smallest=distancep.get(i);
	  	        			index=i;
	  	        		}
	  	        		
	  	        		
	  	        	}
	  	        	
	  	        	System.out.println("distance of closest police man "+distancep);
	  	        	System.out.println("index of police man"+ index);
	  	        	
	  	        	//User id of the closest community watcher-
	  	        	System.out.println(" userid here "+messages1.get(1).getString("userid"));
	  	        	closestuseridp=messages1.get(index).getString("userid");
	  	        	System.out.println("Closest police officer user id is "+closestuseridp);
	  	        	
	  	      	ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
    	    	 
         	    // Retrieve the object by id
         	      query.getInBackground(closestuseridp, new GetCallback<ParseObject>() {
         	      public void done(ParseObject police, ParseException e) {
         	        if (e == null) {
         	     
         	        String username=police.get("username").toString();
         	        String phonenumber=police.get("Pnumber").toString();
         	       System.out.println(phonenumber);
         	       
         	       String Message="PM: EMERGENCY! Please check the link below to see the location of " +myusername+ " out NOW and reach him/her to HELP. http://54.68.2.96/hackathon/bsr?id="+myuserid;
         	       sendSMS(phonenumber,Message);
         	      policeman.setText("Police Officer Informed: "+username +"- "+phonenumber);
         	        }
         	        
         	        else
         	        {
         	        	System.out.println("error here"+e.toString());
         	        }
         	      }
         	    });
	  	        	
	  	        	
	  	        	
	  	        	
	  	        	
	 	        } else {
	 	            Log.d("score", "Error: " + e.getMessage());
	 	        }
	 	    }
	 	}); // end of closest police watch code
	 	
 	
	 	
		
		}
	}
}