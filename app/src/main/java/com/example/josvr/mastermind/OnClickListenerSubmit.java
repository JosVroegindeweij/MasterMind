package com.example.josvr.mastermind;

import android.view.View;
import android.view.View.OnClickListener;


class OnClickListenerSubmit implements OnClickListener {
    private MasterMindPuzzle mmp;
    private PlayActivity playActivity;


    OnClickListenerSubmit(MasterMindPuzzle mmp, PlayActivity playActivity) {
        this.mmp = mmp;
        this.playActivity = playActivity;
    }

    @Override
    public void onClick(View view) {
        int totalCols = mmp.getTotalCols();
        int selectedRow = mmp.getSelectedRow();

        if (!mmp.isComplete()) return;

        mmp.checkAttempt(selectedRow);
        playActivity.updateFeedback();

        for (int i = 0; i < totalCols; i++) playActivity.updateColor(selectedRow, i);

        if (mmp.checkVictory())
            playActivity.displayVictory();
        else {
            selectedRow = mmp.incSelectedRow();
            if (mmp.checkLoss())
                playActivity.displayLoss();
            else {
                mmp.setSelectedCol(0);

                playActivity.startPulseViewAt(selectedRow, 0);
            }
        }
    }
}
