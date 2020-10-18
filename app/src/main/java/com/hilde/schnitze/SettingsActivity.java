package com.hilde.schnitze;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Map;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.Preference.OnPreferenceClickListener;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;


public class SettingsActivity extends AppCompatActivity {

    Context context = this;
    //TODO: Essen hinzufügen - ok clean classes and rename/refactor
    //TODO: Essen anzeigen - in progress
    //TODO: Essen löschen
    //TODO: Essen alles_löschen
    //TODO: Bild hinzufügen in der mitte mit Teller und Messer Gabel
    //TODO: after add in DB, change the db entries Number, (also for the getrandomnumber)
    //TODO: after remove in DB, change the db entries number, ...
    //TODO: check schnellen Zwischenspeicher, wenn vorhanden tue nix, ansonsten erstelle
    //TODO: überlege ob die zwei/drei DB Objekte wirklich notwendig sind.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment(context))
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //View view = this.findViewById(R.layout.settings_activity);
        //schliessen = view.findViewById(R.id.button_schliessen);
        //schliessen.setOnClickListener(schliessenButtonOnClickListener);
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.finish();
        return true;
        //return super.onSupportNavigateUp();
    }

    public static class SettingsFragment extends PreferenceFragmentCompat  {
        SharedPreferences sharedPreferences;
        EditTextPreference etp;
        Context context;
        DBHelper db;
        Button add_button;


        public SettingsFragment(Context context){
            this.context = context;
            this.db = new DBHelper(this.context);
        }

        // own listener
        private OnPreferenceClickListener myonClickListener = new OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                onButtonClicked();
                return true;
            }
        };

        private void onButtonClicked() {
            //ui_food_name = mydata.getRandomFoodName();
            //tv.setText(ui_food_name);
            Log.i("SETTINGS", "content etp: "+etp.getText());
        }

        private OnPreferenceChangeListener onSharedListener = new OnPreferenceChangeListener () {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Log.i("SETTINGS", "changed value: "+newValue.toString());
                onPrefContentChanged(newValue);
                return true;
            }
        };

        private void onPrefContentChanged(Object newValue) {
            Log.i("SETTINGS", "new content: "+newValue.toString());

            if (db.findFood(newValue.toString()) == true) {
                Toast.makeText(this.context,"This item is already in database.",10).show();
            } else {
                db.addFood(newValue.toString());
                Toast.makeText(this.context,"Item is added to database.",5).show();
            }


            if (etp.getPreferenceDataStore() != null){

                Log.i("SETTINGS", "datastore_name: "+etp.getPreferenceDataStore());
            }

            //etp.getExtras();
        }
        // own listener

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity()); // connect to acitivy

            etp = findPreference("ui_add_foodname");
            if (etp != null){
                etp.setOnPreferenceClickListener(myonClickListener);
                etp.setOnPreferenceChangeListener(onSharedListener);
            }





            Map<String, ?> foobar = sharedPreferences.getAll();
            Log.i("SETTINGS", ""+foobar.size());

            for (Map.Entry<String, ?> entry : foobar.entrySet()) {
                if (!entry.getKey().isEmpty() ){
                    Log.i("SETTINGS",entry.getKey());
                }
            }

            onSharedPreferenceChanged(sharedPreferences, getString(R.string.fragment_add));
        }

        //@Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            Preference preference = findPreference(key);
            if (preference instanceof EditTextPreference) {
                EditTextPreference etp = (EditTextPreference) preference;
                Log.i("SETTINGS", ""+etp.getText());
            } else {
                Log.i("SETTINGS", "Event onSharedPrefChange fired");

                SharedPreferences checker = context.getSharedPreferences("ui_add_foodname", Context.MODE_PRIVATE); //edit_text_preference_1
                Map<String, ?> foobar = checker.getAll();  //getString("FoodName", "");
                for (Map.Entry<String, ?> entry : foobar.entrySet()) {
                    //if (entry.getKey().isEmpty() ){
                        Log.i("SETTINGS","key in ui_add_foodname: "+entry.getKey());
                    //}
                }
            }
        }

        /*
        public Preference.OnPreferenceChangeListener changeListener = new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                onChangeDetected(Preference preference, Object newValue);
                return false;
            }

        };

        public void onChangeDetected(Preference preference, Object newValue){
            Preference pref = (EditTextPreference) preference;
            Log.i("SETTINGS", ""+pref.getTitle());
        }
        */

        @Override
        public void onDestroy() {
            super.onDestroy();
            //db.close(); //TODO: use singelton class and getInstance
        }

    }
}