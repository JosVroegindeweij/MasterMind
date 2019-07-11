package com.spaceprograms.mastermind.logic;

import android.view.View;
import android.view.View.OnClickListener;

import com.spaceprograms.mastermind.activities.PlayActivity;

public class OnClickListenerClear implements OnClickListener {
    private MasterMindPuzzle mmp;
    private PlayActivity playActivity;

    public OnClickListenerClear(MasterMindPuzzle mmp, PlayActivity playActivity) {
        this.mmp = mmp;
        this.playActivity = playActivity;
    }

    @Override
    public void onClick(View v) {
        mmp.resetRow();
        playActivity.updateColor(mmp.getSelectedRow());
        mmp.setSelectedCol(0);
        playActivity.startPulseViewAt(mmp.getSelectedRow(), 0);
    }
}