package com.gc.gameon;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class GameOnMain extends Activity implements OnNavigationListener {
    // necessary data holders
    private static int correctGuesses = 0, totalGuesses = 0, roundFromMain,
            firstNumber, secondNumber, randomOne, randomTwo, answer, operand,
            range, min, pause, ansOrder = 0;
    private String[] options = {"AddOn", "SubOn", "MultOn", "DivOn"};
    protected boolean land = false;
    private static TextView textView;
    private static final String DATABASE_NAME = "db";
    private ScoreOn score;
    private GameDB db;
    private ActionBar actionBar;

    // main display
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_on_main);

        // setting spinner option on action bar
        actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        modeOfPlay();

        score = new ScoreOn();
        db = new GameDB(this, DATABASE_NAME, null, 1);

        if (savedInstanceState == null) {
            // setting the spinner position from ListOn
            roundFromMain = 0;

            operand = this.getIntent().getIntExtra("op", 4);
        } else {
            textView = (TextView) findViewById(R.id.firstNumber);
            textView.setText("" + firstNumber);
            textView = (TextView) findViewById(R.id.operand);
            textView.setText(savedInstanceState.getString("operand"));
            textView = (TextView) findViewById(R.id.secondNumber);
            textView.setText("" + secondNumber);

            switch (savedInstanceState.getInt("ansOrder")) {
                case 1: // first possible order
                    textView = (TextView) findViewById(R.id.button1);
                    textView.setText("" + (answer));
                    textView = (TextView) findViewById(R.id.button2);
                    textView.setText("" + randomOne);
                    textView = (TextView) findViewById(R.id.button3);
                    textView.setText("" + randomTwo);
                    ansOrder = 1;
                    break;

                case 2:
                    textView = (TextView) findViewById(R.id.button1);
                    textView.setText("" + randomOne);
                    textView = (TextView) findViewById(R.id.button2);
                    textView.setText("" + (answer));
                    textView = (TextView) findViewById(R.id.button3);
                    textView.setText("" + randomTwo);
                    ansOrder = 2;
                    break;
                case 3:
                    textView = (TextView) findViewById(R.id.button1);
                    textView.setText("" + randomOne);
                    textView = (TextView) findViewById(R.id.button2);
                    textView.setText("" + randomTwo);
                    textView = (TextView) findViewById(R.id.button3);
                    textView.setText("" + (answer));
                    ansOrder = 3;
                    break;
            }

            land = true;
            displayScore();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        textView = (TextView) findViewById(R.id.operand);
        String test = textView.getText().toString();

        outState.putString("operand", test);
        outState.putInt("ansOrder", ansOrder);
    }

    // for different styles of play
    private void modeOfPlay() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.myspinnertext, options);
        adapter.setDropDownViewResource(R.layout.myspinner);
        actionBar.setListNavigationCallbacks(adapter, this);
    }

    // set operand
    public void setOperation(int i) {
        firstNumber = (int) (Math.random() * 10) + 1;
        secondNumber = (int) (Math.random() * 10) + 1;

        switch (i) {
            case 0:
                answer = firstNumber + secondNumber;
                textView = (TextView) findViewById(R.id.operand);
                textView.setText("+");
                min = 2;
                range = (20 - min) + 1;
                break;
            case 1:
                answer = firstNumber - secondNumber;
                textView = (TextView) findViewById(R.id.operand);
                textView.setText("-");
                min = -9;
                range = (9 - min) + 1;
                break;
            case 2:
                answer = firstNumber * secondNumber;
                textView = (TextView) findViewById(R.id.operand);
                textView.setText("*");
                min = 1;
                range = (100 - min) + 1;
                break;
            case 3:
                answer = firstNumber / secondNumber;
                textView = (TextView) findViewById(R.id.operand);
                textView.setText("/");
                min = 0;
                range = (10 - min) + 1;
                break;
        }

        mainDisplay();
        displayScore();
    }

    // specific score display for instant update
    private void displayScore() {
        textView = (TextView) findViewById(R.id.counter);
        textView.setText(correctGuesses + "/" + totalGuesses);
    }

    // setup updated display for multiple operands
    protected void mainDisplay() {
        textView = (TextView) findViewById(R.id.firstNumber);
        textView.setText("" + firstNumber);

        textView = (TextView) findViewById(R.id.secondNumber);
        textView.setText("" + secondNumber);

        randomGenerator(); // fill data fields

        // shuffle within buttons
        switch ((int) (Math.random() * 3) + 1) {
            case 1: // first possible order
                textView = (TextView) findViewById(R.id.button1);
                textView.setText("" + (answer));
                textView = (TextView) findViewById(R.id.button2);
                textView.setText("" + randomOne);
                textView = (TextView) findViewById(R.id.button3);
                textView.setText("" + randomTwo);
                ansOrder = 1;
                break;

            case 2:
                textView = (TextView) findViewById(R.id.button1);
                textView.setText("" + randomOne);
                textView = (TextView) findViewById(R.id.button2);
                textView.setText("" + (answer));
                textView = (TextView) findViewById(R.id.button3);
                textView.setText("" + randomTwo);
                ansOrder = 2;
                break;
            case 3:
                textView = (TextView) findViewById(R.id.button1);
                textView.setText("" + randomOne);
                textView = (TextView) findViewById(R.id.button2);
                textView.setText("" + randomTwo);
                textView = (TextView) findViewById(R.id.button3);
                textView.setText("" + (answer));
                ansOrder = 3;
                break;
        }
    }

    // go through and fill all data fields
    protected static void randomGenerator() {

        randomOne = (int) ((Math.random() * range) + min);
        randomTwo = (int) ((Math.random() * range) + min);

        while (randomOne == randomTwo || randomOne == (answer)) {
            randomOne = (int) ((Math.random() * range) + min);
        }

        while (randomOne == randomTwo || randomTwo == (answer)) {
            randomTwo = (int) (Math.random() * range) + min;
        }
    }

    // action to buttons response
    public void startAnswersCheck(View v) {
        textView = (TextView) v;

        // if correct
        if (textView.getText().equals(("" + (answer)))) {
            // commends to reset display
            correctGuesses++;
            totalGuesses++;
            resetAnswer();

            // little congrats toast
            Toast.makeText(this, "You got it! Great Job", Toast.LENGTH_SHORT)
                    .show();

            MusicOn(1);

            // record play
            score.setCorrect(correctGuesses);
            score.setTries(totalGuesses);

        } else // if incorrect
        {
            // re-display score
            totalGuesses++;
            displayScore();

            // wrong toast
            Toast.makeText(this, "Wrong answer,try again!", Toast.LENGTH_SHORT)
                    .show();

            // wrong sound
            MusicOn(2);

            // vibe
            VibeOn();

            // record play
            score.setTries(totalGuesses);
            score.setMisses(totalGuesses - correctGuesses);
        }
    }

    // allows to vibe according to preferences
    public void VibeOn() {
        SharedPreferences sharedPreferences = getSharedPreferences("Options", 0);
        if (sharedPreferences.getInt("Vibration Options", 1) == 1) {
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(500);
        }
    }

    // play music according to preferences
    public void MusicOn(int option) {
        SharedPreferences sharedPreferences = getSharedPreferences("Options", 0);
        if (sharedPreferences.getInt("Music Options", 1) == 1) {
            switch (option) {
                case 1:
                    // correct sound
                    MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.ding);
                    mediaPlayer.start();
                    break;
                case 2:
                    mediaPlayer = MediaPlayer.create(this, R.raw.uhoh);
                    mediaPlayer.start();
                    break;
                case 3:
                    mediaPlayer = MediaPlayer.create(this, R.raw.sneeze);
                    mediaPlayer.start();
                    break;
            }
        }
    }

    // reset answer format for switch in operands in the low level
    public void resetAnswer() {
        textView = (TextView) findViewById(R.id.operand);
        String test = textView.getText().toString();
        ansOrder = 0;

        switch (test) {
            case "+":
                setOperation(0);
                break;
            case "-":
                setOperation(1);
                break;
            case "*":
                setOperation(2);
                break;
            case "/":
                setOperation(3);
                break;
        }
    }

    // action to reset score
    public void resetButton(View v) {
        if (totalGuesses > 0) {
            totalGuesses = 0;
            correctGuesses = 0;
            MusicOn(3);
            displayScore();
            db.addRecord(score);
            score.newRounds(this);
        }
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

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        if (operand > 3)
            operand = pause;

        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

        // TODO Auto-generated method stub
        textView = (TextView) findViewById(R.id.operand);
        String test = (String) textView.getText();

        switch (test) {
            case "+":
                pause = 0;
                break;
            case "-":
                pause = 1;
                break;
            case "*":
                pause = 2;
                break;
            case "/":
                pause = 3;
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        // TODO Auto-generated method stub
        if (roundFromMain == 0) {
            itemPosition = operand;
            roundFromMain++;
            Toast.makeText(this, options[itemPosition], Toast.LENGTH_SHORT).show();
        }

        // error handling for index over 3
        if (itemPosition > 3 || itemPosition < 0) {
            itemPosition = operand;
        }

        // safeguard from drawing new numbers for problem
        if (land == false) {
            switch (itemPosition) {
                case 0:
                    setOperation(itemPosition);
                    land = false;
                    return true;
                case 1:
                    setOperation(itemPosition);
                    land = false;
                    return true;
                case 2:
                    setOperation(itemPosition);
                    land = false;
                    return true;
                case 3:
                    setOperation(itemPosition);
                    land = false;
                    return true;
                default:
                    return false;
            }
        }else{
            land = false;
            return false;
        }
    }
}

