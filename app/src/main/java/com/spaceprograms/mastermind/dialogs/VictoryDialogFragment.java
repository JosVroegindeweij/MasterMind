package com.spaceprograms.mastermind.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.spaceprograms.mastermind.R;
import com.spaceprograms.mastermind.activities.PlayActivity;

import java.util.Arrays;
import java.util.stream.IntStream;

public class VictoryDialogFragment extends DialogFragment {

    private Context context;
    private int[] code;

    public VictoryDialogFragment() {

    }

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
        code = args.getIntArray("code");

        LayoutInflater infl = LayoutInflater.from(context);
        View codeView = infl.inflate(R.layout.dialog_layout, null);
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
        alertDialogBuilder.setPositiveButton(R.string.newGame,  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (context != null) startActivity(new Intent(context, PlayActivity.class));
            }
        });
        alertDialogBuilder.setNegativeButton(R.string.backToGame, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        return alertDialogBuilder.create();
    }

    public void setContext(Context context) {
        this.context = context;
    }




}