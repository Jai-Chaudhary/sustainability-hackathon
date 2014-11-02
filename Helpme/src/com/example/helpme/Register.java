package com.example.helpme;

import java.util.ArrayList;
import java.util.List;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Register extends Activity implements OnClickListener {
	
	EditText usernameet;
	EditText passwordet;
	EditText ename,enumber,pnumber;
	Button login,register;
	String username,password,bloodtypetext,enametext,enumbertext,pnumbertext;
	private Spinner spinner1,spinner2;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registeractivity);
		 
		
		//Registration fields
		  usernameet=(EditText) findViewById(R.id.usernameregister);
		  passwordet=(EditText) findViewById(R.id.passwordregister);
		  spinner1=(Spinner) findViewById(R.id.spinner1);
		  spinner2 = (Spinner) findViewById(R.id.spinner2);
		  ename=(EditText) findViewById(R.id.ename);
		  pnumber=(EditText) findViewById(R.id.pnumber);
		  enumber=(EditText) findViewById(R.id.enumber);
		  
				
	      //register button
		  register=(Button) findViewById(R.id.register);
		  register.setOnClickListener(this);
				
				
		 
			List<String> list = new ArrayList<String>();
			list.add("Need Help");
			list.add("Police Officer");
			list.add("Community Helper");
			
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner2.setAdapter(dataAdapter);	
			
			List<String> list1 = new ArrayList<String>();
			list1.add("O-");
			list1.add("O+");
			list1.add("B-");
			list1.add("B+");
			list1.add("A-");
			list1.add("A+");
			list1.add("AB-");
			list1.add("AB+");
			
			ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list1);
			dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner1.setAdapter(dataAdapter1);
	     
	}
	
	public void onClick(View v)
	{
		if(v==register)
		{
			 Parse.initialize(this, "DaeYRB80R0WhaY3ICgIrFpxSCZRNF99HFTLRn6sA", "YfIOGj1lCdBcc6QPDuPVeK4VHhZKiiSKJU6YVTEs");
				
			  ParseUser user=new ParseUser();
			
			  username=usernameet.getText().toString();
			  password=passwordet.getText().toString();
			  bloodtypetext=String.valueOf(spinner1.getSelectedItem());
			  enametext=ename.getText().toString();
			  enumbertext=enumber.getText().toString();
			  pnumbertext=pnumber.getText().toString();
			
			  user.setUsername(username);
			  user.setPassword(password);
			  user.put("Ename", enametext);
			  user.put("Enumber", enumbertext);
			  user.put("Bloodtype", bloodtypetext);
			  user.put("Pnumber", pnumbertext);
			  user.put("Type",  String.valueOf(spinner2.getSelectedItem()));
				
			  user.signUpInBackground(new SignUpCallback(){
				
				public void done(ParseException e)
				{
					if(e==null)
					{Toast.makeText(Register.this, "Signed up", Toast.LENGTH_LONG).show();
					finish();
					Intent intent=new Intent(Register.this, Login.class);
					startActivity(intent);
					}
					
					else
					{
						Toast.makeText(Register.this, "Error:"+e.toString(), Toast.LENGTH_LONG).show();
					
					}
				}
			});
		}
	}

}
