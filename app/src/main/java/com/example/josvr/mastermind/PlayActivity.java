package com.example.josvr.mastermind;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.stream.IntStream;

public class PlayActivity extends AppCompatActivity {

    private ObjectAnimator currentAnimation = null;
    private MasterMindPuzzle mmp;
    private ImageView[][] guesses;
    private ImageView[][] feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_play);

        initiateGame();
    }

    /**
     * This method initiates what's needed to play the actual game, like the logic, the buttons and guess circles.
     */
    private void initiateGame() {
        mmp = new MasterMindPuzzle(getApplicationContext());
        int totalRows = mmp.getTotalRows(), totalCols = mmp.getTotalCols();
        guesses = new ImageView[totalRows][totalCols];
        feedback = new ImageView[totalRows][totalCols];

        initiatePlayingField();
        initiateButtons();

        startPulse(guesses[mmp.getSelectedRow()][mmp.getSelectedCol()]);
    }

    /**
     * This method will inflate the guess layout and add it to the playing field. Also adds OnClickListeners.
     */
    private void initiatePlayingField() {
        LayoutInflater li = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup vgGuess = findViewById(R.id.guesses);

        for (int i = 0; i < 10; i++) {
            LinearLayout guess = (LinearLayout) li.inflate(R.layout.guess, vgGuess, false);

            guesses[i][0] = guess.findViewById(R.id.guess0);
            guesses[i][1] = guess.findViewById(R.id.guess1);
            guesses[i][2] = guess.findViewById(R.id.guess2);
            guesses[i][3] = guess.findViewById(R.id.guess3);

            feedback[i][0] = guess.findViewById(R.id.feedback0);
            feedback[i][1] = guess.findViewById(R.id.feedback1);
            feedback[i][2] = guess.findViewById(R.id.feedback2);
            feedback[i][3] = guess.findViewById(R.id.feedback3);

            for (int j = 0; j < guesses[0].length; j++) {
                guesses[i][j].setOnClickListener(new OnClickListenerGuess(mmp, this, i, j));
            }

            vgGuess.addView(guess);
        }
    }

    /**
     * This method sets up the buttons with OnClickListeners.
     */
    private void initiateButtons() {
        findViewById(R.id.submit).setOnClickListener(new OnClickListenerSubmit(mmp, this));
        findViewById(R.id.clear).setOnClickListener(new OnClickListenerClear(mmp, this));

        findViewById(R.id.buttonBlack).setOnClickListener(new OnClickListenerColor(this, mmp, ContextCompat.getColor(getApplicationContext(), R.color.black)));
        findViewById(R.id.buttonWhite).setOnClickListener(new OnClickListenerColor(this, mmp, ContextCompat.getColor(getApplicationContext(), R.color.white)));
        findViewById(R.id.buttonBlue).setOnClickListener(new OnClickListenerColor(this, mmp, ContextCompat.getColor(getApplicationContext(), R.color.blue)));
        findViewById(R.id.buttonGreen).setOnClickListener(new OnClickListenerColor(this, mmp, ContextCompat.getColor(getApplicationContext(), R.color.green)));
        findViewById(R.id.buttonYellow).setOnClickListener(new OnClickListenerColor(this, mmp, ContextCompat.getColor(getApplicationContext(), R.color.yellow)));
        findViewById(R.id.buttonRed).setOnClickListener(new OnClickListenerColor(this, mmp, ContextCompat.getColor(getApplicationContext(), R.color.red)));
    }

    /**
     * Updates the color of the guess array at {@code row},{@code col} according to the color array.
     *
     * @param row row
     * @param col col
     */
    void updateColor(int row, int col) {
        guesses[row][col].getDrawable().mutate().setTint(mmp.getColor("guess", col));
    }

    /**
     * Updates the color of the guess array at {@code row} according to the color array.
     *
     * @param row row
     */
    void updateColor(int row) {
        IntStream.range(0, guesses[row].length).forEach(s -> updateColor(row, s));
    }

    /**
     * Updates the color of the feedback array at the selected row according to the color array.
     */
    void updateFeedback() {
        int selectedRow = mmp.getSelectedRow();
        IntStream.range(0, feedback[selectedRow].length).forEach(s -> feedback[selectedRow][s].getDrawable().mutate().setTint(mmp.getColor("feedback", s)));
    }

    /**
     * This method makes the given view pulsate.
     *
     * @param v view
     */
    void startPulse(final View v) {
        if (currentAnimation != null) {
            currentAnimation.end();
        }
        ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                v,
                PropertyValuesHolder.ofFloat("scaleX", 1f, 0.8f),
                PropertyValuesHolder.ofFloat("scaleY", 1f, 0.8f));
        scaleDown.setDuration(310);

        scaleDown.setRepeatCount(ObjectAnimator.INFINITE);
        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);
        scaleDown.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                v.setScaleX(1);
                v.setScaleY(1);
            }
        });
        scaleDown.start();

        currentAnimation = scaleDown;
    }

    /**
     * Makes the guess at position {@code row}, {@code col} pulsate.
     *
     * @param row row
     * @param col col
     */
    void startPulseViewAt(int row, int col) {
        startPulse(guesses[row][col]);
    }

    void displayVictory(){

    }

    void displayLoss(){

    }
}
