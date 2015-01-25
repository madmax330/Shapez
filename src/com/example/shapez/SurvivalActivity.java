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
import android.widget.Toast;

public class SurvivalActivity extends Activity {
	
	Timer newImageTimer, nullImageTimer, previousImagesTimer, showAgainTimer;
	TextView scoreLabel; 
	Button startBtn, nextBtn, againBtn;
	ImageButton yellowBtn, greenBtn, redBtn, blueBtn; 
	ImageView imageView, oneWrong, twoWrong, threeWrong; 
	Random randomImage;
	Boolean gotIt, takeInput;
	int currentImageInt, randomInt; 
	int rate; 
	final int n = 4;
	int[] sequence;
	int[] userAnswer;
	int sizeTracker, j, checkInt, currentPosition, entriesCount, userScore, chances, againInt; 
	String scoreString;
	
	SharedPreferences sharedPrefs;
	SharedPreferences.Editor editor;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_survival);
		
		scoreLabel = (TextView) findViewById(R.id.scoreLabel);
		startBtn = (Button) findViewById(R.id.startBtn);
		nextBtn = (Button) findViewById(R.id.nextBtn);
		againBtn = (Button) findViewById(R.id.againBtn);
		yellowBtn = (ImageButton) findViewById(R.id.yellowBtn);
		greenBtn = (ImageButton) findViewById(R.id.greenBtn);
		redBtn = (ImageButton) findViewById(R.id.redBtn);
		blueBtn = (ImageButton) findViewById(R.id.blueBtn);
		imageView = (ImageView) findViewById(R.id.imageView);
		oneWrong = (ImageView) findViewById(R.id.oneWrong);
		twoWrong = (ImageView) findViewById(R.id.twoWrong);
		threeWrong = (ImageView) findViewById(R.id.threeWrong);
		
		imageView.setVisibility(View.INVISIBLE);
		
		newImageTimer = new Timer();
		nullImageTimer = new Timer();
		previousImagesTimer = new Timer();
		showAgainTimer = new Timer();
		
		randomImage = new Random();
		
		sharedPrefs = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
		editor = sharedPrefs.edit();
		
		rate = 1000; 
		sizeTracker = 0;
		j = 0;
		checkInt = 0;
		currentPosition = 0;
		entriesCount = 0;
		userScore = 0;
		chances = 3;
		againInt = 0;
		scoreString = String.valueOf(userScore);
		
		gotIt = false;
		takeInput = false;
		
		sequence = new int[100];
		userAnswer = new int[100];
		for(int i = 0; i < 100; i++){
			sequence[i] = -1;
			userAnswer[i] = -1;
		}
		
		scoreLabel.setText(scoreString);
		
		nextBtn.setVisibility(View.GONE);
		againBtn.setVisibility(View.GONE);
		
		oneWrong.setVisibility(View.INVISIBLE);
		twoWrong.setVisibility(View.INVISIBLE);
		threeWrong.setVisibility(View.INVISIBLE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.survival, menu);
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
	
	public void startImage(View v){
		takeInput = true;
		startBtn.setVisibility(View.GONE);
		
		newImageTimer.schedule(new TimerTask(){
			@Override 
			public void run() {
				// TODO auto-generated method stub
				runOnUiThread(new Runnable(){
					public void run() {
						nextImage(); 
					}
					
				});
			}
		}, rate);
	}
	
	
	public void nextImage(){
		imageView.setVisibility(View.VISIBLE);
		againBtn.setVisibility(View.VISIBLE);
		sizeTracker++;
		gotIt = false;
		while(randomInt == currentImageInt){
			randomInt = randomImage.nextInt(n) +1;
		}
		currentImageInt = randomInt;
		switch (randomInt){
		case 1:
			imageView.setImageResource(R.drawable.yellow); 
			sequence[currentPosition] = 1; 
			break;
	
		case 2:
			imageView.setImageResource(R.drawable.green);
			sequence[currentPosition] = 2;
			break; 
		
		case 3:
			imageView.setImageResource(R.drawable.red);
			sequence[currentPosition] = 3;
			break; 
	
		case 4:
			imageView.setImageResource(R.drawable.blue);
			sequence[currentPosition] = 4;
			break; 
		}
		currentPosition++;
		
		nullImageTimer.schedule(new TimerTask(){
			@Override 
			public void run() {
				// TODO auto-generated method stub
				runOnUiThread(new Runnable(){
					public void run() {
						showNullImg(); 
					}
					
				});
			}
		}, rate);
	}
	
	public void showNullImg(){
		imageView.setImageResource(R.drawable.questionmark);
	}
	
	public void nextBtnTask(View v){
		takeInput = true;
		oneWrong.setVisibility(View.INVISIBLE);
		twoWrong.setVisibility(View.INVISIBLE);
		threeWrong.setVisibility(View.INVISIBLE);
		nextBtn.setVisibility(View.GONE);
		previousImagesTimer.schedule(new TimerTask(){
			@Override 
			public void run() {
				// TODO auto-generated method stub
				runOnUiThread(new Runnable(){
					public void run() {
						showPrevious(); 
					}
					
				});
			}
		}, rate);
		
	}
	
	public void showPrevious(){
		if(j < sizeTracker){
			switch(sequence[j]){
			case 1:
				imageView.setImageResource(R.drawable.yellow);
				break;
			
			case 2:
				imageView.setImageResource(R.drawable.green);
				break;
				
			case 3:
				imageView.setImageResource(R.drawable.red);
				break;
				
			case 4: 
				imageView.setImageResource(R.drawable.blue);
				break;
			}
			j++; 
			
			previousImagesTimer.schedule(new TimerTask(){
				@Override 
				public void run() {
					// TODO auto-generated method stub
					runOnUiThread(new Runnable(){
						public void run() {
							showPrevious(); 
						}
						
					});
				}
			}, rate);
		}//endif
		else{
			newImageTimer.schedule(new TimerTask(){
				@Override 
				public void run() {
					// TODO auto-generated method stub
					runOnUiThread(new Runnable(){
						public void run() {
							nextImage(); 
						}
					
					});
				}
			}, rate);
		}

	}
	
	public void againBtnTask(View v){
		againBtn.setVisibility(View.GONE);
		showAgainTimer.schedule(new TimerTask(){
			@Override 
			public void run() {
				// TODO auto-generated method stub
				runOnUiThread(new Runnable(){
					public void run() {
						showAgain(); 
					}
				
				});
			}
		}, rate);
	}
	
	public void showAgain(){
		if(againInt < sizeTracker){
			switch(sequence[againInt]){
			case 1:
				imageView.setImageResource(R.drawable.yellow);
				break;
			
			case 2:
				imageView.setImageResource(R.drawable.green);
				break;
				
			case 3:
				imageView.setImageResource(R.drawable.red);
				break;
				
			case 4: 
				imageView.setImageResource(R.drawable.blue);
				break;
			}
			againInt++; 
			
			previousImagesTimer.schedule(new TimerTask(){
				@Override 
				public void run() {
					// TODO auto-generated method stub
					runOnUiThread(new Runnable(){
						public void run() {
							showAgain(); 
						}
						
					});
				}
			}, rate);
		}//endif
		
		else{
			
			nullImageTimer.schedule(new TimerTask(){
				@Override 
				public void run() {
					// TODO auto-generated method stub
					runOnUiThread(new Runnable(){
						public void run() {
							showNullImg(); 
						}
						
					});
				}
			}, rate);
			
		}
	}
	
	public void checkAnswer(){
		gotIt = checkArray();
		chances--;
		
		if(gotIt){
			score();
			entriesCount = 0;
			checkInt = 0;
			j = 0;
			againInt = 0;
			chances = 3;
			nextBtn.setVisibility(View.VISIBLE);
			againBtn.setVisibility(View.GONE);
			imageView.setImageResource(R.drawable.check);
			Toast.makeText(SurvivalActivity.this, 
				    "You got it!", Toast.LENGTH_SHORT).show();
		}
		else{
			if(chances == 0){
				Toast.makeText(SurvivalActivity.this, 
					    "No More Tries Left", Toast.LENGTH_SHORT).show();
				imageView.setImageResource(R.drawable.wrong);
				showWrongs();
				gameOver();
			}
			else{
				takeInput = true;
				imageView.setImageResource(R.drawable.wrong);
				entriesCount = 0;
				checkInt = 0;
				showWrongs();
				Toast.makeText(SurvivalActivity.this, 
					    "Try Again", Toast.LENGTH_SHORT).show();
			}
		}
		
	}
	
	public Boolean checkArray(){
		while(checkInt<sizeTracker){
			if(userAnswer[checkInt] != sequence[checkInt]){
				return false;
			}
			checkInt++;
		}
		return true;
	}
	
	public void showWrongs(){
		if(3-chances == 2){
			twoWrong.setVisibility(View.VISIBLE);
		}
		if (3-chances == 1){
			oneWrong.setVisibility(View.VISIBLE);
		}
		if(3-chances == 3){
			threeWrong.setVisibility(View.VISIBLE);
		}
	}
	
	
	public void score(){
		userScore = userScore + 1;
		scoreString = String.valueOf(userScore);
		scoreLabel.setText(scoreString); 
	}
	public void gameOver(){
		editor.putInt("returnInt", 2);
		editor.commit();
		Intent intent = new Intent(this, GameOverActivity.class);
		Bundle extras = new Bundle();
		extras.putInt("survivalScore", userScore);
		intent.putExtras(extras);
		startActivity(intent);
	}
	
	public void yellowBtnClicked(View v){
		if(takeInput){
			userAnswer[entriesCount] = 1;
			entriesCount++;
			if(entriesCount == sizeTracker){
				takeInput = false;
				checkAnswer();
			}
		}
	}
	
	public void greenBtnClicked(View v){
		if(takeInput){
			userAnswer[entriesCount] = 2;
			entriesCount++;
			if(entriesCount == sizeTracker){
				takeInput = false;
				checkAnswer();
			}
		}
	}
	
	public void redBtnClicked(View v){
		if(takeInput){
			userAnswer[entriesCount] = 3;
			entriesCount++;
			if(entriesCount == sizeTracker){
				takeInput = false;
				checkAnswer();
			}
		}
	}
	
	public void blueBtnClicked(View v){
		if(takeInput){
			userAnswer[entriesCount] = 4;
			entriesCount++;
			if(entriesCount == sizeTracker){
				takeInput = false;
				checkAnswer();
			}
		}
	}
	
	public void backToPrevious(View v){
		Intent in = new Intent(this, MemoryActivity.class);
		startActivity(in);
	}
	
}//Final Bracket
