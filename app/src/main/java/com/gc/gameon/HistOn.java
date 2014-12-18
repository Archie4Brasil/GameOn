package com.gc.gameon;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteCursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;

public class HistOn extends Activity {

	private SimpleCursorAdapter adapter;
	private static final String DATABASE_NAME = "db";
	private MenuItem itemMenu;
	private GameDB db;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history_on);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		// simpler way to run DBAdapter
		loadDisplay();

		// first call to context menu
		registerForContextMenu(listView);
		// listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		//
		// listView.setMultiChoiceModeListener(new MultiChoiceModeListener() {
		// int countItems = 0;
		//
		// @Override
		// public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		// // TODO Auto-generated method stub
		// return false;
		// }
		//
		// @Override
		// public void onDestroyActionMode(ActionMode mode) {
		// // TODO Auto-generated method stub
		// }
		//
		// @Override
		// public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		// // TODO Auto-generated method stub
		// MenuInflater inflater = getMenuInflater();
		// inflater.inflate(R.menu.context, menu);
		// return true;
		// }
		//
		// @Override
		// public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		// // TODO Auto-generated method stub
		// // Respond to clicks on the actions in the CAB
		// switch (item.getItemId()) {
		// case R.id.item_delete:
		// deleteSelectedItems();
		// mode.finish(); // Action picked, so close the CAB
		// return true;
		// default:
		// return false;
		// }
		//
		// }
		//
		// @Override
		// public void onItemCheckedStateChanged(ActionMode mode,
		// int position, long id, boolean checked) {
		// // TODO Auto-generated method stub
		// if (checked) {
		// countItems++;
		// adapter.setNewSelection(position, checked);
		// } else {
		// countItems--;
		// adapter.removeSelection(position);
		// }
		// mode.setTitle(countItems + " selected");
		//
		// }
		// });
		//
		// listView.setOnItemLongClickListener(new OnItemLongClickListener() {
		//
		// public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
		// int pos, long id) {
		// // TODO Auto-generated method stub
		//
		// Log.i("long clicked","pos: " + pos);
		//
		// SQLiteCursor cursor = (SQLiteCursor) arg0.getItemAtPosition(pos);
		//
		// deleteHistory(Integer.parseInt(cursor.getInt(0)+""));
		//
		// return true;
		// }
		// });
		//
	}

	public void loadDisplay() {
		db = new GameDB(this, DATABASE_NAME, null, 1);
		SQLiteCursor constantsCursor = (SQLiteCursor) db.getReadableDatabase()
				.rawQuery(
						"SELECT _ID, " + GameDB.TIME + ", " + GameDB.ROUNDS
								+ ", " + GameDB.TRIES + ", " + GameDB.CORRECT
								+ ", " + GameDB.MISSES + ", "
								+ GameDB.PERCENTAGE + " FROM "
								+ GameDB.TABLE_NAME + " ORDER BY "
								+ GameDB.TIME + " DESC, " + GameDB.ROUNDS
								+ " DESC", null);

		adapter = new SimpleCursorAdapter(this, R.layout.row, constantsCursor,
				new String[] { GameDB.ID, GameDB.ROUNDS, GameDB.TRIES,
						GameDB.CORRECT, GameDB.MISSES, GameDB.PERCENTAGE },
				new int[] { R.id.id, R.id.rounds, R.id.tries, R.id.correct,
						R.id.misses, R.id.percentage }, 0);

		listView = (ListView) findViewById(R.id.listView1);
		listView.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_connections, menu);
		itemMenu = menu.findItem(R.id.bridge);
		itemMenu.setTitle("SetsOn");
		return true;
	}

	// response to menu click
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (((MenuItem) item).getItemId()) {
		case R.id.bridge:
			startActivity(new Intent(this, SetsOn.class));
			finish();
			return true;
		default:
			return super.onOptionsItemSelected((MenuItem) item);
		}
	}

	// action to delete data base specific row
	public void deleteHistory(final int i) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Would you like to Delete this Round")
				.setPositiveButton("Nevermind",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

							}
						})
				.setNegativeButton("Delete",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// User cancelled the dialog
								db.delete(i);
								loadDisplay();
							}
						}).show();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		loadDisplay();
	}

	// inflating context menu
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.context, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		switch (item.getItemId()) {
		case R.id.item_delete:
			db.delete((int) info.id);
			loadDisplay();
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

}