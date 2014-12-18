package com.gc.gameon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListOn extends Activity implements OnItemClickListener {
	private String[] options = { "Add On", "Sub On", "Multi On", "Div On" };

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_game_on);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, options);

		ListView mylist = (ListView) findViewById(R.id.listOn);
		mylist.setAdapter(adapter);

		mylist.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent on = new Intent(this, GameOnMain.class);

		switch (arg2) {
		case 0:
			on.putExtra("op", 0);
			break;
		case 1:
			on.putExtra("op", 1);
			break;
		case 2:
			on.putExtra("op", 2);
			break;
		case 3:
			on.putExtra("op", 3);
			break;
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
