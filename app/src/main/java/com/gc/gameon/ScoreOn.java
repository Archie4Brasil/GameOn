package com.gc.gameon;

import android.content.Context;
import android.database.sqlite.SQLiteCursor;

public class ScoreOn {
	private int rounds, tries, correct, misses;
	private static final String DATABASE_NAME = "db";

	public ScoreOn() {
		this.correct = 0;
		this.rounds = 1;
		this.misses = 0;
		this.tries = 0;
	}

	public void newRounds(Context context) {
		GameDB db = new GameDB(context, DATABASE_NAME, null, 1);
		SQLiteCursor constantsCursor = (SQLiteCursor) db.getReadableDatabase()
				.rawQuery(
						"SELECT " + GameDB.ROUNDS + " FROM "
								+ GameDB.TABLE_NAME + " ORDER BY "
								+ GameDB.ROUNDS + " DESC", null);
		if (constantsCursor.equals(null)) {
			rounds = 1;

		} else {
			constantsCursor.moveToFirst();
			rounds = constantsCursor.getInt(0) + 1;
		}
		correct = tries = misses = 0;

	}

	public String getRounds() {
		return Integer.toString(rounds);
	}

	public String getTries() {
		return Integer.toString(tries);
	}

	public void setTries(int plays) {
		this.tries = plays;
	}

	public String getCorrect() {
		return Integer.toString(correct);
	}

	public void setCorrect(int correct) {
		this.correct = correct;
	}

	public String getMisses() {
		return Integer.toString(misses);
	}

	public void setMisses(int misses) {
		this.misses = misses;
	}

	public String getPercentage() {
		double correctD = (double) correct;
		double triesD = (double) tries;
		return Integer.toString((int) ((correctD / triesD) * 100));
	}

}
