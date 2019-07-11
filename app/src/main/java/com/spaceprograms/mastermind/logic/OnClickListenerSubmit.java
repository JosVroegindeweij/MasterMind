package com.spaceprograms.mastermind.logic;

import android.view.View;
import android.view.View.OnClickListener;

import com.spaceprograms.mastermind.activities.PlayActivity;

import java.util.stream.IntStream;

public class OnClickListenerSubmit implements OnClickListener {
    private MasterMindPuzzle mmp;
    private PlayActivity playActivity;


    public OnClickListenerSubmit(MasterMindPuzzle mmp, PlayActivity playActivity) {
        this.mmp = mmp;
        this.playActivity = playActivity;
    }

    @Override
    public void onClick(View view) {
        int totalCols = mmp.getTotalCols();
        final int selectedRow = mmp.getSelectedRow();

        if (!mmp.isComplete()) return;

        mmp.checkAttempt(selectedRow);
        playActivity.updateFeedback();

        IntStream.range(0,totalCols).forEach(i -> playActivity.updateColor(selectedRow, i));

        if (mmp.checkVictory())
            playActivity.displayVictory();
        else {
            mmp.incSelectedRow();
            if (mmp.checkLoss())
                playActivity.displayLoss();
            else {
                mmp.setSelectedCol(0);

                playActivity.startPulseViewAt(selectedRow + 1, 0);
            }
        }
    }
}
