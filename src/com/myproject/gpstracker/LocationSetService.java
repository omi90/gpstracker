package com.myproject.gpstracker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class LocationSetService extends IntentService {
	GPSTracker gps;
	static final String KEY_DEVICEID = "deviceid";
	public LocationSetService() {
		super("Location Setter Service");
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onHandleIntent(Intent arg0) {
		gps = new GPSTracker(this);
        Resources r = getResources();
        System.out.println("in service");
        // check if GPS enabled     
        if(gps.canGetLocation()){
        	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    		String devId = prefs.getString(KEY_DEVICEID, "value");
    		String ipadd = r.getString(R.string.appip);
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            String URL = "http://"+ipadd+"/map/setlocation.php?name="+devId+"&longitude="+longitude+"&lattitude="+latitude;
            setdata(URL);
        }    
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
}
