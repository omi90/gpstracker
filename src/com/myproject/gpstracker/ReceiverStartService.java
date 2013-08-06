package com.myproject.gpstracker;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ReceiverStartService extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent==null || intent.getAction()==null){
			Intent startServiceIntent = new Intent(context, LocationSetService.class);
	        context.startService(startServiceIntent);
	        return;
		}
		setServiceAlarm(context);
	}
	private void setServiceAlarm(Context context) {
		Intent myAlarm = new Intent(context, ReceiverStartService.class);
        PendingIntent recurringAlarm = PendingIntent.getBroadcast(context, 456329, myAlarm, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarms = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        long interval = 5000;
        alarms.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, recurringAlarm);
	}
	
}
