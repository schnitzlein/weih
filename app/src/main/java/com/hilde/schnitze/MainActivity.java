package com.hilde.schnitze;

import android.content.Context;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    String essen_heute;

    List<String> food_list = new ArrayList<String>();

    private final int FOOD_LIST_SIZE = 10;

    DataManager mydata;
    Context mycontext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */


        mydata = new DataManager(mycontext);
        mydata.food_list = mydata.setupFoodList();
        mydata.essen_heute = mydata.food_list.get(mydata.getMyRandom());

        //Log.i("SYSTEM", "Entry is: "+mydata.isCurrentFoodNameListEmpty()); // remove or fix for DB
        //Log.i("SYSTEM", "List is: "+mydata.isCurrentFoodNameListEmpty());  // remove or fix for DB
        mydata.saveCurrentFood(mydata.essen_heute);
        mydata.saveCurrentFoodList(mydata.food_list);



        //TODO: init database only onetime
        //TODO: check if database is empty always,
        //TODO: add option in settings if database is empty recreate it
        //TODO: add via option UI to add entrys in database
        //TODO: Performance/Stabilty/Ressources: onTrimMemory crash prevent: https://developer.android.com/topic/performance/memory
        //TODO: Refactor, especiallty the Class Names and the Settings Entries are obsolete, wrong, or misleading
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        //TODO: add the layout

        if (id == R.id.action_settings_add) {
            Intent intent = new Intent(this, SettingsDataActivity.class);
            startActivity(intent);
            return true;
        }

        /*
        if (id == R.id.action_settings_show){
            Intent intent = new Intent(this, SettingsActivityShow.class);
            startActivity(intent);
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mydata.closeDB();
        Log.i("SYSTEM", "Database Connection closed");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("SYSTEM", "Activity onStop called");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("SYSTEM", "Activity onStart called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("SYSTEM", "Activity onResume called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("SYSTEM", "Activity onPause called");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

}