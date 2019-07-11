package com.spaceprograms.mastermind.logic;

import android.view.View;
import android.view.View.OnClickListener;

import com.spaceprograms.mastermind.activities.PlayActivity;

public class OnClickListenerGuess implements OnClickListener {
    private MasterMindPuzzle mmp;
    private PlayActivity playActivity;
    private int row;
    private int col;

    public OnClickListenerGuess(MasterMindPuzzle mmp, PlayActivity playActivity, int row, int col) {
        this.mmp = mmp;
        this.playActivity = playActivity;
        this.row = row;
        this.col = col;
    }

    @Override
    public void onClick(View v) {
        if (mmp.getSelectedRow() != row || mmp.getSelectedCol() == col) return;
        mmp.setSelectedCol(col);
        playActivity.startPulse(v);
    }
}