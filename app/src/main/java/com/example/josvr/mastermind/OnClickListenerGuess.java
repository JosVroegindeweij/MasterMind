package com.example.josvr.mastermind;

import android.view.View;
import android.view.View.OnClickListener;

class OnClickListenerGuess implements OnClickListener {
    private MasterMindPuzzle mmp;
    private PlayActivity playActivity;
    private int row;
    private int col;

    OnClickListenerGuess(MasterMindPuzzle mmp, PlayActivity playActivity, int row, int col) {
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