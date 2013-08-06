package com.myproject.gpstracker;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	Button btnLogin,btnCancel;
	EditText devid,pass;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		btnLogin = (Button) findViewById(R.id.btnlogin);
		btnCancel = (Button) findViewById(R.id.btncancel2);
		devid = (EditText) findViewById(R.id.devid2);
		pass = (EditText) findViewById(R.id.password);
		btnLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String devidtext = devid.getText().toString();
				String passtext = pass.getText().toString();
				if(devidtext.equalsIgnoreCase(""))
					Toast.makeText(getApplicationContext(), "Please enter Device ID.", Toast.LENGTH_LONG).show();    
				else if(passtext.equalsIgnoreCase(""))
					Toast.makeText(getApplicationContext(), "Please enter Password.", Toast.LENGTH_LONG).show();
				else
					Log.d("","login");
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
}
