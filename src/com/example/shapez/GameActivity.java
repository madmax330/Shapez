package com.example.shapez;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends Activity {

	Timer imageTimer; 
	ImageView nextImage;
	ImageButton greenBtn, redBtn, blueBtn, yellowBtn; 
	Button startBtn;
	Random rand;
	int rate, currentImageInt, decrease, randInt;
	int userScore;
	Boolean something_pressed, gameOverDone; 
	TextView scoreNum;
	String scoreString;
	SharedPreferences sharedPref;
	SharedPreferences.Editor editor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		scoreNum = (TextView) findViewById(R.id.scoreNum);
		nextImage = (ImageView) findViewById(R.id.randomImageView);
		greenBtn = (ImageButton) findViewById(R.id.greenBtn);
		redBtn = (ImageButton) findViewById(R.id.redBtn);
		blueBtn = (ImageButton) findViewById(R.id.blueBtn);
		yellowBtn = (ImageButton) findViewById(R.id.yellowBtn);
		startBtn = (Button) findViewById(R.id.startBtn); 
		imageTimer = new Timer(); 
		currentImageInt = -1; 
		randInt = -1; 
		decrease = 0; 
		rate = 1000;
		userScore = 0;
		something_pressed = true; 
		gameOverDone = false;
		
		scoreString = String.valueOf(userScore);
		scoreNum.setText(scoreString);
		
		sharedPref = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
		editor = sharedPref.edit();
		
		nextImage.setVisibility(View.INVISIBLE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
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
	
	public void start_game(View view) {
		startBtn.setVisibility(View.GONE);
		
		imageTimer.schedule(new TimerTask(){
			@Override 
			public void run() {
				// TODO auto-generated method stub
				runOnUiThread(new Runnable(){
					public void run() {
						gameTime(); 
					}
					
				});
			}
		}, rate);
	}
	
	public void gameTime() {
		nextImage.setVisibility(View.VISIBLE);
		final int n = 4;
		if(something_pressed){
			rand = new Random(); 
			randInt = rand.nextInt(n) +1; 
		
			while(randInt == currentImageInt){
				randInt = rand.nextInt(n) +1;
			}
			if(randInt == 1){  
				nextImage.setImageResource(R.drawable.blue); 
			}
		
			if(randInt == 2){

				nextImage.setImageResource(R.drawable.yellow); 
			}
		
			if(randInt == 3){
		
				nextImage.setImageResource(R.drawable.green); 
			}
		
			if(randInt == 4){
			
				nextImage.setImageResource(R.drawable.red); 
			}
		
			currentImageInt = randInt; 
			decrease = 20;
			rate = rate - decrease; 
			something_pressed = false; 
		
			imageTimer.schedule(new TimerTask(){
				@Override 
				public void run() {
					// TODO auto-generated method stub
					runOnUiThread(new Runnable(){
						public void run() {
							gameTime(); 
						}
					
					});
				}
			}, rate);
		}//endif
		else{
			if(!gameOverDone){
				gameOver();
			}
		}
		
	}
	
	
	public void red_pressed(View v){
		if(randInt == 4){
			score(); 
		}
		else{
			gameOver();
		}
	}
	
	public void yellow_pressed(View v){
		if(randInt ==2){
			score();
		}
		else{
			gameOver();
		}
	}
	
	public void green_pressed(View v){
		if(randInt == 3){
			score();
		}
		else{
			gameOver();
		}
	}
	
	public void blue_pressed(View v){
		if (randInt == 1){
			score();
		}
		else {
			gameOver();
		}
	}
	
	public void score(){
		userScore = userScore + 1; 
		something_pressed = true; 
		scoreString = String.valueOf(userScore);
		scoreNum.setText(scoreString);
	}
	
	public void gameOver(){
		gameOverDone = true; 
		editor.putInt("returnInt", 1);
		editor.commit();
		Intent  intent = new Intent(this, GameOverActivity.class);
		Bundle extras = new Bundle();
		extras.putInt("theScore", userScore);
		intent.putExtras(extras);
		   startActivity(intent); 
	}
	
	
	
	
}//Final Bracket
