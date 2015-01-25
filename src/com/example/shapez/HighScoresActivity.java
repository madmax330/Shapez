package com.example.shapez;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class HighScoresActivity extends Activity {

	
	SharedPreferences sharedPref;
	
	int classicHS, survivalHS;
	TextView classicHStext, survivalHStext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_high_scores);
		
		sharedPref = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
		
		classicHStext = (TextView) findViewById(R.id.classicHS);
		survivalHStext = (TextView) findViewById(R.id.survivalHS);
		
		survivalHS = sharedPref.getInt("survivalHighScore", 0);
		classicHS = sharedPref.getInt("high_score", 0);
		
		classicHStext.setText("Classic High Score: " + classicHS);
		survivalHStext.setText("Survival High Score: " + survivalHS);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.high_scores, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void toHome(View v){
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
}
