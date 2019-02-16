package com.example.josvr.mastermind;

import android.view.View;
import android.view.View.OnClickListener;

class OnClickListenerClear implements OnClickListener {
    private MasterMindPuzzle mmp;
    private PlayActivity playActivity;

    OnClickListenerClear(MasterMindPuzzle mmp, PlayActivity playActivity) {
        this.mmp = mmp;
        this.playActivity = playActivity;
    }

    @Override
    public void onClick(View v) {
        mmp.resetRow();
        playActivity.updateColor(mmp.getSelectedRow());
        mmp.setSelectedCol(0);
        playActivity.startPulse(mmp.getSelectedRow(), 0);
    }
}