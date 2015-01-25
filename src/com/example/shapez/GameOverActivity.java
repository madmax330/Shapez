package com.example.shapez;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class GameOverActivity extends Activity {
	
	TextView scoreTextView;

	int theScore, survivalScore, returnInt;
	int highScore;
	int survivalHS;
	//Context context = getActivity();
	SharedPreferences sharedPref;
	SharedPreferences.Editor editor;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_over);
		
		scoreTextView = (TextView) findViewById(R.id.scoreTextView);
		Intent Rintent = getIntent(); 
		Bundle extras = Rintent.getExtras();
		
		sharedPref = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
		editor = sharedPref.edit();
		
		returnInt = sharedPref.getInt("returnInt", -1);
		 highScore = sharedPref.getInt("high_score", -1);
		 survivalHS = sharedPref.getInt("survivalHighScore", -1);
		 
		 if(returnInt == 2){
				survivalScore = extras.getInt("survivalScore");
				scoreTextView.setText("Your score is: " + survivalScore);
			}
		if(returnInt == 1){
				theScore = extras.getInt("theScore");
				//theScore = intent.getDoubleExtra("theScore",0);
				scoreTextView.setText("Your score is: " + theScore);
		}
		 
		 if(survivalHS == -1){
			 editor.putInt("survivalHighScore", survivalHS);
			 editor.commit();
		 }
		
		if(highScore == -1){
				editor.putInt("high_score", theScore);
				editor.commit(); 
		}
			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_over, menu);
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
	
	
	public void backToGame(View v){
		if(returnInt == 2){
			if(survivalScore > survivalHS){
				editor.putInt("survivalHighScore", survivalScore);
				editor.commit();
			}
			Intent in = new Intent(this, SurvivalActivity.class);
			startActivity(in);
		}
		else{
			if(theScore > highScore){
				editor.putInt("high_score", theScore);
				editor.commit(); 
			}
			Intent  intent = new Intent(this, GameActivity.class);
			startActivity(intent); 
		}
		
	}
	
	public void toHome (View v){
		if(survivalScore > survivalHS){
			editor.putInt("survivalHighScore", survivalScore);
			editor.commit();
		}
		if(theScore > highScore){
			editor.putInt("high_score", theScore);
			editor.commit(); 
		}
		Intent  intent = new Intent(this, MainActivity.class);
		   startActivity(intent); 
	}
	
}
