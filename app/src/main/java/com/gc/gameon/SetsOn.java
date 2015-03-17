package com.gc.gameon;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;

public class SetsOn extends Activity {
	private Switch setMusic, setTimer, setVibe;
    private int options;
	private MenuItem itemMenu;
	protected GameDB db = new GameDB(this, GameDB.TABLE_NAME, null, 1);

    protected SharedPreferences sharedPreferences;



    @Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_on);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        // addPreferencesFromResource(R.xml.preferences);


        setMusic = (Switch) findViewById(R.id.musicOn);
		setMusic.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				if (isChecked) {
                    SharedPreferences sharedPreferences = getSharedPreferences("Options", 0);
					Editor editor = sharedPreferences.edit();
					editor.putInt("Music Options", 1);
					editor.commit();
					setMusic.setChecked(true);

				} else {
                    SharedPreferences sharedPreferences = getSharedPreferences("Options", 0);
					Editor editor = sharedPreferences.edit();
					editor.putInt("Music Options", 0);
					editor.commit();
					setMusic.setChecked(false);
				}

			}
		});

		setVibe = (Switch) findViewById(R.id.vibeOn);
		setVibe.setOnCheckedChangeListener(new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
		boolean isChecked) {

		if(isChecked){
		SharedPreferences sharedPreferences = getSharedPreferences("Options", 0);
		Editor editor = sharedPreferences.edit();
		editor.putInt("Vibration Options", 1);
		editor.commit();
		setVibe.setChecked(true);
		}else{
		SharedPreferences sharedPreferences = getSharedPreferences("Options", 0);
		Editor editor = sharedPreferences.edit();
		editor.putInt("Vibration Options", 0);
		editor.commit();
		setVibe.setChecked(false);
		}

		}
		});

        checkPreference();
	}

    // setting previous preferences
    public void checkPreference()
    {
        sharedPreferences = getSharedPreferences("Options", 0);

        if (sharedPreferences.getInt("Music Options", 1) == 1) {
            setMusic.setChecked(true);
        } else{
            setMusic.setChecked(false);
        }

        if(sharedPreferences.getInt("Vibration Options",1) == 1){
            setVibe.setChecked(true);
        } else {
            setVibe.setChecked(false);
        }
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_connections, menu);
		itemMenu = menu.findItem(R.id.bridge);
		itemMenu.setTitle("HistOn");
		return true;
	}

	// response to menu click
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (((MenuItem) item).getItemId()) {
		case R.id.bridge:
			startActivity(new Intent(this, HistOn.class));
			finish();
			return true;
		default:
			return super.onOptionsItemSelected((MenuItem) item);
		}
	}

	public void deleteHistory(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(
				"Are you sure you would like to Delete your whole score?")
				.setPositiveButton("Nevermind",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

							}
						})
				.setNegativeButton("Delete",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// User cancelled the dialog
								db.deleteTable();
							}
						}).show();

	}
}
