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
        int currentColor = mmp.getColor("guess", mmp.getSelectedCol());
        if (color == currentColor) mmp.setColor(playActivity.getResources().getColor(R.color.gray));
        else {
            mmp.setColor(color);
            mmp.setSelectedColNextFreeCol();
            playActivity.startPulseViewAt(mmp.getSelectedRow(), mmp.getSelectedCol());
        }
    }
}