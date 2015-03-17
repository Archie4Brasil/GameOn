package com.gc.gameon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ListOn extends Activity{
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_game_on);

	}

	public void onItemClick(View v) {
		// TODO Auto-generated method stub
		Intent on = new Intent(this, GameOnMain.class);

        TextView text = (TextView) v;
		if(text.getText().equals("Start Adding")) {
            on.putExtra("op", 0);
        } else if(text.getText().equals("Start Subtracting")) {
            on.putExtra("op", 1);
        } else if(text.getText().equals("Start Multiplying")) {
            on.putExtra("op", 2);
        } else {
			on.putExtra("op", 3);
		}

		startActivity(on);
	}

	// Inflate the menu; this adds items to the action bar if it is present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// response to menu click
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
		switch (((MenuItem) item).getItemId()) {
		case R.id.history:
			startActivity(new Intent(this, HistOn.class));
			return true;
		case R.id.settings:
			startActivity(new Intent(this, SetsOn.class));
			return true;
		default:
			return super.onOptionsItemSelected((MenuItem) item);
		}
	}

}
