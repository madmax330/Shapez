package com.example.shapez;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MemoryGame extends Activity {
	
	TextView triesLabel;
	ImageButton greenBtn, redBtn, blueBtn, yellowBtn; 
	Button goBtn, againBtn, nextBtn;
	Timer sequenceTimer, showAgainTimer, nullImgTimer;
	ImageView mainImageView;
	int level;
	int tries, randomInt,currentImageInt, arraySize, rate;
	Boolean gotIt, showAgain, doElse, takeInput;
	int[] solution;
	int[] userGuess;
	int i, j, a, checkCount;
	Random rand; 
	final int n = 4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_memory_game);
		
		triesLabel = (TextView) findViewById(R.id.triesLabel);
		greenBtn = (ImageButton) findViewById(R.id.greenBtn);
		redBtn = (ImageButton) findViewById(R.id.redBtn);
		blueBtn = (ImageButton) findViewById(R.id.blueBtn);
		yellowBtn = (ImageButton) findViewById(R.id.yellowBtn);
		
		goBtn = (Button) findViewById(R.id.goBtn);
		//Log.d(getPackageName(), goBtn != null ? "goBtn is not null!" : "goBtn is null!");
		againBtn = (Button) findViewById(R.id.againBtn);
		//Log.d(getPackageName(), againBtn != null ? "againBtn is not null!" : "againBtn is null!");
		nextBtn = (Button) findViewById(R.id.nextBtn);
		//Log.d(getPackageName(), nextBtn != null ? "nextBtn is not null!" : "nextBtn is null!");
		mainImageView = (ImageView) findViewById(R.id.sequenceView);
		
		mainImageView.setVisibility(View.INVISIBLE);
		
		sequenceTimer = new Timer();
		showAgainTimer = new Timer();
		nullImgTimer = new Timer();

		Intent Rintent = getIntent(); 
		Bundle extras = Rintent.getExtras();
		level = extras.getInt("level");
		
		solution = new int[10];
		userGuess = new int[10];
		i = 0;
		j = 0;
		a = 0;
		checkCount = 0;
		gotIt = false; 
		showAgain = true;
		doElse = true;
		takeInput = false;
		tries = 3; 
		randomInt = -1;
		currentImageInt = -1;
		rand = new Random(); 
		arraySize = 0;

		triesLabel.setText("Chances: " + tries);
		
		goBtn.setVisibility(View.VISIBLE);
		againBtn.setVisibility(View.GONE);
		nextBtn.setVisibility(View.GONE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.memory_game, menu);
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
	
	
	public void determineRate(){//determine rate according to level
		
		switch(level){
		case 1:
			arraySize = 3;
			rate = 250;
			break;
			
		case 2:
			arraySize = 5;
			rate = 500;
			break;
			
		case 3:
			arraySize = 7;
			rate = 750;
			break;
		}
		
	}//determineRate end
	
	public void showImages(View v){//initialize and go to show sequence
		
		goBtn.setVisibility(View.GONE);
		againBtn.setVisibility(View.VISIBLE);
		nextBtn.setVisibility(View.GONE);

		determineRate();
		doElse = true; 
		takeInput = true;
		sequenceTimer.schedule(new TimerTask(){
			@Override 
			public void run() {
				// TODO auto-generated method stub
				runOnUiThread(new Runnable(){
					public void run() {
						showSequence(); 
					}
					
				});
			}
		}, rate);
	}//showImages end
	
	public void showSequence(){//show random sequence
		mainImageView.setVisibility(View.VISIBLE);
		triesLabel.setText("Chances: " + tries);
		if(i<arraySize){
			randomInt = rand.nextInt(n) +1; 
			while(randomInt == currentImageInt){
				randomInt = rand.nextInt(n) +1;
			}
			currentImageInt = randomInt;
			switch (randomInt){
			case 1:
				mainImageView.setImageResource(R.drawable.yellow); 
				solution[i] = 1;
				break;
		
			case 2:
				mainImageView.setImageResource(R.drawable.green);
				solution[i] = 2;
				break; 
			
			case 3:
				mainImageView.setImageResource(R.drawable.red);
				solution[i] = 3;
				break; 
		
			case 4:
				mainImageView.setImageResource(R.drawable.blue);
				solution[i] = 4;
				break; 
			}
			i++;
			
			sequenceTimer.schedule(new TimerTask(){
				@Override 
				public void run() {
					// TODO auto-generated method stub
					runOnUiThread(new Runnable(){
						public void run() {
							showSequence(); 
						}
					
					});
				}
			}, rate);
		}//endif
		if(i>=arraySize){
			nullImgTimer.schedule(new TimerTask(){
				@Override 
				public void run() {
					// TODO auto-generated method stub
					runOnUiThread(new Runnable(){
						public void run() {
							showNullImage(); 
						}
					
					});
				}
			}, rate);
		}
		//mainImageView.setImageResource(R.drawable.transparent);
	}//showSequence end
	
	
	public void showAgainOnBtnPush(View v){
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
	public void showAgain(){//show sequence again
		
		
		if(a<arraySize){
			switch (solution[a]){
			case 1:
				mainImageView.setImageResource(R.drawable.yellow); 
				break;
		
			case 2:
				mainImageView.setImageResource(R.drawable.green);
				break; 
			
			case 3:
				mainImageView.setImageResource(R.drawable.red);
				break; 
		
			case 4:
				mainImageView.setImageResource(R.drawable.blue);
				break; 
			}
			a++;
			
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
		}//endif
		else{
			nullImgTimer.schedule(new TimerTask(){
				@Override 
				public void run() {
					// TODO auto-generated method stub
					runOnUiThread(new Runnable(){
						public void run() {
							showNullImage(); 
						}
					
					});
				}
			}, rate);
		}
		//mainImageView.setImageResource(R.drawable.transparent);
	}//showAgain end
	
	public void showNullImage(){
		mainImageView.setImageResource(R.drawable.questionmark);
	}
	
	public void yellowClicked(View v){
		if(takeInput){
			userGuess[j] = 1;
			j++;
			if(j>=arraySize){
				gotIt = checkSolution();
				reset();
			}
		}
	}//yellowCliked end
	
	public void greenClicked(View v){
		if(takeInput){
			userGuess[j] = 2;
			j++;
			if(j>=arraySize){
				gotIt = checkSolution();
				reset();
			}
		}
	}//greenClicked end

	public void redClicked(View v){
		if(takeInput){
			userGuess[j] = 3;
			j++;
			if(j>=arraySize){
				gotIt = checkSolution();
				reset();
			}
		}
	}//redClicked end

	public void blueClicked(View v){
		if(takeInput){
			userGuess[j] = 4; 
			j++;
			if(j>=arraySize){
				gotIt = checkSolution();
				reset();
			}
		}
		
	}//blueClicked end
	
	public Boolean checkSolution(){//see if solution
		while(checkCount<arraySize){
			if(solution[checkCount] != userGuess[checkCount]){
			return false; 	
			}
			checkCount++;
		}
		return true;
	}//checkSolution end
	
	
	public void reset(){//end game and continue to new sequence or try again on old sequence
		if(tries == 1 && !gotIt){//guessed wrong, no more guesses
			i = 0;
			j = 0;
			a = 0;
			checkCount = 0;
			gotIt = false;
			doElse = false; 
			takeInput = false;
			tries = 3; 
			triesLabel.setText("Chances: 0");
			Toast.makeText(MemoryGame.this, 
				    "No More Tries Left", Toast.LENGTH_SHORT).show();
			againBtn.setVisibility(View.GONE);
			nextBtn.setVisibility(View.VISIBLE);
		}
		if(gotIt){
			mainImageView.setImageResource(R.drawable.check);
			nextBtn.setVisibility(View.VISIBLE);
			againBtn.setVisibility(View.GONE);
			i = 0; 
			j = 0; 
			a = 0;
			checkCount = 0;
			gotIt = false;
			takeInput = false; 
			tries = 3;
			Toast.makeText(MemoryGame.this, 
				    "You Got It!", Toast.LENGTH_SHORT).show();
		}
		else{//guessed wrong, guess again
			if(tries > 1 && doElse){
				mainImageView.setImageResource(R.drawable.wrong);
				j = 0;
				checkCount = 0;
				gotIt = false;
				tries = tries - 1; 
				triesLabel.setText("Chances: " + tries);
				Toast.makeText(MemoryGame.this, 
				    "Try Again", Toast.LENGTH_SHORT).show();
			}
			
		} 
	}//reset end
	
	
	public void backToPrevious(View v){//back to previous view
		Intent back = new Intent(this, MemoryActivity.class);
		startActivity(back); 
	}//backToPrevious end
	
	
}//Final bracket
