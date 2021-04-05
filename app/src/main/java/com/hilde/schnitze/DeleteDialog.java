package com.hilde.schnitze;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

// my Custom Dialog
public class DeleteDialog extends Dialog {
    private String foodname;
    final boolean delete = false;
    public TextView tvMessage;
    OnMyDialogResult mDialogResult; // the callback
    Bundle dialogBundle;

    Context context;

    public DeleteDialog(Context context, String name) {
        super(context);
        this.context = context;
        this.foodname = name;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycustomdialog);
        Button buttonno = (Button) findViewById(R.id.dialogButtonNegative);
        buttonno.setOnClickListener(new NOKListener());

        Button buttonyes = (Button) findViewById(R.id.dialogButtonPositive);
        buttonyes.setOnClickListener(new OKListener());
        tvMessage = (TextView) findViewById(R.id.dialogText);
        tvMessage.setText("Are you sure to delete: "+ this.foodname + "?");
        //Log.i("DATA","Data from creating Dialog: "+ this.foodname);

    }

    private class NOKListener implements android.view.View.OnClickListener {
        @Override
        public void onClick(View v) {
            if( mDialogResult != null ){
                //mDialogResult.finish(String.valueOf(tvMessage.getText()));
                //dialogBundle.putBoolean("DIALOG_DELETE", false);
                //dialogBundle.putString("DIALOG_FOODNAME", String.valueOf(tvMessage.getText()));
                mDialogResult.finish(String.valueOf(false), dialogBundle);
            }
            DeleteDialog.this.dismiss();
        }
    }

    private class OKListener implements android.view.View.OnClickListener {
        @Override
        public void onClick(View v) {
            if( mDialogResult != null ){
                //mDialogResult.finish(String.valueOf(tvMessage.getText()));
                //dialogBundle.putBoolean("DIALOG_DELETE", true);
                //dialogBundle.putString("DIALOG_FOODNAME", String.valueOf(tvMessage.getText()));
                mDialogResult.finish(String.valueOf(true), dialogBundle);
            }
            DeleteDialog.this.dismiss();
        }

    }

    public OnMyDialogResult getDialogResult(){
        return mDialogResult;
    }

    public void setDialogResult(OnMyDialogResult dialogResult){
        mDialogResult = dialogResult;
    }

    public interface OnMyDialogResult{
        void finish(String result, Bundle communication);
    }

}
