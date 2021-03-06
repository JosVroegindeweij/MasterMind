package com.spaceprograms.mastermind.logic;

import android.view.View;
import android.view.View.OnClickListener;

import com.spaceprograms.mastermind.activities.PlayActivity;

public class OnClickListenerColor implements OnClickListener {
    private MasterMindPuzzle mmp;
    private int color;
    private PlayActivity playActivity;

    public OnClickListenerColor(PlayActivity playActivity, MasterMindPuzzle mmp, int color) {
        this.mmp = mmp;
        this.color = color;
        this.playActivity = playActivity;
    }

    @Override
    public void onClick(View v) {
        int col = mmp.getSelectedCol(), row = mmp.getSelectedRow();
        int currentColor = mmp.getColor("guess", col);
        if (color == currentColor){
            mmp.resetColor();
            playActivity.updateColor(row, col);
        } else {
            mmp.setColor(color);
            playActivity.updateColor(row, col);
            col = mmp.setSelectedColNextFreeCol();
        }
        playActivity.startPulseViewAt(row, col);
    }
}