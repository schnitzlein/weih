package com.hilde.schnitze;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class DataManager {

    private int FOOD_LIST_SIZE = 10;
    String essen_heute;
    List<String> food_list = new ArrayList<String>();

    Context context;

    DBHelper dbh;

    SharedPreferences sharedPreferences;

    DataManager(Context context) {
        this.context = context;
        dbh = new DBHelper(this.context);

        // TODO: put init of data in sub function if constructor is used twice in classes
        sharedPreferences = context.getSharedPreferences("Food", Context.MODE_PRIVATE);
        boolean ret2 = dbh.hasDatabaseData(this.context);
        boolean ret = dbh.doesDatabaseExist(this.context);
        Log.i("DATA", ""+ret);

        boolean has_db_data = dbh.checkOrCreateDefaultData(this.context, this.setupFoodList());

        // read db data to SharedPreferences, if this shared data is not empty
        if (isCurrentFoodNameListEmpty()){
            List<String> my_list = dbh.getAllData();
            saveCurrentFoodList(my_list);
        }

        if (isCurrentFoodNameEmpty()){
            saveCurrentFood( getRandomFoodName() );
        }


    }

    // current Food single String
    public void saveCurrentFood(String current_foodname) {
        sharedPreferences = context.getSharedPreferences("Food", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("FoodName", current_foodname);
        editor.commit();
        Log.i("DATA",current_foodname);
    }

    public String loadCurrentFood() {
        sharedPreferences = context.getSharedPreferences("Food", Context.MODE_PRIVATE);
        return sharedPreferences.getString("FoodName", "");
    }

    public boolean isCurrentFoodNameEmpty() {
        sharedPreferences = context.getSharedPreferences("Food", Context.MODE_PRIVATE);
        boolean isFoodEmpty = sharedPreferences.getString("FoodName", "").isEmpty();
        return isFoodEmpty;
    }

    // week Food List of String
    public void saveCurrentFoodList(List<String> current_foodname_list) {
        Set<String> foodset = new HashSet<>(current_foodname_list); // framework depended cast, potential app crash here
        sharedPreferences = context.getSharedPreferences("Food", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("FoodNameList",foodset);
        editor.commit();
        for (Iterator it = foodset.iterator(); it.hasNext();) {
            Log.i("DATA",it.next().toString());
        }
    }

    public List<String> loadCurrentFoodList() {
        sharedPreferences = context.getSharedPreferences("Food", Context.MODE_PRIVATE);
        Set<String> foodset = sharedPreferences.getStringSet("FoodNameList", null);
        List<String> targetList = new ArrayList<>(foodset);
        return targetList;
    }

    public boolean isCurrentFoodNameListEmpty() {
        sharedPreferences = context.getSharedPreferences("Food", Context.MODE_PRIVATE);
        Set<String> foodset = sharedPreferences.getStringSet("FoodNameList", null);
        boolean isFoodEmpty = foodset.isEmpty();
        return isFoodEmpty;
    }


    public String getRandomFoodName(){
        if (food_list.isEmpty()){
            food_list = setupFoodList();
        }
        essen_heute = food_list.get(this.getMyRandom());
        return essen_heute;
    }

    // TODO: add this sharedPreferences.registerOnSharedPreferenceChangeListener(); to react on change FOOD_LIST_SIZE
    public int getMyRandom(){
        Random r = new Random();
        int rand_number = r.nextInt(FOOD_LIST_SIZE);
        return rand_number;
    }

    public int getMyRandom(int random_range){
        Random r = new Random();
        int rand_number = r.nextInt(random_range);
        return rand_number;
    }

    public List<String> setupFoodList() {
        List<String> v = new ArrayList<String>();
        v.add("Mediterrane Nudelsuppe");
        v.add("Nudelsuppe");
        v.add("Cannelloni");
        v.add("Lasagne");
        v.add("Gemüselasagne");
        v.add("Nudeln mit Brokkoli");
        v.add("Spaghetti Carbonara");
        v.add("Nudelsalat");
        v.add("Nudelauflauf");
        v.add("Nudeln mit Tomatensoße");
        return v;
    }

    // close sql connection, keeps android system clean
    public void closeDB() {
        dbh.closeDatabaseConnection();
    }

}