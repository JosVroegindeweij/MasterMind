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

        mmp = new MasterMindPuzzle(this);
        int totalRows = mmp.getTotalRows();
        int totalCols = mmp.getTotalCols();
        guesses = new ImageView[totalRows][totalCols];
        feedback = new ImageView[totalRows][totalCols];


        LayoutInflater
                li = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ViewGroup vgGuess = findViewById(R.id.guesses);

        for (int i = 0; i < 10; i++) {
            LinearLayout guess = (LinearLayout) li.inflate(R.layout.guess, vgGuess, false);

            ImageView guess0 = guess.findViewById(R.id.guess0);
            ImageView guess1 = guess.findViewById(R.id.guess1);
            ImageView guess2 = guess.findViewById(R.id.guess2);
            ImageView guess3 = guess.findViewById(R.id.guess3);
            ImageView feedback0 = guess.findViewById(R.id.feedback0);
            ImageView feedback1 = guess.findViewById(R.id.feedback1);
            ImageView feedback2 = guess.findViewById(R.id.feedback2);
            ImageView feedback3 = guess.findViewById(R.id.feedback3);

            guesses[i][0] = guess0;
            guesses[i][1] = guess1;
            guesses[i][2] = guess2;
            guesses[i][3] = guess3;
            feedback[i][0] = feedback0;
            feedback[i][1] = feedback1;
            feedback[i][2] = feedback2;
            feedback[i][3] = feedback3;

            guess0.setOnClickListener(new OnClickListenerGuess(mmp, this, i, 0));
            guess1.setOnClickListener(new OnClickListenerGuess(mmp, this, i, 1));
            guess2.setOnClickListener(new OnClickListenerGuess(mmp, this, i, 2));
            guess3.setOnClickListener(new OnClickListenerGuess(mmp, this, i, 3));

            vgGuess.addView(guess);
        }

        startPulse(guesses[mmp.getSelectedRow()][mmp.getSelectedCol()]);

        findViewById(R.id.submit).setOnClickListener(new OnClickListenerSubmit(mmp, this));
        findViewById(R.id.clear).setOnClickListener(new OnClickListenerClear(mmp, this));
        findViewById(R.id.buttonBlack).setOnClickListener(new OnClickListenerColor(this, mmp, getResources().getColor(R.color.black)));
        findViewById(R.id.buttonWhite).setOnClickListener(new OnClickListenerColor(this, mmp, getResources().getColor(R.color.white)));
        findViewById(R.id.buttonBlue).setOnClickListener(new OnClickListenerColor(this, mmp, getResources().getColor(R.color.blue)));
        findViewById(R.id.buttonGreen).setOnClickListener(new OnClickListenerColor(this, mmp, getResources().getColor(R.color.green)));
        findViewById(R.id.buttonYellow).setOnClickListener(new OnClickListenerColor(this, mmp, getResources().getColor(R.color.yellow)));
        findViewById(R.id.buttonRed).setOnClickListener(new OnClickListenerColor(this, mmp, getResources().getColor(R.color.red)));
    }

    void updateColor(int row, int col) {
        guesses[row][col].getDrawable().setTint(mmp.getColor("guess", col));
    }

    void updateColor(int row) {
        for (int i = 0; i < guesses[0].length; i++) {
            updateColor(row, i);
        }
    }

    void updateFeedback() {
        for (int i = 0; i < feedback[0].length; i++) {
            System.out.println("Color at "+ i + ": " + mmp.getColor("feedback",i));
            feedback[mmp.getSelectedRow()][i].getDrawable().setTint(mmp.getColor("feedback", i));
        }
    }

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

    void startPulse(int row, int col) {
        startPulse(guesses[row][col]);
    }

    void startPulseViewAt(int row, int col) {
        startPulse(guesses[row][col]);
    }
}
