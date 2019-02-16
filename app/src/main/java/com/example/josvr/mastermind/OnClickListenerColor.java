package com.example.josvr.mastermind;

import android.view.View;
import android.view.View.OnClickListener;

class OnClickListenerColor implements OnClickListener {
    private MasterMindPuzzle mmp;
    private int color;
    private PlayActivity playActivity;

    OnClickListenerColor(PlayActivity playActivity, MasterMindPuzzle mmp, int color) {
        this.mmp = mmp;
        this.color = color;
        this.playActivity = playActivity;
    }

    @Override
    public void onClick(View v) {
        mmp.setColor(color);
        mmp.setSelectedColNextFreeCol();
        playActivity.startPulseViewAt(mmp.getSelectedRow(), mmp.getSelectedCol());
    }
}