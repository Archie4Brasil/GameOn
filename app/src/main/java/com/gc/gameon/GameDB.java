package com.gc.gameon;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class GameDB extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "db";
	public static final String TABLE_NAME = "score";
	public static final String TIME = "time";
	public static final String ROUNDS = "rounds";
	public static final String TRIES = "tries";
	public static final String CORRECT = "correct";
	public static final String MISSES = "misses";
	public static final String PERCENTAGE = "percentage";
	public static final String ID = "_id";

	SQLiteDatabase db;

	public GameDB(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DATABASE_NAME, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + TIME + " REAL, "
				+ ROUNDS + " REAL, " + TRIES + " REAL, " + CORRECT + " REAL, "
				+ MISSES + " REAL, " + PERCENTAGE + " REAL);");

	}

	public void addRecord(ScoreOn score) {
		db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(TIME, System.currentTimeMillis());
		values.put(ROUNDS, score.getRounds());
		values.put(TRIES, score.getTries());
		values.put(CORRECT, score.getCorrect());
		values.put(MISSES, score.getMisses());
		values.put(PERCENTAGE, score.getPercentage());

		db.insert(TABLE_NAME, ROUNDS, values);
		db.close();

	}

	public void insert(ContentValues cv) {
	}

	public void delete(int id) {
		this.getWritableDatabase().delete(TABLE_NAME, "_id = " + id, null);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		android.util.Log.w("Score",
				"Upgrading database, which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS score");
		onCreate(db);
	}

	public void deleteTable() {
		// TODO Auto-generated method stub
		this.getWritableDatabase().delete(TABLE_NAME, null, null);
	}

}
