package com.hilde.schnitze;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragmentCompat;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class SettingsFragmentAdd extends Fragment {

    //DataManager mydata;
    TextView tv;
    //add here the TextInput
    private Button addButton;
    //Button add_button;
    //String ui_food_name;

    //Button deleteButton;
    Context context;
    TableLayout tbl;

    SharedPreferences sharedPreferences;
    EditTextPreference etp;
    static DBHelper db;
    ArrayList<String> array_list;

    private final Object sync = new Object();

    // TODO: cleanup and solve the misleading names in menu
    // TODO: refactor me


    public SettingsFragmentAdd(Context context){
        this.context = context;
    }


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.db = new DBHelper(this.context);

        //
        db.deleteFoodID(70);
        //


        array_list  = new ArrayList<String>();
        array_list = db.getAllData();

        tbl = (TableLayout)view.findViewById(R.id.foodtable);



        //array_list.forEach((n) -> Log.i(("DEBUG", n));
        for (String food : array_list){
            TableRow newRow = new TableRow(context);
            newRow.setBackgroundColor(170);
            newRow.setPadding(5,0,0,0);

            newRow.addView(this.addText(food, context));
            tbl.addView(newRow);
            Log.d("DATA","add: "+food);
        }

    }

    public TableRow addRow(Context ctx){
        TableRow newRow = new TableRow(ctx);
        //newRow.setBackgroundColor(234);

        return newRow;
    }

    public TextView addText(String text, Context ctx){
        TextView tv = new TextView(ctx);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
        tv.setText(text);
        tv.setOnClickListener(addTextOnClickListener);

        // database id : FoodName
        this.db.findFoodAndPrint(text);


        return tv;
    }

    // create a specific Text OnClickListener
    public final View.OnClickListener addTextOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            TextView tv = (TextView) v;
            final String foodname = tv.getText().toString();
            SettingsFragmentAdd.db.findFoodAndPrint(foodname);
            Toast.makeText(context, "Try Deleting: " + foodname, Toast.LENGTH_SHORT).show();

            // Dialog Box
            DeleteDialog dialog = new DeleteDialog(context, foodname);
            //dialog.setDialogQuestion("Are you sure to delete:"+ foodname +"?");
            dialog.setDialogResult(new DeleteDialog.OnMyDialogResult() {
                public void finish(String result, Bundle communcation) {
                    // now you can use the 'result' on your activity
                    Log.i("DATA", "foobar: " + result);

                    // ...
                    //String foodname = communcation.getString("DIALOG_FOODNAME", "");
                    //boolean delete = communcation.getBoolean("DIALOG_DELETE", false);
                    //Log.i("DATA", "delete: " + delete);
                    if (Boolean.valueOf(result) == true) {
                        //db.findFoodname(foodname);
                        db.deleteFoodname(foodname);
                        Toast.makeText(context, "Success with Deleting: " + foodname, Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(context, "Abort Deleting: " + foodname, Toast.LENGTH_LONG).show();
                    }
                }
            });
            dialog.show();

            Log.i("DATA", "ergebnisse: " + dialog.delete);






            /*
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setItems(new String[]{foodname},dialogClickListener);
            builder.setMessage("Are you sure to delete:"+ foodname +"?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
            */
        }
    };

    // Dialog Box Listener
    public DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicke
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    };




    /*
    private View.OnClickListener addButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addButtonClicked();
        }
    };

    private void addButtonClicked() {
        ui_food_name = mydata.getRandomFoodName();
        tv.setText(ui_food_name);

    }
     */
}