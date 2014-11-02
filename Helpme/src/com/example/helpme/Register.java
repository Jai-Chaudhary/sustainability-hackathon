package com.example.helpme;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity implements OnClickListener {
	
	EditText usernameet;
	EditText passwordet;
	EditText bloodtype;
	EditText ename,enumber;
	Button login,register;
	String username,password,bloodtypetext,enametext,enumbertext;
	
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registeractivity);
		
		
		//Registration fields
		  usernameet=(EditText) findViewById(R.id.usernameregister);
		  passwordet=(EditText) findViewById(R.id.passwordregister);
		  bloodtype=(EditText) findViewById(R.id.bloodtype);
		  ename=(EditText) findViewById(R.id.ename);
		  enumber=(EditText) findViewById(R.id.enumber);
		  
				
	      //register button
		  register=(Button) findViewById(R.id.register);
		  register.setOnClickListener(this);
				
				
		//parse initialized with key and app id
				
	     
	}
	
	public void onClick(View v)
	{
		if(v==register)
		{
			 Parse.initialize(this, "DaeYRB80R0WhaY3ICgIrFpxSCZRNF99HFTLRn6sA", "YfIOGj1lCdBcc6QPDuPVeK4VHhZKiiSKJU6YVTEs");
				
			  ParseUser user=new ParseUser();
			
			  username=usernameet.getText().toString();
			  password=passwordet.getText().toString();
			  bloodtypetext=bloodtype.getText().toString();
			  enametext=ename.getText().toString();
			  enumbertext=enumber.getText().toString();
			
			  user.setUsername(username);
			  user.setPassword(password);
			  user.put("Ename", enametext);
			  user.put("Enumber", enumbertext);
			  user.put("Bloodtype", bloodtypetext);
				
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
