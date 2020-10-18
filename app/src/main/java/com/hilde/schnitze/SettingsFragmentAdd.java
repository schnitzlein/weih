package com.hilde.schnitze;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import java.util.ArrayList;


public class SettingsFragmentAdd extends Fragment {

    DataManager mydata;
    TextView tv;
    //add here the TextInput
    private Button addButton;
    Button add_button;
    String ui_food_name;
    Context context;
    TableLayout tbl;

    SharedPreferences sharedPreferences;
    EditTextPreference etp;
    DBHelper db;
    ArrayList<String> array_list;


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
        array_list  = new ArrayList<String>();
        array_list = db.getAllData();


        tbl = (TableLayout)view.findViewById(R.id.foodtable);
        // delcare a new row
        //TableRow newRow = new TableRow(context);
        // add views to the row
        //newRow.addView(this.addText("foobar", context));
        // add the row to the table layout
        //tbl.addView(newRow);


        //array_list.forEach((n) -> Log.i(("DEBUG", n));
        for (String food : array_list){
            TableRow newRow = new TableRow(context);
            newRow.setBackgroundColor(170);
            newRow.setPadding(5,0,0,0);

            newRow.addView(this.addText(food, context));
            tbl.addView(newRow);
        }
    }

    public TableRow addRow(Context ctx){
        TableRow newRow = new TableRow(context);
        //newRow.setBackgroundColor(234);

        return newRow;
    }

    public TextView addText(String text, Context ctx){
        TextView tv = new TextView(ctx);
        tv.setText(text);
        //tv = (TextView)view.findViewById(R.id.textview_second);
        //tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);

        return tv;
    }




    private View.OnClickListener addButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addButtonClicked();
        }
    };

    private void addButtonClicked() {
        ui_food_name = mydata.getRandomFoodName();
        tv.setText(ui_food_name);
        //todo add here nav graph back or something dialog ok added toast
    }
}