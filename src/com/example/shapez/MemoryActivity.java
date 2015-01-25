package com.example.shapez;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MemoryActivity extends Activity {
	
	int levelPicked;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_memory);
		
		levelPicked = -1; 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.memory, menu);
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
	
	public void lvl1(View view){
		levelPicked = 1; 
		memoryGame();
	}
	
	public void lvl2(View view){
		levelPicked = 2;
		memoryGame();
	}
	
	public void lvl3(View view){
		levelPicked = 3;
		memoryGame();
	}
	public void memoryGame(){
		Intent intent = new Intent(this, MemoryGame.class);
		Bundle extras = new Bundle();
		extras.putInt("level", levelPicked);
		intent.putExtras(extras);
		startActivity(intent);
	}
	
	public void toSurvival(View v){
		Intent in = new Intent(this, SurvivalActivity.class);
		startActivity(in);
	}
	
	public void backToPrevious(View v){
		Intent back = new Intent(this, MainActivity.class);
		startActivity(back); 
	}
	
}//Final Bracket
