package com.example.uberv.expressionismus;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;

import static android.content.ContentValues.TAG;


public class SaveRegexDialogFragment extends DialogFragment {
    public static final String LOG_TAG=SaveRegexDialogFragment.class.getSimpleName();

    private EditText regexNameEt;
    private String regex;

    public static SaveRegexDialogFragment newInstance(String regex){
        SaveRegexDialogFragment dialog= new SaveRegexDialogFragment();
        dialog.regex=regex;
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(LOG_TAG,"onCreateDialog()");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View layout = inflater.inflate(R.layout.dialog_save_regex,null);
        regexNameEt= (EditText) layout.findViewById(R.id.regex_name_et);
        // save in SQLiteDb
        regexNameEt.setHint("Choose Regex Name");

        builder.setView(layout)
                // Add action buttons
                .setPositiveButton(R.string.save_btn_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                        Log.d(LOG_TAG,regexNameEt.getText().toString());
                        // TODO check naming(replace " " with _
                        //TODO save file
                    }
                })
                .setNegativeButton(R.string.cancel_btn_text, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SaveRegexDialogFragment.this.getDialog().cancel();
                    }
                });

        // Pass null as the parent view because its going in the dialog layout

        return builder.create();
    }

}
