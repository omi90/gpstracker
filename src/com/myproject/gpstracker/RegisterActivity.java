package com.myproject.gpstracker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	Button btnRegister,btnCancel;
	EditText devid,pass,mail ;
	static final String KEY_DEVICEID = "deviceid";
	static final String KEY_EMAIL = "email";
	RegisterActivity reg = this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		btnRegister = (Button) findViewById(R.id.btnregister);
		btnCancel = (Button) findViewById(R.id.btncancel);
		devid = (EditText) findViewById(R.id.devid);
		pass = (EditText) findViewById(R.id.pass);
		mail = (EditText) findViewById(R.id.email);
		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent loginIntent = new Intent("android.intent.action.LOGIN");
				startActivity(loginIntent);
			}
		});
		btnRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String devidtext = devid.getText().toString();
				String passtext = pass.getText().toString();
				String mailtext = mail.getText().toString();
				if(devidtext.equalsIgnoreCase(""))
					Toast.makeText(getApplicationContext(), "Please enter Device ID.", Toast.LENGTH_LONG).show();    
				else if(passtext.equalsIgnoreCase(""))
					Toast.makeText(getApplicationContext(), "Please enter Password.", Toast.LENGTH_LONG).show();    
				else if(mailtext.equalsIgnoreCase(""))
					Toast.makeText(getApplicationContext(), "Please enter Email Address.", Toast.LENGTH_LONG).show();    
				else{
					Resources r =  getResources();
					String URL = "http://"+r.getString(R.string.appip)+"/map/register.php?devid="+devidtext+"&password="+passtext+"&email="+mailtext;
                    System.out.println(URL);
                    if(setdata(URL)){
                    	saveRegisterState(devidtext, passtext, mailtext);
                    	Toast.makeText(getApplicationContext(), "Device Registered with ID : '"+devidtext+"'.", Toast.LENGTH_LONG).show();    
                    	Intent svcintent = new Intent(reg,LocationSetService.class);
                        reg.startService(svcintent);
                    	Intent loginIntent = new Intent("android.intent.action.LOGIN");
            			startActivity(loginIntent);
            			setServiceAlarm();
            		}
                    else
                    	Toast.makeText(getApplicationContext(), "Please enter someother Device ID.", Toast.LENGTH_LONG).show();    
    			}
			}
		});
	}
	private void saveRegisterState(String devidtext,String pass,String mailtext){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Editor ed = prefs.edit();
        ed.putString(KEY_DEVICEID, devidtext);
        ed.putString(KEY_EMAIL, mailtext);
        ed.commit();
    }
	private void setServiceAlarm() {
		Intent myAlarm = new Intent(getApplicationContext(), ReceiverStartService.class);
        PendingIntent recurringAlarm = PendingIntent.getBroadcast(getApplicationContext(),456329, myAlarm, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarms = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Calendar updateTime = Calendar.getInstance();
        long interval = 5000;
        alarms.setInexactRepeating(AlarmManager.RTC_WAKEUP, updateTime.getTimeInMillis(),interval, recurringAlarm);
        System.out.println("alarm set");
	}	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	private boolean setdata(String urlstr){
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.
                    ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy); 
            URL url = new URL(urlstr);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            if(readStream(con.getInputStream()))
            	return true;
            } catch (Exception e) {
            e.printStackTrace();
          }
        return false;
    }
    private boolean readStream(InputStream in) {
            BufferedReader reader = null;
            try {
              reader = new BufferedReader(new InputStreamReader(in));
              String line = "";
              while ((line = reader.readLine()) != null) {
                System.out.println(line);
                if(line.trim().replaceAll(" ", "").equalsIgnoreCase("registered"))
                	return true;
              }
            } catch (IOException e) {
              e.printStackTrace();
            } finally {
              if (reader != null) {
                try {
                  reader.close();
                } catch (IOException e) {
                  e.printStackTrace();
                  }
              }
            }
            return false;
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}
}
