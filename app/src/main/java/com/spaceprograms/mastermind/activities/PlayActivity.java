package com.spaceprograms.mastermind.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.spaceprograms.mastermind.R;
import com.spaceprograms.mastermind.dialogs.LossDialogFragment;
import com.spaceprograms.mastermind.dialogs.VictoryDialogFragment;
import com.spaceprograms.mastermind.logic.MasterMindPuzzle;
import com.spaceprograms.mastermind.logic.OnClickListenerClear;
import com.spaceprograms.mastermind.logic.OnClickListenerColor;
import com.spaceprograms.mastermind.logic.OnClickListenerGuess;
import com.spaceprograms.mastermind.logic.OnClickListenerSubmit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class PlayActivity extends AppCompatActivity {

    private ObjectAnimator currentAnimation = null;
    private MasterMindPuzzle mmp;
    private ImageView[][] guesses;
    private ImageView[][] feedback;
    private final int[] colors = new int[] {R.color.black, R.color.white, R.color.red, R.color.blue, R.color.green, R.color.yellow, R.color.orange, R.color.pink};

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

        if(li == null){
            return;
        }
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

        LinearLayout colorButtonsLayout = findViewById(R.id.colorButtons);
        List<Button> colorButtons = new ArrayList<>();
        LayoutInflater li = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (li == null) {
            return;
        }

        int colors = getApplicationContext().getResources().getInteger(R.integer.maxColors);
        int weightSum = getApplicationContext().getResources().getInteger(R.integer.colorButtonWeightSumStandard);

        if (colors >= weightSum){
            colorButtonsLayout.setWeightSum(colors);
            for (int i = 0; i < colors; i++) {
                addColorButton(li, colorButtonsLayout, colorButtons);
            }
        } else if (colors % 2 == 0) {
            colorButtonsLayout.setWeightSum(weightSum);
            for (int i = 0; i < weightSum; i++) {
               if (i < (weightSum - colors) / 2 || i > colors){
                   addColorButtonFiller(li, colorButtonsLayout);
               } else {
                   addColorButton(li, colorButtonsLayout, colorButtons);
               }
            }
        } else {
            if (weightSum % 2 == 0) {
                colorButtonsLayout.setWeightSum(++weightSum);
            }
            for (int i = 0; i < weightSum; i++) {
                if (i < (weightSum - colors) / 2 || i > colors){
                    addColorButtonFiller(li, colorButtonsLayout);
                } else {
                    addColorButton(li, colorButtonsLayout, colorButtons);
                }
            }
        }
    }

    /**
     * This method adds fillers for the color button layout.
     *
     * @param li LayoutInflater to be used
     * @param colorButtonsLayout LinearLayout to add the views to
     */
    private void addColorButtonFiller(LayoutInflater li, LinearLayout colorButtonsLayout){
        colorButtonsLayout.addView(li.inflate(R.layout.color_button_filler, colorButtonsLayout, false));
    }

    /**
     * This method adds color buttons dynamically.
     *
     * @param li LayoutInflater to be used
     * @param colorButtonsLayout LinearLayout to add the buttons to
     * @param colorButtons List of color buttons to access individual buttons at a later point.
     */
    private void addColorButton(LayoutInflater li, LinearLayout colorButtonsLayout, List<Button> colorButtons) {
        Button colorButton = (Button) li.inflate(R.layout.color_button, colorButtonsLayout, false);
        colorButton.setOnClickListener(new OnClickListenerColor(this, mmp, ContextCompat.getColor(getApplicationContext(), this.colors[colorButtons.size()])));

        GradientDrawable background = (GradientDrawable) ContextCompat.getDrawable(getApplicationContext(), R.drawable.color_button);
        if (background != null){
            ((GradientDrawable) background.mutate()).setColor(ContextCompat.getColor(getApplicationContext(), this.colors[colorButtons.size()]));
        }
        colorButton.setBackground(background);

        colorButtons.add(colorButton);
        colorButtonsLayout.addView(colorButton);
    }


    /**
     * Updates the color of the guess array at {@code row},{@code col} according to the color array.
     *
     * @param row row
     * @param col col
     */
    public void updateColor(int row, int col) {
        guesses[row][col].getDrawable().mutate().setTint(mmp.getColor("guess", col));
    }

    /**
     * Updates the color of the guess array at {@code row} according to the color array.
     *
     * @param row row
     */
    public void updateColor(int row) {
        IntStream.range(0, guesses[row].length).forEach(s -> updateColor(row, s));
    }

    /**
     * Updates the color of the feedback array at the selected row according to the color array.
     */
    public void updateFeedback() {
        int selectedRow = mmp.getSelectedRow();
        IntStream.range(0, feedback[selectedRow].length).forEach(s -> feedback[selectedRow][s].getDrawable().mutate().setTint(mmp.getColor("feedback", s)));
    }

    /**
     * This method makes the given view pulsate.
     *
     * @param v view
     */
    public void startPulse(final View v) {
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
    public void startPulseViewAt(int row, int col) {
        startPulse(guesses[row][col]);
    }

    public void displayVictory(){
        FragmentManager fm = getSupportFragmentManager();
        VictoryDialogFragment victoryDialog = VictoryDialogFragment.newInstance(mmp.getCode());
        victoryDialog.setContext(getApplicationContext());
        victoryDialog.show(fm, "fragment_victory");
    }

    public void displayLoss(){
        FragmentManager fm = getSupportFragmentManager();
        LossDialogFragment lossDialog = LossDialogFragment.newInstance(mmp.getCode());
        lossDialog.setContext(getApplicationContext());
        lossDialog.show(fm, "fragment_loss");
    }
}
