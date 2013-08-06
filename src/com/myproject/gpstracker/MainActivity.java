package com.myproject.gpstracker;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.RequestWrapper;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class MainActivity extends Activity {
	GPSTracker gps;
	static final String KEY_DEVICEID = "deviceid";
	static final String KEY_EMAIL = "email";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Thread timer = new Thread(){
			public void run(){
				try {
					sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				finally{
					changeView();
				}
			}
		};
		timer.start();
		/*btnConnect = (Button) findViewById(R.id.btnConnect);
        ip = (EditText) findViewById(R.id.editText1);
        // show location button click event
		btnConnect.setOnClickListener(new View.OnClickListener() {
		    @Override
            public void onClick(View arg0) {        
                // create class object
                gps = new GPSTracker(MainActivity.this);
                
                // check if GPS enabled     
                if(gps.canGetLocation()){
                	String ipadd = ip.getText().toString();
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    String URL = "http://"+ipadd+"/map/setlocation.php?name=202&longitude="+longitude+"&lattitude="+latitude;
                    System.out.println(URL);
                    setdata(URL);
                    //new RequestTask().execute(URL); 
                     
                    // \n is for new line
                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();    
                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }
                 
            }
        });*/
	}
	private void changeView(){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String devId = prefs.getString(KEY_DEVICEID, "value");
		if(devId.equalsIgnoreCase("value")){
			Intent registerIntent = new Intent(this,RegisterActivity.class);
			//Intent registerIntent = new Intent("android.intent.action.REGISTER");
			startActivity(registerIntent);
		}
		else{
			Intent loginIntent = new Intent(this,LoginActivity.class);
			startActivity(loginIntent);
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}	 
}
