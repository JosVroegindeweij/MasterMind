package com.spaceprograms.mastermind.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.spaceprograms.mastermind.R;
import com.spaceprograms.mastermind.activities.PlayActivity;

import java.util.stream.IntStream;

public class VictoryDialogFragment extends DialogFragment {

    private Context context;
    private int[] code;

    public static VictoryDialogFragment newInstance(int[] code) {
        VictoryDialogFragment vdf = new VictoryDialogFragment();
        Bundle args = new Bundle();
        args.putIntArray("code", code);
        vdf.setArguments(args);
        return vdf;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            code = args.getIntArray("code");
        }

        LayoutInflater infl = LayoutInflater.from(context);
        View codeView = infl.inflate(R.layout.dialog_endgame, null);
        ImageView[] codeArray = {
                codeView.findViewById(R.id.code0), codeView.findViewById(R.id.code1),
                codeView.findViewById(R.id.code2), codeView.findViewById(R.id.code3)};
        IntStream.range(0, codeArray.length).forEach(i -> {
            GradientDrawable d = new GradientDrawable();
            d.setShape(GradientDrawable.OVAL);
            d.setColor(code[i]);
            d.setStroke(5, getResources().getColor(R.color.gray, null));
            codeArray[i].setImageDrawable(d);
        });

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(R.string.victoryTitle);
        alertDialogBuilder.setMessage(R.string.victoryMessage);
        alertDialogBuilder.setView(codeView);
        alertDialogBuilder.setPositiveButton(R.string.newGame,  (dialog, which) -> {if (context != null) startActivity(new Intent(context, PlayActivity.class));});
        alertDialogBuilder.setNegativeButton(R.string.backToGame, (dialog, which) -> {if (context != null) startActivity(new Intent(context, PlayActivity.class));});

        return alertDialogBuilder.create();
    }

    public void setContext(Context context) {
        this.context = context;
    }




}