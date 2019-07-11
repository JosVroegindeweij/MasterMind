package com.spaceprograms.mastermind.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;

import com.spaceprograms.mastermind.activities.PlayActivity;

public class LossDialogFragment extends DialogFragment {

    private EditText mEditText;
    private Context context;

    public LossDialogFragment() {

    }

    public static LossDialogFragment newInstance() {
        return new LossDialogFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Loss!");
        alertDialogBuilder.setMessage("Unlucky, you didn't guess the code!");
        alertDialogBuilder.setPositiveButton("New game",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (context != null) startActivity(new Intent(context, PlayActivity.class));
            }
        });
        alertDialogBuilder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
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