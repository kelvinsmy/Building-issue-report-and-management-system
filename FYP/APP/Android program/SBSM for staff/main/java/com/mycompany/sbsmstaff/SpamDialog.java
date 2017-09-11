package com.mycompany.sbsmstaff;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class SpamDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view= inflater.inflate(R.layout.fragment_spam_dialog,null);
        builder.setView(view);
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){

            }
        });
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){

            }
        });


        Dialog dialog= builder.create();
        return dialog;
    }
}