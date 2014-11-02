package com.example.helpme;
import java.util.List;


import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver
{
	
	String userid,type;
	double latitudenew,longitudenew;
	String objectid;
         @Override
            public void onReceive(Context context, Intent intent)
            {
                    // TODO Auto-generated method stub
        	 Parse.initialize(context, "DaeYRB80R0WhaY3ICgIrFpxSCZRNF99HFTLRn6sA", "YfIOGj1lCdBcc6QPDuPVeK4VHhZKiiSKJU6YVTEs");
        	 
        	 LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

  		    Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

  		    if(location != null)
  		    { 
  		    latitudenew = location.getLatitude();
  		    longitudenew = location.getLongitude();
  		   Log.d("Added message", "Police or Community data added"); }
        	 
        	 userid=ParseUser.getCurrentUser().getObjectId();
        	 type=ParseUser.getCurrentUser().get("Type").toString();
        	 
        	 
        	 if(type.equalsIgnoreCase("Need Help"))
        	 {
        		 
        			ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
        	    	 
             	    // Retrieve the object by id
             	      query.getInBackground(userid, new GetCallback<ParseObject>() {
             	      public void done(ParseObject victim, ParseException e) {
             	        if (e == null) {
             	     
             	        	
             	          victim.put("lat",latitudenew);
             	          victim.put("long",longitudenew);
             	          victim.saveInBackground();
             	        }
             	        
             	        else
             	        {
             	        	System.out.println("Error inside need help is "+e.toString());
             	        }
             	      }
             	    });
        	 }
        	 
        	 if(type.equalsIgnoreCase("Police Officer"))
        	 {
        	ParseQuery<ParseObject> query=ParseQuery.getQuery("police");
         	query.whereEqualTo("userid", userid);
         	
         	query.findInBackground(new FindCallback<ParseObject>() {
         	public void done(List<ParseObject> messages, ParseException e) {
         	    	
         	    	
         	    	if(messages.size()!=0)
         	    	{
         	    	objectid=messages.get(0).getObjectId();
         	    	
         	    	ParseQuery<ParseObject> query = ParseQuery.getQuery("police");
         	    	 
         	    // Retrieve the object by id
         	      query.getInBackground(objectid, new GetCallback<ParseObject>() {
         	      public void done(ParseObject police, ParseException e) {
         	        if (e == null) {
         	     
         	          police.put("lat",latitudenew);
         	          police.put("long",longitudenew);
         	          police.saveInBackground();
         	        }
         	      }
         	    });
         	    	}
         	    	
         	    	else
         	    	{
         	    		ParseObject message=new ParseObject("police");
         				message.put("userid", userid);
         				message.put("lat",latitudenew);
         				message.put("long",longitudenew);
         				message.saveInBackground();
         	    	}
         	    		
         	    	
         	    }
         	});
        	 
        	 
        	 
        		
             }// end of if police officer
        	 
        	 else if (type.equalsIgnoreCase("Community Helper"))
        	 {
        		 ParseQuery<ParseObject> query=ParseQuery.getQuery("Community");
              	query.whereEqualTo("userid", userid);
              	
              	query.findInBackground(new FindCallback<ParseObject>() {
              	public void done(List<ParseObject> messages, ParseException e) {
              	    	
              	    	
              	    	if(messages.size()!=0)
              	    	{
              	    	objectid=messages.get(0).getObjectId();
              	    	
              	    	ParseQuery<ParseObject> query = ParseQuery.getQuery("Community");
              	    	 
              	    // Retrieve the object by id
              	      query.getInBackground(objectid, new GetCallback<ParseObject>() {
              	      public void done(ParseObject police, ParseException e) {
              	        if (e == null) {
              	     
              	          police.put("lat",latitudenew);
              	          police.put("long",longitudenew);
              	          police.saveInBackground();
              	        }
              	      }
              	    });
              	    	}
              	    	
              	    	else
              	    	{
              	    		ParseObject message=new ParseObject("Community");
              				message.put("userid", userid);
              				message.put("lat",latitudenew);
              				message.put("long",longitudenew);
              				message.saveInBackground();
              	    	}
              	    		
              	    	
              	    }
              	});
             	 
        	 }
        	 
        	 
            }
         
         
      
}