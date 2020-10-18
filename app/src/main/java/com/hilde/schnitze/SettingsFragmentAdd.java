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
import android.widget.Toast;

import java.util.ArrayList;


public class SettingsFragmentAdd extends Fragment {

    DataManager mydata;
    TextView tv;
    //add here the TextInput
    private Button addButton;
    Button add_button;
    String ui_food_name;

    Button deleteButton;
    Context context;
    TableLayout tbl;

    SharedPreferences sharedPreferences;
    EditTextPreference etp;
    static DBHelper db;
    ArrayList<String> array_list;

    // TODO: override onFocusListener Interface with argument

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
        //deleteButton = (Button) view.findViewById(R.id.floatingActionButton);

        tbl = (TableLayout)view.findViewById(R.id.foodtable);

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
        tv.setOnClickListener(addTextOnClickListener);

        // database id : FoodName
        this.db.findFoodAndPrint(text);
        //tv = (TextView)view.findViewById(R.id.textview_second);
        //tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);

        return tv;
    }

    // create a specific Text OnClickListener
    public View.OnClickListener addTextOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){

            TextView tv = (TextView) v;
            String str = tv.getText().toString();
            Toast.makeText(context,"Hi there: "+str,Toast.LENGTH_LONG).show(); // make it to 10 secs
            SettingsFragmentAdd.db.findFoodAndPrint(str);
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