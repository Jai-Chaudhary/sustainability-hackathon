package com.example.helpme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import com.parse.*;

public class Login extends Activity implements OnClickListener{

	EditText usernameet;
	EditText passwordet;
	Button login,register;
	String username,password;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginactivity);
	
		
		//username and password fields
		usernameet=(EditText) findViewById(R.id.username);
		passwordet=(EditText) findViewById(R.id.password);
		
		//login Button
		login=(Button) findViewById(R.id.login);
		login.setOnClickListener(this);
		
		//register button
		register=(Button) findViewById(R.id.register);
		register.setOnClickListener(this);
		
		
		//parse initialized with key and app id
		
		Parse.initialize(this, "DaeYRB80R0WhaY3ICgIrFpxSCZRNF99HFTLRn6sA", "YfIOGj1lCdBcc6QPDuPVeK4VHhZKiiSKJU6YVTEs");
			
		
	}
	
	
	public void onClick(View v)
	{
		if(v==register)
		{   
			Intent intent=new Intent(Login.this, Register.class);
			startActivity(intent);
			
		}
		
		if(v==login)		
		{
			
			username=usernameet.getText().toString();
			password=passwordet.getText().toString();
			
			ParseUser.logInInBackground(username, password, new LogInCallback(){
				
				public void done(ParseUser username,ParseException e)
				{
					if(e==null)
					{Toast.makeText(Login.this, "Username is "+username.getUsername().toString(), Toast.LENGTH_LONG).show();
					Intent intent=new Intent(Login.this, Map.class);
					startActivity(intent);
					}
					
					else
					{
					Toast.makeText(Login.this, "No Username or password as filled above", Toast.LENGTH_LONG).show();
							
						
					}
					
				}
			});
			
		}
		
	}
	
}
